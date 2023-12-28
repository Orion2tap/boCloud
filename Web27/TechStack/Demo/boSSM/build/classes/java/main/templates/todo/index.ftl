<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Todo index</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
          integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"
            integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd"
            crossorigin="anonymous"></script>
    <#-- jQuery CDN-->
    <script src="https://cdn.jsdelivr.cn/npm/jquery@1.12.4/dist/jquery.min.js" integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ" crossorigin="anonymous"></script>

</head>
<body>

<h1>Todo 首页</h1>

<div class="row">
    <div class="col-lg-6">
        <form action="/todo/add" method="post">
            <div class="input-group">
                <label>
                    <input type="text" class="form-control" name="content" placeholder="请输入 todo">
                </label>
                <span class="input-group-btn">
                    <button class="btn btn-default" type="submit">添加</button>
                </span>
            </div>
        </form>
    </div>
</div>

<#list todos as t>
    <h3>
        ${t.id}: ${t.content}
        <a href="/todo/delete?id=${t.id}">删除</a>
        <a href="/todo/edit?id=${t.id}">编辑</a>
<#--        <a href="/todo/complete?id=${t.id}">完成</a>-->
    </h3>

<#--    <h4>创建时间: ${t.createdTime}</h4>-->
<#--    <h4>修改时间: ${t.updatedTime}</h4>-->

</#list>

</body>
</html>