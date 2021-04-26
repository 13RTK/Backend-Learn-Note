# 1.概念



JDBC: Java Database Connectivity

由使用java在jdk中具有的相关的接口，可以用来连接数据库，但**仅限于关系型数据库**(MySQL, Oracle, SQL server)，其余类似Redis, mongdb则不行

****









# 2.使用



## 1) 导入相应jar包

**注：这里以MySQL为例**



- 在Maven库中搜索"jdbc MySQL"，找到相应的jar包并下载

![Xnip2021-04-23_21-09-17](JDBC/Xnip2021-04-23_21-09-17.jpg)









- 将jar包导入项目中，并以library的方式添加，步骤同Apache.Commons.IO

![Xnip2021-04-23_21-12-24](JDBC/Xnip2021-04-23_21-12-24.jpg)







## 2) 基本结构

抽象类比: java这边就是原料厂，数据库就是产品公司。





- 加载驱动程序(产品公司打电话向原料厂要材料，并指明自己是MySQL公司)

```java
Class.forName("com.mysql.jdbc.Driver");
```



- 获取一个与数据库的连接(告诉原料厂该怎么走，走到哪？)

```java
Connection connection = DriverManger.getConnection(URL, USER, PASSWORD);
```

这里需要指定相关参数:(**最好定义为public static final 字段**)

URL: 数据库的位置，如果在本地则为: "jdbc:mysql://localhost:3306/database_name"(要在最后指定库名)

USER: 用户名，本地可为: "root"

PASSWORD: 用户名对应的密码





- 获取数据库操作对象(货送到了，需要找人卸货)

```java
Statement statement = connection.createStatement();
```





- 使用创建的对象进行操作(卸货中)

```
ResultSet resultSet = statement.execute_operator("SQL statement");
```

1. "operator"可以是增删改查中的任何操作
2. 执行结果会放回一个ResultSet集合
3. 如需要显示执行结果(如果是查询操作)，则需要使用ResultSet获取字段信息
4. 获取字段可使用"get_type()"方法，返回相应的字段值(**需要和表中的字段顺序相对应**)
5. "SQL statement"需要指定库中的表





- 输出(通过ResultSet对象获取字段信息)

```java
while (resultSet.next()) {
  type info1 = resultSet.get_type();
  ...
  System.out.println(info1 + " " + ...)
}
```





exam:

![Xnip2021-04-23_21-34-00](JDBC/Xnip2021-04-23_21-34-00.jpg)





![Xnip2021-04-23_21-34-17](JDBC/Xnip2021-04-23_21-34-17.jpg)

- 注意使用后需要关闭操作对象和连接(**按照栈的顺序**)








