# 一、库/表/字段





## 字段

- 字段名必须为小写字母，不得以数字开头





表示是和否的字段: 

- is开头，类型为unsigned tinyint，长度为1



对于不可能为负的字段

- 必须使用unsigned





- 小数必须为decimal

















## 表





**强制**

表内必须要有的三个字段:

- id: 必须为主键其自增，类型必须为 unsigned bigint
- create_time: 必须为datetime
- update_time:必须为datetime 







- 表名必须为小写字母，不得以数字开头
- 表名不能写成复数
- 表名不能为关键字





单表容量超过2GB，单表行数超过500万行，需要分库分表









## 索引

主键索引: pk_****

唯一键索引: uk_***









## 库



- 库名必须为小写

****













# 二、索引

**新手不建议做**





- 对于唯一特性的字段(即使是多个字段的组合)，**应该设置唯一索引**，防止产生垃圾数据/脏数据

- 多表查询在开发中不允许数据类型不同，**查询的两个匹配字段都应该有索引**
- 在varchar上建立索引时，必须给定索引的长度

****















# 三、SQL语句



不能通过写出所有字段来代替*

```mysql
count(all_columns) != count(*)
```

*能统计出null







不能使用"="取判断null，只能使用IS NULL或者ISNULL()函数

```mysql
column = null (x)
column IS NULL (v)
ISNULL(column)
```







- 页面查询禁止左模糊或者全模糊





- 不要使用外键和级联(尤其是在高并发集群项目中)

- 外键和级联应该在service层/应用层解决









**注：实际开发过程中不得使用存储过程**(不好调试，中间容易出错，没法扩展，没有移植性)







做数据更新之前:

- 需要先进行查询，核对后再更新







子查询in操作应该避免







数据库编码格式应该统一为UTF-8

****









































