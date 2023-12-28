<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${board.name}</title>
</head>
<body>
<h1>${board.name}</h1>

<div>
    <#list topics as t>
    <h3>
        <a href="/topic/detail/${t.id}">
            ${t.id}: ${t.title}
        </a>
    </h3>
    <a href="/topic/edit?id=${t.id}">编辑</a>
    <a href="/topic/delete?id=${t.id}">删除</a>
    <a>创建时间: ${t.createdTime}</a>
    <a>修改时间: ${t.updatedTime}</a>
    </#list>
</div>

<form action="/topic/add/${board.id}" method="POST">
    <input type="text" name="title" placeholder="请输入 title">
    <br>
    <input type="text" name="content" placeholder="请输入 content">
    <br>
    <button type="submit">添加</button>
</form>

</body>
</html>