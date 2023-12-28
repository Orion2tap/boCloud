// 提莫的代理类
class TeemoProxy implements Champion {
    private Teemo teemo;
    // 布尔值默认为 false, 代表玩家控制
    private boolean isAutoPlay;

    public TeemoProxy(Teemo teemo) {
        this.teemo = teemo;
    }

    public void setAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
    }

    public void useUltimateAbility() {
        if (isAutoPlay) {
            System.out.println("代理自动控制提莫使用终极技能");
        } else {
            teemo.useUltimateAbility();
        }
    }

    public void buyItem(String item) {
        if (isAutoPlay) {
            System.out.println("代理自动为提莫购买装备: " + item);
        } else {
            teemo.buyItem(item);
        }
    }
}
