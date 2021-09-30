

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

- charset: column未指定则取决于table，table未指定则取决于database，database未指定则取决于server



















### server与client通信时charset的转换



#### 1) client sent message

- client和server通信过程中规定的数据格式为MySQL通信协议
- 对于类UNIX系统，其字符编码决定于以下顺序:

```shell
LC_ALL -> LC_CTYPE -> LANG
```



Eg:

![Xnip2021-09-28_09-52-27](MySQL Note.assets/Xnip2021-09-28_09-52-27.jpg)







#### 2) server receive message

- 应该保证client和server的charset相同
- server接收请求时会根据变量character_set_client来解码，该变量可以通过SET进行修改
- 如果client发送的数据编码不能用character_set_client来表示(utf8超过了ASCII的范围)，则会报错



查看/修改 character_set_client:

![Xnip2021-09-28_09-58-07](MySQL Note.assets/Xnip2021-09-28_09-58-07.jpg)











#### 3) server process

- 服务器接收请求时以"character_set_client"为准来解码，之后将其转换为character_set_connection对应的编码
- 在处理时(比较, 匹配等)以session级别的"character_set_connection"和"collation_connection"为准
- 这两个变量可以通过SET修改

**注意：**如果比较数据列的比较规则/字符集和上述两个var不同，则比较时**以列的为准**



查看:

![Xnip2021-09-28_21-28-40](MySQL Note.assets/Xnip2021-09-28_21-28-40.jpg)





修改:

![Xnip2021-09-28_21-30-09](MySQL Note.assets/Xnip2021-09-28_21-30-09.jpg)







查看列的比较规则

syntax:

```mysql
SHOW FULL COLUMNS FROM table_name;
```



Eg:

![Xnip2021-09-28_21-31-52](MySQL Note.assets/Xnip2021-09-28_21-31-52.jpg)















#### 4) server respond

- server通过character_set_connection编码进行匹配比较后，通过SESSION级别的character_set_results返回结果
- 其同样可以通过SET来修改



查看:

![Xnip2021-09-28_21-37-41](MySQL Note.assets/Xnip2021-09-28_21-37-41.jpg)





修改:

![Xnip2021-09-28_21-38-37](MySQL Note.assets/Xnip2021-09-28_21-38-37.jpg)















#### 5) client receive respond

- client接收响应后会以其自身的字符编码来显示(UNIX默认以utf8来显示)











### 默认情况

- 如果client未指定字符集，则使用client系统的字符集，如果MySQL并不支持该charset，则使用MySQL默认的charset
- 在MySQL5.7及之前的版本中，默认charset为latin1，之后为utf8mb4
- 如果使用了dafault_character_set启动项，则忽略client系统的字符编码



批量修改字符集:

- 通过SET NAMES可以一次修改character_set_client/connection/results三个变量

syntax:

```mysql
SET NAMES charset_name;
```

****





















# 四、InnoDB存储结构

- 从MySQL5.5.5开始，InnoDB作为MySQL的默认存储引擎
- InnoDB在处理数据时会将Disk里的数据加载到memory里，每次读取和写入的单位为页，其默认大小为16KB
- 变量"innodb_page_size"表明了innodb存储引擎每页的大小，**该变量无法在运行过程中修改**

查看页的大小:

![Xnip2021-09-29_09-19-13](MySQL Note.assets/Xnip2021-09-29_09-19-13.jpg)









## 1. InnoDB行格式

- 我们写入的每条数据作为一条记录，记录在表中的存放形式由行格式决定
- 目前的四种行格式: COMPACT, REDUNDANT, DYNAMIC, COMPRESSED
- 指定/修改行格式:

```mysql
CREATE TABLE table_name(
column type
...
)CHARSET=charset_name ROW_FORMAT=row_format_name;
```



```mysql
ALTER TABLE table_name ROW_FORMAT=new_row_format_name;
```



Eg:

![Xnip2021-09-29_09-38-22](MySQL Note.assets/Xnip2021-09-29_09-38-22.jpg)





查看表的行格式等数据

syntax:

```mysql
SHOW TABLE STATUS LIKE 'table_name';
```













### COMPACT

一条COMPACT行格式的记录组成:

|                  |            |            |         |      |         |
| :--------------: | :--------: | :--------: | :-----: | :--: | :-----: |
| 变长字段长度列表 | NULL值列表 | 记录头信息 | 列1的值 | ...  | 列n的值 |

- 前三列为记录的额外信息，其余为记录的真实数据







#### 1) 记录的额外信息

- 其有三个部分: 变长字段长度列表、NULL值列表、记录头信息



##### 1. 变长字段长度列表

- 由于一些变长的数据类型存储数据的字节数不固定，所以需要存储字段所占用的实际字节数(不然server会懵逼的)
- 由此可知，存储变长字段会占用两部分空间: 数据占用的字节数 数据内容。且分别位于一条记录的两个部分中
- 该列表存储的变长字段字节数，会按照列顺序的逆序进行存放
- InnoDB在读取数据前会先查看表结构，如果变长字段允许存储的最大字节数(W * M)都小于255，则其数据长度用一个字节表示
- 该列表不存储为NULL的数据
- 如果表中所有的列都没使用变长数据类型，或者所有的变长数据列的值都为null，则该列表不存在



实例:

![Xnip2021-09-29_10-29-33](MySQL Note.assets/Xnip2021-09-29_10-29-33.jpg)

- 该表采用ASCII字符集编码，所以一个字符需要使用一个字节进行编码
- 对于name字段中的数据'aaa'，其包含三个字符，在ASCII中一个字符使用一个字节，所以该条数据占用空间为3字节
- 因为变长字段长度在记录时按照列顺序的逆序，所以对于第一条记录效果如下:

|  03  | NULL值列表 | 记录头信息 | 列1  | 。。。 | 列n  |
| :--: | :--------: | :--------: | :--: | :----: | ---- |

- 实际上没有空格











对于内容较长的字段:

- 如果W * M ≤ 255，则只用一个字节表示变长数据占用的字节数
- 否则分情况:
- L ≤ 127，则使用1个字节
- 否则使用两个字节



说明:

- W为字符集规定的表示字符时所用最长的字节数
- M为该类型中存储字符的最大数量，即varchar(10)中的10
- L为数据实际占用的字节数



比如对于该实例:

- W为1，因为charset为ASCII
- M为3，因为varchar(3)
- 对于数据'ccc'，其实际所占字节数为3，所以L为3













##### 2. NULL值列表

- 该列表记录所有值为NULL的列(在一条记录中)
- 如果列为primary key或者被NOT NULL修饰，则NULL值列表不计入
- 同变长字段长度列表相同，如果所有的列中都没有值为NULL的，则该列表不存在
- 该列表同样通过列顺序的逆序存储，每个列都用一个二进制位存储。该位为1，则该列的值为null，为0则反之
- 该列表必须使用整数个字节位表示，不足则高位补0
- 如果表中可以为null的列有9个，则使用2个字节表示





实例:

![Xnip2021-09-29_10-51-15](MySQL Note.assets/Xnip2021-09-29_10-51-15.jpg)

- 该表主键为列id，对于第一条记录: Tel字段值为NULL，所以记录为010(按照adress -> Tel -> name的顺序)
- 对于第二条记录: 110，高位补零后: 0000 0110



填充NULL值列表后第二条记录:

|  3   | 0000 0110 | ...  | ...  | ...  | ...  |
| :--: | :-------: | :--: | :--: | :--: | :--: |



第一条记录:

| 05 03 | 0000 0010 | ...  | ...  | ...  | ...  |
| :---: | :-------: | :--: | :--: | :--: | :--: |



















##### 3. 记录头信息(了解)



















#### 2) 记录的真实数据

- 除了手动添加的数据外，MySQL还会默认添加一些列(隐藏列)

|   Column    | Is necessary | Space  |         Description          |
| :---------: | :----------: | :----: | :--------------------------: |
|  DB_ROW_ID  |      No      | 6bytes | ROW_ID, Unique mark a record |
|  DB_TRX_ID  |     Yes      | 6bytes |        Transaction ID        |
| DB_ROLL_PRT |     Yes      | 7bytes |      roll back pointer       |





- InnoDB的主键策略: 优先使用用户指定的主键，如果如果没有指定则选一个NOT NULL修饰的UNIQUE键作为主键
- 如果两者都没有，则自动添加隐藏列"DB_ROW_ID"作为主键
- 除了第一个可选，其余两个是必须的













#### 3) CHAR(M)存储

- 对于一个CHAR(M)类型的列，虽然其不是变长数据类型，但如果其对应的字符集是变长的(如utf8)，则其占用的字节数同样会被保存在变长字段长度列表中
- 在COMPACT行格式中，如果采用变长字符集，则CHAR(M)列至少占用M个字节，如采用utf8编码的char(10)，一个字符至少占用10个字节
- 设计的初衷是为了方便修改，而不需要动态的分配对应的空间

























### REDUNDANT(了解)

- 其在MySQL5.0之间一直使用

















### 溢出列

- 当一列或多列中的数据所占的字节数大于页的大小时(16KB)，只会存储数据的一部分，其余部分存储到其他页中，记录后半部分用20个字节存储指向剩余数据的页地址
- 对于COMPACT和REDUNDANT行格式，如果列的记录数据过大，则只存储前768字节，剩余数据存储在其他页中(溢出页)
- 对于表中需要使用溢出页来存储数据的列来说，其被称为溢出列(off-page列)
- VARCHAR(M), TEXT, BLOB都可能成为溢出列





溢出页的临界点

- 每页需存储的额外信息需要132字节(规定！)
- MySQL中规定一页中至少两行记录(两行数据)
- 每条记录中的额外信息需要27bytes
- 溢出页没有这些规定

参照以上的规则可得:

132 + 2 * (27 + n) < 16384

(仅作为参考，因为实际情况中每页不一定只有两条记录)











### DYNAMIC/COMPRESSED

- MySQL5.7的默认ROW_FORMAT为DYNAMIC
- 在处理溢出数据时，DYNAMIC页中不会存储任何真实数据，而是全部存储在溢出页中
- COMPRESSED会使用压缩算法

****























# 五、InnoDB索引页结构

- page是InnoDB管理存储的基本单位，有多种不同的page存在
- 存放第四章中记录的page被称为INDEX页
- 每个页中有多条记录，每条记录的结构对应第四章的结构



## 1. 索引页结构概述

(了解)



|    **File Header**     |
| :--------------------: |
|    **Page Header**     |
| **Infimum + Supremum** |
|    **User Records**    |
|     **Free Space**     |
|   **Page Directory**   |
|    **File Trailer**    |





Description:

|        Name        |   Space   |            Description             |
| :----------------: | :-------: | :--------------------------------: |
|    File Header     |  38Bytes  |        General Info of page        |
|    Page Header     |  56Bytes  | Some exclusive info. of index page |
| Infimum + Supremum |  26Bytes  |        Two virtual records         |
|    User Records    | Unconfirm |    User storage record content     |
|     Free Space     | Unconfirm |           Not used Space           |
|   Page Directory   | Unconfirm |    Relative location of records    |
|    File Trailer    |  8Bytes   |      Check integrity of page       |

















## 2. 记录在页中的存储

- 我们添加的数据按照指定的ROW_FORMAT存储在User Record部分
- 最初生成页时并没有User Record部分，而是插入记录时从Free Space中划分空间









### 记录中的记录头



实例:

![Xnip2021-09-30_09-47-22](MySQL Note.assets/Xnip2021-09-30_09-47-22.jpg)

- charset为ASCII，ROW_FORMAT为COMPACT



记录头的属性:

|     Name     | Byte |                         Description                          |
| :----------: | :--: | :----------------------------------------------------------: |
|   预留位1    |  1   |                            No use                            |
|   预留位2    |  1   |                            No use                            |
| deleted_flag |  1   |                          is deleted                          |
| min_rec_flag |  1   | Min item record <br />in the each level' non-left node in B+ tree |
|   n_owned    |  4   |             The number of records in each group              |
|   heap_no    |  13  |   The relative location of the current record in page heap   |
| record_type  |  3   | 0 means common, 1 means non-leaf node in B+ tree<br />2 means Infimum, 3 means Supremum |
| next_record  |  16  |           The relative position of the next record           |





测试数据:

![Xnip2021-09-30_10-27-41](MySQL Note.assets/Xnip2021-09-30_10-27-41.jpg)



插入的记录在索引页中User Page部分的表示:



1st:

| deleted_flag | Min_rec_flag | n_owned | heap_no | record_type | next_record | data | data | data | others |
| :----------: | :----------: | :-----: | :-----: | :---------: | :---------: | ---- | ---- | ---- | ------ |
|      0       |      0       |    0    |    2    |      0      |     32      | 1    | 100  | aaaa | ...    |





2nd:

| deleted_flag | Min_rec_flag | n_owned | heap_no | record_type | next_record | data | data | data | others |
| :----------: | :----------: | :-----: | :-----: | :---------: | :---------: | ---- | ---- | ---- | ------ |
|      0       |      0       |    0    |    3    |      0      |     32      | 2    | 200  | bbbb | ...    |





3rd:

| deleted_flag | Min_rec_flag | n_owned | heap_no | record_type | next_record | data | data | data | others |
| :----------: | :----------: | :-----: | :-----: | :---------: | :---------: | ---- | ---- | ---- | ------ |
|      0       |      0       |    0    |    4    |      0      |     32      | 3    | 300  | cccc | ...    |





4th:

| deleted_flag | Min_rec_flag | n_owned | heap_no | record_type | next_record | data | data | data | others |
| :----------: | :----------: | :-----: | :-----: | :---------: | :---------: | ---- | ---- | ---- | ------ |
|      0       |      0       |    0    |    5    |      0      |    -111     | 4    | 400  | dddd | ...    |

- **注意：**省略了预留位1, 2









#### deleted_flag

- 用来标记该记录是否被删除，占用1bit，记录被删除则记为1
- 没错，删除数据后，记录不会真正的删除，空间也不会被回收。记录之间形成了一条链表，删除记录时则断开该链表即可O(1)
- 被"删除"的记录会组成一个"垃圾链表"，该链表占用的空间为"可重用空间"
- 如果有新记录插入，则可能会覆盖掉可重用空间





#### min_rec_flag

- 如果是B+树每层非叶子节点中的最小目录项记录，则会添加该标记(暂时做了解)







#### n_owned:

(之后讲解)







#### heap_no

- 所有记录在堆中的相对位置，包含deleted_flag为1的记录(堆即为记录紧靠排列形成的结构)
- 处于堆中靠前位置的记录heap_no较小



- 用户插入记录的heap_no从2开始，0和1是InnoDB在每个页中自动添加的两条伪记录(Infimum和Supremum)，这两条记录的heap_no最小
- **对于完整的记录而言**，比较记录的大小就是比较主键的大小(只是完整的记录)
- InnoDB规定: 任何用户记录都大于Infimum，都小于Supremum，结构:

|   记录头   |   69 6E 66 69 6D 75 6D 00   |
| :--------: | :-------------------------: |
| **记录头** | **73 75 70 72 65 6D 75 6D** |

数据部分固定，表示两个单词



- 这两条记录单独放在称为"Infimum + Supremum"的部分
- heap_no在分配后不会改变，即使记录被删除









#### record_type

(索引部分再讲解)













#### next_record

- 表示从当前记录到下一条记录中真实数据的距离，为正值则说明下一条记录在后面，这样形成了链表
- 如果删除一条记录，则将其next_record值变为0即可，InnoDB始终会根据主键维护一个单向链表
- 如果插入新记录，则会复用原来被删除记录的存储空间

****



















## 3. Page Directory

虽然记录被作为链表维护，但查询时并非像普通链表那样一条一条的查询

- InnoDB会将所有的记录(包含Infimum和Supremum但不包含被删除的记录)分作几个组
- 每组最后一条记录中头信息的n_owned属性表示该组中记录的总数
- 每组中最后一条记录在page的地址偏移量会被单独提取出来，存储在page中的page Directory部分，该部分中的这些偏移量被称为slot(槽)，每个slot占用2bytes
- 所以Page中的Page Directory就是由slot组成的



Eg:

![Xnip2021-09-30_14-18-21](MySQL Note.assets/Xnip2021-09-30_14-18-21.jpg)







- 实例中有四条用户数据，和两条伪数据(Infimum, Supremum)，InnoDB将它们分为两组:
- 第一组只有infimum，剩余5条记录一组
- 分组的规定:

1. infimum记录所在分组只能有一条记录(自成一组)
2. Supremum记录所在分组只能为1～8条记录
3. 其余分组的记录只能有4～8条





### 记录分组步骤

- 初始情况: 

最初只有两条记录，分属两个组，所以只有两个slot



- 插入数据:

找到比插入数据主键大的最小主键值的slot(地址偏移量)

将该slot对应的n_owned值加一，直到记录数为8个



- 如果插入的记录等于8：

再次插入时将组中记录分为两组，一组4条，一组5条，新增一个slot



Eg:

![Xnip2021-09-30_14-27-01](MySQL Note.assets/Xnip2021-09-30_14-27-01.jpg)

- 通过主键寻找记录，其实是通过二分法找到对应的slot，再找对应的记录

















## 4. Page Header

- 该部分为Index页专有



结构及描述:

|       Name        | Bytes |                         Description                          |
| :---------------: | :---: | :----------------------------------------------------------: |
| PAGE_N_DIR_SLOTS  |   2   |           The quantity of slots in page directory            |
|   PAGE_HEAP_TOP   |   2   |                The min address in free space                 |
|    PAGE_N_HEAP    |   2   | The first bit means whether the record is compact <br />The left 15 bits means the quantity of record in this heap<br />Include Infimum, Supremum and "deleted" record |
|     PAGE_FREE     |   2   | All the deleted record will build a linked list by next_record <br />PAGE_FREE stand the offset of the linked list in page |
|   PAGE_GARBAGE    |   2   |                 The bytes of deleted records                 |
| PAGE_LAST_INSERT  |   2   |                 The last position to insert                  |
|  PAGE_DIRECTION   |   2   |                  The direction of inserting                  |
| PAGE_N_DIRECTION  |   2   |              The number of continuous inserting              |
|    PAGE_N_RECS    |   2   | The quantity of user records(not include infimum and supremum) |
|  PAGE_MAX_TRX_ID  |   8   |      The max transaction ID to modify the current page       |
|    PAGE_LEVEL     |   2   |           The level in B+ tree of the current page           |
|   PAGE_INDEX_ID   |   8   |                The index of the current page                 |
| PAGE_BTR_SEG_LEAF |  10   |                             ...                              |
| PAGE_BTR_SEG_TOP  |  10   |                             ...                              |





### PAGE_DIRECTION

- 如果插入记录的主键值大于上一条记录，则该条记录的插入方向为"右边"，反之为"左边"，其状态用PAGE_DIRECTION表示





### PAGE_N_DIRECTION

- 如果连续几次插入的方向一致，则InnoDB会记录下插入的数量，该数量用PAGE_N_DIRECTION表示
- 一旦插入的方向发生变化，则该值清零













## 5. File Header



结构/描述

|               Name                | Bytes |                         Description                          |
| :-------------------------------: | :---: | :----------------------------------------------------------: |
|      FILE_PAGE_SPACE_CHKSUM       |   4   | 在4.0.14之前，其用来表示页面所在的表空间ID <br />现在用来表示页的校验和 |
|         FILE_PAGE_OFFSET          |   4   |                             页号                             |
|          FILE_PAGE_PREV           |   4   |                         上一页的页号                         |
|          FILE_PAGE_NEXT           |   4   |                         下一页的页号                         |
|           FILE_PAGE_LSN           |   8   |          页面被修改时对应的LSN(Log Sequence Number)          |
|          FILE_PAGE_TYPE           |   2   |                           页的类型                           |
|        FILE_PAGE_FLUSH_LSN        |   8   |                             ...                              |
| FILE_PAGE_ARCH_LOG_NO_OR_SPACE_ID |   4   |                             ...                              |





### FILE_PAGE_SPACE_OR_CHKSUM

- 其代表当前page的校验和
- 通过算法用一个短的string代表一个长的string，这个短的string就代表校验和
- 省去了长字符比较的时间损耗





### FILE_PAGE_OFFSET

- 页面的页号，InnoDB通过它来唯一定位一个page









### FILE_PAGE_TYPE

- page的类型:

|           Name           | Hexadecimal |            Description             |
| :----------------------: | :---------: | :--------------------------------: |
| FILE_PAGE_TYPE_ALLOCATED |   0x0000    |              Not used              |
|    FILE_PAGE_UNDO_LOG    |   0x0002    |           undo log page            |
|     FILE_PAGE_INODE      |   0x0003    |  The info. of storage paragraphs   |
| FILE_PAGE_IBUF_FREE_LIST |   0x0004    |     "Change Buffer" free list      |
|  FILE_PAGE_IBUF_BITMAP   |   0x0005    |   Attribution of "Change Buffer"   |
|    FILE_PAGE_TYPE_SYS    |   0x0006    |       store some system data       |
|  FILE_PAGE_TYPE_TRX_SYS  |   0x0007    |   The data of transaction system   |
|  FILE_PAGE_TYPE_FSP_HDR  |   0x0008    |   The info. of table head space    |
|   FILE_PAGE_TYPE_XDES    |   0x0009    | Some attributions in storage space |
|   FILE_PAGE_TYPE_BLOB    |   0x000A    |           overflow page            |
|     FILE_PAGE_INDEX      |   0x45BF    |             Index Page             |











### FILE_PAGE_PREV/FILE_PAGE_NEXT

- 对于溢出的数据，InnoDB会将溢出的记录存储到溢出页中
- 这两个属性代表该index页上一页和下一页的页号(双向链表)
- 并不是所有类型的页都有这个属性

















## 6. File Trailer

- 为了防止页面记录更改后没有及时写到磁盘中，File Trailer中的数据用来检测一个page是否完整
- 该部分由8bytes组成



### 前四个bytes

- 代表page的校验和，其与File Header的校验和对应
- 校验的方法:

先将File Header的检验和写入到磁盘中，待所有记录都写入到磁盘后，再将校验和写入到File Trailler中

如果两个校验和一致，则说明页面刷新成功







### 后四个bytes

- 代表最后修改时对应的LSN后四个bytes，也用于校验page的完整性















