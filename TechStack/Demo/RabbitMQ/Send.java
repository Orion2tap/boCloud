import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ服务主机地址
        factory.setHost("localhost");

        // 创建连接
        try (Connection connection = factory.newConnection();
             // 创建通道
             Channel channel = connection.createChannel()) {

            // 声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 定义消息内容
            String message = "Hello World!";
            // 发布消息到队列
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            // 在控制台打印消息发送情况
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
