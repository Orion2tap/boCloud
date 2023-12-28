### 最小化可行产品 MVP Minimum Viable Product
-----------------------------------------
##### 自底向上实现
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

##### 层次
                        TodoController
                        ↑           ↑
                TodoService         html
                        ↑
                TodoMapper
                        ↑ [这一层绑定依靠 Spring MyBatis 动态代理]
                TodoMapper.xml    [标明对应哪个接口, 配置每个接口方法对应的 SQL 语句]
                ↑            ↑
         DataBase            TodoModel   

### 其他
18. MVC
    * **Model 数据模型**, Service 和 Model
    * **View 视图**, HTML 页面
    * **Controller 控制器**, Server 和 Route

1. 访问 localhost:9000
  1. 浏览器向服务器发起GET请求
  2. 服务器监听到请求, 调用socketReadAll函数读取请求数据, 并存储到Request类型的实例r中,
  3. 调用responseForPath函数, 根据请求路径"/", 调用routeIndex函数
  4. 在routeIndex函数中, 先调用html函数获取响应的body部分, 再与响应头拼接, 返回完整响应
  5. 服务器获取响应, 调用socketSendAll函数发送给浏览器

4. **浮点数除法**
```
// integer division in floating-point context 错误
int a = 1;
int b = 2;
double c = a / b;
// 两个int类型相除 结果是double类型 那么操作数之一必须是double类型
```
6. **static(静态)**
  * 带static     属性/函数   和类绑定
  * 不带static   属性/函数  和实例绑定
7. **split()** 找不到时返回原值
17. `arrayCopy( arr1, 2, arr2, 5, 10);` 将arr1数组里从索引为2的元素开始, 复制到数组arr2里的索引为5的位置, 复制的元素个数为10个.
11. FileInputStream和FileOutputStream的相对路径以**项目根目录**为锚点
13. 一般情况下绝对路径>相对路径(html用相对路径, 因为会经过服务器处理)
14. **URLDecoder.decode()**
```
    // 当URL有中文字符，浏览器会转换成application/x-www-form-urlencoded MIME 字符串
    // decode之前: application/x-www-form-urlencoded MIME 字符串 (中文以%XX%XX""格式显示)
    // decode之后: 普通字符串(能正常显示中文)
    for (String e : args) {
        String[] kv = e.split("=", 2);
        String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
        String v = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
        this.query.put(k, v);
    }
```
15. **ORM**(对象关系映射) 对象(比如Message类)和数据库中的数据(比如两行数据)一一对应
16. Win 系统下 MySQL Workbench 建库时 第一个 defaultC 改为 utf8mb4
18. 约定大于配置 in Spring Boot
    * resources/boGroup/boSSM/mapper 对应 java/boGroup/boSSM/mapper 要求目录层级相同
    * 不写首页的路由, Spring 会自动去 Templates 文件夹内查找名字为 index 的页面并返回
    * 图片加载: 对于 html 的`<img src='/bobo.jpg' alt="bobo">`, Spring 将自动在 static 目录下查找图片并加载

### [BUG]
* routeImage的图片显示[白: 图片的二进制数据不一定满足utf规范，转成字符串再转回二进制会丢失信息]
* TodoService.delete(...)中for each循环, 使用break是否可以解决java.util.ArrayList$Itr.next的BUG
```
public static void delete(Integer id) {
    ArrayList<Todo> ms = load();
    for (Todo m : ms) {
        if (m.id.equals(id)) {
            ms.remove(m);
            break;                // 添加这行
        }
    }

    save(ms);
}
```
* HTML中先引入jquery再引入bootstrap 如果顺序正确 引入更高级jquery版本
  ```
  Uncaught Error: Bootstrap's JavaScript requires jQuery
      at bootstrap.min.js:6:37
  ```
* http://119.45.22.158:8080/ 无法访问可能是梯子没关 / 删除项目的 build 文件夹然后重新 run
