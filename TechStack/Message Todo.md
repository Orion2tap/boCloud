### [留言板]
#### 1. 交互一
  * 浏览器访问 http://localhost:9000/message
  * 服务器监听到请求 调用**socketReadAll**函数获取请求的原始数据
  * 生成一个Request类型的实例r 对原始请求数据进行**解析**
  * **responseForPath**函数根据请求的路径调用routeMessage函数
  * **routeMessage**函数根据请求类型GET将请求的query储存到data
  * 调用**MessageService**的load函数获取文件Message.txt的所有留言MessageList
    判断data为空 调用工具类中的html函数**载入html.basic.html**作为响应的body部分
  * 调用messageListHtml函数将messageList转换为String类型 并**替换页面上的标记**
  * **拼接完整响应**并转换成二进制后 服务器调用**socketSendAll**函数将响应发送给浏览器

#### 2. 交互二
  * 浏览器输入留言并点击"GET提交"按钮
  * 服务器监听到请求 调用socketReadAll函数获取请求的原始数据
  * 生成一个Request类型的实例r 对原始请求数据进行解析
  * responseForPath函数根据请求的路径调用routeMessage函数
  * routeMessage函数根据请求类型GET将请求的query储存到data
  * 调用MessageService的load函数载入文件Message.txt的所有留言MessageList
    判断data不为空 调用MessageService的save函数将data添加到Message.txt
  * 调用工具类中的html函数载入html.basic.html作为响应的body部分
  * 调用messageListHtml函数将messageList转换为String类型 并替换页面上的标记
  * 拼接完整响应并转换成二进制后 服务器调用socketSendAll函数将响应发送给浏览器

#### 3. 交互三
  * 浏览器输入留言并点击"POST提交"按钮
  * 服务器监听到请求 调用socketReadAll函数获取请求的原始数据
  * 生成一个Request类型的实例r 对原始请求数据进行解析
  * responseForPath函数根据请求的路径调用routeMessage函数
  * routeMessage函数根据请求类型POST将请求的form储存到data
  * 调用MessageService的load函数载入文件Message.txt的所有留言MessageList
     判断data不为空 调用MessageService的save函数将data添加到Message.txt
  * 调用工具类中的html函数载入html.basic.html作为响应的body部分
  * 调用messageListHtml函数将messageList转换为String类型 并替换页面上的标记
  * 拼接完整响应并转换成二进制后 服务器调用socketSendAll函数将响应发送给浏览器

### [Todo]
#### 1. 添加todo
1. 在todo\index.html页面 输入content 点击"添加"按钮
2. 浏览器访问 http://localhost:9000/todo/add
3. 服务器获取请求的原始数据并解析 根据路径调用RouteTodo类的add函数
4. 从数据的body部分获取form 传入TodoService的add函数
5. 从form的content属性中获取todo内容 添加其他属性后更新到数据库
6. 调用redirect函数重定向到/todo路由

#### 2. 删除todo
1. 在todo\index.html页面 对id为1的todo点击"删除"按钮
2. 浏览器访问 http://localhost:9000/todo/delete?id=1
3. 服务器获取请求的原始数据并解析 根据路径调用RouteTodo类的delete函数
4. 从数据的query部分获取todoId 传入TodoService的delete函数
5. 根据id删除指定todo并同步到数据库
6. 调用redirect函数重定向到/todo路由

#### 3. 修改todo
- 跳转到编辑todo页面
  1. 在todo\index.html页面 对id为1的todo点击"编辑"按钮
  2. 浏览器访问 http://localhost:9000/todo/edit?id=4
  3. 服务器获取请求的原始数据并解析 根据路径调用RouteTodo类的edit函数
  4. 从数据的query部分获取todoId 调用TodoService类的findById函数
  5. 调用Route类的html函数载入todo\edit.html作为响应的body部分
  6. 用todo的当前id及content替换掉页面上的标记
  7. 调用responseWithHeader函数 传入状态码、header和body 返回响应的字符串形式
  8. 将响应转换成二进制 服务器调用socketSendAll函数将响应发送给浏览器
- 修改todo
  1. 在todo\edit.html页面 修改todo之后点击"添加"按钮
  2. 浏览器访问 http://localhost:9000/todo/update
  3. 服务器获取请求的原始数据并解析 根据路径调用RouteTodo类的update函数
  4. 从数据的form部分获取id和content 传入TodoService的update函数
  5. 更新指定todo并同步到数据库
  6. 调用redirect函数重定向到/todo路由
