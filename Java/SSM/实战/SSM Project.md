# 一、注册/表/依赖



## 1. 后端部分

- 导入Thymeleaf的拓展依赖:

```xml
<dependency>
  <groupId>org.thymeleaf.extras</groupId>
  <artifactId>thymeleaf-extras-springsecurity5</artifactId>
  <version>3.0.4.RELEASE</version>
</dependency>
```



- 设置Thymeleaf方言:

```java
@Bean
public SpringTemplateEngine springTemplateEngine(@Autowired ITemplateResolver resolver){
  SpringTemplateEngine engine = new SpringTemplateEngine();
  engine.setTemplateResolver(resolver);
  engine.addDialect(new SpringSecurityDialect());   //添加针对于SpringSecurity的方言
  return engine;
}
```

![Xnip2022-03-28_10-34-21](SSM Project.assets/Xnip2022-03-28_10-34-21.jpg)







- 在SecurityConfig中设置基于JDBC的持久化Token:
- 注意第一次使用时会创建一张表

```java
@Bean
public PersistentTokenRepository jdbcRepository(@Autowired DataSource dataSource){
  JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();  //使用基于JDBC的实现
  repository.setDataSource(dataSource);   //配置数据源
  return repository;
}
```



- 设置一个Respositotry自动注入(利用上述我们刚刚注册的Bean)
- 在configure方法中添加该repository:

![Xnip2022-03-28_11-04-27](SSM Project.assets/Xnip2022-03-28_11-04-27.jpg)



![Xnip2022-03-28_11-04-34](SSM Project.assets/Xnip2022-03-28_11-04-34.jpg)

<hr>









## 2. 数据库

- 创建一个新的库和三张表

Eg:

![Xnip2022-03-28_10-37-57](SSM Project.assets/Xnip2022-03-28_10-37-57.jpg)

<hr>

















## 3. 前端

在template中添加一个注册页面

Eg:

![Xnip2022-03-28_10-40-24](SSM Project.assets/Xnip2022-03-28_10-40-24.jpg)



修改对应的属性:

![Xnip2022-03-28_10-44-45](SSM Project.assets/Xnip2022-03-28_10-44-45.jpg)



![Xnip2022-03-28_11-06-50](SSM Project.assets/Xnip2022-03-28_11-06-50.jpg)

<hr>











## 4. 实现注册

- 创建对应功能的接口和实现类:

![Xnip2022-03-28_14-08-35](SSM Project.assets/Xnip2022-03-28_14-08-35.jpg)

我们这里使用了事务注解，使得创建用户和添加学生操作形成了一个整体



- 在Root容器中注册事务管理器:

![Xnip2022-03-28_14-12-57](SSM Project.assets/Xnip2022-03-28_14-12-57.jpg)

注意添加后写上一个开启事务管理器的注解







- 创建对应的Mapper:
    - 添加用户
    - 添加学生

![Xnip2022-03-28_14-08-52](SSM Project.assets/Xnip2022-03-28_14-08-52.jpg)

注意：这里我们使用了@Optional注解来回传id字段





- 自动注入Service实例，在AuthController中使用即可

Eg:

![Xnip2022-03-28_14-11-10](SSM Project.assets/Xnip2022-03-28_14-11-10.jpg)





