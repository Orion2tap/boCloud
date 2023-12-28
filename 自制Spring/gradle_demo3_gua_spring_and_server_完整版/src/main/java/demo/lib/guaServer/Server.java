package demo.lib.guaServer;

import demo.lib.Common.Utility;
import demo.lib.guaSpringMVC.Dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class Server {
     Dispatcher servlet;

    public  void main(String[] args) {
        run(9000);
    }

    public Server(Dispatcher servlet) {
        this.servlet = servlet;
    }

    public  void run(int port) {
        // 监听请求
        // 获取请求数据
        // 发送响应数据
        // 我们的服务器使用 9000 端口
        // 不使用 80 的原因是 1024 以下的端口都要管理员权限才能使用
        Utility.log("服务器启动, 访问 http://localhost:%s", port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                // accept 方法会一直停留在这里等待连接
                try (Socket socket = serverSocket.accept()) {
                    // 客户端连接上来了
                    Utility.log("client 连接成功");
                    // 读取客户端请求数据
                    String request = SocketOperator.socketReadAll(socket);
                    byte[] response;
                    if (request.length() > 0) {
                        // 输出响应的数据
                        Utility.log("请求:\n%s", request);
                        // 解析 request 得到 path
                        Request r = new Request(request);

                        // 根据 path 来判断要返回什么数据
                        response = responseForPath(r);
                    } else {
                        response = new byte[1];
                        Utility.log("接受到了一个空请求");
                    }
                    SocketOperator.socketSendAll(socket, response);
                }
            }
        } catch (IOException ex) {
            System.out.println("exception: " + ex.getMessage());
        }
    }

    private  byte[] responseForPath(Request request) {
        String r =  this.servlet.doDispatch(request.path);
        return r.getBytes(StandardCharsets.UTF_8);
    }
}
