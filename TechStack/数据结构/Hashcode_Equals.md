```
// 通过 IDE 自动重写 equals 和 hashCode
// 目的:
//      使相同属性值的两个对象一定返回相同的 hashCode
//      属性值不同的两个对象尽量返回不同的 hashCode

import java.util.Objects;

class Person {
    String name;
    Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    // 重写前属性值相同的两个对象返回的默认 hashCode 值不同, 重写后相同
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    // 如果属性不同的两个对象返回了相同的hashCode
    // 通过 equals 逐个判断属性值是否相等, 都相等返回 true
    @Override
    public boolean equals(Object o) {
        // 地址相等说明是同一个对象, 直接返回 true
        if (this == o) return true;

        // 待比较对象 o 不是 Person 类的实例, 直接返回 false
        if (!(o instanceof Person)) return false;

        // 地址不等且 o 是 Person 类的实例
        // 对 o 做类型转换
        Person person = (Person) o;

        // 调用 Objects.equals 逐个判断属性值是否相等, 都相等返回 true
        return Objects.equals(name, person.name) &&
                Objects.equals(age, person.age);
    }
}

public class Hashcode_Equals {
    public static void main(String[] args) {
        Person p1 = new Person ("Bob", 20);
        Person p2 = new Person ("Bob", 20);
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
    }
}
```
