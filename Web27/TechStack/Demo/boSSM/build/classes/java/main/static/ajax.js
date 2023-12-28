// AJAX (Asynchronous JavaScript and XML)
// 浏览器提供的用 JS 异步获取链接内容的 API

var ajax = function (method, path, data, callback) {
    // 创建 AJAX 对象
    let r = new XMLHttpRequest();
    // // 设置 AJAX 请求的方法和地址
    r.open(method, path, true)

    // 注册响应函数
    r.onreadystatechange = function () {
        if (r.readyState === 4 && r.status === 200) {
            // 执行回调函数
            callback(r.response);
        }
    }

    // 发送请求
    r.send(JSON.stringify(data))
}

var url = "https://www.kuaibiancheng.com/sandbox/todo/3587405093/all"
var method = "GET";
var data = ""

ajax(method, url, data, function (response) {
    console.log("response: ", response)
    console.log("response after parse: ", JSON.parse(response))

})