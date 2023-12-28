#### CMD 启动 Tomcat
```bash
# 准备:
# 配置 JAVA_HOME 环境变量 https://blog.csdn.net/n_s_X14/article/details/87641236
# 配置 tomcat 环境变量 https://blog.csdn.net/haishu_zheng/article/details/50768272

# Win
# 确认 8080 端口未被使用
$ netstat -ano|findstr 8080   
$ tskill xxxxx
# 进入 tomcat 的 bin 目录
$ cd E:/Application/apache-tomcat-9.0.37/bin
# 启动  
$ .\startup.bat
# 关闭
$ netstat -ano|findstr 8080   
$ tskill xxxxx

# Mac
# 进入 tomcat 目录
$ cd xxx/apache-tomcat-9.0.37
# 修改 apache-tomcat-9.0.37 内所有文件权限
$ chmod -R 777 .
# 进入 bin 目录
$ cd bin
# 启动
$ .\startup.sh
# 关闭
$ lsof -i:8080
$ kill 端口号
# 浏览器访问 `localhost:8080` 可以看到 tomcat 页面
```

#### idea 配置 Tomcat (测试项目为 ServletDemo)
```bash
# 1. 修改 Application.properties
# 本地连接数据库默认使用的 TLS 协议版本: TLSv1, TLSv1.1, 服务器端的 openJDK 11 在一次更新中关闭了这个默认设置, 因此需要手动添加参数 enabledTLSProtocols=TLSv1,TLSv1.1,TLSv1.2
spring.datasource.url=jdbc:mysql://localhost:3306/ssm?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&enabledTLSProtocols=TLSv1,TLSv1.1,TLSv1.2

# 2. 修改 build.gradle
plugins {
//    id 'java'
  id 'io.spring.dependency-management' version '1.0.7.RELEASE'
  id 'war'
  id 'idea'
}

# 3. run [SSM build] 获得 war 包
# 4. IDEA 配置 tomcat
    # Edit Configuration -> "+" -> Tomcat Server -> local
    # Name: tomcat
    # Server -> Configure -> 导入"apache-tomcat-9.0.37"文件夹
    # Deployment -> "+" -> External Source -> 导入 build/libs 下的 war包 -> Application context:/ [目前只有一个war包 设置为根路径即可]
# 5. run "tomcat" 弹出 http://localhost:8080/ 并显示响应字符串 "servletDemo"
# 6. tomcat 运行 war 包的原理: 将用户导入的 war 包解压到 apache-tomcat-9.0.37/webapps/ROOT 目录
```
