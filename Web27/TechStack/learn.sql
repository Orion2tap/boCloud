-- Customers, 顾客信息
-- OrderItems, 订单中的物品
-- Orders, 订单表
-- Products, 产品目录
-- Vendors, 供应商

-- 2. 检索数据

-- 去重
select distinct vend_id, prod_price
from products

-- 限制结果
-- limit 4 offset 2 等价于 limit 2, 4
select prod_name
from products

-- 3. 排序检索数据

-- 按相对位置排序 (前提是排序用的列都被检索)
-- 先按 prod_price 排序, 后按 prod_name 排序
-- order by 位置必须在 where 之后且位于 select 语句末尾
select prod_id, prod_price, prod_name
from products
where ...
order by 2, 3

-- 4. 过滤数据

-- 范围值检查
select prod_name
from products
where prod_price between 5 and 10

-- 空值检查
select prod_name
from products
where prod_price is null

-- 5. 高级数据过滤

-- 优先级 and > or
select prod_name
from products
where
    (vend_id = 'DLL01' or
    vend_id = 'BRS01') and
    prod_price >= 10

-- 用 in 代替一组 or 操作符
select prod_name
from products
where vend_id in (
    'DLL01',
    'BRS01',
    'RYL01'
)

-- 用 not 否定其后的条件
select prod_name
from products
where not vend_id = 'DLL01'

-- 6. 通配符过滤

-- % 表示匹配给定位置的 0 个, 1 个或多个字符
-- 'fish%' 以 fish 开头
-- '%fish%' 包含 fish
-- 'F%h' 以 F 开头, 以 h 结尾
-- 'F%h%' 以 F 开头, 以 h 结尾, h 之后为空格
-- '%' 除 null 之外的行
select prod_name
from products
where prod_name like 'fish%'

-- _ 表示匹配给定位置的 1 个或多个字符
select prod_name
from products
where vend_id like '_fish'

-- [] 表示匹配指定字符集中的 1 个字符
-- '[JM]%' 匹配名字以 J 或 M 开头的联系人
-- '[^JM]%' 匹配名字不以 J 或 M 开头的联系人
select cust_contact
from customers
where cust_contact like '[JM]%' -- TODO 查询不到理想结果

-- 7. 创建计算字段

-- 拼接字段
select concat(vend_name, ' (', vend_country, ')') as vend_title
from Vendors
order by vend_name

-- 过滤空格
-- ltrim 去掉左边空格
-- rtrim 去掉右边空格
-- trim 去掉两边空格
select rtrim(vend_name)
from vendors
order by vend_name

-- 8. 使用函数处理数据

-- MySQL 5.7 支持的函数
-- https://www.docs4dev.com/docs/zh/mysql/5.7/reference/function-reference.html

-- 9. 汇总数据

-- count(*) 既能对整个表统计行数, 包括 null
-- count(..) 也能对指定列统计行数, 忽略 null
select count(*)
from customers

-- avg() 只能用于对指定列取均值
-- 忽略 prod_price 为 null 的行
select avg(prod_price)
from products

-- max() 只能用于对指定列取最大值
-- 对于字符串, MySQL 返回最大字符串值
select max(prod_name)
from products

-- sum() 只能用于对指定列取和
select sum(prod_price)
from products

-- 10. 分组数据

-- where 数据分组前过滤
-- having 数据分组后过滤
select vend_id, count(*) as num_prods
from products
where prod_price >= 4
group by vend_id
having num_prods >= 2
order by ...

-- 11. 使用子查询

-- 使用 CTE (Common Table Expression) 代替子查询
with ordered_details as (
    select
        user_id,
        name,
        row_number() over (partition by user_id order by date_updated desc) as details_rank
    from billingdaddy.billing_stored_details
),

final as (
    select user_id, name
    from ordered_details
    where details_rank = 1
)

select * from final

-- 12. 联结表

-- cross join 返回笛卡尔积的联结
select
    vend_name,
    prod_name,
    prod_price
from
    vendors,
    products

-- 等价于 inner join
select
    vend_name,
    prod_name,
    prod_price
from
    vendors,
    products
where vendors.vend_id = products.vend_id

-- 13. 创建高级联结

-- self join 需要给表起别名
select
    c1.cust_id,
    c1.cust_name,
    c1.cust_contact
from
    customers as c1,
    customers as c2
where
    c1.cust_name = c2.cust_name and
    c2.cust_name = 'Jim Jones'

-- https://github.com/liubobo1996/boboWeb/raw/master/MyPic/join.webp
-- inner join
-- left join (全称 left outer join)
-- right join (全称 right outer join)
-- full join (全称 full outer join)

-- 14. 组合查询

-- 在保证性能前提下, 适用于过滤条件复杂或从多个表检索数据
-- 默认去重, 不去重使用 union all
-- order by 只能有一条且位于末尾, 效果是对总查询结果排序
select ...
from ...
where 条件 A
union
select ...
from ...
where 条件 B
order by ...
-- 等价于
select ...
from ...
where
    条件 A or
    条件 B
order by ...

-- 15. 插入数据

-- 插入完整的一行
-- 明确列名的前提下, 列之间顺序不影响插入结果
insert into customers (
    cust_id,
    cust_name,
    cust_address,
    ...
)
values (
    '1000000006',
    'Toy Land',
    '123 Any Street',
    ...
)

-- 插入不完整的一行
-- 前提是未插入的列允许 null 或存在默认值
insert into customers (
    cust_id,
    cust_name
)
values (
    '1000000006',
    'Toy Land'
)

-- 插入检索出的数据 (insert select)
-- customers 表与 tableA 表结构必须相同
-- 插入后 customers 表中主键 (cust_id) 不能存在重复
-- 列名可以不匹配, 插入遵守的是一一对应原则
insert into customers (
    cust_id,
    cust_name
)
select (
    id,
    name
)
from tableA

-- 复制表
-- 可以在复制的表上测试 sql 代码而不影响原表数据
create table custcopy as
select *
from customers;

-- 16. 更新和删除数据

-- 没有 where 将导致更新所有行
-- 通过设置 null 实现删除某个列的值
update customers
set
    cust_contact = 'Sam Roberts',
    cust_email = null
where cust_id = '1000000005'

-- 先用 select 对 where 子句测试, 明确要操作的数据
-- 然后在 update 和 delete 中使用该 where 子句

-- 17. 创建和操纵表

-- alter table 前应对表结构和表数据做备份

-- 18. 使用视图

-- 创建
create view product_customers as
select
    cust_name,
    cust_contact,
    prod_id
from
    customers,
    orders,
    orderitems
where
    customers.cust_id = orders.cust_id and
    orderitems.order_num = orders.order_num

-- 使用
select
    cust_name,
    cust_contact
from product_customers
where prod_id = 'RGAN01'

-- 19. 使用存储过程

-- 创建
-- 把查询结果赋值给 int 类型的参数 s 输出
create procedure proc1(out s int)
begin
    select count(*)
    into s
    from user;
end
-- 调用
call proc1(...);

-- 20. 管理事务处理

--- 声明保留点 回滚到保留点
start transaction
savepoint point
insert into ...
rollback to savepoint point
commit

-- 21. 使用游标

-- MySQL 游标只能用于存储过程和函数
-- 修改默认分隔符 (; 改为 //)
delimiter //
-- 创建存储过程
create procedure proce_order()
begin
    -- 创建局部变量
    declare num int;
    -- 创建游标
    declare ordernumbers cursor
    for
    select order_num
    from orders;
    -- 打开游标
    open ordernumbers;
    -- 获取第一行数据
    fetch ordernumbers into num;
    -- 查询结果
    select num;
    -- 关闭游标
    close ordernumbers;
end;
-- 调用存储过程
call proce_order();

-- 22. 高级 SQL 特性

-- 约束的类型
-- 1. 主键
alter table vendors
add constraint primary key (vend_id)
-- 2. 外键 (MySQL 会先增加索引)
alter table `ssm`.`topicstar`
    add index `FK_Topicstar_topicId_Topic_id_idx` (`topicId` asc);
alter table `ssm`.`topicstar`
    add constraint `FK_Topicstar_topicId_Topic_id`
        foreign key (`topicId`)
        references `ssm`.`topic` (`id`)
        on delete cascade
        on update cascade;
-- 3. 唯一约束
create table customers (
    cust_id integer not null,
    cust_email  varchar(100) not null,
    unique (cust_email)
)
-- 4. 检查约束
create table order_items (
    order_num integer not null,
    quantity  integer not null check (quantity > 0)
)

-- 索引
create index prod_name_index
on products (prod_name)

-- 触发器

-- 修改默认分隔符 (; 改为 //)
delimiter //
-- 创建触发器，触发器名称为 t1
create trigger t1
    -- 触发器执行在 update 操作之后
    after update
    -- 监视 tableA 表
    on tableA
    for each row
begin
    -- 触发执行的 SQL 语句
    update tb_class set num = num + 1 where id = 1;
end //
