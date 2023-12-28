### [Request类]
#### 1. 在构造函数中解析request得到method, path, requestLine, headerLines, body
```
/*
                                    rawData
    GET /search?name=gua&height=169 HTTP/1.1/r/nConnection: Keep-Alive/r/n/r/nbody
    POST /search HTTP/1.1/r/nContent-Type:application/x-www-form-urlencoded /r/n/r/nname=gua&height=169
                                       ↓
                                     parts
    ["GET /search?name=gua&height=169 HTTP/1.1/r/nConnection: Keep-Alive", "body"]
    ["POST /search HTTP/1.1/r/nContent-Type:application/x-www-form-urlencoded ", "name=gua&height=169"]
                                       ↓                                                      ↓
                                     headers                                                 body (交给parseForm解析)
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
```
#### 2. 调用headerFromRequest 解析headerLines得到headers
```
/*
                                            headerLines
    ["Host: localhost:3000", "Content-Type: text/html", "Cookie: XWINDEXGREY=0; pgv_pvid=2811078640"]
                                                ↓
               e                             kv                        k               v
    "Host: localhost:3000"      ["Host", "localhost:3000"]      "Host"          "localhost:3000"
    "Content-Type: text/html"   ["Content-Type", "text/html"]   "Content-Type"  "text/html"
               ⋮                              ⋮                         ⋮                ⋮
                                                ↓
                                             headers(其中Cookie键值对交给cookiesFromHeader解析)
    {"Host"="localhost:3000", "Content-Type"="text/html", "Cookie"="XWINDEXGREY=0; pgv_pvid=2811078640"}
 */
```
#### 3. 调用cookiesFromHeader 解析headers得到cookies
```
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
```
#### 4. 对于GET请求 调用parsePath 解析path得到path, query
```
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
```
#### 5. 对于POST请求 调用parseForm 解析body得到form
```
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
```
