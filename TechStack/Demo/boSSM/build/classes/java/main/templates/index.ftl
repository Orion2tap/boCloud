<html>
<head>
    <meta charset="utf-8">
    <title>主页</title>
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
        <li role="presentation" class="active"><a href="/">首页</a></li>
        <li role="presentation"><a href="/register">注册</a></li>
        <li role="presentation"><a href="/login">登录</a></li>
<#--        <li role="presentation"><a href="/todo">TODO</a></li>-->
    </ul>
</nav>

<h3>
    <a>你好,${username}</a>
    <img src='/bobo.jpg' alt="bobo">
    <img src='/binbin.jpg' alt="binbin">
</h3>

</body>
</html>