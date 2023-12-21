# TechStack&BUG

- [TechStack\&BUG](#techstackbug)
  - [最小化可行产品 MVP Minimum Viable Product](#最小化可行产品-mvp-minimum-viable-product)
    - [自底向上实现](#自底向上实现)
    - [层次](#层次)
  - [POJO(VO DTO BO/DO PO) + DAO](#pojovo-dto-bodo-po--dao)

## 最小化可行产品 MVP Minimum Viable Product

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

## POJO(VO DTO BO/DO PO) + DAO

```bash
# https://zhuanlan.zhihu.com/p/102389552
POJO（plain ordinary java object）
# 简单无规则java对象, 以下都属于该类对象
        VO（value object）
        # 值对象/视图对象，后端 Controller 的返回值, 返回给前端的数据对象
        DTO（Data Transfer Object）
        # 后端 Controller 内定义的 Java 对象, 用于项目内跨层传输
        BO（business objectr）
        # 业务对象, 根据具体的业务场景封装的对象, PO + PO + PO = BO, 钉子 + 锤子 + 钳子 = 工具箱
        DO（Domain Object）
        # 领域对象, 针对具体业务数据组成抽象出来的类, 等价于BO
        PO（persistant object）
        # 持久对象, 对应数据库中某个表中的一条记录
DAO（data access object）
# 数据访问对象, 对应数据库的 Mapper
```
