# 一、获取数据库连接

- Java定义了一个Driver驱动接口，每个想要接入的数据库都需要实现这个类
- 所以我们需要导入对应数据库重写的接口



## 1.1 第一种获取连接的方式

![Xnip2021-12-13_21-18-10](JDBC.asset/Xnip2021-12-13_21-18-10.jpg)







## 1.2 第二种获取连接的方式

![Xnip2021-12-13_21-37-22](JDBC.asset/Xnip2021-12-13_21-37-22.jpg)

- 其实在MySQL对应jar包内有一个META-INF/services/java.sql.Driver文件
- MySQL默认为我们加载了，所以可以省去加载的步骤直接通过DriverManager获取连接

![Xnip2021-12-13_21-38-30](JDBC.asset/Xnip2021-12-13_21-38-30.jpg)







## 1.3 第三种方式

![Xnip2021-12-13_21-56-35](JDBC.asset/Xnip2021-12-13_21-56-35.jpg)



连接所需的参数:

实现驱动、url，password，user



1. 将这些参数放在单独的配置文件中方便修改，实现了程序与数据的解耦(分离)
2. 项目编译时不需要重新打包(代码没变，只是外部的配置文件变了)

<hr>

















# 二、使用PreparedStatement进行CRUD



## 2.1 Statement的弊端

- 在使用Statement对象调用execute方法时，拼接字符串很麻烦
- 会被SQL注入攻击

![Xnip2021-12-14_21-16-10](JDBC.asset/Xnip2021-12-14_21-16-10.jpg)



![Xnip2021-12-14_21-18-25](JDBC.asset/Xnip2021-12-14_21-18-25.jpg)



![Xnip2021-12-14_21-22-30](JDBC.asset/Xnip2021-12-14_21-22-30.jpg)

SQL注入:

为防止SQL注入，可以使用PreparedStatement









## 2.2 PreparedStatement对象

- 写好SQL语句，其中需要传入的参数用占位符(?)代替
- 通过Connection对象调用prepareStatement方法，传入写好的SQL语句，返回一个PreparedStatement对象
- 通过该PreparedStatement对象调用setType()方法设置占位符代表的参数(index, param)，注意索引从1开始





![Xnip2021-12-14_21-54-50](JDBC.asset/Xnip2021-12-14_21-54-50.jpg)









## 2.3 通用的更新方法

![Xnip2021-12-14_22-22-31](JDBC.asset/Xnip2021-12-14_22-22-31.jpg)











## 2.4 查询操作

- execute(): 单纯的执行
- executeQuery(): 该执行会返回一个ResultSet对象
- 通过ResultSet对象，调用getType获取每条记录对应索引的值即可
- 最好将每条数据封装为一个对象

![Xnip2021-12-14_22-28-48](JDBC.asset/Xnip2021-12-14_22-28-48.jpg)







