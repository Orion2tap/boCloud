### [gradle]
#### 用途
  1. 下载第三方库
  2. 项目打包

#### 目录
  1. code
    * .gradle
    * .idea
    * build
      * libs    项目的jar包位置
    * db
    * gradle
      * wrapper
        * gradle-6.5-all.zip 指定版本的Gradle
        * gradle-wrapper.jar 用于下载Gradle的代码
        * gradle-wrapper.properties 控制Gradle Wrapper运行时行为
          * 属性distributionUrl: Gradle的下载链接
    * src
      * main 源码
        * java
          * boMVC
        * resources 资源
          * static
          * templates
          * application.properties      数据库配置
          * schema.sql                  sql语句
      * test 测试
    * build.gradle      当前项目构建的配置文件
    * gradlew           使用Gradle Wrapper在Linux/Unix平台构建的执行脚本
    * gradlew.bat       使用Gradle Wrapper在Windows平台构建的执行脚本
    * settings.gradle   多项目构建的配置文件
  2. External Libraries 第三方库
    * <12.0.2>                                  JDK
    * Gradle: junit:junit:4.12
    * Gradle: org.freemarker:freemarker:2.3.30  模板引擎
      * freemarker-2.3.30.jar
    * Gradle: org.hamcrest:hancrest-core:1.3
