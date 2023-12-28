var log = console.log.bind(console)

var e = function (selector) {
    return document.querySelector(selector)
}
log("login.js xxxx")

/*

1. 2≤长度≤10
2. 第一位是字母
3. 最后一位只能是字母或数字
4. 只能包含字母、数字、下划线

legal = 'qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890_'
 */

// 用户名检查
// var usernameCheck = function (name) {
//     // Unicode: A~Z 65~90 a~z 97~122
//     if (name.length >= 2 && isLetter(name.charCodeAt(0))) {
//         log("检查合格")
//     } else {
//         log("用户名错误")
//     }
// }

// 用户名检查
var usernameCheck = function (name) {
    // 长度检查
    if (name.length < 2 || name.length > 10 ) {
        log('长度检查不通过')
        return false
    }

    // 首位检查
    if (!isLetter(name.charCodeAt(0))) {
        log('首位检查不通过')
        return false
    }

    // 末位检查
    if (!isLetter(name.charCodeAt(name.length - 1))&&!isDigit(name[name.length - 1])) {
        log('末位检查不通过')
        return false
    }

    // 只能包含字母、数字、下划线
    var legal = 'qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890_'
    for (let i = 0; i < name.length; i++) {
        if (legal.indexOf(name[i]) === -1) {
            log('逐位检查不通过')
            return false
        }
    }

    return true
}

// 判断是否为字母
var isLetter = function (code) {
    if ((code >= 65 && code <= 90) || (code >= 97 && code <= 122)) {
        return true
    } else {
        return false
    }
}

// 判断是否为数字
var isDigit = function (str) {
    for (let i = 0; i < 10; i++) {
        // 在比较两件事情时，双等号将执行类型转换; 三等号将进行相同的比较，而不进行类型转换
        // if (str === i) {
        //     return true
        // }
        if (str == i) {
            return true
        }
    }
    return false
}

// 给button绑定click事件
var bindButtonClick = function () {
    var button = e("#id-button-login")
    button.addEventListener('click', function () {
        var input = e('#id-username')

        var h3 = e('#id-check')
        if (usernameCheck(input.value)) {
            h3.innerText = '【检查合格】'
        } else {
            h3.innerText = '【用户名错误】'
        }
    })

}

var bindTargetClick = function () {
    var test = e('.test')
    test.addEventListener('click', function (event) {
        log('target:', event.target)
        log('innerText:', event.target.innerText)
        log('length:', event.target.length)
        log('classList:', event.target.classList)

    })
}

var _main = function () {
    // bindButtonClick()
    bindTargetClick()

}

_main()

