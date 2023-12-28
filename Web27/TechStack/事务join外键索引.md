#### 事务
```bash
# 提交事务
START TRANSACTION;
SELECT *
	FROM sqljoin.user;
INSERT
	INTO sqljoin.user
		(id, username, role)
			values ('3', '用户3', '1');
COMMIT;

# 回滚事务
START TRANSACTION;
SELECT *
	FROM sqljoin.user;
INSERT
	INTO sqljoin.user
		(id, username, role)
			values ('3', '用户3', '1');
ROLLBACK;

# 事务的性质 (ACID)
1. 原子性 Atomicity
# 事务是最小的执行单位，不允许分割。事务的原子性确保动作要么全部完成，要么完全不起作用；
2. 一致性 Consistency
# 执行事务前后，数据保持一致，比如 Topic userId 的 id 始终是存的 User 表里的 id.
3. 隔离性 Isolation
# 并发访问数据库时，一个用户的事务不被其他事务所干扰，各并发事务之间数据库是独立的；(COMMIT 之前的操作对其他数据库访问者不可见)
4. 持久性 Durability
# 一个事务被提交之后。它对数据库中数据的改变是持久的，即使数据库发生故障也不应该对其有任何影响。

# 并发事务带来的问题 (原始数据为[t0 c0])
		# 脏读
		    (用户1改到一半用户2读)
		    1. 用户1修改标题            [t1 c0]
		    2. 用户2读新标题旧内容       [t1 c0]
		    3. 用户1修改内容            [t1 c1]
		# 脏写
		    又叫丢失修改, 用户1改到一半用户2改
		    1. 用户1修改标题 [t1 c0]
		    2. 用户2修改标题 [t2 c0]
		    3. 用户2修改内容 [t2 c2]
		    4. 用户1修改内容 [t2 c1]
		# 不可重复读
		    用户1读,用户2改,用户1再读发现被修改
		    1. 用户1读数据   [t0 c0]
		    2. 用户2改数据   [t2 c2]
		    3. 用户1读数据   [t2 c2]
		# 幻读
		    用户1读,用户2增删,用户1再读发现多了或少了数据
		    1. 用户1读数据   [1 t0 c0]
		    2. 用户2插数据   [1 t0 c0] [2 t0 c0]
		    3. 用户1读数据   [1 t0 c0] [2 t0 c0]

# 不可重复读和幻读的区别
		# 不可重复读: 读数据时数据被修改
		# 幻读:读数据时数据被插入或删除

```
###### 事务隔离级别
  ![图片_事务隔离级别](https://github.com/liubobo1996/MyPic/raw/main/%E4%BA%8B%E5%8A%A1%E9%9A%94%E7%A6%BB%E7%BA%A7%E5%88%AB.png)

#### join
```bash
SELECT * FROM TopicComment
inner join Topic
on Topic.id = TopicComment.topicId
where Topic.id = 1

# 当on条件不满足时 仍旧取出左边表格的数据 数据不存在用NULL填充
SELECT * FROM Topic
left join TopicComment
on Topic.id = TopicComment.topicId
where Topic.id = 3
```

#### 外键
```bash
# 在 workbench 中 选中 sqljoin.topiccomment 表
# 右键 -> Alter Table -> Foreign Keys
1. Foreign Key Name 			
		* FK_topicId_Topic_id
2. Referenced Table 			
		* `ssm`.`topic`
3. Column 								
		* topicId (topiccomment 表的 topicId)
4. Referenced Column 			
		* id (topic 表的 id, 必须是主键)
5. On Update 							
		* CASCADE (级联更新)
6. On Delete 							
		* NO ACTION (级联删除一般不启用)
```

#### 索引
```bash
# 哈希索引(适用于等值查询, 不支持范围查询和模糊查询等)

# B+树索引
![](assets/markdown-img-paste-20200905111814177.png)
# 主键索引 (比如以 id 为索引 且 id 为主键)
		1. 树节点存储多条间断的 id
		2. 叶子节点存储数据[聚簇索引]
		3. 树结点之间存在 id 范围关系
		4. 叶子节点之间形成链表结构
# 二级索引 (比如以 username 为索引)
		1. 树节点存储多条间断的 username
		2. 叶子节点不存储数据只存储主键的值 (id)[非聚簇索引]
		3. 树结点之间存在 username 范围关系
		4. 叶子节点之间形成链表结构
# 覆盖索引
		sql 要返回的值刚好是索引的 key
# 非覆盖索引
		需要多找一次主键索引, 这叫回表
# 联合索引
![](assets/markdown-img-paste-20200905123516490.png)
# 唯一索引
		可防止插入重复数据
# 联合唯一索引
# 全文索引						
```
