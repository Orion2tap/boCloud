<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>SSM weibo</title>
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
<h1>Weibo</h1>
<div class="row">
    <div class="col-lg-6">
        <form action="/weibo/add" method="post">
            <div class="input-group">
                <input type="text" class="form-control" name="content" placeholder="请输入 weibo">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="submit">发表</button>
                </span>
            </div>
        </form>
    </div>
</div>

<#list weibos as w>
    <h3>
        ${w.id}: ${w.content}
        <a href="/weibo/delete?id=${w.id}">删除</a>
        <a href="/weibo/edit?id=${w.id}">编辑</a>
<#--        <a href="/weibo/complete?id=${w.id}">完成</a>-->
    </h3>
<#--    <h4>创建时间: ${w.createdTime}</h4>-->
<#--    <h4>修改时间: ${w.updatedTime}</h4>-->
</#list>
</body>
</html>