<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Weibo 编辑</title>
</head>
<body>
    <h1>Weibo 编辑</h1>

    <form action="/weibo/update" method="post">
        <input name="id" value="${weibo.id}" hidden>
        <br/>
        <input name="content" value="${weibo.content}">
        <br/>
        <button type="submit">修改</button>
    </form>
</body>
</html>