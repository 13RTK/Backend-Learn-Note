











# 一、MySQL基础操作(数据库操作)





## 1.MySQL的开启和关闭

***注：`为反引号，位于TAB上方(Windows 10)**



### 1)win10 任务管理器 服务

### 2)终端命令：

```cmd
net stop/start mysql57
```



## 2.MySQL的连接和退出



### 1)终端命令连接：

```cmd
mysql -u root -p
```



### 2)终端命令退出：

```cmd
exit /q quit
```



## 3.库的创建



### 1)创建库

SQL语句：

```sql
create database database_name;
```



### 2)创建与关键字冲突的库：

SQL语句：

```sql
create database `database_name`;
```



### 3)避免重名而创建库：

SQL语句：

```sql
create database if not exists database_name;
```



## 4.库的删除



### 1)删除库：

SQL语句：

```sql
drop database database_name;
```



### 2)避免不存在而删除库：

SQL语句：

```sql
drop database if exists database_name;
```



### 3)删除与关键字冲突的库：

SQL语句：

```sql
drop database `database_name`;
```



## 5.查看创建库时使用的语句及字符编码

SQL语句：

```sql
show create database database_name;
```



## 6.创建库时添加字符编码

**(Windows学习可用gbk, 但Mac、Linux和实际生产中需用utf-8)**
SQL语句：

```sql
create database if not exists `database_name` charset=编码格式;
```



## 7.更改库的字符编码

SQL语句：

```sql
alter database database_name charset=编码;
```

****















# 二、MySQL基础操作(表操作)



## 操作表





### 1、使用库、显示库中的表



#### 1) 使用库

SQL语句：

```sql
use database_name;
```



#### 2) 显示库中所有表

SQL语句：

```sql
show tables;
```



### 2、创建表

#### 1)常规方法

SQL语句：

```sql
create table table_name(
parameter_1 parameter_1_type,
parameter_2 parameter_2_type,
...
);
```

**参数类型讲解**

```sql
int ：整数
varchar(lenght) ：字符串
```













#### 2)进阶方法

SQL语句：

```mysql
create table if not exists table_name(
parameter_1 int auto_increment primary key comment 'The comments',
parameter_2 varchar(length) not null comment '',
parameter_3 varchar(length) default 'something value' comment '',
...
)engine=innodb;
```

**相关讲解**

```mysql
auto_imcrement: 自增
primary key：主键(唯一的)
comment ''：注释
not null：不能为空(强制填写)
default ''：不主动填写后自动填充的默认值，不写defaul语句则会默认为null
engine=innodb：MySQL的默认引擎
```



**将已存在表的字段添加到新创建的表中**

```mysql
create table if not exists table_name select column1, column2... from exists_table_name
```









### 3、显示表(简单排列)

**不同于show table**

SQL语句

```sql
desc table_name;
```

**desc**:  *descending*                          **adj.**下降的；递降的



### 4、删除表

SQL语句

```sql
drop table if exists table_name_1, table_name_2, ...; 
```



### 5、修改表



#### 1)修改表名



SQL语句

```sql
alter table table_name rename to table_new_name;
```



#### 2)修改字段(column)



##### 一、添加字段(add column)



SQL语句

**默认方法：会默认添加到最后一行**

```sql
alter table table_name add column column_type() default 'The default info.' comment 'The value' after column_name;
```



SQL语句

**可用after指定位置**

```sql
alter table table_name add column column_type() after target_column;
```



SQL语句

**可用first置于首位**

```SQL 
alter table table_name add column column_type() first;
```





##### 二、删除字段(drop column)



SQL语句

```sql
alter table table_name drop column;
```



##### 三、修改字段信息



SQL语句

**修改字段名和类型(change)**

```sql
alter table table_name change column_name column_new_name column_new_type;
```

```sql
alter table table_name change column_name column_new_name column_new_type null_option comment 'The sentence of comment';
```



SQL语句

**修改字段类型(modify)**

注：还可添加其他参数

```sql
alter table table_name modify column_name column_new_type;
```

```sql
alter table table_name modify column_name column_new_type null_option comment 'The sentence of comment';
```







## 操作数据





### 1)添加数据

**注意：**

**1.primary key不能重复**

**2.除了not null的参数，其余都不能写为null**



SQL语句

**繁琐写法(参数顺序需要与table_name后一致)**

```sql
insert into table_name (column_1, column_2, ...) values(value_1, value_2, ...);
```



SQL语句

**默认写法(参数顺序和数量需要和table默认顺序和数量一样)**

```sql
insert into table_name values(value_1, value_2, ...);
```

**注：value值在允许前提下可以为null，也可以为default**



SQL语句

**自定义写法(可自己定义参数数量与顺序)**

```sql
insert into table_name (self_column_1, self_column_2, ...) values(value_1, value_2, ...)
```



SQL语句

**插入多条数据**

```sql
insert into table_name values(value_1, value_2, ...),(value_a, value_b, ...);
```





**跳过已经存在的数据**

```mysql
INSERT IGNORE INTO table_name values(...), (...) ...;
```













### 2)数据查询



**查询所有数据(不推荐使用)**

SQL语句

```sql
select * from table_name;
```



SQL语句

```sql
select column_1, column_2, ... from table_name;
```





### 3)删除数据

SQL语句

**删除符合where后条件的**

```sql
delete from table_name where column=target_value;
```

**注：column最好为一个唯一值，如选择有重复的column，则会删除多条数据**



SQL语句

**其余条件用法**

```sql
delete from table_name where condition_statement;
```

examples:

SQL语句

```sql
delete from table_name where column>value;
```





**删除表内所有数据(不建议)**

注：会进行遍历，会比较慢

SQL语句

```sql
delete from table_name;
```

**使用该方法删除后，再次添加数据时，编号会接着被删除的数据，所以不推荐使用**



**推荐删除方法**

SQL语句

```sql
truncate table table_name;
```





### 4)更新数据/更改数据

**注：**

**1.where后的condition最好不要有重复符合的**

**2.不带where的话所有column都会被更改**



SQL语句

```mysql
update table_name set edited_1, edited_2 where column_condition;
```

- 注意更新多条字段时，字段间用","而不是AND



**例子**

SQL语句

```mysql
update table_name set name='xxx' where id=x or id=y;
```







## MySQL的编码问题

**注：生产环境中的MySQL的字符是UTF-8，但windows10 默认为gbk，需要注意**



**查看编码设置**

SQL语句

```sql
show variables like 'character_set_%';
```



**修改编码设置**

SQL语句

```sql
set character_set_clinet=gbk;
```

(需要修改的值：client, results)

****







# 三、数据类型



## 1.int整形相关类型

|    type     |  size   |                       signed range                       |         unsigned range          |
| :---------: | :-----: | :------------------------------------------------------: | :-----------------------------: |
|   tinyint   | 1 btye  |                       (-128, 127)                        |            (0, 255)             |
|  smallint   | 2 bytes |                    (-37 768, 32 767)                     |           (0, 65,535)           |
|   medium    | 3 bytes |                 (-8 388 608  8 388 607)                  |         (0, 16,777,215)         |
| int/integer | 4 bytes |             (-2 147 483 648, 2 147 483 647)              |       (0, 4 294 967 295)        |
|   bigint    | 8 bytes | (-9 223 372 036 854 775 808, 9 223 372 036 854 775 807 ) | (0，18 446 744 073 709 551 615) |



SQL语句

```sql
create table table_name(
column_1 tinyint(size) ......
column_2 smallint(size) unsigned ......
);
```





## 2.浮点型相关类型

|  type  |  size   |
| :----: | :-----: |
| float  | 4 bytes |
| double | 8 bytes |

usage method:



SQL语句

```sql
create table table_name(
parameter_1 float(total_width, 小数位数),
parameter_2 double unsigned(total_width, 小数位数)
...
);
```

**注：float和double会丢失精度(在超过规定位数后)**





## 3.定点数类型

**因为其整数和小数部分分开存放，故不会丢失精度(支持无符号)**



SQL语句

```sql
create table table_name(
parameter_1 decimal(m,d),
parameter_2 decimal unsigned(m, d)
);
```





## 4.字符串类型



注：

**1.SQL里的char就是字符串，不同于C和Java**

**2.char和varchar区别：**

​	1.char的长度不变，而varchar会回收多余的字节

​	2.char比varchar要更加高效

|    type    |         size          |              usage              |
| :--------: | :-------------------: | :-----------------------------: |
|    char    |      0~255 bytes      |           定长字符串            |
|  varchar   |     0~65535 bytes     |           变长字符串            |
|  tinyblob  |      0~255 bytes      | 不超过 255 个字符的二进制字符串 |
|  TINYTEXT  |      0-255 bytes      |          短文本字符串           |
|    BLOB    |    0-65 535 bytes     |     二进制形式的长文本数据      |
|    TEXT    |    0-65 535 bytes     |           长文本数据            |
| MEDIUMBLOB |  0-16 777 215 bytes   |  二进制形式的中等长度文本数据   |
| MEDIUMTEXT |  0-16 777 215 bytes   |        中等长度文本数据         |
|  LONGBLOB  | 0-4 294 967 295 bytes |    二进制形式的极大文本数据     |
|  LONGTEXT  | 0-4 294 967 295 bytes |          极大文本数据           |





## 5.布尔类型



SQL语句

```sql
create table table_name(
column_1 boolean,
...
);
```

```sql
insert into table_name values(true/fasle);
```





## 6.枚举类型



注意：**添加枚举类型字段参数时，添加的参数一定只能为enum内存在的参数(有局限性)**



SQL语句

```sql
create table table_name(
column_1 enum('parameter_1', 'parameter_2', 'parameter_3', ......),
......
);
```



SQL语句

```sql
insert into table_name values('parameter_*');
```





#### **特：枚举类型enum的存储方式(另类的插入方式)**



例子:

SQL语句

```sql
create table test(
gender enum('man', 'woman', '?', 'it')
);
```



SQL语句_1

```sql
insert into test values('man');
```



SQL语句_2

```sql
insert into test values('1');
```



SQL语句_1等价于SQL语句2(排序从1开始)

由于只存数字，所以比较快，节省空间





## 7.set集合类型



注：**同enum类型一样，添加字段参数时只能选择已有参数，但可以选择多个。**



创建方式：

SQL语句

```sql
create table table_name(
column_1 set('parameter_1', 'parameter_2', ...),
......
);
```



插入方式：

SQL语句

```sql
insert into table_name values('parameter_1, parameter_2');
```

注：逗号只能在单引号内，不能在单引号外，不然就算做多个字段参数。







## 8.日期和时间类型



|   type    | bytes |       format        |
| :-------: | :---: | :-----------------: |
|   date    |   3   |     YYYY-MM-DD      |
|   time    |   3   |      HH:MM:SS       |
|   year    |   1   |        YYYY         |
| datetime  |   8   | YYYY-MM-DD HH:MM:SS |
| timestamp |   4   |   YYYYMMDD HHMMSS   |



创建方式：

SQL语句

```sql
create table table_name(
column datetime;
);
```





插入方式：

SQL语句

```sql
insert into table_name values('2000-02-07 13:00:00');
```

****















# 四、字段属性



### 1.主键primary key



**注：**

**1.一个表里可以有组合键/复合主键(本质也是一个主键)，但不能有多个主键**

**2.可能与其他库和表关联(例如身份证号)**



#### 1)创建和添加

只能给唯一的，不会重复的值，插入主键值时不能重复，最好是不会修改的值

**注：默认情况下不能为null；如果设置为为auto_increment ，则可以为null，其会自动添加**



SQL语句

```sql
create table if not exists table_name(
column type auto_increment primary key
);
```



**创建后再添加主键**

```sql
alter table table_name add primary key (column_name);
```



#### 2)组合键

可以在创建表示选择多个字段作为主键



**创建后再添加主键**

SQL语句

```sql
alter table table_name add primary key (column_1, column_2, ...);
```





#### 3)删除主键



SQL语句

```sql
alter table table_name drop primary key;
```







### 2.唯一键

与主键的不同：

**可以为空，可以有多个，仅保证数据不重复，一般只在一张表内起作用**





#### 1)创建唯一键



SQL语句

```sql
create table table_name(
column_1 type unique
column_2 type unique
);
```





**创建后添加唯一键**

SQL语句

```sql
alter table table_name add unique(column);
```





#### 2)删除唯一键



SQL语句

```sql
alter table table_name drop index column;
```

****





# 五、注释



## 1.SQL内注释



## 2.代码内注释

****





# 六、四大规范



## 1.字段完整(保证实体的完整性)/域完整性



主键、自增





## 2.合适的数据类型机约束/实体完整性



类型、null、default





## 3.引用和被引用/引用完整性



可能被其他的表应用





## 4.自定义约束/自主完整性

自己需求的约束

****









# 七、外键

阿里巴巴Java开发手册: 外键与级连更新只适用于单机低并发应用，不适合分布式、高并发集群，外键影响数据库的插入速度，所以不得使用外键





作用：

**在一个表中引用另一个表内的数据**



**注：外键无法使用desc语句查看，只能通过show create table查询，且创建均在外表中进行**



![image-20201213141016051](MySQL Note.assets/image-20201213141016051.png)





## 1.创建外键



Table_1:

SQL语句

```sql
create table if not exists table_1(
column_1 type options...,
......
);
```



引用Table_1中的column_1

Table_2:

SQL语句

```sql
create table if not exists table_2(
need to set_column same_type,
foreign key (set_column) references target_table(column)
);
```





创建后/删除后，再添加外键

SQL语句

```sql
alter table table_name add foreign key (set_column) references target_table(column);
```



## 2.删除外键

**注：需输入CONSTRAINT后的内容**



![image-20201213141215991](MySQL Note.assets/image-20201213141215991.png)

SQL语句

```sql
alter table table_name drop foreign key target_ibfk_1;
```





## 3.外键相关操作



场景：(一个数据被删除，其作为外键的值在其他表内需要进行一些操作。)



具体场景：

**学生被开除，但他的ID存在于学校的食堂中，不能因为他被开除，就抹去他产生的交易，否则会影响食堂的营业额统计。**





### 1)置空操作



将该学生的ID改为null,但其对应的其他记录(消费金额等)依然存在。



**在创建时设置**



SQL语句

```sql
create table if not exists table_name(
column_1 type...,
...,
foreign key (set_column) references target_tabel(column) on delete set null on update cascade;
);
```





### 2)级联操作



该学生休学一年，其学号进行了变更，那么其之前使用老学号所生成的数据都应该变更为新的学号。



该学生需要隐藏信息，那么删除时，其通过外键相关联的其他数据都应该被删除。





设置同置空



SQL语句

```sql
create table if not exists table_name(
column_1 type...,
...,
foreign key (set_column) references target_tabel(column) on delete set null on update cascade
);
```

****



















# 八、数据库设计





## 1) 基本概念

- 行: 一条数据/实体
- 列: 一个字段/属性

**注：**可以对应到OOP语言中的对象



**数据冗余：**

- 数据冗余是无法避免的
- 留存数据冗余可以增加查询性能，但会降低性能(主要用于需要经常查询的数据)
- 数据冗余过多对后期管理不友好















## 2) 实体的关系(关系型数据库)

- 一对一
- 一对多
- 多对一
- 多对一









## 3) 六大范式



### 1. 第一范式: 确保字段的原子性

- 在给定的条件内，确保字段不能再被分割
- 该性质需要前提条件，**如果条件改变**，字段是否遵循原子性的判断结果也会被改变













### 2. 第二范式: 非键字段必须依赖于键字段

- 表中**不应该出现的字段不能被加入**
- 不能扯淡！

例子：学生基础信息表中不应该出现食堂消费记录信息















### 3. 第三范式: 消除传递依赖性

- 尽可能地去除冗余的字段

例子: 总分可以由语数外相加得来，那就**没必要单独设立一个总分字段**









### 4. 总结

设计数据库时，没有绝对的标准，一切都要先看需求

****











# 九、单表查询



## 1) SELECT



exam:

![Xnip2021-04-01_14-22-30](MySQL Note.assets/Xnip2021-04-01_14-22-30.jpg)









指定查询结果的字段名:

```shell
select "content" as column
```







exam:

![Xnip2021-04-01_14-24-16](MySQL Note.assets/Xnip2021-04-01_14-24-16.jpg)







## 2) FROM

指明查询的来源(字段/表/库)

- 一次性查询多张表时，会返回一个笛卡尔积



exam:

![Xnip2021-04-05_15-06-37](MySQL Note.assets/Xnip2021-04-05_15-06-37.jpg)









## 3) dual

使用select语句进行查询时，如果没有指定表(from table_name)，其会默认从dual中进行查询，不过可以省略



exam: 

![Xnip2021-04-06_13-52-26](MySQL Note.assets/Xnip2021-04-06_13-52-26.jpg)









## 4) WHERE

指定查询时的条件



exam:

![Xnip2021-04-11_16-07-52](MySQL Note.assets/Xnip2021-04-11_16-07-52.jpg)

where指定字段条件(条件之间可以使用and, or, not 等逻辑符号)









## 5) IN



用于指定where中的查询条件



格式:

```mysql
SELECT (content) FROM table_name WHERE column_name IN (column_content);
```



可以加上not取反:

```mysql
SELECT (content) FROM table_name WHERE column_name NOT IN (column_content);
```



exam:

![Xnip2021-05-17_15-28-43](MySQL Note.assets/Xnip2021-05-17_15-28-43.jpg)



![Xnip2021-05-17_15-34-17](MySQL Note.assets/Xnip2021-05-17_15-34-17.jpg)









## 6) between...and



通过between...and，可以在where后指定范围



格式:

```mysql
SELECT (content) FROM table_name WHERE column_name BETWEEN (start) AND (end);
```

**注意：**与大于小于不同，between...and**包含开始和结束位置**



可以通过not取反

```mysql
SELECT (content) FROM table_name WHERE column_name NOT BETWEEN (start) AND (end);
```





exam:

![Xnip2021-05-17_15-54-47](MySQL Note.assets/Xnip2021-05-17_15-54-47.jpg)



![Xnip2021-05-17_15-55-16](MySQL Note.assets/Xnip2021-05-17_15-55-16.jpg)









## 7) is null

- 用于查找指定字段属性为null的行(实体)



格式:

```mysql
SELECT (content) FROM table_name WHERE column_name IS null;
```



通过not取反

```mysql
SELECT (content) FROM table_name WHERE column_name IS NOT null;
```

**注意：是is not, 不是not is**



![Xnip2021-05-17_20-44-25](MySQL Note.assets/Xnip2021-05-17_20-44-25.jpg)







## 8) 聚合函数

用于获取指定的内容(和，最大/小值，平均值，个数)

- **注意：通过聚合函数查找的结果表名，需要写在聚合函数后面**

exam:

![Xnip2021-05-21_11-13-17](MySQL Note.assets/Xnip2021-05-21_11-13-17.jpg)













### sum:

```mysql
SELECT SUM(column_name) FROM table_name;
```



exam:

![Xnip2021-05-17_21-03-35](MySQL Note.assets/Xnip2021-05-17_21-03-35.jpg)







### max:

```mysql
SELECT MAX(column_name) FROM table_name;
```



exam:

![Xnip2021-05-17_21-05-30](MySQL Note.assets/Xnip2021-05-17_21-05-30.jpg)









### min:

```mysql
SELECT MIN(column_name) FROM table_name;
```



exam:

![Xnip2021-05-17_21-07-22](MySQL Note.assets/Xnip2021-05-17_21-07-22.jpg)









### avg:

```mysql
SELECT AVG(column_name) FROM table_name;
```



exam:

![Xnip2021-05-17_21-08-30](MySQL Note.assets/Xnip2021-05-17_21-08-30.jpg)









### count:

```mysql
SELECT COUNT(column_name) FROM table_name;
```

**注：只有count会跳过null**

exam:

![Xnip2021-05-17_21-09-59](MySQL Note.assets/Xnip2021-05-17_21-09-59.jpg)















## 9) 模糊查询

- 用于查询大致匹配的数据(同姓等等)



格式

```mysql
SELECT (content) FROM table_name WHERE (column_name) LIKE (vague)
```







exam:

![Xnip2021-05-19_10-11-59](MySQL Note.assets/Xnip2021-05-19_10-11-59.jpg)

**注意："%"用于代表多个字符，不限字符数**



![Xnip2021-05-19_10-16-26](MySQL Note.assets/Xnip2021-05-19_10-16-26.jpg)

**注："_"只能用于代表一个字符**









## 10) 排序查询

- 根据指定的字段，按照升序或降序排列



格式

```mysql
SELECT (content) FROM table_name ORDER BY (column_name) ASC/DESC
```

**注意：asc为升序，desc为降序，未指定则默认升序**

- asc: ascending
- desc: descending



exam:

![Xnip2021-05-19_10-25-00](MySQL Note.assets/Xnip2021-05-19_10-25-00.jpg)



![Xnip2021-05-19_10-25-54](MySQL Note.assets/Xnip2021-05-19_10-25-54.jpg)



![Xnip2021-05-19_10-26-21](MySQL Note.assets/Xnip2021-05-19_10-26-21.jpg)







## 11) 分组查询

- 将聚合函数查询出的数据**通过字段进行分组**
- **注意：**使用GROUP BY 时必须包含所有的查询字段



格式

```mysql
SELECT func(column_name) AS 'result column_name', group_column AS 'result column_name' FROM table_name GROUP BY group_column;
```

**格式：select 聚合函数，分组字段 from 表名 group by 分组字段**





exam:

![Xnip2021-05-19_10-47-41](MySQL Note.assets/Xnip2021-05-19_10-47-41.jpg)

**末尾可以添加排序**













## 12) 聚合分组搜索

- 使用group_concat将需要的字段聚合起来(显示在一行中)，需结合goup by





格式

```mysql
SELECT GROUP_CONCAT(column_name), (group_by_column_name) FROM table_name GROUP BY column;
```



exam:

![Xnip2021-05-20_21-17-52](MySQL Note.assets/Xnip2021-05-20_21-17-52.jpg)









## 13) HAVING

- 用于在查询后的结果表上，进一步进行查询(使用结果表的字段名)





格式:

```mysql
SELECT (content) FROM table_name WHERE (column_name) (condition) HAVING (column_name) (condition);
```







exam:

![Xnip2021-05-23_13-59-20](MySQL Note.assets/Xnip2021-05-23_13-59-20.jpg)















## 14) LIMIT

- 用于限制查询结果的**起始位置和结果长度**



格式：

```mysql
SELECT (content) FROM table_name LIMIT start_index, data_length;
```

- 如果"LIMIT"后只写一个数字，则**默认从0开始**，**将输入的数字作为结果的长度**
- 查询的结果**起始位置与数组的规则相同**



exam:

![Xnip2021-05-23_14-10-52](MySQL Note.assets/Xnip2021-05-23_14-10-52.jpg)

- 从索引0处开始，查询两条数据





![Xnip2021-05-23_14-12-16](MySQL Note.assets/Xnip2021-05-23_14-12-16.jpg)

- 默认从索引0处开始，查询一条数据











## 15) DISTINCT

- 去除重复的结果，**相同结果只显示一次**



格式:

```mysql
SELECT DISTINCT (content) FROM table_name;
```





exam:

![Xnip2021-05-23_14-20-53](MySQL Note.assets/Xnip2021-05-23_14-20-53.jpg)





结合用法:

![Xnip2021-05-23_14-22-38](MySQL Note.assets/Xnip2021-05-23_14-22-38.jpg)













## 16) OFFSET

- 用于跳过开头的数据



格式:

```mysql
SELECT (content) FROM table_name (condition) OFFSET (num_of_skipDate);
```







exam:

![Xnip2021-05-30_16-19-14](MySQL Note.assets/Xnip2021-05-30_16-19-14.jpg)























****











# 十、多表查询





## 1) Union 联合查询

- 连接两个SELECT语句，将多个查询结果**组合到一个表中**





- 格式

```mysql
SELECT (content) FROM (table_name) WHERE (column_name) (condition) UNION (ALL/DISTINCT) SELECT...
```

**后接"ALL"则将所有数据列出(无论是否重复)**



exam:

![Xnip2021-05-26_09-46-09](MySQL Note.assets/Xnip2021-05-26_09-46-09.jpg)













## 2) INNER JOHN 內连接

- 用于与其他的表关联起来，添加查询条件



- 格式

```mysql
SELECT (colunmn_name), (column_name) FROM (table_name) INNER JOIN (another_table_name) ON (condition)
```

**注：內连接的条件为两个表中字段的关系，通常为同一字段，使用格式: table_name.column(用"."表示调用表中的字段)**

- 如果多个表中有同名字段，则需要在字段前加上表名



exam:

![Xnip2021-05-26_10-18-19](MySQL Note.assets/Xnip2021-05-26_10-18-19.jpg)





多表的情况(再加一句INNER JOIN 即可):

![Xnip2021-05-26_10-27-28](MySQL Note.assets/Xnip2021-05-26_10-27-28.jpg)













## 3) LEFT JOIN 左连接

- 以左边的表为基准进行查询






- 格式

```mysql
SELECT (content) FROM (left_table_name) LEFT JOIN (right_table_name) ON (condition);
```





exam:

![Xnip2021-05-28_10-11-30](MySQL Note.assets/Xnip2021-05-28_10-11-30.jpg)













## 4) RIGTH JOIN 右连接

- 以右表为基准，进行查询



- 格式

```mysql
SELECT (content) FROM (left_table_name) RIGTH JOHN (right_table_name) ON (condition);
```





exam:

![Xnip2021-05-28_10-19-00](MySQL Note.assets/Xnip2021-05-28_10-19-00.jpg)















## 5) CROSS JOIN 交叉连接

**使用较少**

- 会返回两表中指定字段的**笛卡尔积**(即所有字段之间的排列结果)



- 格式

```mysql
SELECT (content) FROM (table_name) CROSS JOIN (table_name) option:condition;
```





exam:

![Xnip2021-05-28_10-26-38](MySQL Note.assets/Xnip2021-05-28_10-26-38.jpg)















## 6) NATURAL JOIN

**DBA的内容**



- 自然连接，当两个表中有**公共字段(即字段名相同)**时，会自动进行获取(默认为內连接)



- 格式

```mysql
SELECT (content) FROM (table_name) NATURAL JOIN (another_table_name);
```





exam:

![Xnip2021-05-28_10-41-02](MySQL Note.assets/Xnip2021-05-28_10-41-02.jpg)





自然右连接/左连接:

- 同右连接和左连接相同，以左/右表为基准进行的查询





**注意：当两表中没有公共字段时，会返回两表的笛卡尔积**

exam:

![Xnip2021-05-28_10-46-45](MySQL Note.assets/Xnip2021-05-28_10-46-45.jpg)









## 7) USING

- 当两张表中有两个以上的公共字段(甚至结构相同)时，使用USING指定基准字段

**注：此时如果使用自然连接，则会返回null！**



- 格式

```mysql
SELECT (content) FROM (table_name) INNER JOIN (another_table) USING(public_column);
```





exam:

![Xnip2021-05-28_10-57-58](MySQL Note.assets/Xnip2021-05-28_10-57-58.jpg)





![Xnip2021-05-28_10-59-51](MySQL Note.assets/Xnip2021-05-28_10-59-51.jpg)











## 8) 规范

- 最好使用INNER JOIN (table) ON的形式
- 在ON后指定查询的字段条件，**有利于其他不了解表结构的人了解查询基准，增加可读性**

****











# 十一、子查询







## 1. IN/NOT IN

- 通过对一张表进行查询后的结果(**结果必须只有一个字段**)，可以作为WHERE语句中的条件，再进行查询



- 格式

```mysql
SELECT (content) FROM (table_name) WHERE base_column (NOT) IN (sub_query_result);
```

**如果查询出的结果只有一条，则可以将IN换为=，但不推荐，IN更通用**





exam:

![Xnip2021-05-28_11-14-04](MySQL Note.assets/Xnip2021-05-28_11-14-04.jpg)















## 2. EXISTS/NOT EXISTS

- 用于WHERE中，**指定子查询不存在/存在的情况**





- 格式

```mysql
SELECT (content) FROM (table_name) WHERE (NOT) EXISTS (sub_query_result);
```







exam:

![Xnip2021-05-28_11-24-15](MySQL Note.assets/Xnip2021-05-28_11-24-15.jpg)

****











# 十二、视图(view)

**了解**

- 不能对视图创建索引，也不能关联触发器Trigger





- 视图的作用:

用于显示指定的信息(屏蔽掉敏感信息)



## 1. 视图的创建/使用



- 格式

```mysql
CREATE VIEW (view_name) AS SELECT (content) FROM (table_name/Query Statement) (option: condition);
```







exam:

![Xnip2021-05-30_09-58-06](MySQL Note.assets/Xnip2021-05-30_09-58-06.jpg)





![Xnip2021-05-30_09-58-18](MySQL Note.assets/Xnip2021-05-30_09-58-18.jpg)







![Xnip2021-05-30_09-59-00](MySQL Note.assets/Xnip2021-05-30_09-59-00.jpg)



**在终端中的使用：**



显示视图:

 ![Xnip2021-05-30_10-02-42](MySQL Note.assets/Xnip2021-05-30_10-02-42.jpg)





显示创建视图的信息:

![Xnip2021-05-30_10-03-53](MySQL Note.assets/Xnip2021-05-30_10-03-53.jpg)















## 2. 修改/删除视图



### 修改

```mysql
ALTER VIEW (view_name) AS (new_content);
```



exam:

![Xnip2021-05-30_10-08-41](MySQL Note.assets/Xnip2021-05-30_10-08-41.jpg)









### 删除

```mysql
DROP VIEW IF EXISTS (view_name);
```



![Xnip2021-05-30_10-10-30](MySQL Note.assets/Xnip2021-05-30_10-10-30.jpg)









## 3. 视图算法

- 如果将子查询放入视图中，需要指定视图算法才能使子查询有效
- 共有三种算法选项:

1. UNDEFINDED(未定义)
2. MERGE(融合)
3. TEMPTABLE(临时)



- 格式

```mysql
CREATE ALGORITHM=[options] VIEW (view_name) AS (view_content/query statement);
```





exam:

![Xnip2021-05-30_10-20-52](MySQL Note.assets/Xnip2021-05-30_10-20-52.jpg)



Navicat: 选中视图右击，选择高级即可修改视图算法

![Xnip2021-05-30_10-21-35](MySQL Note.assets/Xnip2021-05-30_10-21-35.jpg)

****



















# 十三、事务(transaction)

**注意：只有在创建数据库时，指定引擎为innodb后，才能使用事务**



- 将所有的数据都确认后再更新，而不会一条一条地更新
- 事务要么多条一起更新，要么直接回滚
- 处于事务中的语句，**只有在提交后才能生效**（网购时双方都确认后才会更新账户数据）







## 1. 事务的简单使用



- 开启事务

```mysql
start transaction;
```



exam:

![Xnip2021-05-30_10-58-55](MySQL Note.assets/Xnip2021-05-30_10-58-55.jpg)













- 结束事务/提交

```mysql
commit;
```



exam:

![Xnip2021-05-30_11-00-07](MySQL Note.assets/Xnip2021-05-30_11-00-07.jpg)











- 回滚事务/取消事务

```mysql
rollback;
```



exam:

![Xnip2021-05-30_11-02-25](MySQL Note.assets/Xnip2021-05-30_11-02-25.jpg)













## 2. 回滚到指定回滚点

- 类似恢复虚拟机快照，或者Git版本回滚
- 需要手动设置回滚点





### 设置回滚点

```mysql
SAVEPOINT (point_name);
```







### 回滚到指定回滚点

```mysql
ROLLBACK TO (point_name);
```







![Xnip2021-05-30_11-23-33](MySQL Note.assets/Xnip2021-05-30_11-23-33.jpg)



![Xnip2021-05-30_11-24-34](MySQL Note.assets/Xnip2021-05-30_11-24-34.jpg)











## 3. ACID

- A: Atomicity             原子性：事务是一个不能再分隔的单元，事务中的操作要么都发生，要么都不发生
- C: Consistency        一致性：事务前后数据完整性保持一致
- I:   Isolation              独立性：事务之间相互隔离
- D: Durabiliy              持久性：确认后不能修改

****

















# 十四、索引

**了解**



- 用于加快查询速度
- 会拖累增，删，改的速度，还会占用一定的空间
- 数据量小较时，不应该使用





## 1. 创建索引

- 普通索引

```mysql
CREATE INDEX index_name ON table_name(column);
```

![Xnip2021-06-02_16-43-21](MySQL Note.assets/Xnip2021-06-02_16-43-21.jpg)





- 唯一键

```mysql
CREATE unique index_name ON table_name(column);
```

![Xnip2021-06-02_16-47-41](MySQL Note.assets/Xnip2021-06-02_16-47-41.jpg)









- 主键索引

```mysql
CREATE primary key index_name ON table_name(column);
```











## 2. 删除索引



```mysql
DROP INDEX index_name ON table;
```



![Xnip2021-06-02_18-23-29](MySQL Note.assets/Xnip2021-06-02_18-23-29.jpg)

****













# 十五、存储过程

**了解**

阿里巴巴java开发手册: 存储过程难以调试和扩展，没有移植性，禁止使用



## 1) delimiter

- 可以将原本的语句结束标志从";"改成其他符号(一般用//)



```mysql
delimiter //
```



exam:

![Xnip2021-06-10_15-06-06](MySQL Note.assets/Xnip2021-06-10_15-06-06.jpg)

可用同样的方法修改回来













## 2) procedure

- 存储过程的关键字，可用于操作存储过程
- 中间可以使用事务



创建存储过程

完整流程:

```mysql
delimiter //
create procedure proc_name()
begin
start transaction
SQL1;
SQL2:
...
commit
end //

delimiter ;
```



exam:

![D997FADB-01A3-4F3C-ABE5-5AE89A0CCF4E](MySQL Note.assets/D997FADB-01A3-4F3C-ABE5-5AE89A0CCF4E.png)













使用存储过程:

```mysql
call proc_name();
```



exam:

![93666A23-4164-4F57-AF6B-89DD5AC849FF](MySQL Note.assets/93666A23-4164-4F57-AF6B-89DD5AC849FF.png)











查看已经创建的存储过程:

```mysql
show create procedure pro_name;
```



exam:

![24D6ABC6-0459-4EA2-8F15-A95F1AB1ECAC](MySQL Note.assets/24D6ABC6-0459-4EA2-8F15-A95F1AB1ECAC.png)







删除存储过程:

```mysql
drop procedure pro_name
```

****















# 十六、函数

**了解**



## 1) number



### rand(): 随机数

可用生成随机数，随机排序等等



exam:

![Xnip2021-06-10_15-51-28](MySQL Note.assets/Xnip2021-06-10_15-51-28.jpg)



![Xnip2021-06-10_15-51-57](MySQL Note.assets/Xnip2021-06-10_15-51-57.jpg)













### ceil(向上取整)



exam:

![Xnip2021-06-10_15-53-57](MySQL Note.assets/Xnip2021-06-10_15-53-57.jpg)









### round(四舍五入)



exam:

![Xnip2021-06-10_15-54-48](MySQL Note.assets/Xnip2021-06-10_15-54-48.jpg)









### floor(向下取整)



exam:

![Xnip2021-06-10_15-56-28](MySQL Note.assets/Xnip2021-06-10_15-56-28.jpg)







### truncate(截断数位)

exam:

![Xnip2021-06-10_15-58-52](MySQL Note.assets/Xnip2021-06-10_15-58-52.jpg)















## 2) string





### ucase

- 将字符串转换为大写

  

exam:

![Xnip2021-06-10_16-04-59](MySQL Note.assets/Xnip2021-06-10_16-04-59.jpg)







### lcase

- 将字符串转换为小写

![Xnip2021-06-10_16-05-19](MySQL Note.assets/Xnip2021-06-10_16-05-19.jpg)









### left

- 从左边截取字符串

![Xnip2021-06-10_16-05-53](MySQL Note.assets/Xnip2021-06-10_16-05-53.jpg)









### right

- 从右边截取字符串

![Xnip2021-06-10_16-06-37](MySQL Note.assets/Xnip2021-06-10_16-06-37.jpg)













### substring

- 获取一个子字符串

![Xnip2021-06-10_16-08-16](MySQL Note.assets/Xnip2021-06-10_16-08-16.jpg)













### concat

- 拼接字符串

![Xnip2021-06-10_16-10-13](MySQL Note.assets/Xnip2021-06-10_16-10-13.jpg)











## 3) others



### now()

![Xnip2021-06-10_16-16-00](MySQL Note.assets/Xnip2021-06-10_16-16-00.jpg)











### unix_timestamp()



获取Unix时间戳

![Xnip2021-06-10_16-16-57](MySQL Note.assets/Xnip2021-06-10_16-16-57.jpg)









### 输出时间

格式化输出![Xnip2021-06-10_16-24-16](MySQL Note.assets/Xnip2021-06-10_16-24-16.jpg)











### 加密



sha:

![Xnip2021-06-10_16-25-04](MySQL Note.assets/Xnip2021-06-10_16-25-04.jpg)





MD5:

![Xnip2021-06-10_16-25-41](MySQL Note.assets/Xnip2021-06-10_16-25-41.jpg)

****

















# 十七、全文本搜索

- 常用的引擎中，只有MyISAM支持，Innodb并不支持
- 在效果上看起来与LIKE模糊查询与正则表达式相同，后两个在查询时有三个缺点:
- 它们都会尝试匹配所有的行
- 它们在匹配多个词时，只有第一个匹配后才能匹配剩下的，不能对匹配规则进行定义(添加和/或等关系)
- 获取的匹配结果只会单纯地返回，不会区分匹配度(哪条数据先出现、出现的次数最多)



所以全文本搜索的特点如下:

1. 使用时不需要查看每一行，不需要分别处理每个词
2. 在使用时仅在创建索引的指定列中进行搜索
3. 查询结果可以反映出匹配的频率，出现次序等等



关于Innodb与MyISAM:

1. innodb支持事务，但MyISAM不支持
2. innodb支持外键，但MyISAM不支持。如果将一个包含外键的innodb表转为MyISAM会失败的
3. 从MySQL5.5开始，innodb就成为了MySQL的默认引擎
4. 从MySQL5.6开始，innodb和MyISAM都支持全文本索引，之前只有MyISAM支持







## 1. 建立索引列

- 由于全文本搜索只对创建索引的列进行搜索，所以需要在创建表的时候指定索引列

Syntax:

```mysql
CREATE TABLE IF NOT EXISTS table_name (
column_name type,
...
FULLTEXT(index_column),
......
)ENGINE=MYISAM
```

注: 如果要将数据导入到一个新表中(比如将SQL的结果存在一个新的表里)，一定要等数据插入完成后再指定全文本搜索索引如下









Syntax:

```mysql
ALTER TABLE table_name add FULLTEXT(index_column);
```













## 2. 使用全文本索引

- 在WHERE子句中使用MATCH()和AGAINST()

Syntax:

```mysql
SELECT
	column_name
FROM
	table
WHERE MATCH(index_column) AGAINST('rule');
```



- match()中的字段名一定要是表中FULLTEXT索引指定的字段，AGAINST()为搜索的规则


Eg:

![Xnip2021-08-12_17-16-57](MySQL Note.assets/Xnip2021-08-12_17-16-57.jpg)



*注：*

1. Match()和Against()是一起使用的，两者结合起来会形成一个字段
2. 如果查询Match() Against()字段，则会查询出每个字段的匹配度(而非查询出匹配的字段)



Eg:

![Xnip2021-08-12_18-58-47](MySQL Note.assets/Xnip2021-08-12_18-58-47.jpg)











## 3. 使用查询扩展

- 查询扩展能够放宽查询的范围
- 查询扩展的结果也会包含一些没有匹配内容的数据
- 查询扩展会先对数据进行一次基础的全文本搜索，再检查匹配行中的有用的词，再根据有用的词再进行一次全文本搜索



Syntax:

```mysql
SELECT
	column_name
FROM
	table_name
WHERE Match(index_column) Against(rule WITH QUERY EXPANSION);
```







Eg:

![Xnip2021-08-16_16-22-28](MySQL Note.assets/Xnip2021-08-16_16-22-28.jpg)















## 4. 布尔文本搜索

- 使用布尔文本搜索时**不需要对查询列创建一个索引**
- 布尔文本搜索是一种非常缓慢的操作方式，速度与数据量成反比
- 布尔文本搜索可以定义查询条件的逻辑关系(制定包含数据与不包含的数据等等)



Syntax:

```mysql
SELECT
	column_name
FROM
	table_name
WHERE Match(index_column) Against(rule IN BOOLEAN MODE);
```



Eg:

![Xnip2021-08-16_16-37-32](MySQL Note.assets/Xnip2021-08-16_16-37-32.jpg)







- 全文本布尔操作符:

| Operator |         Describe         |
| :------: | :----------------------: |
|    +     |      包含，必须含有      |
|    -     |      排除，不得含有      |
|    >     |     包含，且增加权重     |
|    <     |     包含，但减少权重     |
|    ()    |   将词组组合成子表达式   |
|    ~     |    取消一个词的排序值    |
|    *     |        词尾通配符        |
|    ""    | 将词组组合成一个新的短语 |





Eg:

![Xnip2021-08-16_16-48-33](MySQL Note.assets/Xnip2021-08-16_16-48-33.jpg)







## 5. 补充说明

- 在进行全文本搜索时，短词会被忽略(长度 <= 3的词组)
- 在进行全文本搜索时，会忽略掉一些MySQL自带的内建词(stopword)
- 除了布尔文本搜索，全文本搜索时会忽略掉在索引行中50%的数据中都出现过的词
- 如果表中数据行数小于3，则全文本搜索不会返回任何结果(原因同上一条)
- Against()中的规则会自动忽略掉其中的单引号'，例如don't会被看作是dont
- 从MySQL5.6开始，Innodb和MyISAM都支持全文本搜素了



Eg:

![Xnip2021-08-16_16-57-21](MySQL Note.assets/Xnip2021-08-16_16-57-21.jpg)

****













# 十八、游标

// 了解



- 游标存储SQL的结果，它只能在存储过程中使用
- 使用游标前必须声明它(其定义的SELECT语句)，声明后需要打开打开才能使用，使用后必须关闭



## 1. 创建/打开/关闭游标



在存储过程中创建游标:

Syntax:

```mysql
CREATE PROCEDURE procedure_name()
BEGIN
	DECLARE cursor_name CURSOR
	FOR
	SELECT...										#SQL Query
END;
```





打开已经创建的游标:

Syntax:

```mysql
OPEN cursor_name;
```





关闭打开的游标:

Syntax:

```mysql
CLOSE cursor_name;
```







****















# 十九、触发器

- 触发器可以在指定操作发生后自动执行
- 触发器只能响应三中语句: DELETE, UPDATE, INSERT
- 最好保证触发器的名字是数据库范围内是唯一的





## 1. 创建/删除/查看触发器



创建:

- 创建时需要给出三个参数:
- 响应的时间(操作之前或者之后)、响应的操作、关联的表
- 一个触发器只能与一张表或者一个事件关联



Syntax:

```mysql
CREATE TRIGGER trigger_name AFTER/BEFORE INSERT/UPDATE/DELETE ON relate_table
option(FOR EACH ROW) execute_SQL
```



Eg:

![Xnip2021-09-13_20-41-41](MySQL Note.assets/Xnip2021-09-13_20-41-41.jpg)

**注意：**《MySQL必知必会》中返回NEW临时表的形式已经不能使用了，在MySQL5.7中会报错，需要将数据传入到一个自定义的变量中才行(@insert)









删除:

- 触发器不能进行覆盖，所以想要修改触发器只能先删除再创建

Syntax:

```mysql
DROP TRIGGER trigger_name
```







查看:

- 触发器的查看与表相同

Syntax:

```mysql
show create trigger trigger_name;
show triggers;
```











## 2. INSERT触发器

- INSERT触发器只能将插入的值通过insert赋给变量(@)，触发后查询该变量即可



Syntax:

```mysql
CREATE TRIGGER trigger_name AFTER/BEFORE ON trigger_table
FOR EACH ROW SELECT 'text' INTO @var
```



Eg:

![Xnip2021-09-13_20-41-41](MySQL Note.assets/Xnip2021-09-13_20-41-41.jpg)













## 3. DELETE触发器

- 所有被删除的数据都在临时表OLD中，通过OLD.的方式直接调用即可



Syntax:

```mysql
CREATE TRIGGER trigger_name BEFORE DELETE trigger_table
FOR EACH ROW SQL;
```





Eg:

![Xnip2021-09-13_21-35-58](MySQL Note.assets/Xnip2021-09-13_21-35-58.jpg)















## 4. UPDATE触发器

- 更新的数据存储在名为NEW的虚拟表中
- 更新前的老数据在名为OLD的虚拟表中
- 可以在BEFORE触发器中更新NEW中的数据，从而实现在数据插入之前对数据进行更新
- OLD表中的数据是只读的，无法更改









Eg:

![Xnip2021-09-14_08-15-19](MySQL Note.assets/Xnip2021-09-14_08-15-19.jpg)

****













# 二十、全球化/本地化





## 1. 字符集



显示支持的字符集:

```mysql
show character set;
```

Eg:

![Xnip2021-09-14_10-16-53](MySQL Note.assets/Xnip2021-09-14_10-16-53.jpg)

- 同时会显示该字符集默认的校对



显示默认的字符集:

```mysql
show variables like 'character%';
```



Eg:

![Xnip2021-09-14_10-18-06](MySQL Note.assets/Xnip2021-09-14_10-18-06.jpg)











## 2. 校对

- 规定了字符的比较规则(是否区分大小写等等)



显示所有支持的校对

Syntax:

```mysql
show collation;
```



Eg:

![Xnip2021-09-14_10-21-55](MySQL Note.assets/Xnip2021-09-14_10-21-55.jpg)

- 同时会显示其支持的字符集











## 3. 字符集和校对的使用

- 对于库/表/字段都可以设置不同的字符集和校对
- 如果指定了character set和collate，则使用指定的字符集和校对
- 如果只指定了character set，则只会使用该字符集默认的校对





指定表的字符集和校对:

Syntax:

```mysql
create table table_name(
column1...,
) DEFAULT CHARACTER SET char_name
COLLATE collation_name;
```



Eg:

![Xnip2021-09-14_10-32-29](MySQL Note.assets/Xnip2021-09-14_10-32-29.jpg)





修改表的字符集

Syntax:

```mysql
ALTER TABLE table_name CONVERT TO CHARACTER SET char_set_name;
```



Eg:

![Xnip2021-09-14_10-43-27](MySQL Note.assets/Xnip2021-09-14_10-43-27.jpg)















指定字段的字符集和校对

Syntax:

```mysql
create table table_name(
column1 type character set xx collate xx,
...
);
```





Eg:

![Xnip2021-09-14_10-38-18](MySQL Note.assets/Xnip2021-09-14_10-38-18.jpg)







修改字段的字符集

Syntax:

```mysql
ALTER TABLE table_name MODIFY column_name type character set char_set_name;
```





Eg:

![Xnip2021-09-14_10-46-39](MySQL Note.assets/Xnip2021-09-14_10-46-39.jpg)

****





# 二十一、安全管理



## 1. 用户管理



- 日常中不得使用root用户
- 用户信息都存储在mysql库，user表中
- 查看当前用户的方法: SELECT user()或者SELECT current_user();



Eg: 

![Xnip2021-09-14_10-55-58](MySQL Note.assets/Xnip2021-09-14_10-55-58.jpg)





创建用户账号

Syntax:

```mysql
CREATE USER user_name IDENTIFIED BY 'password';
```





Eg:

![Xnip2021-09-14_11-03-28](MySQL Note.assets/Xnip2021-09-14_11-03-28.jpg)







重命名用户名

Syntax:

```mysql
RENAME USER user_name to new_user_name
```





Eg:

![Xnip2021-09-14_11-05-45](MySQL Note.assets/Xnip2021-09-14_11-05-45.jpg)

- 注：在MySQL5之后的版本才能这样用，之前的版本只能通过UPDATE更新字段







删除用户

syntax:

```mysql
DROP user user_name;
```



Eg:

![Xnip2021-09-14_11-07-59](MySQL Note.assets/Xnip2021-09-14_11-07-59.jpg)

- 在MySQL5之后的版本通过drop可以删除用户及其权限，但之前的版本无法删除权限









## 2. 用户权限

- 添加/删除对于整个服务器的权限: GRANT ALL/REVOKE ALL
- 针对整个数据库: ON database.* 和 FROM database.*
- 针对某张表: ON database.table_name 和 FROM database.table
- 在使用GRANT/REVOKE时，其指定的库/表可以不存在(即在设计之前对用户进行权限的管理)，但该权限不会随对应的库表的删除而消失



查看用户权限

syntax:

```mysql
show grant for user_name;
```





Eg:

![Xnip2021-09-14_11-13-29](MySQL Note.assets/Xnip2021-09-14_11-13-29.jpg)

- USAGE表示没有任何权限，TO后面是用户@主机名的组合
- 查询时如果不指定主机名，则默认使用%即不限制主机名











设置用户权限

syntax:

```mysql
GRANT SELECT ON database_name.table_name TO user_name;
```





Eg:

<img src="MySQL Note.assets/Xnip2021-09-14_13-27-25.jpg" alt="Xnip2021-09-14_13-27-25" style="zoom:50%;" />









删除用户权限

syntax:

```mysql
REVOKE operation ON database.table FROM user_name
```





Eg:

![Xnip2021-09-14_13-43-24](MySQL Note.assets/Xnip2021-09-14_13-43-24.jpg)





















