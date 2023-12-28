<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>TopicComment 编辑</title>
</head>
<body>
    <h1>TopicComment 编辑</h1>
    <form action="/topicComment/update" method="post">
        <a>${comment.id}</a>
        <input name="id" value="${comment.id}" hidden>
        <br>
        <textarea name="content">${comment.content}</textarea>
<#--        <input name="content" value="${topic.content}">-->
        <br>
        <button type="submit">更新</button>
    </form>
</body>
</html>
