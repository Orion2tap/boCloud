import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler {

    // private TodoMapper todoMapper;
    // 被代理对象, 本例中是一个被代理类的实例 todoMapperImpl
    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    // https://www.liaoxuefeng.com/wiki/1252599548343744/1264803678201760
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入处理器");
        before();
        // 在代理类的 invoke 方法中调用被代理类的 invoke 方法
        // String result = todoMapper.selectTodo(id);
        Object result = method.invoke(target, args);
        /*
            Object result 对应 String result
            method        对应 selectTodo
            target        对应 todoMapper
            args          对应 id
         */
        after();

        System.out.printf("方法: %s\n",  method.toString());
        for (int i = 0; i < args.length; i++) {
            System.out.printf("第 %s 个参数: %s\n", i + 1, args[i]);
        }
        return result;
    }

    private  void before() {
        System.out.println("建立数据库连接等");
    }

    private  void after() {
        System.out.println("关闭数据库连接等");
    }

//    @SuppressWarnings("unchecked")
//    public <T> T getProxy() {
//        // 调用 JDK 的代理工厂方法, 动态生成一个具体代理类的实例, 不用显式创建一个实现具体接口的代理类
//        return (T) Proxy.newProxyInstance(
//                // 被代理类的类加载器
//                target.getClass().getClassLoader(),
//                // 被代理类所有需要实现的接口
//                target.getClass().getInterfaces(),
//                // 动态代理类的实例(处理器)
//                this
//        );
//    }

    public static void main(String[] args) {
//        TodoMapper todoMapperProxy = new TodoMapperProxy();
//        todoMapperProxy.selectTodo(888);

        // 被代理类的实例
        TodoMapper todoMapperImpl = new TodoMapperImpl();

        // 调用 JDK 的代理工厂方法, 动态生成一个具体代理类的实例, 不用显式创建一个实现指定接口的具体代理类
        TodoMapper t = (TodoMapper) Proxy.newProxyInstance(
              // 被代理类的类加载器
              todoMapperImpl.getClass().getClassLoader(),
              // 被代理类所实现接口的 Class 数组
              // 等价于 new Class[]{TodoMapper.class}, 把接口本身放到 Class 数组中
              todoMapperImpl.getClass().getInterfaces(),
              // 动态代理类的实例(处理器)
              new DynamicProxy(todoMapperImpl)
        );

//        TodoMapper t = dynamicProxy.getProxy();

        t.selectTodo(999);
    }
}

/*
    接口: A
    被代理类: A1           (实现了 A 接口)
    动态代理类生成器: D           (在invoke()内添加动作)
    生成的具体动态代理类: D1    (D1 通过代理 A1 实现代理 A)

    步骤:
    1. 创建 A1 的实例 a1
    2. 调用 D 的构造方法, 传入 a1, 生成 d
    3. 调用 Proxy.newProxyInstance(A1的类加载器, A1所有需实现接口, d) 生成 d1
    4. 调用 d1.selectTodo(999)
        * spring 拦截, 调用 d.invoke()
        * d.invoke() 进一步调用 before() + a1.invoke() + after() 加上前后动作
 */

 /*
   Mybatis Mapper 没有实现接口 A 也可以实现动态代理, 见 MybatisMapper.java
 */
