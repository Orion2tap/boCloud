package boMVC.route;

import boMVC.Request;
import boMVC.Utility;
import boMVC.models.Session;
import boMVC.models.User;
import boMVC.models.UserRole;
import boMVC.BoTemplate;
import boMVC.service.SessionService;
import boMVC.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

public class RouteUser {
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/login", RouteUser::login);
        map.put("/register", RouteUser::register);
        map.put("/profile", RouteUser::profile);
        map.put("/admin/users", RouteUser::adminUsers);
        map.put("/admin/user/update", RouteUser::update);

        return map;
    }

    // 注册
    public static byte[] register(Request request) {
        String result = "";

        if (request.method.equals("POST")) {
            HashMap<String, String> data = request.form;
            Utility.log("[注册成功]:%s", data);
            UserService.add(data);
            result = "注册成功";
        }

//        String body = Route.html("register.html");
//        body = body.replace("{result}", result);

        HashMap<String, Object> d = new HashMap<>();
        d.put("result", result);
        String body = BoTemplate.render(d, "register.ftl");

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    // 登录
    public static byte[] login(Request request) {
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        // 获取数据
        HashMap<String, String> data = null;
        if (request.method.equals("POST")) {
            data = request.form;
        } else if (request.method.equals("GET")) {
            data = request.query;
        } else {
            String m = String.format("unknown method (%s)", request.method);
            throw new RuntimeException(m);
        }

        String loginResult = "";
        if (data != null) {
            // 验证账密
            if (UserService.validLogin(data)) {
                String username = data.get("username");
                User user = UserService.findByUsername(username);
                // 生成sessionId (token)
                String sessionId = UUID.randomUUID().toString();
                Utility.log("[获取user.Id]:%s", user.id);
                Utility.log("[生成sessionId]:%s", sessionId);
                // 保存映射 每个登录中的用户id对应一个sessionId
                SessionService.add(user.id, sessionId);
                // 响应头增加字段 "Set-Cookie: session_id=随机字符串“
                header.put("Set-Cookie", String.format("session_id=%s", sessionId));
                loginResult = "登录成功";
            } else {
                loginResult = "登录失败";
            }
        }

//        String body = Route.html("login.html");
//        body = body.replace("{loginResult}", loginResult);
        HashMap<String, Object> d = new HashMap<>();
        d.put("loginResult", loginResult);
        String body = BoTemplate.render(d, "login.ftl");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    // 个人资料
    public static byte[] profile(Request request) {
        User current = RouteUser.currentUser(request);
        if (current.role.equals(UserRole.guest)) {
            Utility.log("游客身份");
            return Route.redirect("/login");
        }

//        String body = Route.html("profile.html");
//        body = body.replace("{id}", String.valueOf(current.id));
//        body = body.replace("{username}", current.username);
//        body = body.replace("{role}", String.valueOf(current.role));
        HashMap<String, Object> d = new HashMap<>();
        d.put("u", current);
        String body = BoTemplate.render(d, "profile.ftl");

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    // 管理员后台
    public static byte[] adminUsers(Request request) {
        User current = RouteUser.currentUser(request);

        // 验证管理员权限
        if (current.role.equals(UserRole.admin)) {
//            String body = Route.html("admin.html");
//            body = body.replace("{users}", UserService.adminUsersHtml());
            HashMap<String, Object> d = new HashMap<>();
            d.put("users", UserService.load());
            String body = BoTemplate.render(d, "admin.ftl");

            HashMap<String, String> header = new HashMap<>();
            header.put("Content-Type", "text/html");

            String response = Route.responseWithHeader(200, header, body);
            return response.getBytes(StandardCharsets.UTF_8);
        } else {
            Utility.log("非管理员禁止访问");
            return Route.redirect("/login");
        }
    }

    // 管理员后台更新用户密码
    public static byte[] update(Request request) {
        HashMap<String, String> form = request.form;
        Integer id = Integer.valueOf(form.get("id"));
        String password = form.get("password");

        UserService.updatePassword(id, password);

        return Route.redirect("/admin/users");
    }

    // 查询当前用户
    public static User currentUser(Request request) {
        Utility.log("[查询当前用户中...]");
        if (request.cookies.containsKey("session_id")) {
            /*
                          --空--> 游客
              [session_id]                 --空--> 游客
                          --非空-> [Session]                            --空--> 游客
                                           --非空-> [userId] ---> [User]
                                                                       --非空-> 返回
             */
            String sessionId = request.cookies.get("session_id");
            Session s = SessionService.findBySessionId(sessionId);
            if(s == null) {
                return UserService.guest();
            }

            Integer userId = s.userId;
            Utility.log("[获取用户id]:%s", userId);
            User user = UserService.findById(userId);

            if (user == null) {
                return UserService.guest();
            } else {
                Utility.log("[当前用户]:%s", user.username);
                return user;
            }
//            return Objects.requireNonNullElseGet(user, UserService::guest);
        } else {
            return UserService.guest();
        }
    }
}
