import com.rabbitmq.client.*;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ服务主机地址
        factory.setHost("localhost");
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 创建DeliverCallback实例
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // 从delivery对象获取消息内容，并转换为字符串
            String message = new String(delivery.getBody(), "UTF-8");
            // 在控制台打印接收到的消息
            System.out.println(" [x] Received '" + message + "'");
        };

        // 开始监听消息，使用deliverCallback处理接收到的消息
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
