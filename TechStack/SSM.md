### MyCode SSM版
#### boMVC 和 boSSM 的区别
* boMVC (1 + 2)
  1. http 服务器 [收发和解析请求, 通过 Socket 和 Request 类]
  2. web [处理请求, 基于自建框架]
* boSSM (仅 3)
  1. http服务器 [使用内置 tomcat 服务器, 在 Linux 上会拆分]
  2. Servlet 接口 [ http 服务器须实现该接口才能调用 web 框架]
  3. web [基于 Spring Boot 框架]

#### 配置变动
  * 配置 build.gradle
    * 插件、依赖和第三方库
  * 配置 application.properties  
    * 数据库 日志 服务器运行端口
  * 重建 db
  * boMVC 移植到 boSSM
  * 配置 SsmApplication
    * 本地入口 相当于 Mycode 的 erver.run()
  * 配置 ServletInitializer
    * tomcat 使用的生产级 servlet 入口
  * 本地入口 SSM [dev]
    * Edit Configurations -> ＋ -> Spring Boot
    * Main class: boSSM.SsmApplication
    * 其他选项默认

#### 目录
* main
  * java
    * kybmig
      * ssm
        * controller
        * mapper
        * model
        * service
        * JDBCDemo
        * ServletInitializer
        * SsmApplication
        * Utility
  * resources
    * kybmig
      * ssm
        * mapper
    * static
    * templates
    * application.properties
    * schema.sql

#### Spring、 Spring MVC、 Spring Boot
* **Spring** 最早用来做客户端的框架
* **Spring MVC** 在 Spring 基础上的拓展的用于 Web 开发的模块
* xml 配置文件 告诉 Spring MVC 去哪里下载需要的模块
* **Spring Boot** Spring + Spring MVC + 配置

#### JavaBean
* 一种符合特定规范的类
* 特点
  1. 所有属性为 private
  2. 提供默认构造方法
  3. 提供 getter 和 setter
  4. 实现 serializable 接口

#### Spring 从请求中取参数的几种方式
  * HelloWorldController
    1. demoRoute 给传入参数加上注解 @RequestParam, 将请求中参数 xxx 的值作为传入参数 name 的值, 获取 xxx 失败则设置默认值
    2. demoRoute1 传入参数 name , Spring 自动去 request 中寻找参数 name
    3. demoRoute2 传入整个 request, 通过 getParameter 方法取参数 name
    4. demoRoute3 给传入参数加上注解 @PathVariable, 告诉 Spring 去路径中取参数
      * 动态路由 "/demo3/{name}", 如果访问 /demo3/bobo, 那么 bobo 将作为参数 name 的值传给路由函数

#### 依赖注入
  * 设计基于 Java 的面向对象思想
  * TodoService 类加上注解 @Service, 用于 Spring 扫描
  * 在需要传入 TodoService 实例的地方, Spring 找到扫描阶段存储的 TodoService 类, new 一个实例 todoService 传入
  * 表现为所有的路由函数都不再有 static 关键字, 因为都是实例方法  
  ```
        public TodoController(TodoService todoService) {
      //        this.todoService = new TodoService();
          this.todoService = todoService;
      }
  ```

#### JDBC
  * 全称
    * Java Database Connectivity
  * 定义
    1. 一系列函数的规范, API
    2. 是 Java 连接数据库所需函数的具体实现, 也叫驱动 (类比显卡驱动)
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

#### SQL 注入
  * `select * from todo where id = 1 or true`
    * 期望的抽象语法树（Abstract Syntax Tree, AST）
    ```
    {
    类型: select,
    表: todo,
        查询条件: {
          id="1 or true",
        }
    }
    ```
    * 实际的抽象语法树
    ```
    {
    类型: select,
    表: todo,
        查询条件: {
            语句: or
            第一部分: id=1,
            第二部分: true
            }
        }
    }
    ```
  * 解决方式 **预编译** `select * from todo where id = ?`
    1. 先解析成 ast
    ```
    {
    类型: select,
    表: todo,
        查询条件: {
          id=?,
        }
    }
    ```
    2. 再输入 "1 or true"
    ```
    {
    类型: select,
    表: todo,
        查询条件: {
          id=1 or true,
        }
    }
    ```
  * 代码实现
    1. 即时 SQL
    ```
        String sql = String.format("select * from `Todo`");
        ...
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        ...
    ```  
    2. 预编译 SQL
    ```
        String sql = "select * from `ssm`.`Todo` where id = ?";
        ...
        PreparedStatement statement = connection.prepareStatement(sql);
        // 下标从 1 开始, 第 1 个 ? 匹配参数 content
        statement.setString(1, content);
        ResultSet rs = statement.executeQuery();  
        ...
    ```

#### 获取数据库的自增id
    ```
        TodoModel m = new TodoModel();
        m.setContent(content);

        MysqlDataSource ds = Utility.getDataSource();
        String sqlInsert = "INSERT INTO Todo (content) VALUES (?)";

        ...

        Connection connection = ds.getConnection();
        // 第二个参数 将id传递给statement实例
        PreparedStatement statement = connection.prepareStatement(
                sqlInsert, Statement.RETURN_GENERATED_KEYS
        );
        statement.setString(1, content);
        statement.executeUpdate();

        // 调用statement实例的getGeneratedKeys方法获取所有id
        ResultSet rs = statement.getGeneratedKeys();
        // 跳到第一行
        rs.first();
        Integer id = rs.getInt("GENERATED_KEY");
        m.setId(id);

        rs.close();
        connection.close();
        statement.close();
    ```

#### BaseModel
    ```
        // 设置能取到私有属性
        f.setAccessible(true);
        // "取得 obj 对象在这个 Field 上的值" http://www.51gjie.com/java/791.html
        // 获取 this (当前运行的实例) 在 f 属性上的值
        Object v = f.get(this);
        // f.getName() 获取 f 属性的名字
        String s = String.format("%s: %s, ", f.getName(), v);
        sb.append(s);
    ```
