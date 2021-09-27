

# 一、MySQL概述



## 1. /bin目录



MySQL在mac OS上的安装路径:

```shell
/usr/local/mysql/
```





## 2. 启动服务器



### mysqld:

- 启动一个服务器进程
- 并不常用





### mysql_safe:

- 间接调用mysqld并一直监控服务器的运行情况
- 进程出现问题时，可以帮助重启服务器
- 通过其启动时，能将服务器的出错信息和其他诊断信息输出到错误日志





### mysql.server:

- 会间接调用mysql_safe
- 其只是一个链接





### mysqld_multi:

- 启动/停止多个服务器进程











## 3. 启动MySQL客户端



启动MySQL客户端:

```shell
mysql -h host_name -u user_name -ppassword
```

- h: 服务器所在的域名或者IP地址
- u: 客户端用户名
- p: 用户密码



- 多个客服端之间互不影响





连接的注意事项:

- 不要在一行中输入密码，最好采用(不会显示密码):

```shell
mysql -u root -p
```





- 如果非要在一行中输入密码，则密码和-p之间不能有任何空白字符
- 参数之间的顺序并不固定，可以随意更改
- **对于UNIX系统**，省略-u后，会将当前操作系统的用户名作为连接客服端的用户名处理











## 4. server和client的连接过程



### 网络

- 当服务器和客服端不在同一主机上时，通常需要借助TCP/IP协议来通信
- 服务器/客户端进程使用该协议进行通信时会向系统申请一个端口，MySQL默认会申请3306端口

使用mysqld程序启动，使用-P参数:

```shell
mysqld -P
```

- 注意是大写的P，小写代表password









### 在Windows操作系统上

- 可以使用命名管道或者共享内存
- 共享内存只能用于服务器和客户端位于同一台Windows主机中的情况





### 在UNIX操作系统上

- 如果server和client都在同一台UNIX机器上，可以使用UNIX域套接字(domain socket)进行进程间通信
- 默认的套接字文件路径:

```shell
/tmp/mysql.sock
```













## 5. 服务器处理请求

最终的效果:

- 客户端进程向服务器发送一段**文本**(SQL语句)，服务器处理后向客户端进程返回一段文本(处理结果)





### 连接管理

- 通过上述过程进行连接
- 每当一个客户端进程连接服务器进程时，服务器进程都会创建一个线程专门用于与该客户端交互
- 客户端断开连接后，服务器会将该线程缓存起来，之后分配给新的客户端(避免频繁的创建和销毁线程)
- 为了不影响性能，我们可以指定同时连接服务器的最大数量
- 如果两者不在同一台设备上，还可以通过TLS进行传输层加密
- 建立连接后，服务器会一直等待客户端的请求







### 解析/优化

- 查询缓存:

把处理过的查询请求与结果保存起来，用于之后的查询

当表的结构/数据被修改后，其查询缓存都将被删除

从MySQL5.7.20开始不推荐使用查询缓存了，在MySQL8.0被直接删除





- 语法解析:

如果在缓存没有命中，则进入查询阶段





- 查询优化:

MySQL会自动将我们的语句进行一些优化(改变表的连接方式，简化等等)

可以使用EXPLAIN来查看语句的执行计划

Eg:

![Xnip2021-09-23_21-16-40](MySQL Note.assets/Xnip2021-09-23_21-16-40.jpg)









### 存储引擎

- 一个封装了数据存储/提取操作的模块，规定了读取数据和写入数据的方式
- 不同的存储引擎管理表的结构不同，采用的算法也不同
- 可以简单的分为server和存储引擎层，不涉及数据的部分全部为server层









## 6. 常用的存储引擎

| Storage Engine |           Description           |
| :------------: | :-----------------------------: |
|    ARCHIVE     |     用于存档(无法修改数据)      |
|   BLACKHOLE    |    丢弃写操作，读取会返回空     |
|      CSV       | 存储数据时，用","分隔各个数据项 |
|   FEDERATED    |         用于访问远程表          |
|     InnoDB     |     支持事务、行级锁、外键      |
|     MEMORY     |  数据只存储在内存(用于临时表)   |
|     MERGE      |  管理多个MyISAM表构成的表集合   |
|     MyISAM     |         处理非事务存储          |
|      NDB       |        MySQL集群专用存储        |

- 默认引擎为InnoDB(从5.5.5开始)，最常用的为MyISAM和InnoDB
- 不同的引擎功能不同









## 7. 存储引擎操作





### 查看支持的引擎

syntax:

```mysql
show engines;
```



Eg:

![Xnip2021-09-25_21-43-33](MySQL Note.assets/Xnip2021-09-25_21-43-33.jpg)









### 设置表的存储引擎



在创建表时指定

syntax:

```mysql
create table table_name(
column_1 type,
...
)engine=engine_name;
```



Eg:

![Xnip2021-09-25_21-53-08](MySQL Note.assets/Xnip2021-09-25_21-53-08.jpg)







修改已存在表的存储引擎

syntax:

```mysql
alter table table_name engine = new_engine_name;
```

![Xnip2021-09-25_21-56-32](MySQL Note.assets/Xnip2021-09-25_21-56-32.jpg)

****



















# 二、MySQL设置



## 1. 启动参数/配置文件



### CLI上使用选项参数

- 该方法仅针对当次启动



- 禁用TCP/IP通信

```shell
mysqld --skip-networking
```





- 设置默认的存储引擎

```shell
mysqld --default-storage-engine=engine_name
```

**注：**选项名和选项值之间不得有空格





- 查看运行程序的帮助说明

```shell
mysql --help
mysql_safe --help
mysqld --verbose --help(特殊)
```

- 许多选项参数都有对应的短形式
- 短形式允许选项名与选项值之间用space隔开











### 配置文件

- 比起CLI，修改配置文件能一劳永逸！



UNIX系统依次读取配置文件的顺序:

|        Path        |             Describ              |
| :----------------: | :------------------------------: |
|    /etc/my.cnf     |                                  |
| /etc/mysql/my.cnf  |                                  |
| SYSCONFDIR/my.cnf  |                                  |
| $MYSQL_HOME/my.cnf |        仅指定服务器的选项        |
| default-extra-file | 由命令行指定的额外的配置文件路径 |
|     ～/.my.cnf     |          用于用户的选项          |
|   /.mylogin.cnf    |    仅指定客户端的登录路径选项    |

- 除了$MYSQL_HOME/my.cnf和/.mylogin.cnf外其余配置文件既能放server也能放client的文件
- .mylogin.cnf只能用mysql_config_editor创建/修改



Eg:

![Xnip2021-09-26_08-16-08](MySQL Note.assets/Xnip2021-09-26_08-16-08.jpg)



![Xnip2021-09-26_08-15-02](MySQL Note.assets/Xnip2021-09-26_08-15-02.jpg)





配置文件中程序对应组名以及作用

| Program_name | Effect |               Group                |
| :----------: | :----: | :--------------------------------: |
|    mysqld    | server |         [mysqld], [server]         |
| mysqld_safe  | server | [mysqld], [server], [mysqld_safe]  |
| mysql.server | server | [mysqld], [server], [mysql.server] |
|    mysql     | client |         [mysql], [client]          |
|  mysqladmin  | client |       [mysqladmin], [client]       |
|  mysqldump   | client |       [mysqldump], [client]        |

- 任意选项组后都能添加版本号，用于特定的版本





多个配置文件的优先级:

- 如果多个配置文件中有重复的配置选项，且选项值不同，则以最后一次加载的文件为准
- 加载顺序与读取顺序相同





同一个配置文件中组的优先级:

- 如果同一个配置文件中有多个相同作用的组，且选项值不同，则以最后一个组为准

如:

```json
[server]
default-storage-engine=InnoDB

[mysqld]
default-storage-engine=MyISAM
```

- 此时默认的引擎为MyISAM，因为[mysqld]选项组靠后









额外文件:

- 在CLI中，使用default-file可以指定server加载**唯一的配置文件**
- 使用default-extra-file则依然会加载默认的配置文件















### CLI和配置文件的区别

- 如果一个选项同时出现在配置文件和命令行，则以CLI为准
- 选项只能用于CLI，类似default-file和default-extra-file这样的放在配置文件中无意义















## 2. 系统变量

- 用于在运行中限制服务器
- 可以在运行中修改，不需要重启







### 查看系统变量

syntax:

```mysql
show variables like parttern
```

- 可以结合通配符进行模糊查询
- 未指定global和session时，默认输出为会话变量



Eg:

![Xnip2021-09-26_10-25-09](MySQL Note.assets/Xnip2021-09-26_10-25-09.jpg)



![Xnip2021-09-26_10-25-57](MySQL Note.assets/Xnip2021-09-26_10-25-57.jpg)















### 设置系统变量



#### 通过启动项

- 命令行直接设置(同启动参数)
- 配置文件

**注：**通过启动项的方式时，各个单词可以使用-隔开，也能使用_，但对于系统变量而言，必须使用 _(建议统一使用 _)。







#### 运行过程中修改

- 大多数系统变量可以在运行时修改
- 但系统变量按照作用范围可分为GLOBAL(全局)和SESSION(会话)



全局变量(global):

- 在服务器启动时初始化为默认值，默认值来自CLI或者配置文件
- 会影响服务器的整体操作
- 通过CLI和配置文件设置的是全局变量



会话变量(session):

- 影响服务器和某个客户端的连接(大部分会根据全局变量进行初始化)









#### 通过client

- 设置全局变量

syntax:

```mysql
SET GLOBAL variable_name = value
```

- 设置的全局变量对当前连接的client中会话变量无影响



Eg:

![Xnip2021-09-26_10-49-46](MySQL Note.assets/Xnip2021-09-26_10-49-46.jpg)







- 设置会话变量

syntax:

```mysql
SET SESSION variable_name = value;
```

- 同样，会话变量不会影响到全局





Eg:

![Xnip2021-09-26_10-52-42](MySQL Note.assets/Xnip2021-09-26_10-52-42.jpg)







**注意：**

1. 如果在server运行中修改了global变量，之后与其连接的client的session变量都会受到影响
2. 修改后不会影响已经与其建立连接的client中的session变量
3. 如果修改后的变量值与配置文件中不同，则下次启动后，还是会恢复至配置文件/CLI中的值
4. 一些变量只有global或者session作用范围
5. 部分变量是只读的(如版本)
6. 部分启动项和系统变量相同，但部分只能作为启动项或者系统变量













## 3. 状态变量

- 显示server的运行状态
- 不能自由修改，只能自动生成
- 同样分为global和session



查看

syntax:

```mysql
show status like variable_name;
```

- 不指定范围则默认为session



Eg:

![Xnip2021-09-26_11-04-41](MySQL Note.assets/Xnip2021-09-26_11-04-41.jpg)

****













# 三、字符集/比较规则

- 字符集: 字符范围的编码规则，一个字符集能有多个比较规则
- 比较规则: 确定字符集后对字符进行比较的方式(是否区分大小写等等)







## 1. 常见的字符集



### ASCII

- 共收录128个字符(2^7)，可以使用一个字节来编码表示



### ISO 8859-1

- 共收录256个字符(2^8)，在ASCII的基础上添加了128个西欧常用的字符，可以使用一个字节来编码表示，别名为"Latin1"
- 其为MySQL5.7及其之前版本的默认字符集



### GB2312

- 收录了较多的汉字和其他字符，兼容ASCII，如果字符在ASCII中，则用一个字节编码表示，否则用两个编码



### GBK

- GB2312的扩展，兼容GB2312



### UTF-8

- 最常用的字符编码，兼容ASCII字符集，采用变长编码(编码时使用1~4个字节)
- 其属于Unicode字符集的一种编码方式，其下还有UTF-16, UTF-32(分别采用2～4和4个字节进行编码)















## 2. MySQL中支持的字符集/比较规则



### utf8和utf8mb4

- 常用的字符用1～3个字节就能表示，所以定义了utf8mb3
- utf8mb3: 只使用1～3个字节表示字符
- uft8mb4: 使用1～4个字节表示，同utf8一样









### 查看支持的字符集

syntax:

```mysql
SHOW (CHARSET/CHARACTER) SET (LIKE 'charset_name');
```



Eg:

![Xnip2021-09-27_13-45-40](MySQL Note.assets/Xnip2021-09-27_13-45-40.jpg)

- Default collation: 表示默认的比较规则(ci: case insensitive 即忽略大小写)
- Maxlen: 表示该字符集表示字符时最多使用的字节数

















### 查看比较规则

syntax:

```mysql
SHOW COLLATION (LIKE 'collation_name');
```





Eg:

![Xnip2021-09-27_13-50-33](MySQL Note.assets/Xnip2021-09-27_13-50-33.jpg)

- 比较规则的命令意义: 字符集 + 语言 + 区分重音/大小写等
- Default值为Yes的比较规则为该字符集的默认比较规则
- 后缀含义:

| 后缀 |        释义        |        描述        |
| :--: | :----------------: | :----------------: |
| _ai  | accent insensitive |     不区分重音     |
| _as  |  accent sensitive  |      区分重音      |
| _ci  |  case insensitive  |    不区分大小写    |
| _cs  |   case sensitive   |     区分大小写     |
| _bin |       binary       | 以二进制的方式比较 |















## 3. 使用字符集和比较规则



### 字符集和比较规则的级别



#### 1) server

- 系统变量character_set_server和collation_server表示server级别的字符集和比较规则



查看:

![Xnip2021-09-27_14-01-31](MySQL Note.assets/Xnip2021-09-27_14-01-31.jpg)



修改这两个变量的方法

- 通过CLI启动项
- 通过配置文件:

```shell
[server]
character_set_server=charset_name
collation_server=charset_name
```



Eg:

![Xnip2021-09-27_14-11-18](MySQL Note.assets/Xnip2021-09-27_14-11-18.jpg)









#### 2) database

- 创建时未指定则以server为准



查看当前database的charset和collation(需要先use选中)

```mysql
SHOW VARIABLES LIKE 'character_set_database'
SHOW VARIABLES LIKE 'collation_database'
```

- 这两个变量不能用mysql的SET语句修改，只能通过CLI和配置文件



- 在创建时指定charset和collation:

```mysql
CREATE DATABASE IF NOT EXISTS database_name
CHARACTER SET charset_name
COLLATE collation_name
```







Eg:

![Xnip2021-09-27_14-22-45](MySQL Note.assets/Xnip2021-09-27_14-22-45.jpg)







- 修改已经存在的库:

```mysql
ALTER DATABASE database_name
CHARACTER SET new_charset
COLLATE new_collation;
```



Eg:

![Xnip2021-09-27_14-29-48](MySQL Note.assets/Xnip2021-09-27_14-29-48.jpg)













#### 3) table

- 没有指定则使用其对应database的charset和collation
- 在创建时指定:

```mysql
CREATE TABLE table_name(
column_1 type
...
)CHARACTER SET charset_name COLLATE collation_name
```





Eg:

![Xnip2021-09-27_14-58-05](MySQL Note.assets/Xnip2021-09-27_14-58-05.jpg)







- 修改表的字符集:

```mysql
ALTER TABLE table_name
CHARACTER SET charset_name
COLLATE collation_name
```















#### 4) column

- 没有指定时则以table为准
- 创建时指定

```mysql
CREATE TABLE table_name(
column_1 type CHARACTER SET charset_name COLLATE collation_name
...
)
```



Eg:

![Xnip2021-09-27_15-06-56](MySQL Note.assets/Xnip2021-09-27_15-06-56.jpg)







- 修改列/字段的字符编码和排序规则

```mysql
ALTER TABLE table_name MODIFY column_name type CHARACTER SET new_charset COLLATE collation;
```







Eg:

![Xnip2021-09-27_15-10-37](MySQL Note.assets/Xnip2021-09-27_15-10-37.jpg)















#### 5) 只修改charset/collation

- 只修改charset，collate变为该charset默认的collate
- 只修改collate，charset变为该collate对应的charset
- 两者会自动对应













#### 6) 优先级总结

- charset: column取决于table，table取决于database，database取决于server



















### server与client通信时使用的charset















