package demo.testRoute;

import demo.GET;
import demo.Service.TodoService;

public class UserRoute {
    private TodoService todoService;

    public UserRoute(TodoService todoService) {
        this.todoService = todoService;
    }
}
