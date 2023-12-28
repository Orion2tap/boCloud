package boMVC;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SocketOperator {
    public static String socketReadAll(Socket socket) throws IOException {
        // InputStream读取字节(byte[])
        // InputStreamReader读取字符(char)
        InputStreamReader r = new InputStreamReader(socket.getInputStream());

        int bufferSize = 1024;
        char[] data = new char[bufferSize];
        StringBuilder request = new StringBuilder();

        while(true) {
            // data         缓冲区
            // 0            数组data中将写入数据的初始偏移量
            // data.length  要读取的最大字符数
            // 返回的size    读入缓冲区的总字符数
            int size = r.read(data, 0, data.length);
            // 把缓冲区数据追加到request
            if ( size > 0) {
                request.append(data, 0, size);
            }
            Utility.log("[读取数据]: [" + size + "丨" + data.length + "]");

            // 无数据可读 跳出循环
            if (!r.ready()) {
                break;
            }
        }
        return request.toString();
    }

    public static void socketSendAll(Socket socket, byte[] response) throws IOException {
        socket.getOutputStream().write(response);
    }

}
