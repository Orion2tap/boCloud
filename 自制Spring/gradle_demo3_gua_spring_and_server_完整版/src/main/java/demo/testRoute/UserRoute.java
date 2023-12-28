package demo.testRoute;

import demo.Service.UserService;
import demo.lib.Common.Utility;
import demo.lib.guaSpring.Controller;
import demo.lib.guaSpringMVC.GET;


@Controller
public class UserRoute {
    private UserService userService;

    public UserRoute(UserService userService) {
        Utility.log("class %s, 依赖注入 class %s", this.getClass(), userService.getClass());
        this.userService = userService;
    }


    @GET("/")
    public String index(){
        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String body = "<h1>index</h1>";
        String r = header + body;
        return r;
    }

    @GET("/login")
    public String login() {
        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String body = "hello gua";
        String r = header + body;
        return r;
    }

    @GET("/favicon.ico")
    public String icon() {
        String header = "HTTP/1.1 404 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String r = header;
        return r;
    }
}
