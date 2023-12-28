<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Todo 编辑</title>
</head>
<body>
<h1>Todo 编辑</h1>

<form action="/todo/update" method="post">
    <input type="text" name="id" value="${todo.id}" placeholder="请输入 todo_id">
    <br/>
    <input type="text" name="content" value="${todo.content}" placeholder="请输入 todo">
    <br/>
    <button type="submit">修改</button>
</form>

</body>
</html>