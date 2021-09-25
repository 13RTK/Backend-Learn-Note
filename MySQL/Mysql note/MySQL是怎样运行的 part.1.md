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













