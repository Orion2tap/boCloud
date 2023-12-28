<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Topic 编辑</title>
</head>
<body>
    <h1>Topic 编辑</h1>
    <form action="/topic/update" method="post">
        <a>${topic.id}</a>
        <a>${topic.title}</a>
        <input name="id" value="${topic.id}" hidden>
        <br>
        <input name="title" value="${topic.title}" hidden>
        <br>
        <textarea name="content">${topic.content}</textarea>
<#--        <input name="content" value="${topic.content}">-->
        <br>
        <button type="submit">更新</button>
    </form>
</body>
</html>
