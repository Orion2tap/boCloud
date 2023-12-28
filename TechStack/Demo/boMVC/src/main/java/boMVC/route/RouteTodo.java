package boMVC.route;


import boMVC.Request;
import boMVC.Utility;
import boMVC.models.Todo;
import boMVC.models.User;
import boMVC.models.UserRole;
import boMVC.BoTemplate;
import boMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.Function;

public class RouteTodo {
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/todo", RouteTodo::index);
        map.put("/todo/add", RouteTodo::add);
        map.put("/todo/delete", RouteTodo::delete);
        map.put("/todo/edit", RouteTodo::edit);
        map.put("/todo/update", RouteTodo::update);
        map.put("/todo/complete", RouteTodo::complete);

        return map;
    }

    public static byte[] index(Request request) {
        User current = RouteUser.currentUser(request);
        if (current.role.equals(UserRole.guest)) {
            Utility.log("[游客重定向到登录页面]");
            return Route.redirect("/login");
        }

//        String body = Route.html("todo/index.html");
//        body = body.replace("{todos}", TodoService.todoListHtml());
        HashMap<String, Object> d = new HashMap<>();
        d.put("todos", TodoService.load());
        String body = BoTemplate.render(d, "todo/index.ftl");

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] add(Request request) {
        String content = request.form.get("content");
        TodoService.add(content);
        return Route.redirect("/todo");
    }

    public static byte[] delete(Request request) {
        Integer todoId = Integer.valueOf(request.query.get("id"));
        TodoService.delete(todoId);
        return Route.redirect("/todo");
    }

    public static byte[] edit(Request request) {
        Utility.log("[编辑]:%s", request.query);

        Integer todoId = Integer.valueOf(request.query.get("id"));
        Todo t = TodoService.findById(todoId);

//        String body = Route.html("todo/edit.html");
//        if (t != null) {
//            body = body.replace("{todo_id}", t.id.toString());
//            body = body.replace("{todo_content}", t.content);
//        } else {
//            body = "t is null";
//        }
        HashMap<String, Object> d = new HashMap<>();
        d.put("todo", t);
        String body = BoTemplate.render(d, "todo/edit.ftl");

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] update(Request request) {
        HashMap<String, String> form = request.form;
        Integer todoId = Integer.valueOf(form.get("id"));
        String todoContent = form.get("content");
        TodoService.update(todoId, todoContent);

        return Route.redirect("/todo");
    }

    public static byte[] complete(Request request) {
        Integer todoId = Integer.valueOf(request.query.get("id"));
        TodoService.complete(todoId);
        return Route.redirect("/todo");
    }
}
