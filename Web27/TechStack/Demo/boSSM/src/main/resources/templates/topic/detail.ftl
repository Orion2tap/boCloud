<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${topic.title}</title>
</head>
<body>
    <h1>${topic.title}</h1>
    <h3>${topic.content}</h3>

    <#list topic.comments as c>
        <div>
            ${c.id}: ${c.content}  --- ${c.user.username}
        </div>
        <a href="/topicComment/edit?id=${c.id}">编辑</a>
        <a href="/topicComment/delete?id=${c.id}">删除</a>
        <a>创建时间: ${c.createdTime}</a>
        <a>修改时间: ${c.updatedTime}</a>
    </#list>

    <form action="/topicComment/add/${topic.id}" method="POST">
        <input type="text" name="content" placeholder="请输入 content">
        <br>
        <button type="submit">发表评论</button>
    </form>
</body>
</html>