// 定义一个接口，代表食物
interface Food {
    void eat();
}

// 实现类 - 汉堡
class Burger implements Food {
    public void eat() {
        System.out.println("吃汉堡！");
    }
}

// 实现类 - 三明治
class Sandwich implements Food {
    public void eat() {
        System.out.println("吃三明治！");
    }
}

// 简单工厂 - 厨师
class FoodFactory {
    static Food getFood(String type) {
        if (type.equals("Burger")) {
            return new Burger();
        } else if (type.equals("Sandwich")) {
            return new Sandwich();
        }
        return null;
    }
}

// 客户端代码
public class Restaurant {
    public static void main(String[] args) {
        Food myFood = FoodFactory.getFood("Burger");
        myFood.eat();
    }
}
