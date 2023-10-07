# TechStack&BUG

- [TechStack\&BUG](#techstackbug)
  - [最小化可行产品 MVP Minimum Viable Product](#最小化可行产品-mvp-minimum-viable-product)
    - [自底向上实现](#自底向上实现)
    - [层次](#层次)
  - [IP PV UV](#ip-pv-uv)
  - [POJO(VO DTO BO/DO PO) + DAO](#pojovo-dto-bodo-po--dao)
  - [Controller 注入接口而不是实现类](#controller-注入接口而不是实现类)
  - [@RequiredArgsConstructor 和 @AllArgsConstructor](#requiredargsconstructor-和-allargsconstructor)
  - [@AllArgsConstructor 导致 @Value 取不到值](#allargsconstructor-导致-value-取不到值)
  - [kotlin 版本不一致](#kotlin-版本不一致)
  - [JavaBeans pattern / Builder pattern / @Data 比较](#javabeans-pattern--builder-pattern--data-比较)
  - [Functional interface and lambda expression](#functional-interface-and-lambda-expression)
    - [Supplier](#supplier)
    - [Dynamic proxy](#dynamic-proxy)

## 最小化可行产品 MVP Minimum Viable Product

### 自底向上实现

     1. **操作场景的文档** （对这些数据的操作）
          - 有一个主页，可看到所有todo
          - 主页有一个表单可以添加todo
     2. Model
     3. DataBase
          1. workbench 建表
          2. 记录 SQL 语句
     4. Mapper 接口 + XML 配置 (根据文档，实现 CRUD 和其他操作)
     5. Service
     6. Controller
     7. HTML
     8. JS
     9. CSS

### 层次

         View(HTML JS CSS)
          ↑
     TodoController
          ↑
     TodoService
          ↑
     TodoMapper
          ↑              [这一层绑定依靠 Spring MyBatis 动态代理]
     TodoMapper.xml      [标明对应哪个接口, 配置每个接口方法对应的 SQL 语句]
     ↑            ↑
     DataBase            TodoModel

## IP PV UV

```bash
# IP(Internet Protocol), 独立 IP 数, 24 小时内相同公网 IP 地址只被计算一次
# PV(Page View), 访问量, 即页面浏览量或点击量, 用户每次刷新被计算一次
# UV(Unique　Visitor), 独立访客, 访问您网站的一台电脑客户端为一个访客, 00:00-24:00 内相同的客户端只被计算一次, 对于一些 UV 量很低的页面, 可以使用 robots 文件进行屏蔽
```

## POJO(VO DTO BO/DO PO) + DAO

```bash
# https://zhuanlan.zhihu.com/p/102389552
POJO（plain ordinary java object）
# 简单无规则java对象, 以下都属于该类对象
        VO（value object）
        # 值对象/视图对象，后端 Controller 的返回值, 返回给前端的数据对象
        DTO（Data Transfer Object）
        # 版本1 后端 Controller 内定义的 Java 对象
        # 版本2 前端通过 AJAX 请求的数据 (可简单理解为 JSON)
        BO（business objectr）
        # 业务对象, 根据具体的业务场景封装的对象, PO + PO + PO = BO, 钉子 + 锤子 + 钳子 = 工具箱
        DO（Domain Object）
        # 领域对象, 针对具体业务数据组成抽象出来的类, 等价于BO
        PO（persistant object）
        # 持久对象, 对应数据库中某个表中的一条记录
DAO（data access object）
# 数据访问对象, 对应数据库的 Mapper
```

## Controller 注入接口而不是实现类

```bash
# 如果只是单纯注入是可以用实现类接收注入对象的，但是往往开发中会对实现类做增强，如事务，日志等，实现增强的 AOP 技术是通过动态代理实现的，而 spring 默认是 JDK 动态代理，对实现类对象做增强得到的增强类与实现类是兄弟关系，所以不能用实现类接收增强类对象，只能用接口接收
```

## @RequiredArgsConstructor 和 @AllArgsConstructor

```bash
@RequiredArgsConstructor
# 根据类中的 final 字段自动生成一个构造函数，但并不会自动添加 @Autowired 注解。
# 这意味着生成的构造函数将用于初始化字段，但它们不是通过 Spring 的依赖注入机制初始化的。相反，它们会在创建类的实例时直接使用提供的值进行初始化。
# 而对于非 final 字段，它并不会被包括在生成的构造函数中，也不会被自动初始化或注入。
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
# 根据类中的 final 字段自动生成一个构造函数，并在该构造函数的参数上自动添加 @Autowired 注解。
# 从而实现了对这些依赖项的自动注入
@AllArgsConstructor
# 自动生成一个包含所有类字段的构造函数。这个构造函数用于初始化对象的实例，并且参数列表中包含了所有类字段，可以用于直接传递字段的值。
```

## @AllArgsConstructor 导致 @Value 取不到值

```bash
# fast-app modules/user/rest/UserController.java

# 使用 @RequiredArgsConstructor(onConstructor = @__(@Autowired)) 替代 @AllArgsConstructor 可以解决 @Value 无法取到值的问题，是因为它的工作原理略有不同。

@AllArgsConstructor
# 这个注解会生成一个包含所有字段的构造函数，但它不会自动添加 @Autowired 注解。在这种情况下，如果你在类中使用 @Value 来注入值，可能会遇到取不到值的问题。因为 @Value 注解是由 Spring 在运行时进行处理的，而 Lombok 生成的构造函数不会包含这方面的处理逻辑。

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
# 这个注解的作用是生成一个包含所有 final 字段的构造函数，并在构造函数的参数上自动添加 @Autowired 注解。这意味着，使用这个注解生成的构造函数可以正确地进行依赖注入。因此，你可以在这个构造函数中正常使用 @Value 注解来获取配置值。

# 总结
# @RequiredArgsConstructor(onConstructor = @__(@Autowired)) 提供了一种更加集成地方式来处理依赖注入，它会在构造函数中自动添加 @Autowired 注解，从而保证依赖注入的正常进行，包括了使用 @Value 注解来获取配置值。
```

## kotlin 版本不一致

```bash
# idea -> build -> rebuild project
```

## JavaBeans pattern / Builder pattern / @Data 比较

```java
JavaBeans pattern // 简单易用，但可能导致对象在构建过程中处于不一致状态。
Builder pattern // 强制执行特定的构建过程，确保对象始终处于有效状态。适用于具有可选参数的复杂对象。
@Data // 减少样板代码，适用于简单的数据承载类。

// 选择适合你应用程序要求和对象复杂度的模式取决于具体情况。
```

## Functional interface and lambda expression

### Supplier<T>

```java
interface Supplier<T> {
    T get();
}

```

```java
// call

public static void main(String[] args) {
     // Create a factory for generating random numbers.
     // Equivalent to "(new Random()).nextInt(100)"
     Supplier<Integer> randomNumberFactory = () -> new Random().nextInt(100);

     // Use the factory to generate random numbers.
     // When an object implements the `Supplier<T>` interface, calling the parameterless `get` method of this object will execute the corresponding lambda expression.
     int random1 = randomNumberFactory.get();
     int random2 = randomNumberFactory.get();

     System.out.println("Random Number 1: " + random1); 
     System.out.println("Random Number 2: " + random2); 
}

```

### Dynamic proxy

```java

```
