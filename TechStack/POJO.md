# POJO

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
