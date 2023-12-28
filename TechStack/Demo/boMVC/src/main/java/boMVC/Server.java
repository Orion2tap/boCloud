package boMVC;

import boMVC.route.Route;
import boMVC.route.RouteAjaxTodo;
import boMVC.route.RouteTodo;
import boMVC.route.RouteUser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Function;

public class Server {
    public static void main(String[] args) {
        run(3344);
    }

    private static void run(int port) {
        Utility.log("[服务器启动]: http://localhost:%s", port);
        // 创建一个ServerSocket实例
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // 监听请求
            while (true) {
                // 连接成功 创建一个Socket实例s
                try (Socket socket = serverSocket.accept()) {
                    Utility.log("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
                    Utility.log("[client连接成功]");
                    // 读取客户端请求数据
                    String request = SocketOperator.socketReadAll(socket);

                    byte[] response;
                    if (request.length() > 0) {
                        Utility.log("[请求报文]:\n%s", request);
                        Request r = new Request(request);

                        // 根据path返回响应
                        response = responseForPath(r);
                        // 处理空请求
                    } else {
                        response = new byte[1];
                        Utility.log("[空请求]");
                    }
                    // 发送响应
                    SocketOperator.socketSendAll(socket, response);
                    Utility.log("[响应已发送]");
                    Utility.log("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
                }
            }
        } catch (IOException e) {
            System.out.println("exception: " + e.getMessage());
        }
    }

    // 路由字典
    private static byte[] responseForPath(Request request) {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        // 路由表合并
        map.putAll(Route.routeMap());
        map.putAll(RouteTodo.routeMap());
        map.putAll(RouteUser.routeMap());
        map.putAll(RouteAjaxTodo.routeMap());

        // 调用路由
//        Function<Request, byte[]> function = map.get(request.path);
//        if (function != null) {
//            Utility.log("[访问路径]:%s", request.path);
//            return function.apply(request);
//        } else {
//            return Route.route404();
//        }
        Function<Request, byte[]> function = map.getOrDefault(request.path, Route::route404);
        return function.apply(request);
    }
}
