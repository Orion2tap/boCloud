# EffectiveJava-GPTGuide

- [EffectiveJava-GPTGuide](#effectivejava-gptguide)
  - [Common](#common)
    - [使用内部类和匿名内部类实现简单闭包](#使用内部类和匿名内部类实现简单闭包)
    - [使用内部类和匿名内部类实现回调](#使用内部类和匿名内部类实现回调)
  - [Rules](#rules)
    - [01 用 static factory method 代替 constructors](#01-用-static-factory-method-代替-constructors)

## Common

### 使用内部类和匿名内部类实现简单闭包

```java
// 动态地指定计算器要执行的操作
public class Calculator {
    // member interface 成员接口
    interface Operation {
        int calculate(int a, int b);
    }

    public int performOperation(int a, int b, Operation operation) {
        return operation.calculate(a, b);
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        // 使用内部类实现加法
        Operation addition = new Operation() {
            @Override
            public int calculate(int a, int b) {
                return a + b;
            }
        };

        int result1 = calculator.performOperation(5, 3, addition); // 结果为 8

        // 使用内部类实现乘法
        Operation multiplication = new Operation() {
            @Override
            public int calculate(int a, int b) {
                return a * b;
            }
        };

        int result2 = calculator.performOperation(5, 3, multiplication); // 结果为 15
    }
}
```

### 使用内部类和匿名内部类实现回调

```java
// 计算器类
public class Calculator {
    public interface Callback {
        void onResult(int result);
    }

    public void add(int a, int b, Callback callback) {
        int result = a + b;
        callback.onResult(result);
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        // 传递了两个整数和一个匿名内部类作为回调函数
        // 在计算完成后执行一些额外逻辑，无需修改 Calculator 类的代码
        calculator.add(2, 3, new Calculator.Callback() {
            @Override
            public void onResult(int result) {
                System.out.println("计算结果是：" + result);
            }
        });
    }
}

```

## Rules

### 01 用 static factory method 代替 constructors

```java
// constructors 
public class ComplexNumber {
    private final double real;
    private final double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    // 其他方法和成员变量...
}
```

```java
// 调用
ComplexNumber complexNumber1 = new ComplexNumber(2.0, 3.0);
ComplexNumber complexNumber2 = new ComplexNumber(4.0, 5.0);
```

```java
// static factory method 
public class ComplexNumber {
    private final double real;
    private final double imaginary;

    private ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public static ComplexNumber fromCartesian(double real, double imaginary) {
        return new ComplexNumber(real, imaginary);
    }

    public static ComplexNumber fromPolar(double magnitude, double angle) {
        return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }

    // 其他方法和成员变量...
}

```

```java
// 调用
// One advantage of static factory methods is that, unlike constructors, they have names.
ComplexNumber complexNumber1 = ComplexNumber.fromCartesian(2.0, 3.0);
ComplexNumber complexNumber2 = ComplexNumber.fromPolar(5.0, Math.PI / 4);

```

```java
//  A second advantage of static factory methods is that, unlike constructors, they are not required to create a new object each time they’re invoked.
public class Singleton {
    // 私有静态变量
    private static final Singleton instance = new Singleton();

    private Singleton() {
        // 私有构造器，外部无法直接实例化
    }

    public static Singleton getInstance() {
        return instance;
    }
}

```
