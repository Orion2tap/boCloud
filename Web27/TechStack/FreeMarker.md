### [模板引擎 freemarker]
#### 1. 模板的定义
  ```
  模板是一种能够在静态HTML上动态显示内容的页面

  模板文件存放在Web服务器上，就像通常存放静态HTML页面那样。
  当有人来访问这个页面， 模板引擎将会介入执行，然后动态转换模板，
  用最新的数据内容替换模板中的标记，这些内容可以是字符串、对象、List等
  之后将结果发送到访问者的Web浏览器中
  ```
#### 2. 实现
  1. 对应路由
    * 使用guaTemplate.render(Object data, String templateFileName)
    * data为一个HashMap key为页面标记 value为数据
    * templateFileName ftl页面名字
  2. ftl文件
    * 模板字符串格式
      * 传入字符串 ${username}
      * 传入对象 ${m.author} 注意**必须设置属性的setter和getter方法**
      * 传入ArrayList
      ```
      <#list messageList as m>
        <div>${m.author}: ${m.message}</div>
      </#list>
      ```    
