<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>例子11111</title>
</head>

<body>
波波普通版
<h1>波波1号</h1>
<h2>波波2号</h2>
<h3>波波3号</h3>
<!-- form 是用来给服务器传递数据的 tag -->
<!-- action 属性是 path -->
<!-- method 属性是 HTTP方法 一般是 get 或者 post -->
<form action="/message" method="get">
    <!-- textarea 是一个文本域 -->
    <input name="author"/>
    <br/>
    <textarea name="message" rows="8" cols="40"></textarea>
    <br/>

    <!-- button type=submit 才可以提交表单 -->
    <button type="submit">GET 提交</button>
</form>

<form action="/message" method="post">
    <input name="author" />
    <br>
    <textarea name="message" rows="8" cols="40"></textarea>
    <br>
    <button type="submit">POST 提交</button>
</form>

<#list messages as m>
    <h3>
        ${m.author}: ${m.message}
    </h3>
</#list>

</body>
</html>
