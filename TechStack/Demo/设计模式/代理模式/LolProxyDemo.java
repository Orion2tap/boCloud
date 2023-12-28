import java.time.LocalTime;

public class LolProxyDemo {
    public static void main(String[] args) {
        Teemo teemo = new Teemo();
        TeemoProxy proxy = new TeemoProxy(teemo);

        // 获取当前时间
        LocalTime now = LocalTime.now();
        // 设置白天和晚上的时间界限，例如：6:00为早晨，18:00为晚上
        LocalTime morning = LocalTime.of(6, 0);
        LocalTime evening = LocalTime.of(18, 0);

        // 如果现在是白天（6:00-18:00），则启用代理控制；否则，玩家控制
        if (now.isAfter(morning) && now.isBefore(evening)) {
            // 白天：代理控制
            System.out.println("现在是白天，代理自动控制提莫。");
            proxy.setAutoPlay(true);
            proxy.useUltimateAbility();
            proxy.buyItem("纳什之牙");
        } else {
            // 晚上：玩家控制
            System.out.println("现在是晚上，玩家控制提莫。");
            proxy.useUltimateAbility();
            proxy.buyItem("魔切");
        }
    }
}
