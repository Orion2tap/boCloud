// 提莫类
class Teemo implements Champion {
    public void useUltimateAbility() {
        System.out.println("提莫使用终极技能，放置了一个蘑菇");
    }

    public void buyItem(String item) {
        System.out.println("提莫购买了: " + item);
    }
}