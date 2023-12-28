// 定义抽象类 GameLevel
abstract class GameLevel {

    // 模板方法，定义了关卡的执行顺序
    final void playLevel() {
        start();
        play();
        end();
    }

    // 开始关卡，对所有关卡都是一样的
    void start() {
        System.out.println("关卡开始");
    }

    // 抽象方法 play，每个关卡的具体内容由子类实现
    abstract void play();

    // 结束关卡，对所有关卡都是一样的
    void end() {
        System.out.println("关卡结束");
    }
}

// Level1 类继承自 GameLevel
class Level1 extends GameLevel {
    @Override
    void play() {
        System.out.println("玩家正在进行第一关");
    }
}

// Level2 类继承自 GameLevel
class Level2 extends GameLevel {
    @Override
    void play() {
        System.out.println("玩家正在进行第二关");
    }
}

// 主类
public class Main {
    public static void main(String[] args) {
        GameLevel level1 = new Level1();
        GameLevel level2 = new Level2();

        // 按顺序执行关卡
        level1.playLevel();
        level2.playLevel();
    }
}
