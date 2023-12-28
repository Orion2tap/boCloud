### CSS  
  * 三种选择器
  * 样式优先级
  * 选择器优先级
  * 同优先级 后面的样式覆盖前面的样式
  * display
  * position
  * overflow
  * 盒模型
  * float
  * 伪元素
  * 垂直居中和水平居中
  * 伪类
  * 套CSS: BootStrap
  * 抄CSS:
    * 将目标网站的CSS文件逐个保存
    * 引入CSS
    * 如果要抄侧边栏 把侧边栏相关的整个HTML都抄过来
  * **修改routeImage 根据文件类型添加header**  

### note
```
- CSS 的使用
    - 内联 (inline style attribute)  完全不应该这样做
    - `<head>` 标签内的 `<style>` 标签   偶尔可以用
    - `<link>` 标签中的外联            推荐的方式

- 三种主要的选择器
    - 元素选择器
    - class 选择器
    - id 选择器

- 样式优先级(从高到低)
    1. !important
    2. 内联样式
    3. 按顺序执行

- 选择器优先级(从高到低)
    1. !important
    2. 内联样式
    3. id 选择器
    4. class 选择器
    5. 元素选择器

- display 属性
    - none
    - block
    - inline
    - inline-block

- none 不显示

- block 占一行
    - 默认 block 的标签有
    - div p ul ol li h1 h2 h3 h4 h5 h6

- inline 只占 content 的尺寸
    - 默认的标签有 button input span

- inline-block
    - inline-block 是 inline 布局 block 模式
    - inline-block 对外表现为 inline，所以可以和别的 inline 放在一行
    - 对内表现为 block，所以可以设置自身的宽高

- position 属性用于元素定位
    - static      默认定位
    - relative    相对定位, 相对于自己本来应该在的位置
    - absolute    绝对定位, 行为有点奇怪
    - fixed       固定定位, 相对位置是整个窗口

- 非 static 元素可以用 top left bottom right 属性来设置坐标
- 非 static 元素可以用 z-index 属性来设置显示层次
- relative 是相对定位
- absolute 完全绝对定位, 忽略其他所有东西
    - 往上浮动到 非 static 的元素
- fixed 基于 window 的绝对定位
    - 不随页面滚动改变

- overflow 属性
    - visible 默认
    - auto    需要的时候加滚动条
    - hidden  隐藏多余元素
    - scroll  就算用不着也会强制加滚动条

- 盒模型
    - 内容
    - padding
    - border
    - margin

盒模型相关的 CSS
----------------
- border
    - border-width
    - border-style    默认是 none, 表示不显示 border
    - border-color
    - 简写如下, 顺序不要紧：
    - `border: 10px blue solid;`

- border-top
    - border-top-width
    - border-top-style
    - border-top-color

- border-right
    - border-right-width
    - border-right-style
    - border-right-color

- border-bottom
    - border-bottom-width
    - border-bottom-style
    - border-bottom-color

- border-left
    - border-left-width
    - border-left-style
    - border-left-color

- margin
    - margin-top
    - margin-right
    - margin-bottom
    - margin-left

- padding
    - padding-top
    - padding-right
    - padding-bottom
    - padding-left

- 四种写法, 分别对应有 4 2 3 1 个值的时候的解释
    - `margin: top  right  bottom  left`
    - `margin: (top/bottom)  (right/left)`
    - `margin: top  (right/left)  bottom`
    - `margin: (top/right/bottom/left)`
    - padding 同理

- background 相关属性和缩写
    - `background-color: #233;`
    - `background-image: url(bg.png);`
    - `background-repeat: no-repeat;`
    - `background-attachment: fixed; /* 背景图片随滚动轴的移动方式 */`
- 缩写如下
    - `background: #233 url(bg.png) no-repeat;`

- float 属性(最初用于排版)
  - left
  - right
  - float 那一行相当于没有 后面的block元素会接上去

- 水平居中写法
    - block 元素居中, 两步走
        1. 设置自己的宽度
        2. 设置自己的 `margin: 0 auto`;

    - inline inline-block 元素居中
        1. 设置父元素的 text-align 属性
        2. text-align: center;

- 垂直居中
  - 记一下当套路 不需要理解机制
  - 需要父节点是 relative
```
