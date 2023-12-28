// 食物接口
interface Burger {
    void eat();
}

interface Drink {
    void drink();
}

// 汉堡实现
class BeefBurger implements Burger {
    public void eat() {
        System.out.println("吃牛肉汉堡！");
    }
}

class ChickenBurger implements Burger {
    public void eat() {
        System.out.println("吃鸡肉汉堡！");
    }
}

// 饮料实现
class Cola implements Drink {
    public void drink() {
        System.out.println("喝可乐！");
    }
}

class Juice implements Drink {
    public void drink() {
        System.out.println("喝果汁！");
    }
}

// 抽象工厂
interface FastFoodFactory {
    Burger createBurger();
    Drink createDrink();
}

// 牛肉汉堡套餐工厂
class BeefBurgerFactory implements FastFoodFactory {
    public Burger createBurger() {
        return new BeefBurger();
    }

    public Drink createDrink() {
        return new Cola();
    }
}

// 鸡肉汉堡套餐工厂
class ChickenBurgerFactory implements FastFoodFactory {
    public Burger createBurger() {
        return new ChickenBurger();
    }

    public Drink createDrink() {
        return new Juice();
    }
}

// 客户端代码
public class FastFoodRestaurant {
    public static void main(String[] args) {
        FastFoodFactory factory = new BeefBurgerFactory();
        Burger myBurger = factory.createBurger();
        Drink myDrink = factory.createDrink();
        myBurger.eat();
        myDrink.drink();
    }
}
