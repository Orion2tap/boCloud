package boMVC.route;

import boMVC.Request;
import boMVC.Utility;
import boMVC.models.User;
import boMVC.BoTemplate;
import boMVC.service.MessageService;
import boMVC.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.Function;

public class Route {

    // 路由表
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/", Route::routeIndex);
        map.put("/static", Route::routeImage);
        map.put("/message", Route::routeMessage);

        return map;
    }

    // 首页
    public static byte[] routeIndex(Request request) {
        User current = RouteUser.currentUser(request);
//        String body = html("index.html");
//        body = body.replace("{username}", current.username);
        HashMap<String, Object> d = new HashMap<>();

//        d.put("username", current.username);
        User u = UserService.findByUsername(current.username);
        if (u == null) {
            u = UserService.guest();
        }
        d.put("u", u);

        String body = BoTemplate.render(d, "index.ftl");
        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String response = header + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    // 留言板
    public static byte[] routeMessage(Request request) {
        HashMap<String, String> data = null;

        // 根据请求获取新的留言
        // 字典data: {author=bobo, message=I love binbin}
        if (request.method.equals("GET")) {
            Utility.log("[method]: GET");
            data = request.query;
        } else if (request.method.equals("POST")) {
            Utility.log("[method]: POST");
            data = request.form;
        } else {
            String m = String.format("Unknown method <%s>", request.method);
            throw new RuntimeException(m);
        }
        Utility.log("[data]: %s", data);

//        // 获取已存储的所有留言
//        ArrayList<Message> messageList = MessageService.load();

        if (data != null) {
            MessageService.add(data);
//            Message m = new Message();
//            m.author = data.get("author");
//            m.message = data.get("message");
//            // 更新 messageList 并保存到文件
//            messageList.add(m);
//            MessageService.save(messageList);
        }

//        String body = html("html_basic.html");
//        body = body.replace("{messages}", MessageService.messageListHtml());
        HashMap<String, Object> d = new HashMap<>();
        d.put("messages", MessageService.load());
        String body = BoTemplate.render(d, "html_basic.ftl");

        // 拼接完整响应并返回
        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String response = header + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    // 加载图片
    public static byte[] routeImage(Request request) {
        String filepath = request.query.get("file");
//        String dir = "src/main/resources/static";
        String dir = "static";
        String path = dir + "/" + filepath;
        Utility.log("[静态资源加载]:%s", path);

        // 根据文件类型确定响应头的Content-Type字段
        String contentType = "";
        if (filepath.endsWith(".js")) {
            contentType = "application/javascript; charset=utf-8";
        } else if (filepath.endsWith("css")) {
            contentType = "text/css; charset=utf-8;";
        } else {
            contentType = " image/gif";
        }
        String header = String.format("HTTP/1.1 200 very OK\r\nContent-Type: %s;\r\n\r\n", contentType);

        byte[] body = new byte[1];
        // 读取文件
//        try (FileInputStream is = new FileInputStream(path)) {
        try (InputStream is = Utility.fileStream(path)) {
            body = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //                    part1                 body
        //  response丨--------------------丨--------------------丨
        //          0                part1.length            body.length
//        byte[] part1 = header.getBytes();
        byte[] part1 = header.getBytes(StandardCharsets.UTF_8);
        byte[] response = new byte[part1.length + body.length];
        // part1下标0, 复制到 response下标0, 复制元素个数为part1.length
        System.arraycopy(part1, 0, response, 0, part1.length);
        System.arraycopy(body, 0, response, part1.length, body.length);

        return response;
    }

    // 404页面
    public static byte[] route404(Request request) {
        String body = "<html><body><h1>404</h1><br><img src='/static?file=cat.jpg'></body></html>";
        String response = "HTTP/1.1 404 NOT OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

//---------------------------------------------------------------------------------

    // 渲染页面
    public static String html(String htmlName) {
        Utility.log("[加载页面]:%s", htmlName);
//        String dir = "src/main/resources/templates";
        String dir = "templates";
        String path = dir + "/" + htmlName;

        byte[] body = new byte[1];
        // try-with-resources语句 资源自动释放
//        try (FileInputStream is = new FileInputStream(path);) {
        try (InputStream is = Utility.fileStream(path)) {
            body = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(body);
    }

    // 返回完整响应
    public static String responseWithHeader(int code, HashMap<String, String> headerMap, String body) {
        String header = String.format("HTTP/1.1 %s\r\n", code);

        for (String key: headerMap.keySet()) {
            String value = headerMap.get(key);
            String item = String.format("%s: %s \r\n", key, value);
            header = header + item;
        }

        String response =  String.format("%s\r\n\r\n%s", header, body);
        return response;
    }

    // 重定向
    public static byte[] redirect(String url) {
        Utility.log("[重定向]:%s", url);
        String header = String.format(
                "HTTP/1.1 302 move\r\nLocation: %s\r\n\r\n",
                url
        );
        return header.getBytes(StandardCharsets.UTF_8);
    }

}
