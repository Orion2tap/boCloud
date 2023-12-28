<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Ajax Todo 首页</title>
</head>
<body>
<input type="text" id="id-input-todo">
<button id="id-button-add">添加 todo</button>

<div id="id-todo-list">

</div>

<script>
var log = console.log.bind(console)

var e = function (selector) {
    return document.querySelector(selector)
}

/*
1. 给 add button 绑定事件
2. 事件处理函数中, 获取 input 的值
3. 用获取的值, 组装一个 todo-cell HTML 字符串
4. 插入到 div id-todo-list 中

 */

var todoTemplate = function (todo) {
    var t = `
    <div class="todo-cell">
        <span class="span-id-content">${todo.id}: ${todo.content}</span>
        <button class="button-delete">删除</button>
    </div>
    `
    return t
}

var insertTodo = function (todoCell) {
    var list = e("#id-todo-list")
    list.insertAdjacentHTML("beforeend", todoCell)
}

var ajax = function (method, path, data, callback) {
    let r = new XMLHttpRequest()
    r.open(method, path, true)
    r.setRequestHeader("Content-Type", "application/json")

    r.onreadystatechange = function() {
        if (r.readyState === 4) {
            callback(r.response)
        }
    }

    let d = JSON.stringify(data)
    r.send(d)
}

var bindButtonClick = function () {
    var button = e("#id-button-add")
    button.addEventListener("click", function () {
        log("click")
        var input = e("#id-input-todo")
        log("html input: ", input)
        var todo = input.value
        log("输入框值", todo)


        var path = "/ajax/todo/add"
        var method = "POST"
        var data = {
            content: todo
        }
        ajax(method, path, data, function (response) {
            log("response:", response)
            var todoResponse = JSON.parse(response)
            var todoCell = todoTemplate(todoResponse)
            // log("todoCell: ", todoCell)
            insertTodo(todoCell)
        })
    })
}


// 事件委托
var bindDivDeleteClick = function () {
    var button = e("#id-todo-list")
    button.addEventListener("click", function (event) {
        log("delete")
        var target = event.target
        log("target: ", target)

        if (target.classList.contains("button-delete")) {
            var todo = target.closest(".todo-cell")
            log("closet todo: ", todo)
            // todo.remove()

            // 3: 吃了吗
            var id_content = todo.children[0].textContent
            var id = id_content.split(":")[0]
            log("delete todo id : ", id)

            // 发送 Ajax 请求
            var path = "/ajax/todo/delete"
            var method = "POST"
            var data = {
                id: id
            }

            ajax(method, path, data, function (response) {
                log("response:", response)
                todo.remove()
            })
        }
    })
}

var loadTodos = function () {
    var path = "/ajax/todo/all"
    var method = "POST"
    var data = ""
    ajax(method, path, data, function (response) {
        log("todo all: ", response)
        var todoList = JSON.parse(response)
        log("todo all: ", todoList)
        //
        for (let i = 0; i < todoList.length; i++) {
            var todo = todoList[i]
            var html = todoTemplate(todo)
            log("insert html", html)
            insertTodo(html)
        }
    })
}

var _main = function () {

    bindButtonClick()
    bindDivDeleteClick()
    loadTodos()
}

_main()

</script>

</body>
</html>