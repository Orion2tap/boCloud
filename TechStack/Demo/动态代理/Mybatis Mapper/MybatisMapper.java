// 不实现接口 todoMapper, MyBatis 如何完成动态代理
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface TodoMapper {
    //    TodoModel selectTodo(int id);
    // 为便于理解 这里直接返回一个 String
    // 原本应该执行 SQL 语句之后从数据库返回 TodoModel
    String selectTodo(int id);
}

class DynamicProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入处理器");
        return "success";
    }
}

public class MybatisMapper {
    public static void main(String[] args) {
        TodoMapper t = (TodoMapper) Proxy.newProxyInstance(
                TodoMapper.class.getClassLoader(),
                new Class[]{TodoMapper.class},
                new DynamicProxy()
        );

        t.selectTodo(999);

    }
}
/*  ----------------------------例1----------------------------
    接口: A
    被代理类: A1           (实现了 A 接口)
    动态代理类生成器: D           (在invoke()内添加动作)
    生成的具体动态代理类: D1    (D1 通过代理 A1 实现代理 A)

    步骤:
    1. 创建 A1 的实例 a1
    2. 调用 D 的构造方法, 传入 a1, 生成 d
    3. 调用 Proxy.newProxyInstance(A1的类加载器, A1所有需实现接口, d) 生成 d1
    4. 调用 d1.selectTodo(999)
        * 拦截并调用 d.invoke()
        * d.invoke() 进一步调用 before() + a1.invoke() + after() 加上前后动作
 */

 /*
    -----------------例2----------------- 不实现接口 todoMapper, MyBatis 如何完成动态代理
    接口: A
    动态代理类: D

    步骤:
    1. 调用 Proxy.newProxyInstance(A的类加载器, A, d) 生成 t
    2. t.selectTodo(999)
        * 拦截并调用 d.invoke()
 */

/*
    例1和例2的区别 (实现接口和不实现接口)
    例1: d1.selectTodo(999) -> d.invoke() -> before() + selectTodo.invoke(todoMapperImpl, 999) + after()
                                       等价于 before() + todoMapperImpl.selectTodo(999) + after()

    例2: t.selectTodo(999) -> d.invoke()
 */

/*
### 动态代理 (MyBatis)
#### 准备阶段
    1. 项目启动时 MyBatis 通过解析器解析 SQL 语句组装为 Java 中的对象 sqlModel, 根据 .xml 上 mapper 标签 namespace 属性，得到接口 todoMapper
    2. 保存映射 ( mapper 接口内的方法和组装好的 sqlModel 一一对应 ), 等待生成动态代理对象
#### 访问 /todo/edit    
    1. TodoController.edit(999) 内调用 todoService.findById(999)
    2. todoService.findById(999) 内调用 mapper.selectTodo(999)
    3. MyBatis 生成动态处理对象
       TodoMapper t = Proxy.newProxyInstance( TodoMapper 接口的类加载器, TodoMapper 接口, 处理器 MapperProxy )
    4. t.selectTodo(999)
    5. 处理器 MapperProxy 调用 invoke 方法, 方法内的操作包括连接数据库、根据 sqlModel 获得数据、关闭连接等
 */