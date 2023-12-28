package boMVC;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Request {
    public String rawData;
    public String method;
    public String path;
    public String body;

    public HashMap<String, String> query;
    public HashMap<String, String> form;

    public HashMap<String, String> headers;
    public HashMap<String, String> cookies;

    // 解析rawRequest得到method, path, headerLines, body
    public Request(String rawRequest) {
        /*
                                        rawData
        GET /search?name=gua&height=169 HTTP/1.1/r/nConnection: Keep-Alive/r/n/r/nbody
        POST /search HTTP/1.1/r/nContent-Type:application/x-www-form-urlencoded /r/n/r/nname=gua&height=169
                                           ↓
                                         parts
        ["GET /search?name=gua&height=169 HTTP/1.1/r/nConnection: Keep-Alive", "body"]
        ["POST /search HTTP/1.1/r/nContent-Type:application/x-www-form-urlencoded ", "name=gua&height=169"]
                                           ↓                                                      ↓
                                     headersString                                               body (交给parseForm解析)
        "GET /search?name=gua&height=169 HTTP/1.1/r/nConnection: Keep-Alive"                    "body"
        "POST /search HTTP/1.1/r/nContent-Type:application/x-www-form-urlencoded "          "name=gua&height=169"
                                           ↓
                                         lines
        ["GET /search?name=gua&height=169 HTTP/1.1", "Connection: Keep-Alive"]
        ["POST /search HTTP/1.1", "Content-Type:application/x-www-form-urlencoded "]
                                           ↓                        ↓
                                       requestLine              headerLines (交给headerFromRequest解析)
        "GET /search?name=gua&height=169 HTTP/1.1"          ["Connection: Keep-Alive"]
        "POST /search HTTP/1.1"                             ["Content-Type:application/x-www-form-urlencoded "]
                                           ↓
                                      requestLineData
        ["GET", "/search?name=gua&height=169", "HTTP/1.1"]
        ["POST", "/search HTTP/1.1"]
                                           ↓
                    method                                  path (交给parsePath解析)
                    "GET"                           "/search?name=gua&height=169"
                    "POST"                          "/search HTTP/1.1"
        */
        this.rawData = rawRequest;

        String[] parts = rawRequest.split("\r\n\r\n", 2);

        String headersString = parts[0];
        this.body = parts[1];

        String[] lines = headersString.split("\r\n");

        String requestLine = lines[0];
        String[] headerLines = new String[lines.length - 1];
        System.arraycopy(lines, 1, headerLines, 0, lines.length - 1);

        String[] requestLineData = requestLine.split(" ");

        this.method = requestLineData[0];

        this.parsePath(requestLineData[1]);
        this.headersFromRequest(headerLines);
        this.cookiesFromHeader(headers);
        this.parseForm(body);
    }

    // 解析path得到path, query (适用于GET请求)
    public void parsePath(String path) {
        Integer index = path.indexOf("?");
        if (index.equals(-1)) {
            this.path = path;
            this.query = null;
        } else {
            /*
                           path
                "/search?name=gua&height=169"
                            ↓
                   path             queryString
                "/search"       "name=gua&height=169"
                            ↓
                           args
                ["name=gua", "height=169"]
                            ↓
                     e              kv                 k          v
                "name=gua"    ["name", "gua"]       "name"      "gua"
                "height=169"  ["height", "169"]     "height"    "169"
                            ↓
                          query
                {name=gua, height=169}
            */
            this.path = path.substring(0, index);
            String queryString = path.substring(index + 1);
            String[] args = queryString.split("&");

            this.query = new HashMap<>();
            for (String e : args) {
                String[] kv = e.split("=", 2);
                // 当URL有中文字符，浏览器会转换成application/x-www-form-urlencoded MIME 字符串
                // decode之前: application/x-www-form-urlencoded MIME 字符串 (中文以%XX%XX""格式显示, 空格用"+"显示)
                // decode之后: 普通字符串(能正常显示中文和空格)
                String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                String v = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                this.query.put(k, v);
            }
        }
    }

    // 解析headerLines得到headers
    public void headersFromRequest(String[] headerLines) {
        /*
                                                    headerLines
            ["Host: localhost:3000", "Content-Type: text/html", "Cookie: XWINDEXGREY=0; pgv_pvid=2811078640"]
                                                        ↓
                       e                             kv                        k               v
            "Host: localhost:3000"      ["Host", "localhost:3000"]      "Host"          "localhost:3000"
            "Content-Type: text/html"   ["Content-Type", "text/html"]   "Content-Type"  "text/html"
                       ⋮                              ⋮                         ⋮                ⋮
                                                        ↓
                                                     headers (其中Cookie键值对交给cookiesFromHeader解析)
            {"Host"="localhost:3000", "Content-Type"="text/html", "Cookie"="XWINDEXGREY=0; pgv_pvid=2811078640"}
         */
        this.headers = new HashMap<>();
        for (String e : headerLines) {
            String[] kv = e.split(": ", 2);
            String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
            String v = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
            this.headers.put(k, v);
        }
    }

    // 解析headers得到Cookies
    public void cookiesFromHeader(HashMap<String, String> headers) {
        /*
                                             headers
            {"Host"="localhost:3000", "Content-Type"="text/html", "Cookie"="XWINDEXGREY=0; pgv_pvid=2811078640; RK=LMaUks54To"}
                                               ↓
                                          cookieString
                        "XWINDEXGREY=0; pgv_pvid=2811078640; RK=LMaUks54To"
                                               ↓
                                          cookieLines
                        ["XWINDEXGREY=0", " pgv_pvid=2811078640", " RK=LMaUks54To"]
                                               ↓
               e                           k.strip()                  v
            "XWINDEXGREY=0"             "XWINDEXGREY"           "0"
            " pgv_pvid=2811078640"      "pgv_pvid"              "2811078640"
            " RK=LMaUks54To"            "RK"                    "LMaUks54To"
                                               ↓
                                            cookies
                        {XWINDEXGREY=0, pgv_pvid=2811078640, RK=LMaUks54To}
         */
        this.cookies = new HashMap<>();

        if (headers.containsKey("Cookie")) {
            String cookieString = headers.get("Cookie");
            String[] cookieLines = cookieString.split(";");
            for (String e : cookieLines) {
                String k = e.split("=")[0];
                String v = e.split("=")[1];
                this.cookies.put(k.strip(), v);
            }
        }
    }

    // 解析body得到form (适用于POST请求)
    public void parseForm(String body) {
    /*
                                      body
                              "name=gua&height=169"
                                        ↓
                                      args
                            ["name=gua", "height=169"]
                                        ↓
                        e               kv                 k          v
                    "name=gua"    ["name", "gua"]       "name"      "gua"
                    "height=169"  ["height", "169"]     "height"    "169"
                                        ↓
                                      form
                              {name=gua, height=169}
     */
        // 判断 headers 中是否存在 "Content-Type" 字段
        if (this.headers.containsKey("Content-Type")) {
            // 获得内容类型
            String content = this.headers.get("Content-Type");
            // 分类处理
            if (content.equals("application/x-www-form-urlencoded") && body.strip().length() > 0) {
                String[] args = body.split("&");

                this.form = new HashMap<>();
                for (String e : args) {
                    String[] kv = e.split("=", 2);
                    String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                    String v = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                    this.form.put(k, v);
                }
            } else {
                this.form = new HashMap<>();
            }
        } else {
            this.form = new HashMap<>();
        }
    }
}