### XSRF 跨站请求伪造
    定义: 跨站请求伪造 (Cross-site request forgery), 通常缩写为 CSRF 或者 XSRF, 是一种利用的是网站对用户网页浏览器的信任, 诱导用户在当前已登录的 Web 应用程序上执行非本意的操作的攻击方法
    场景: 用户在 A 网站登录后, 误访问钓鱼网站 B, 触发攻击者预先构造的请求访问 A 网站背后服务器, 由于浏览器的 cookie 机制, 该请求会带上 A 网站的 cookie, 因此该请求能够通过服务器的用户验证, 攻击者实现伪装该用户
    解决: 在用户登录的前提下访问页面 A, 服务器将用户 id 映射到一段随机字符串 S, 此后用户在页面 A 提交的请求都会带上两部分的 token, 第一部分用于验证用户身份, 第二部分为字符串 S, 攻击者只能拿到第一部分

### XSS 跨站脚本攻击
    定义: 跨站脚本 (Cross-site scripting) 是一种网站应用程序的安全漏洞攻击, 它允许恶意用户将代码注入到网页上, 其他用户在观看网页时就会受到影响, 利用的是用户对指定网站的信任
    场景: 攻击者访问 A 页面, 将恶意脚本作为输入发送给服务器, 服务器将输入更新到 A 页面, 此后其他用户访问 A 页面, 将自动执行该脚本, 攻击者实现获得更高权限、私密内容、用户cookie等各种内容
    解决: 网站对用户输入中的敏感字符进行转义, 例如使用 Freemarker 自带的 html 转义函数

### 邮件
    1. QQ邮箱
      * 设置-账户-开启IMAP/SMTP服务
    2. build.gradle
      * 引入 spring-boot-starter-mail
    3. application.properties
      ```
      # 配置 QQ 邮箱
      spring.mail.host=smtp.qq.com
      spring.mail.username=284115962@qq.com
      # QQ 邮箱提供的授权码
      spring.mail.password=ziwcbbhyqaipcafg
      # 国外可能使用端口 587
      spring.mail.port=465
      spring.mail.protocol=smtps
      ```
    4. 实现 MailController 注入依赖 MailSender, MailProperties
    5. 实现 mail/index 页面

### 异步发送邮件
    1. SsmApplication 添加`@EnableAsync`
    2. MailController 中实现 AsyncTask 类并加上注解 @Component, 类中的sendMail函数加上注解 @Async
    3. MailController 注入依赖 AsyncTask
    3. 访问 /mail/send/async 路径时调用 `this.async.sendMail(..)`

### 上传图片并显示
    1. /upload/index 页面点击"上传文件" 选择图片
    2. 访问 /upload , 在 avatar 下生成对应的文件名, 通过 FileOutputStream 传输图片的二进制数据
    3. 访问动态路由 /avatar/{imageName}, 通过 FileInputStream 根据路径找到 avatar 文件夹内的图片, 通过链式调用设置响应的状态码、header 和 body, 路由函数的返回值类型为 ResponseEntity<byte[]>
