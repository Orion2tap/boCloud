<html>
    <head>
        <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
        <meta charset="utf-8">
        <!-- title 是浏览器显示的页面标题 -->
        <title>admin</title>
    </head>
    <body>
        <form action="/admin/user/update" method="post">
            <input type="text" name="id" placeholder="用户id">
            <br/>
            <input type="text" name="password" placeholder="修改密码">
            <br/>
            <button type="submit">修改</button>
        </form>

        <#list users as u>
            <h3>
                ${u.username}: ${u.password}
            </h3>
        </#list>

    </body>
</html>