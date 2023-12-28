// // 修改元素h1的文本内容
// let myHeading = document.querySelector('h1');
// myHeading.textContent = 'Hello world!';

// 给按钮button绑定鼠标点击事件
// const button = document.querySelector('button');
// button.onclick = function() {
//   let name = prompt('what is your name?');
//   alert('hello ' + name + ', nice to meet you!');
// }

// 给图片绑定鼠标点击事件
// document.querySelector('img').onclick = function() {
//   alert('binbin is a pig');
// }

// 匿名函数
// document.querySelector('h1').addEventListener('click', () => {
//   alert('I am a pig');
// })

// 图像切换
let currentImg = document.querySelector('img');

currentImg.onclick = function() {
  let currentSrc = currentImg.getAttribute('src');
  // === 类型相同 值也相同 才相等
  // if (currentImg === 'images/bobo.jpg') {
  if (currentSrc === 'images/bobo.jpg') {
    currentImg.setAttribute('src', 'images/kunkun.jpg');
    // console.log("路径1");
  } else {
    currentImg.setAttribute('src', 'images/bobo.jpg');
    // console.log("路径2");
  }
}


// 个性化欢迎信息
let myButton = document.querySelector('button');
let myHeading = document.querySelector('h1');

function setUserName() {
  let myName = prompt('请输入你的名字。');
  if(!myName) {
    setUserName();
  } else {
    localStorage.setItem('name', myName);
    myHeading.textContent = 'hello，' + myName;
  }
}

function getUserName() {
  if(!localStorage.getItem('name')) {
    // 键名不存在于localStorage则返回 null, 判断为true并设置name
    setUserName();
  } else {
    let storedName = localStorage.getItem('name');
    myHeading.textContent = 'hello, ' + storedName;
  }
}

myButton.onclick = function() {
  setUserName();
}

getUserName();
