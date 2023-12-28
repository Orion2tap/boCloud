### MyBatis
  * 概念
        MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。
        MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。
        MyBatis 使用简单的 XML 或注解来配置和映射原生信息，
                将接口和 Java 的 POJOs (Plain Ordinary Java Object, 普通的 Java 对象)
                映射成数据库中的记录。
  * 配置              
    * build.gradle: 引入 MyBatis 库
    * application.properties: MyBatis 连接数据库的配置
    * SsmApplication: @SpringBootApplication(exclude = DataSourceAutoConfiguration.class) 改为 @SpringBootApplication

### TodoMapper 接口 (.java 文件) 和 TodoMapper.xml 的对应关系
  1. xml 的 mapper 标签填写对应的接口
    ```
    <mapper namespace="boSSM.mapper.TodoMapper">
    ```
  2. ArrayList<TodoModel> selectAllTodo();
    ```
    // xml里面只需要返回TodoModel
    // sql结尾不能加分号
    <select id="selectAllTodo" resultType="boSSM.model.TodoModel">
        SELECT * FROM ssm.todo
    </select>
    ```
  3. TodoModel selectTodo(int id);
    ```
    // ${id} 即时 SQL, #{id} 预编译 SQL
    <select id="selectTodo" resultType="boSSM.model.TodoModel">
        SELECT * FROM ssm.todo WHERE id = #{id}
    </select>
    ```
  4. void insertTodo(TodoModel todo);
    ```
    // 1. MyBatis 从传入的 TodoModel 中获取 content, 绑定到 #{content}, 此时 TodoModel 的 id = null
    // 2. MyBatis 从数据库获取 id, 传入TodoModel  
    <insert id="insertTodo" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO ssm.todo (content) VALUES (#{content})
    </insert>
    ```

### TodoService 中 使用 TodoMapper 接口
```
@Service
public class TodoService {
    // Spring 负责依赖注入: new 一个 TodoMapper 的实例 mapper 并传入
    TodoMapper mapper;

    public TodoService(TodoMapper mapper) {
        this.mapper = mapper;
    }

    public TodoModel add(String content) {
        TodoModel m = new TodoModel();
        m.setContent(content);
        Utility.log("m before insert id %s", m.getId());    // id = null
        mapper.insertTodo(m);
        Utility.log("m after insert id %s", m.getId());     // id = 数据库取回的id
        return m;

    }
}
```

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
