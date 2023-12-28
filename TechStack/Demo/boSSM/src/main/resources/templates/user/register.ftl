<html>
    <head>
        <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
        <meta charset="utf-8">
        <!-- title 是浏览器显示的页面标题 -->
        <title>注册</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
              integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
        <#-- 先引入jquery再引入bootstrap-->
        <#-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"
                integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd"
                crossorigin="anonymous"></script>
    </head>
    <body>
        <nav>
            <ul class="nav nav-pills">
                <li role="presentation"><a href="/">首页</a></li>
                <li role="presentation" class="active"><a href="/register">注册</a></li>
                <li role="presentation"><a href="/login">登录</a></li>
                <#--        <li role="presentation"><a href="/todo">TODO</a></li>-->
            </ul>
        </nav>

        <form action="/user/add" method="post">
            <input type="text" name="username" placeholder="请输入用户名">
            <br>
            <input type="text" name="password" placeholder="请输入密码">
            <br>
            <button type="submit">注册</button>
        </form>
    </body>
</html>