# 一、基本操作



## 1. 连接/启动/关闭



- 首先需要先启动服务器(server)

CLI:

```shell
redis-server
```

- 注意此时没有加载任何配置文件



Eg:

![Xnip2022-05-03_18-29-44](Redis basic.assets/Xnip2022-05-03_18-29-44.jpg)





开启一个client客户端与之连接:

CLI:

```shell
redis-cli
```



Eg:

![Xnip2022-05-03_18-32-24](Redis basic.assets/Xnip2022-05-03_18-32-24.jpg)

- 发送`ping`命令返回PONG则说明连接成功





- 通过`select`可以切换一个redis服务器实例中的数据库:

```sql
SELECT number
```

- 在使用终端建立连接时，默认会使用第一个数据库(序号为0)，默认一共有16个数据库；其中第1个数据库(序号为0)的序号不会显示出来

Eg:

![Xnip2022-05-03_18-46-08](Redis basic.assets/Xnip2022-05-03_18-46-08.jpg)



- mac上通过brew安装后，redis的安装路径:

![Xnip2022-05-03_18-47-04](Redis basic.assets/Xnip2022-05-03_18-47-04.jpg)



- Redis在mac上的配置文件路径:

```
/usr/local/etc/redis.conf
```

![Xnip2022-05-03_18-47-43](Redis basic.assets/Xnip2022-05-03_18-47-43.jpg)



在启动时加载配置文件:

![Xnip2022-05-03_20-59-20](Redis basic.assets/Xnip2022-05-03_20-59-20.jpg)



用Homebrew停止redis-server:

```shell
brew services stop redis
```

<hr>







## 2. 数据CRUD

> Redis中的数据(所有类型)都是以键值对的形式存放的(默认放在内存里)



### 1) 添加数据:

- 使用`set`命令(使用set命令添加的数据都是string类型):

Syntax:

```
set key value
```



Eg:

![Xnip2022-05-04_13-03-21](Redis basic.assets/Xnip2022-05-04_13-03-21.jpg)



- 使用`mset`命令一次性添加多个键值对

Syntax:

```
mset key1 value1 key2 value2...
```



Eg:

![Xnip2022-05-04_13-05-46](Redis basic.assets/Xnip2022-05-04_13-05-46.jpg)

<hr>









### 2) 获取/查询数据

- 使用`set`命令插入的string类型数据，通过`get`命令即可获取对应键的值
- 通过`MGET`命令查询出所有的值

Syntax:

```
get key
```



Eg:

![Xnip2022-05-04_13-09-14](Redis basic.assets/Xnip2022-05-04_13-09-14.jpg)



![Xnip2022-05-04_16-40-39](Redis basic.assets/Xnip2022-05-04_16-40-39.jpg)









- 通过`EXISTS`命令可以查看键值对是否存在:

Syntax:

```
EXISTS key
```

- 返回0代表不存在
- 返回1代表存在



Eg:

![Xnip2022-05-04_16-19-20](Redis basic.assets/Xnip2022-05-04_16-19-20.jpg)







- 通过`KEYS`命令可以查询出符合条件的键

Syntax:

```
KEYS *
```

- *代表匹配所有的键



Eg:

![Xnip2022-05-04_16-20-54](Redis basic.assets/Xnip2022-05-04_16-20-54.jpg)





- 使用`RANDOMKEY`命令可以随机获取一个键

Syntax:

```
RANDONKEY
```



Eg:

![Xnip2022-05-04_16-29-16](Redis basic.assets/Xnip2022-05-04_16-29-16.jpg)







- 通过`TYPE`命令可以查看键值对存储的值的类型

Syntax:

```
TYPE key
```



Eg:

![Xnip2022-05-04_16-37-45](Redis basic.assets/Xnip2022-05-04_16-37-45.jpg)

<hr>





### 3) 设置键值对的过期时间

- 在插入string类型数据时设置过期时间
- 插入时不设置过期时间则默认为永久

Syntax:

```
set key value EX expire_time
```



![Xnip2022-05-04_13-12-10](Redis basic.assets/Xnip2022-05-04_13-12-10.jpg)

其中EX是指以秒为单位的过期时间，PX指以毫秒为单位的过期时间



- 使用`EXPIRE`命令为永久保存的键设置过期时间:

Syntax

```
EXPIRE key
```



Eg:









- 通过`ttl`命令查询键值对的过期时间
    - 返回结果为一个整数，其代表该键的过期时间
    - 如果为-1则说明该键值对是永久的
    - 如果为-2则说明该键值对已经过期，或者不存在



Syntax:

```
ttl key
```

- 通过PTTL可以返回以毫秒为单位的过期时间



Eg:

![Xnip2022-05-04_13-19-09](Redis basic.assets/Xnip2022-05-04_13-19-09.jpg)







- 通过`persist`将设置了过期时间的键值对改为永久有效

Syntax:

```
PERSIST key
```



Eg:

![Xnip2022-05-04_16-15-58](Redis basic.assets/Xnip2022-05-04_16-15-58.jpg)

<hr>









### 4) 删除数据

- 使用`del`命令即可

Syntax:

```
del key
```



Eg:

![Xnip2022-05-04_16-17-26](Redis basic.assets/Xnip2022-05-04_16-17-26.jpg)

<hr>










### 5) 移动键值对

- 通过`MOVE`命令可以将键值对移动到对应的数据库中

Syntax:

```
MOVE key db
```



Eg:

![Xnip2022-05-04_16-23-09](Redis basic.assets/Xnip2022-05-04_16-23-09.jpg)

<hr>











### 6) 修改键值对

- 通过`RENAME`命令可以修改键值对的键前
- `RENAMENX`命令可以在修改键的名称前检测是否有重名

Syntax:

```
RENAME key newkey
```



Eg:

![Xnip2022-05-04_16-25-58](Redis basic.assets/Xnip2022-05-04_16-25-58.jpg)



![Xnip2022-05-04_16-27-58](Redis basic.assets/Xnip2022-05-04_16-27-58.jpg)





- 如果键值对中存储的值是纯数字，则可以使用命令进行加减操作:
    - `DECR`:自减
    - `INCR`: 自增
    - `DECRBY`: 减去对应的数字
    - `INCR`: 增加对应的数字



Eg:

![Xnip2022-05-04_16-34-13](Redis basic.assets/Xnip2022-05-04_16-34-13.jpg)



![Xnip2022-05-04_16-34-58](Redis basic.assets/Xnip2022-05-04_16-34-58.jpg)

<hr>







## 3. hash类型





### 1) 创建/修改

hash类型类似Java中值为map的一个map实例:

```java
Map<String, Map<String, T>> map = new HashMap<>();
```



- 通过`HSET`命令可以插入一个hash类型的键值对
- 如果对应key的字段已经存在，则会用这次输入的值覆盖掉之前的值
- 可以一次性插入多个字段和值，返回的值即为插入/修改的字段值

Syntax:

```
HSET key field field_val...
```



Eg:

![Xnip2022-05-04_17-58-09](Redis basic.assets/Xnip2022-05-04_17-58-09.jpg)

<hr>







### 2) 查询

- 通过`HGET`命令可以获取对应key中对应字段的值

Syntax:

```
HSET key field_name
```



Eg:

![Xnip2022-05-04_18-04-46](Redis basic.assets/Xnip2022-05-04_18-04-46.jpg)





- 通过`HGETALL`命令可以获取对应键中所有的字段和值

Syntax:

```
HGETALL key_name
```



Eg:

![Xnip2022-05-04_18-06-49](Redis basic.assets/Xnip2022-05-04_18-06-49.jpg)







- 通过`HEXISTS`命令可以判断对应key中的字段是否存在

Syntax:

```
HEXISTS key field_name
```





Eg:

![Xnip2022-05-04_18-09-15](Redis basic.assets/Xnip2022-05-04_18-09-15.jpg)

<hr>











### 3) 删除

- 通过`HDEL`命令可以删除对应键中的对应字段

Syntax:

```
HDEL key field
```



Eg:

![Xnip2022-05-04_18-10-42](Redis basic.assets/Xnip2022-05-04_18-10-42.jpg)



















