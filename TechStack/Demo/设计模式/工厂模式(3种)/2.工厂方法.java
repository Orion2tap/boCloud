// 食物接口
interface Food {
    void eat();
}

// 汉堡
class Burger implements Food {
    public void eat() {
        System.out.println("吃汉堡！");
    }
}

// 三明治
class Sandwich implements Food {
    public void eat() {
        System.out.println("吃三明治！");
    }
}

// 抽象工厂
abstract class FoodFactory {
    abstract Food createFood();
}

// 汉堡工厂
class BurgerFactory extends FoodFactory {
    Food createFood() {
        return new Burger();
    }
}

// 三明治工厂
class SandwichFactory extends FoodFactory {
    Food createFood() {
        return new Sandwich();
    }
}

// 客户端代码
public class Restaurant {
    public static void main(String[] args) {
        FoodFactory factory = new BurgerFactory();
        Food myFood = factory.createFood();
        myFood.eat();
    }
}
