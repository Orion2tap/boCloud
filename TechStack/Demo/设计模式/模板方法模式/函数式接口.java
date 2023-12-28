// 定义函数式接口 LevelAction
@FunctionalInterface
interface LevelAction {
    void perform();
}

// 定义 GameLevel 类
class GameLevel {

    // playLevel 方法接收三个 LevelAction 类型的参数
    void playLevel(LevelAction setup, LevelAction mainAction, LevelAction cleanup) {
        // 准备
        setup.perform();

        // 主要行动
        mainAction.perform();

        // 清理
        cleanup.perform();
    }
}

// 主类
public class Main {
    public static void main(String[] args) {
        GameLevel level = new GameLevel();

        // 使用 Lambda 表达式定义关卡的每个步骤
        level.playLevel(
            () -> System.out.println("关卡准备中"),
            () -> System.out.println("关卡进行中"),
            () -> System.out.println("关卡清理中")
        );
    }
}
