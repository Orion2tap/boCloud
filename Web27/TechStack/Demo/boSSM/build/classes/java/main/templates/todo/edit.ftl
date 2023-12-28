<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Todo 编辑</title>
</head>
<body>
    <h1>Todo 编辑</h1>

    <form action="/todo/update" method="post">
        <input name="id" value="${todo.id}" hidden>
        <br/>
        <input name="content" value="${todo.content}">
        <br/>
        <button type="submit">修改</button>
    </form>
</body>
</html>