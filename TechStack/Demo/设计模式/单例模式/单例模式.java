// 这个类是我们的特别房间
public class SpecialRoom {

    // 我们创建了一个这个房间的实例
    private static SpecialRoom instance = new SpecialRoom();

    // 构造方法是私有的，这意味着其他人不能从外部创建这个房间
    private SpecialRoom() {}

    // 这个方法允许其他人进入这个房间
    public static SpecialRoom getInstance() {
        return instance;
    }

    public void enter() {
        System.out.println("你进入了特别的房间！");
    }
}

// 这是我们的游戏
public class Game {
    public static void main(String[] args) {
        // 我们尝试进入特别的房间
        SpecialRoom room = SpecialRoom.getInstance();

        // 我们进入房间
        room.enter();
    }
}
