### git 使用
  * 版本管理软件 记录了哪行文字是谁在什么时候什么时间写的
  * 去中心化, 分布式的版本管理 (对比 SVN 中心化)
  * 安装注意事项
    * 编辑器不选vim 选nano
    * ssh 选择 use external openssh

### git 命令行常见命令
  1. 初始化仓库
    * 新建TestGit文件夹
    * 终端进入TestGit文件夹内
    * `git init` 初始化
  2. 设置用户邮箱 `git config --global user.email "you@example.com"`
  3. 设置用户名 `git config --global user.name "Your Name"`
  4. 新建a.txt
    * 查看状态 `git status`
    ```
        On branch master
        No commits yet
        Untracked files:
          (use "git add <file>..." to include in what will be committed)
                a.txt
        nothing added to commit but untracked files present (use "git add" to track)
    ```
    * 加入追踪 `git add a.txt`
    * 提交修改 `git commit -m "first commit"`
    ```
        [master (root-commit) 322bdb1] first commit
         1 file changed, 0 insertions(+), 0 deletions(-)
         create mode 100644 a.txt
    ```
    * 查看日志 `git log`
    ```
        commit 322bdb153e98f9ddc1dfd7b81acd78eef498e100 (HEAD -> master)
        Author: Your Name <you@example.com>
        Date:   Tue Aug 30 20:52:23 2022 +0800

            first commit
    ```
  5. 修改a.txt
    * 加入追踪 `git add a.txt`
    * 提交修改 `git commit -m "second commit"`
  6. 继续修改a.txt
    * 查看变动 `git diff a.txt`
    ```
        diff --git a/a.txt b/a.txt
        index 58c9bdf..a30a52a 100644
        --- a/a.txt
        +++ b/a.txt
        @@ -1 +1,2 @@
         111
        +222
    ```
  7. 分支相关 (17-3 29min起)
    * git branch 查看分支
    * git checkout 分支切换
  8. 本地 git 和 服务器 git 的同步命令
    * git clone
    * git pull
    * git push

### SourceTree 图形化git工具
  * 使用 17-2.git+SourceTree

### Coding 平台
#### HTTPS 方式 git clone 和 git push
  * 创建项目
  * 创建仓库 生成HTTPS链接
  * 克隆仓库
    1. 通过终端 `git clone https://e.coding.net/liubobo1996/java/publicJava.git`
    2. 通过SourceTree -> New tab -> Clone
  * 新建a.txt 通过本地的SourceTree提交
  * 推送提交
    1. 通过终端 `git push ` 输入Coding平台的用户名和密码
    2. 通过SourceTree -> 推送 -> 输入Coding平台的用户名和密码

#### SSH 方式 git clone 和 git push
  * Coding平台 -> 个人账户设置 -> SSH公钥 -> 新增公钥 -> 公钥内容 id_rsa.pub
  * 克隆仓库
    1. 通过终端 `git clone git@e.coding.net:liubobo1996/java/publicJava.git`
      * 更新本地 .ssh/known_hosts 文件
    2. 通过SourceTree -> New tab -> Clone
  * 新建test.txt 通过本地的SourceTree提交    
  * 推送提交
    1. 通过终端 `git push` (C:\Users\o\desktop\remote\publicJava)

#### SSH 方式 git pull
  * 在Coding平台的publicJava项目内新建test2文件
  * 打开 SourceTree 工具 -> 选项 -> SSH客户端配置
    * SSH密钥 C:\Users\o\.ssh\id_rsa
    * SSH客户端 OpenSSH
  * 拉取

### 服务器
  1. 登录服务器 `ssh root@119.45.22.158`
  2. 生成密钥 `ssh-keygen`
  3. 复制公钥 `cat /root/.ssh/id_rsa.pub`
  4. Coding平台 -> 个人账户设置 -> SSH公钥 -> 新增公钥 -> 公钥内容 id_rsa.pub
  5. 从Coding平台拉项目到服务器的/var/www `git pull ...` (服务器上不改动项目)
  6. 可以将服务器公钥添加到Coding平台的部署公钥 这样服务器只能拉项目不能推送修改

### 其他
  * Mac上使用SourceTree如果找不到密钥 `ssh-add -K ~/.ssh/id_rsa`
