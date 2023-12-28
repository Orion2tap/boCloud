package boMVC.route;

import boMVC.Request;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import boMVC.Request;
import boMVC.Utility;
import boMVC.models.Todo;
import boMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class RouteAjaxTodo {

    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/ajax/todo", boMVC.route.RouteAjaxTodo::index);
        map.put("/ajax/todo/add", boMVC.route.RouteAjaxTodo::add);
        map.put("/ajax/todo/all", boMVC.route.RouteAjaxTodo::all);
        map.put("/ajax/todo/delete", boMVC.route.RouteAjaxTodo::delete);


        return map;
    }

    public static byte[] index(Request request) {
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        String body = Route.html("ajax_todo_index.ftl");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] add(Request request) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/json");

        // 从 body 部分获取 JSON 字符串
        // 解析得到 JSON 对象 jsonForm
        //
        JSONObject jsonForm = JSON.parseObject(request.body);
        String content = jsonForm.getString("content");
        Todo todo = TodoService.add(content);
        String body = JSON.toJSONString(todo);

        Utility.log("[JSON String]: %s", request.body);
        Utility.log("[JSON Object]: %s", jsonForm);
        Utility.log("[todo content]: %s", content);
        Utility.log("[JSON response body]: %s", body);

        String response = Route.responseWithHeader(200, headers, body);
        return response.getBytes(StandardCharsets.UTF_8);

    }

    public static byte[] all(Request request) {
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/json");

        String body = JSON.toJSONString(TodoService.load());

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] delete(Request request) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/json");

        // 从 body 部分获取 JSON 字符串
        // 解析得到 JSON 对象 jsonForm
        JSONObject jsonForm = JSON.parseObject(request.body);
        Integer id = Integer.valueOf(jsonForm.getString("id"));
        TodoService.delete(id);
        String body = JSON.toJSONString(id);

        String response = Route.responseWithHeader(200, headers, body);
        return response.getBytes(StandardCharsets.UTF_8);

    }

}
