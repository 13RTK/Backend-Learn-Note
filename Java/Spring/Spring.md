# 一、初入



## 1. 配置一个Spring项目

可以在https://start.spring.io上配置Spring项目:

- 在其中可以选择构建工具(Maven/Gradle)、语言(Java, Kotlin, Groovy)，SpringBoot的版本，项目的具体信息，打包的方式，Java的版本等等
- 在右边可以选择添加对应的依赖

![Xnip2022-07-03_09-42-01](Spring.assets/Xnip2022-07-03_09-42-01.jpg)



![Xnip2022-07-03_09-43-03](Spring.assets/Xnip2022-07-03_09-43-03.jpg)



点击生成后，会生成一个压缩文件包，解压后导入至IDEA即可(注意需要设置项目的Java版本，并加载Maven的pom.xml文件):

![Xnip2022-07-03_09-44-27](Spring.assets/Xnip2022-07-03_09-44-27.jpg)

<hr>











## 2. Hello World

`HelloSpringApplication.java`文件中的`main`方法就是整个Spring项目的入口



编写一个Controller:

- 在HelloSpringApplication类上添加一个`@RestController`注解
- 在类中添加一个hello方法，该方法有一个`@RequestMapping`注解

![Xnip2022-07-03_09-48-29](Spring.assets/Xnip2022-07-03_09-48-29.jpg)



添加好注解后直接运行该程序，此时会在8080端口开启一个Tomcat服务器:

![Xnip2022-07-03_09-49-57](Spring.assets/Xnip2022-07-03_09-49-57.jpg)



通过curl命令即可访问该Controller:

![Xnip2022-07-03_09-50-41](Spring.assets/Xnip2022-07-03_09-50-41.jpg)



我们导入了Actuator工具，访问actuator/health路径可以查看当前项目的状态:

![Xnip2022-07-03_09-51-57](Spring.assets/Xnip2022-07-03_09-51-57.jpg)





补充:

1. `@RestController`:

该注解等同于`@Controller` +` @ResponseBody`

@Controller: 

> 将被其修饰的类注入到SpringBoot的IoC容器中，使得项目运行时，该类就被实例化，即该类充当`Controller`的作用



@ResponseBody:

> 指定被修饰的类中所有的API接口返回的数据都以JSON字符串的形式返回



2. curl:

一个利用URL规则在命令行下工作的文件传输工具，其支持文件的上传和下载







3. Actuator:

![Xnip2022-07-03_11-29-17](Spring.assets/Xnip2022-07-03_11-29-17.jpg)

通过在项目中引入该依赖，可以直接访问项目的`/actuator/health`路径查看项目的状态

也可以访问`/actuator/beans`查看项目中注册的bean，返回的数据都是JSON格式，所以需要安装对应的浏览器插件才可以方便地查看

通过访问`/actuator/mappings`可以查看所有web的URL映射

通过访问`/actuator/env`可以查看所有的环境信息

![Xnip2022-07-03_10-53-07](Spring.assets/Xnip2022-07-03_10-53-07.jpg)



默认情况下，只开放了`health`和`info`，想要手动开启其他的路径访问，需要在配置文件中填写:

```properties
management.endpoints.web.exposure.include=health, beans, info
```

<hr>















## 3. 打包和运行

通过maven中的package插件，我们可以将项目打包为一个`.jar`文件，然后只需要使用`java -jar`命令即可运行该项目:

![Xnip2022-07-03_10-00-19](Spring.assets/Xnip2022-07-03_10-00-19.jpg)



![Xnip2022-07-03_10-00-54](Spring.assets/Xnip2022-07-03_10-00-54.jpg)

<hr>











# 二、Spring数据操作



## 1. JDBC

数据源的通用配置:

![Xnip2022-07-03_10-55-46](Spring.assets/Xnip2022-07-03_10-55-46.jpg)





### 1) 配置数据源

必要的内容:

```yaml
spring:
  datasource:
    url: "jdbc:mysql://localhost/test"
    username: "dbuser"
    password: "dbpass"
```



导入对应的依赖:

- JDBC API
- MySQL Driver

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.28</version>
</dependency>
```





通用的配置:

```yaml
spring:
	datasource:
    url: jdbc:mysql://localhost:3306/book_manage
    username: root
    password: abcdef
    driver-class-name: com.mysql.cj.jdbc.Driver
```





> 默认的数据库连接池为HikariCP

- 设置HikariCP的配置:

```yaml
spring:
	datasource:
      hikari:
        maximumPoolSize: 5
        minimumIdle: 5
        idleTimeout: 600000
        connectionTimeout: 30000
        maxLifetime: 1800000
```





导入配置后，通过`@Resource`注解即可自动注入`JdbcTemplate`实例对象进行JDBC操作:

![Xnip2022-07-04_16-02-39](Spring.assets/Xnip2022-07-04_16-02-39.jpg)



JdbcTemplate文档:

https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html



其他人的博客:

https://www.cnblogs.com/caoyc/p/5630622.html







- 通过在配置文件中设置`initialization-mode: always`，即可自动加载`resources`目录下的.sql文件并运行:

```yaml
spring:
  datasource:
    initialization-mode: always
```

![Xnip2022-07-04_18-51-14](Spring.assets/Xnip2022-07-04_18-51-14.jpg)

---











### 2) 配置多个数据源

- 首先需要在配置文件中填写多个数据源的基本信息

Eg:

```properties
management.endpoints.web.exposure.include=*
spring.output.ansi.enabled=ALWAYS

foo.datasource.url=jdbc:mysql://localhost:3306/sql_practice
foo.datasource.username=root
foo.datasource.password=abcdef
foo.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

bar.datasource.url=jdbc:mysql://localhost:3306/book_manage
bar.datasource.username=root
bar.datasource.password=abcdef
bar.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

- 这里我们用不同的开头来区分不同的数据源，而不是使用默认的`spring`





在运行主类中加载两个数据源并获取它们的url:

![Xnip2022-07-05_15-22-07](Spring.assets/Xnip2022-07-05_15-22-07.jpg)



结果:

![Xnip2022-07-05_15-30-42](Spring.assets/Xnip2022-07-05_15-30-42.jpg)







注意:

1. 多彩输出

如果终端支持ANSI，设置彩色输出会让日志更具可读性。通过在`application.properties`中设置`spring.output.ansi.enabled`参数来支持。



2. 自动配置排除

在`@SpringBootApplication`注解中，通过指定`exclude`的值即可排除对应的自动配置类:

```java
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
                              DataSourceTransactionManagerAutoConfiguration.class,
                                  JdbcTemplateAutoConfiguration.class})
```



3. `@ConfigurationProperties("")`

我们可以通过该注解自动加载配置文件中对应的配置

> 在方法上使用该注解时，该方法必须有`@Bean`注解，且其所在的类上必须有`@Configuration`注解(@SpringBootApplication注解中含有)



说明文章:

https://cloud.tencent.com/developer/article/1870740

---

















### 3) 数据库连接池



- HikariCP

官网:

https://github.com/brettwooldridge/HikariCP



在Spring Boot1.x中，默认会使用Tomcat作为数据源，所以想要使用HikariCP时还需要先排除掉Tomcat的数据源

在Spring Boot2.x中，会默认使用HikariCP作为数据库连接池

常规的配置项:

![Xnip2022-07-03_10-31-24](Spring.assets/Xnip2022-07-03_10-31-24.jpg)







- Druid

官网:

https://github.com/alibaba/druid



在Wiki常见问题一栏中可以看到其各种使用上的方法解答:

![Xnip2022-07-03_10-54-03](Spring.assets/Xnip2022-07-03_10-54-03.jpg)





使用时的注意事项:

1. 因为SpringBoot2.x默认使用的是HikariCP，所以我们需要先在JDBC依赖中排除掉它:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-jdbc</artifactId>
  <exclusions>
    <exclusion>
      <groupId>com.zaxxer</groupId>
      <artifactId>HikariCP</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```



2. Druid依赖需要我们手动导入:

```xml
<dependency>
  <groupId>com.alibaba</groupId>
  <artifactId>druid-spring-boot-starter</artifactId>
  <version>1.1.10</version>
</dependency>
```





使用Druid对密码进行加密的操作:

1. 首先在命令行中进入jar包所在的路径中，然后运行命令:

```shell
java -cp druid-1.0.16.jar com.alibaba.druid.filter.config.ConfigTools 自己的密码
```



2. 之后会生成私钥、公钥和加密后的密码:

![Xnip2022-07-05_16-56-50](Spring.assets/Xnip2022-07-05_16-56-50.jpg)



3. 将公钥和密码写在配置文件中，并启用加密配置

![Xnip2022-07-05_16-58-17](Spring.assets/Xnip2022-07-05_16-58-17.jpg)



加密配置:

```properties
spring.datasource.druid.connection-properties=config.decrypt=true;config.decrypt.key=${public-key}
# 启用ConfigFilter
spring.datasource.druid.filter.config.enabled=true
```



开启慢SQL记录的配置项:

![Xnip2022-07-06_15-30-00](Spring.assets/Xnip2022-07-06_15-30-00.jpg)



配置项:

![Xnip2022-07-08_14-13-47](Spring.assets/Xnip2022-07-08_14-13-47.jpg)



![Xnip2022-07-08_14-15-24](Spring.assets/Xnip2022-07-08_14-15-24.jpg)



注:

- 这里通过在SQL末尾添加了`FOR UPDATE`为当前的读操作加了X锁(独占锁)

---















补充:

- `@Component`注解: 注册一个通用的bean
- `@Repository`: 注册一个与Dao相关的类为bean
- `@Service`: 注册一个服务类为bean
- `@Controller`: 注册一个Controller类为bean
- `@RestController`: 注册一个类为Restful风格的bean(返回的数据都是JSON)



![Xnip2022-07-03_10-58-21](Spring.assets/Xnip2022-07-03_10-58-21.jpg)

<hr>








### 4) Spring中的JDBC操作

环境:



schema:

![Xnip2022-07-06_14-40-05](Spring.assets/Xnip2022-07-06_14-40-05.jpg)





配置文件:

![Xnip2022-07-06_14-41-48](Spring.assets/Xnip2022-07-06_14-41-48.jpg)













- 执行单条SQL语句:

<T> T	queryForObject(String sql, Class<T> requiredType): 执行第一个参数中的sql，返回类型为第二个参数指定的泛型

Eg:

![Xnip2022-07-06_14-42-42](Spring.assets/Xnip2022-07-06_14-42-42.jpg)



<T> List<T>	queryForList(String sql, Class<T> elementType): 执行第一个参数中的sql，返回类型为第二个参数指定的泛型对应的list实例

Eg:

![Xnip2022-07-06_14-45-45](Spring.assets/Xnip2022-07-06_14-45-45.jpg)



<T> T	queryForObject(String sql, RowMapper<T> rowMapper): 执行第一个参数中的sql，返回类型为第二个参数指定的类型(需手动将字段对应实体类的字段)

Eg:

![Xnip2022-07-06_14-55-20](Spring.assets/Xnip2022-07-06_14-55-20.jpg)

- 将`queryForObject`换成`query`即可返回对应的对象集合





修改/查询操作:

- 直接使用update方法即可

![Xnip2022-07-06_15-01-04](Spring.assets/Xnip2022-07-06_15-01-04.jpg)

---









- 执行多条SQL语句

我们可以使用JdbcTemplate或者NamedParameterJdbcTemplate中的方法:

![Xnip2022-07-06_15-04-34](Spring.assets/Xnip2022-07-06_15-04-34.jpg)

---




















### 5) 常用注解



- config
    - @Configuration: 将当前类注册为配置类
    - @ImportResource: 将配置文件以外的配置导入进来
    - @ComponentScan: 指定包扫描的路径
    - @Bean: 将方法返回的作为Bean保持在Context中
- bean定义
    - @Component: 定义类为bean
    - @Repository: 标明访问数据库的类为bean
    - @Service: 注册一个服务层的类为bean
    - @Controller/@RestController: MVC类，后者组合了@ResponseBody
- 注入
    - @Autowired: 根据类型注入一个类
    - @Qualifier: 配合Autowired根据名字进行注入
    - @Resource: 直接根据名字进行注入
    - @Value: 获取配置文件中的值

---















## 2. ORM FrameWork

- ORM: 即Object Relation Mapping



其解决了从OOP到RDBMS之间的关联问题





### 1) JPA

- JPA: 即Java Persistence API，其基于Hibernate

在SpringBoot中引入:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
<dependency>
```



#### 1. 实体的定义

> 在JPA中，我们不会用到具体的SQL，而是通过注解将实体类与表直接对应起来

用到的注解:

- 表 - 实体类注解:
    - @Entity: 表明该类是一个实体类
    - @MappedSuperClass: 指定该实体类对应的父类
    - @Table(name=""): 指定与该实体类关联的表的名称



- 主键:
    - @Id: 指明该字段为主键
    - @GeneratedValue(strategy, generator): 指定主键的生成策略和生成器
    - @SequenceGenerator(name, sequenceName): 设置生成器策略



- 字段 - 属性映射
    - @Column(name, nullable, length, insertable, updatable): 设置与该类属性对应的表中字段，以及该字段是否为空、字段的长度、是否可修改
    - @JoinColumn(name): 指明该字段为表连接使用的字段
    - @JoinTable(name): 指明连接所对应的表



- 关系
    - @OneToOne、@OneToMany、@ManyToOne、@ManyToMany
    - @OrderBy







项目背景:

- 线上咖啡馆，完成客户的询问、点单、支付功能，完成服务员的咖啡师之间的通知功能

![Xnip2022-07-08_14-19-51](Spring.assets/Xnip2022-07-08_14-19-51.jpg)



实体关系:

![Xnip2022-07-08_14-21-55](Spring.assets/Xnip2022-07-08_14-21-55.jpg)



Eg:

为了处理金额，这里引入了两个额外的依赖:

![Xnip2022-07-08_14-35-59](Spring.assets/Xnip2022-07-08_14-35-59.jpg)



两个实体类都添加了createtime和updatetime:

![Xnip2022-07-08_14-42-02](Spring.assets/Xnip2022-07-08_14-42-02.jpg)



运行效果:

![Xnip2022-07-08_14-44-13](Spring.assets/Xnip2022-07-08_14-44-13.jpg)

- 因为使用了`@ManyToMany`注解，所以这里会为我们创建一个外键





因为两个实体中有相同的字段(id, createtime和updatetime)，因此我们可以将这相同的字段抽象出来作为一个父类，注意在父类上添加`@MappedSuperclass`注解来表明该类为父类

Eg:

![Xnip2022-07-08_15-07-08](Spring.assets/Xnip2022-07-08_15-07-08.jpg)

- 注意在子类的`@ToString`注解中加上callsuper选项，这样才能将父类的信息一并打印出来



SpringBootJPA对应的配置:

- spring.jpa.hibernate.ddl-auto=create-drop: 设置jpa创建表的模式
    - create ----每次运行该程序，没有表格会新建表格，表内有数据会清空
    - create-drop ----每次程序结束的时候会清空表
    - update ---- 每次运行程序，没有表格会新建表格，表内有数据不会清空，只会更新
    - validate ---- 运行程序会校验数据与数据库的字段类型是否相同，不同会报错

- spring.jpa.properties.hibernate.show_sql=true: 设置是否显示对应的sql操作
- spring.jpa.properties.hibernate.format_sql=true: 设置是否格式化对应的sql操作

---











#### 2. 操作数据库



步骤:

1. 定义接口，其需要继承`CrudRepository`或者`JpaRepository`，在泛型中添加对应的实体类和主键的类型

> 注意要在主类中添加`@EnableJpaRepositories`注解才行

Eg:

![Xnip2022-07-08_15-13-24](Spring.assets/Xnip2022-07-08_15-13-24.jpg)



2. 通过注解创建对应的实例，调用save方法即可将实体类对象保存到对应的表中去

![Xnip2022-07-08_15-15-01](Spring.assets/Xnip2022-07-08_15-15-01.jpg)





3. 可以在接口中自定义自己想要的查询方法，并且用于接口继承

![Xnip2022-07-08_15-16-26](Spring.assets/Xnip2022-07-08_15-16-26.jpg)



4. 通过自动注入的方式通过接口的实例调用对应的方法即可

Eg:

![Xnip2022-07-08_15-32-05](Spring.assets/Xnip2022-07-08_15-32-05.jpg)



![Xnip2022-07-08_15-32-25](Spring.assets/Xnip2022-07-08_15-32-25.jpg)



![Xnip2022-07-08_15-33-15](Spring.assets/Xnip2022-07-08_15-33-15.jpg)

---











### 2) Mybatis

导入依赖:

```xml
<dependency>
  <groupId>org.mybatis.spring.boot</groupId>
  <artifactId>mybatis-spring-boot-starter</artifactId>
</dependency>
```



mybatis在SpringBoot中的配置:













#### 1. 创建Mapper



Mapper中的注解:

- @Mapper: 标记该类为一个映射
- @Insert: 标记该方法为一个插入方法
- @Select: 标记该方法为一个查询方法
- @Update: 标记该方法为一个更新方法
- @Delete: 标记该方法为一个删除方法
- @Options: 设置是否使用生成的值等等
- @Results: 
    - @Result: 手动将对象的字段与表中字段进行对应(可以通过配置项`map-underscore-to-camel-case`来代替)
- @Param: 将方法中的参数与SQL中的参数对应起来



![Xnip2022-07-08_17-17-14](Spring.assets/Xnip2022-07-08_17-17-14.jpg)



Eg:

- 因为save方法没有将实体类字段和表的字段对应起来，所以输出的类中没有createtime和updatetime字段的值

![Xnip2022-07-08_17-32-34](Spring.assets/Xnip2022-07-08_17-32-34.jpg)

---













## 3. NoSQL





### 1) Redis





#### 1. Jedis的简单使用

导入依赖:

```xml
<dependency>
  <groupId>redis.clients</groupId>
  <artifactId>jedis</artifactId>
</dependency>
```

对应的配置:

```properties
redis.host=localhost
redis.max-total=3
redis.max-idle=3
redis.test-on-borrow=true
```







通过JedisPool获取一个Jedis实例，直接使用其对应的方法即可

> 注意，Jedis不是线程安全的



1. 通过注入获取一个JedisPool实例:

![Xnip2022-07-10_14-45-13](Spring.assets/Xnip2022-07-10_14-45-13.jpg)





2. 通过该JedisPool实例调用`getResource`方法获取一个Jedis实例对象:

![Xnip2022-07-10_14-47-26](Spring.assets/Xnip2022-07-10_14-47-26.jpg)





3. 通过该实例对象创建对应的Redis数据

Jedis中的方法和Redis终端中的命令相同

![Xnip2022-07-10_14-50-11](Spring.assets/Xnip2022-07-10_14-50-11.jpg)

---























---





























