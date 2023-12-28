package demo.testRoute;

import demo.GET;

public class PublicRoute {

    @GET("/")
    public String index() {
        return "index";
    }


    @GET("/login")
    public String login() {
        return "login";
    }
}
