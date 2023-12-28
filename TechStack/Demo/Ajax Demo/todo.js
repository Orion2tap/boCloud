var log = console.log.bind(console)

var e = function (selector) {
    return document.querySelector(selector)
}
log("todo.js xxxx")

/*
1. 给 add button 绑定事件
2. 在事件处理函数中，获取 input 的值
3. 用获取的值，组装一个 todo-cell HTML 字符串
4. 插入 todo-list 中
 */

var todoTemplate = function(todo) {
    var t  = `
       <div class="todo-cell">
            <span>${todo}</span>
            <button class="button-delete">删除</button>
        </div>
    `
    return t;
}

var insertTodo = function (todoCell) {
    var todoList = e("#id-todo-list")
    todoList.insertAdjacentHTML("beforeend", todoCell)
}

// 给button绑定click事件
var bindButtonClick = function () {
    var b = e("#id-button-add")
    log("b: ", b)
    b.addEventListener("click", function () {
        log("click")
        var input = e("#id-input-todo")
        log("input value: ", input.value)
        var todoCell = todoTemplate(input.value)
        insertTodo(todoCell)
    })
}

// 给div绑定click事件
var bindDivClick = function () {
    var d = e("#id-todo-list")
    d.addEventListener("click", function (event) {
        /*
            <div id="id-todo-list">
                <div class="todo-cell">
                    <span>吃饭</span>
                    <button class="button-delete">删除</button>
                </div>
            </div>
         */
        // 获得当前元素
        var target = event.target
        // 如果当前元素存在 class="button-delete" 属性
        if (target.classList.contains("button-delete")) {
            // Element.closest() 匹配特定选择器且离当前元素最近的祖先元素（可以是自己）
            // 获得离当前元素最近的 class="todo-cell" 的元素
            var cell = target.closest(".todo-cell")
            // 删除
            cell.remove()
        }
    })
}

var _main = function () {
    bindButtonClick()
    bindDivClick()
}

_main()