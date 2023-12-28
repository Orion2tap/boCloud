# 最小化可行产品 MVP Minimum Viable Product

### 自底向上实现

     1. **操作场景的文档** （对这些数据的操作）
          - 有一个主页，可看到所有todo
          - 主页有一个表单可以添加todo
     2. Model
     3. DataBase
          1. workbench 建表
          2. 记录 SQL 语句
     4. Mapper 接口 + XML 配置 (根据文档，实现 CRUD 和其他操作)
     5. Service
     6. Controller
     7. HTML
     8. JS
     9. CSS

### 层次

         View(HTML JS CSS)
          ↑
     TodoController
          ↑
     TodoService
          ↑
     TodoMapper
          ↑              [这一层绑定依靠 Spring MyBatis 动态代理]
     TodoMapper.xml      [标明对应哪个接口, 配置每个接口方法对应的 SQL 语句]
     ↑            ↑
     DataBase            TodoModel