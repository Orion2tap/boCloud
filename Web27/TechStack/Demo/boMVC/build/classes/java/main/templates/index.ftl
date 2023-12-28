<html>
<head>
    <meta charset="utf-8">
    <title>主页</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
          integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <#-- JianShu CSS -->
    <link rel="stylesheet" href="/static?file=1.css">
    <link rel="stylesheet" href="/static?file=2.css">

    <#-- 先引入jquery再引入bootstrap-->
    <#-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"
            integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd"
            crossorigin="anonymous"></script>
</head>
<body>

<nav>
    <ul class="nav nav-pills">
        <li role="presentation" class="active"><a href="/">Home</a></li>
        <li role="presentation"><a href="/login">Login</a></li>
        <li role="presentation"><a href="/register">Register</a></li>
        <li role="presentation"><a href="/todo">TODO</a></li>
    </ul>
</nav>

<div class="_3VRLsv" role="main">
    <div class="_gp-ck">
        <h3>
            <a>你好,${u.username}</a>
            <img src='/static?file=bobo.jpg'>
            <img src='/static?file=binbin.jpg'>
        </h3>
    </div>
    <#--侧边栏-->
    <aside class="_2OwGUo">
        <section class="_3Z3nHf">
            <div class="_3Oo-T1"><a class="_1b5rv9 _1OhGeD" target="_blank"
                                    rel="noopener noreferrer"><img src='/static?file=cat.jpg'>
                <div class="_32ZTTG">
                    <div class="_1pXc22">${u.id}</div>
                    <div class="_1pXc22">${u.username}</div>
                    <div class="_1pXc22">${u.role}</div>
                </div>
            </div>
            <div class="_19DgIp"></div>

        </section>
        <div>
            <div class="">
                <section class="_3Z3nHf">
                    <h3 class="QHRnq8 QxT4hD"><span>XXX</span></h3>
                </section>
            </div>
        </div>
    </aside>
</div>

</body>
</html>