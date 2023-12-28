import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler {

    // private Human Human;
    // 进一步抽象成一个 Object 对象 target
    private Object target;

    /*
    public HumanProxy(HumanImpl HumanImpl) {
        this.Human = HumanImpl;
    }
     */
    public DynamicProxy(Object target) {
        // 传入一个被代理类的实例
        // 从而在代理类的 xxx 方法中调用被代理类的 xxx 方法
        this.target = target;
    }

    @Override
    // 在代理类的 invoke 方法中调用被代理类的 invoke 方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        // String result = Human.eat(food);
        Object result = method.invoke(target, args);
        /*
            Object result 对应 String result
            method        对应 eat
            target        对应 Human
            args          对应 food
         */
        after();
        return result;
    }

    private  void before() {
        System.out.println("cook");
    }

    private  void after() {
        System.out.println("sweep");
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        // 调用 JDK 的代理工厂方法, 动态生成一个具体代理类的实例, 不用显式创建一个实现具体接口的代理类
        return (T) Proxy.newProxyInstance(
                // 被代理类的类加载器
                target.getClass().getClassLoader(),
                // 被代理类所有需要实现的接口
                target.getClass().getInterfaces(),
                // 动态代理类的实例(处理器)
                this
        );
    }

    public static void main(String[] args) {
//        Human HumanProxy = new HumanProxy();
//        HumanProxy.eat("rice");

        // 被代理类的实例
        Human humanImpl = new HumanImpl();
        // 使用 动态代理类 包装 被代理类的实例
        DynamicProxy dynamicProxy = new DynamicProxy(humanImpl);

        // 调用 JDK 的代理工厂方法, 动态生成一个具体代理类的实例, 不用显式创建一个实现具体接口的代理类
//        Human HumanDynamicProxy = (Human) Proxy.newProxyInstance(
//              // 被代理类的类加载器
//              humanImpl.getClass().getClassLoader(),
//              // 被代理类所有需要实现的接口
//              humanImpl.getClass().getInterfaces(),
//              // 动态代理类的实例(处理器)
//              dynamicProxy
//        );
        Human HumanDynamicProxy = dynamicProxy.getProxy();

        HumanDynamicProxy.eat("rice");
    }
}