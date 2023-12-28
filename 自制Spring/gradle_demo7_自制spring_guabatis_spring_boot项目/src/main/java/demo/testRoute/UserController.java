package demo.testRoute;


import demo.Common.Utility;
import demo.Model.Session;
import demo.guaServer.GuaRequest;
import demo.guaServer.GuaResponse;
import demo.guaSpring.Controller;
import demo.guaSpringMVC.GET;
import demo.testService.UserService;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        Utility.log("class %s, 依赖注入 class %s", this.getClass(), userService.getClass());
        this.userService = userService;
    }


    @GET("/")
    public void index(GuaRequest guaRequest, GuaResponse guaResponse){
        guaResponse.setStatus(200);
        guaResponse.setHeaders("Content-Type", "text/html");
        guaResponse.setBody("<h1>index</h1>");
    }

    @GET("/tomcat")
    public void tomcat(GuaRequest guaRequest, GuaResponse guaResponse){
        guaResponse.setStatus(200);
        guaResponse.setHeaders("Content-Type", "text/html");
        guaResponse.setBody("<h1>tomcat</h1>");
    }

    @GET("/login")
    public void login(GuaRequest guaRequest, GuaResponse guaResponse) {
        guaResponse.setStatus(200);
        guaResponse.setHeaders("Content-Type", "text/html");
        guaResponse.setBody("hello gua");
    }

    @GET("/favicon.ico")
    public void icon(GuaRequest guaRequest, GuaResponse guaResponse) {
        guaResponse.setStatus(404);
        guaResponse.setHeaders("Content-Type", "text/html");
        guaResponse.setBody("");
    }

    @GET("/session")
    public void session(GuaRequest guaRequest, GuaResponse guaResponse) {
        Session session = userService.findSession(1);
        String body = session.sessionID;

        guaResponse.setStatus(200);
        guaResponse.setHeaders("Content-Type", "text/html");
        guaResponse.setBody(body);
    }
}
