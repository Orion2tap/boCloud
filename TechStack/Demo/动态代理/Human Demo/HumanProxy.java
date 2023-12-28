// 静态代理
//被代理类 humanImpl 和代理类 humanProxy 都实现了接口 human
public class HumanProxy implements Human {
    private Human human;

    // 代理类 humanProxy 的构造方法 1
    public HumanProxy() {
        // new 一个被代理类 humanImpl 的实例
        // 从而在代理类的 eat 方法中调用被代理类的 eat 方法
        this.human = new HumanImpl();
    }

    // 代理类 humanProxy 的构造方法 2
    public HumanProxy(HumanImpl humanImpl) {
        // 传入一个被代理类 humanImpl 的实例
        // 从而在代理类的 eat 方法中调用被代理类的 eat 方法
        this.human = humanImpl;
    }

    @Override
    // 在代理类的 eat 方法中调用被代理类的 eat 方法
    public String eat(String food) {
        before();
        String result = human.eat(food);
        after();

        return result;
    }

    private  void before() {
        System.out.println("cook");
    }

    private  void after() {
        System.out.println("sweep");
    }

    public static void main(String[] args) {
        // 仍然是接口 human 的引用, 调用的也仍然是 eat 方法
        // 但实例化对象的过程改变了 代理类给被代理类的动作 ( eat ) 加上了前后动作 ( cook 和 swap )
        Human humanProxy = new HumanProxy();
        humanProxy.eat("rice");

        /*
            输出:
                cook
                eat rice
                swap
         */
    }
}

/*
    静态代理, 给我们带来了一定灵活性, 在不改变被代理类的方法的前提下,

    通过将      [调用处被代理类的实例化语句]        替换成            [代理类的实例化语句]              的方式
          human humanImpl = new humanImpl();         human humanProxy = new humanProxy();

    实现了改动少量代码就获得额外动作的功能
 */