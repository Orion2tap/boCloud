// 静态代理
//被代理类 todoMapperImpl 和代理类 todoMapperProxy 都实现了接口 todoMapper
public class TodoMapperProxy implements TodoMapper {
    private TodoMapper todoMapper;

    // 代理类 TodoMapperProxy 的构造方法 1
    public TodoMapperProxy() {
        // new 一个被代理类的实例 todoMapperImpl
        // 从而在代理类的 selectTodo 方法中调用被代理类的 selectTodo 方法
        this.todoMapper = new TodoMapperImpl();
    }

    // 代理类 TodoMapperProxy 的构造方法 2
    public TodoMapperProxy(TodoMapperImpl todoMapperImpl) {
        // 传入一个被代理类的实例 todoMapperImpl
        // 从而在代理类的 selectTodo 方法中调用被代理类的 selectTodo 方法
        this.todoMapper = todoMapperImpl;
    }

    @Override
    // 在代理类的 eat 方法中调用被代理类的 eat 方法
    public String selectTodo(int id) {
        before();
        String result = todoMapper.selectTodo(id);
        after();

        return result;
    }

    private  void before() {
        System.out.println("建立数据库连接等");
    }

    private  void after() {
        System.out.println("关闭数据库连接等");
    }

    /*
                                [JDBC]
      * TodoService
        * addBySQL
          * 创建 TodoModel 对象 设置属性 content 为传入的 content
          * 获取数据源
          * 准备 SQL 语句 `INSERT INTO Todo (content) VALUES (?)`
          * 建立数据库连接 (先执行 schema.sql 的建库建表语句)
          * 预编译 将 SQL 语句和数据库主键 id 传递给 statement 实例
          * 参数匹配 从下标 1 开始
          * 执行语句集
            1. executeQuery (执行 select 语句集)
            2. executeUpdate (执行 insert, update, delete 的语句集)
          * 调用 statement 实例的 getGeneratedKeys 方法获取所有id
          * 游标跳转到结果集 rs 的第一行
            ```
              白:
              rs 拿回的数据是根据你的 sql 来的。
              这里我们可以判断自己写的 sql 只会拿回一条数据，所以直接读取第一行就行了。
              如果不止拿回一条数据，就只能遍历
            ```
          * 设置 TodoModel 对象的 id 属性的值
          * 关闭所有连接 后开先关
          * 返回 TodoModel 对象
    */

    public static void main(String[] args) {
        // 仍然是接口 todoMapper 的引用, 调用的也仍然是 selectTodo 方法
        // 但实例化对象的过程改变了 代理类给被代理类的动作 ( selectTodo ) 加上了前后动作 ( 建立数据库连接等 和 关闭数据库连接等 )

        TodoMapper todoMapperProxy = new TodoMapperProxy();
        todoMapperProxy.selectTodo(888);
        /*
            输出:

         */
    }
}

/*
    静态代理, 给我们带来了一定灵活性, 在不改变被代理类的方法的前提下,

    通过将           [调用处被代理类的实例化语句]                替换成               [代理类的实例化语句]                 的方式
          TodoMapper todoMapperImpl = new todoMapperImpl()      TodoMapper todoMapperProxy = new todoMapperProxy

    实现了改动少量代码就获得额外动作的功能
 */