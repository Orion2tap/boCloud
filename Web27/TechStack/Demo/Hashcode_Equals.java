import java.util.Objects;

class Person {
    String name;
    Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(age, person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
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
