### [JavaScript]
#### 基础
  * 变量 `var a = 1`
  * 函数
  ```
  var func = function(name) {
      log(name, 'hello')
  }
  ```
  * 数组
  ```
  var array = [1, 2, 3]
  array.push(4)                   // 存
  log("array 1",  array[1])       // 取
  log('length', array.length)     // 长度
  ```
  * 字典
  ```
  // js 的字典就是对象
  var o = {
      'key': 'value',
      name: 'gua',                // 可以省略 key 的引号
  }
  // 引用字典的值 可以用很简单的点语法(也可以用传统的用法)
  // 引用不存在的值 不会报错 结果是 undefined
  log(o.key, o['name'], o)
  ```
  * 自定义log `var log = console.log.bind(console)`

#### DOM 和 DOM API
  * DOM (文档对象模型 document object model) 是浏览器对 html 文件的描述方式
  * DOM API 是浏览器提供给 JavaScript 操作 html 页面内元素的方式

###### 查找元素
  1. 元素选择器
  ```
  var body = document.querySelector('body')
  ```
  2. class选择器
  ```
  var form = document.querySelector('.login-form')
  ```
  3. id选择器
  ```
  var loginButton = document.querySelector('#id-button-login-2')
  ```

###### 操作元素
  1. 获得和修改特定属性值
  ```
  var user = document.querySelector('#id-input-username-2')
  var userValue = user.value
  user.value = 'set value'
  ```
  2. 获得HTML或纯文本
  ```
  // innderHTML 和 innerText 的区别是 innerText 会把 HTML 相关的关键字转义
  form.innerHTML = '<button>GUA</button>'
  form.innerText = '<button>GUA</button>'
  ```
  3. 添加元素
  ```
  var form = document.querySelector('.login-form')
  form.insertAdjacentHTML('beforebegin', '<h2 class="gua-h1">你好</h2>')

  beforeBegin: 插入到开始标记之前
  afterBegin:  插入到开始标记之后
  beforeEnd:   插入到结束标记之前
  afterEnd:    插入到结束标记之后
  ```
  4. 删除元素
  ```
  var p = document.querySelector('#id-input-password-2')
  p.remove()
  ```

###### 事件
  1. 事件绑定
  ```
  loginButton.addEventListener('click', function() {
      log('按钮被点击到了')
  })
  ```
  * JSON
  * 事件添加流程
    1. 通过DOM API获得元素
    2. 定义回调函数
    3. 调用addEventListener将元素与事件绑定 事件发生时调用回调函数
  * JS为什么一定需要回调
    ```
    JS不支持多线程 监听事件会使程序阻塞
    因此只能先将函数传入 让浏览器代为监听(也叫在浏览器上注册)
    等事件发生时再回头调用该函数
    ```

### [纯前端Todo]
#### 实现
    1. 给 add button 绑定事件
    2. 在事件处理函数中，获取 input 的值
    3. 用获取的值，组装一个 todo-cell HTML 字符串
    4. 插入 todo-list 中

#### 事件冒泡
  * 事件的触发响应会从最底层目标一层层地向外到最外层（根节点）

#### 事件委托
  * 机制
    1. 利用事件冒泡的机制把里层所需要响应的事件绑定到外层
    2. 事件执行中再判断当前响应的事件应该匹配到被代理元素中的哪一个或者哪几个
  * Event.target 获取**第一个**触发事件的元素
  * 优点
    1. 减少内存消耗
    2. 动态绑定事件
  * 不适用事件
    1. 无事件冒泡机制 如focus、blur
    2. 有事件冒泡但性能消耗高 如mousemove、mouseout

#### var 和 let
###### 1. var 变量提升机制
  * 任一种作用域中使用var关键字声明的变量，都会被提升到该作用域的最顶部

###### 2. 作用域
  * var a = 1 函数作用域/全局作用域
    * 分析器会往外层寻找第一个{} 找到后该变量作用域为{}内 即函数作用域
    * 找不到说明该变量在最外层 即全局作用域
  * let b = 1 块级作用域

#### 闭包
  * 定义
    1. 闭包（closure）是一个函数以及其捆绑的周边环境状态（lexical environment 词法环境）的引用的组合
    2. 即闭包让开发者可以从内部函数访问外部函数的作用域
    3. 在JavaScript 中，闭包会随着函数的创建而被同时创建
  * 理解
    * **冻结函数** 函数体内的外部参数的值不会变化 在解冻时才会赋当前值
  * 示例
    ```
    // 这是一个闭包
    for (var i = 0; i < 3; i++) {
        // 内部函数
        function_list.push(function () {    // 同步注册回调函数到异步的宏任务队列
            log("i: ", i)                   // 执行此代码时 同步代码for循环已经执行完成 因此输出i:3
        })
    }

    for (let i = 0; i < 3; i++) {           // 在代码块内 使用let命令声明变量之前 该变量不可用
        // 内部函数
        function_list.push(function () {
            log("i: ", i)
        })
    }

    for (let e of function_list) {
        e()
    }
    ```
