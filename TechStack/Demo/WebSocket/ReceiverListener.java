@Service
public class ReceiverListener implements MessageListener {

    // ... 其他代码 ...

    private final SimpMessagingTemplate template;

    @Autowired
    public ReceiverListener(StringRedisTemplate redisTemplate, OrDeviceDatasService deviceDatasService,
                            SimpMessagingTemplate template) {
        this.redisTemplate = redisTemplate;
        this.deviceDatasService = deviceDatasService;
        this.template = template;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String rawDatas = redisTemplate.getStringSerializer().deserialize(message.getBody());
        log.info("收到的mq消息-->" + rawDatas);

        // 发送消息到 WebSocket 订阅者
        template.convertAndSend("/topic/datas", rawDatas);
    }
}
