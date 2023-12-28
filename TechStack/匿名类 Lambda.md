### [匿名类和lambda表达式]
#### 1. 匿名类
```
/*
传参数的时候, new 一个类, 在类里面写好想实现的函数
注意, 这里并不是 new 了一个 PowerOff 类, PowerOff 是一个接口, 不能 new,
而是 new 一个新的类的实例, 但是省略了类的名字, 只剩下了接口的名字
*/
s1.takeoff(new PowerOff() {
    public void off() {
        System.out.println("匿名类 关闭所有设备");
    }
});
```
#### 2. lambda 表达式
```
// 直接把实现好的方法传进去
() -> {
    System.out.println("lambda 关闭所有设备");
}

// Java 会自己创建一个实现了 PowerOff 接口的类, 把实现好的方法给该类
// 然后 new 一个该类的实例, 传给 takeoff 方法
s1.takeoff(() -> {
    System.out.println("lambda 关闭所有设备");
});

```
