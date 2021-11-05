# 一、定义

- 是sun公司发明的用来开发动态web的技术，其提供了一个Servlet接口
- 开发servlet的步骤:
  - 编写一个实现Servlet接口的类
  - 将该类部署到web服务器中

- 实现了Servlet接口的Java程序称为Servlet

****













# 二、Hello Servlet

- sun公司默认接口为HttpServlet类和GenericServlet



## 1. 构建一个普通的Maven项目，删除其中的src目录

![Xnip2021-10-25_14-37-14](Servlet/Xnip2021-10-25_14-37-14.jpg)



![Xnip2021-10-25_14-40-02](Servlet/Xnip2021-10-25_14-40-02.jpg)



尽可能在主项目POM中导入所有的依赖:

![Xnip2021-10-25_14-44-30](Servlet/Xnip2021-10-25_14-44-30.jpg)



![Xnip2021-10-25_14-46-12](Servlet/Xnip2021-10-25_14-46-12.jpg)









## 2. 创建一个子module

- 其中属于子module的pom.xml文件中会继承父项目的依赖，这样就不需要重复导入Maven配置了

![Xnip2021-10-25_15-08-35](Servlet/Xnip2021-10-25_15-08-35.jpg)



![Xnip2021-10-25_15-13-21](Servlet/Xnip2021-10-25_15-13-21.jpg)

父项目中的子项目模组:

```xml
<modules>
        <module>servlet-01</module>
    </modules>
```

子项目中用于定位的父项目模块信息:

```xml
<parent>
    <artifactId>JavaWeb-02</artifactId>
    <groupId>com.alex</groupId>
    <version>1.0-SNAPSHOT</version>
  </parent>
```











## 3. 按照规定创建目录

- main目录中创建一个Java源目录，一个Resources资源目录

![Xnip2021-10-25_15-39-30](Servlet/Xnip2021-10-25_15-39-30.jpg)





## 4. 优化Maven项目

1. 修改web.xml为最新内容
2. 构建完整的Maven结构

![Xnip2021-10-25_16-09-58](Servlet/Xnip2021-10-25_16-09-58.jpg)





## 5. 编写一个Servlet

1. 首先编写一个普通类
2. 继承HttpServlet类
3. 实现抽象类



继承关系:

![Xnip2021-10-25_16-19-34](Servlet/Xnip2021-10-25_16-19-34.jpg)

![Xnip2021-10-25_16-21-37](Servlet/Xnip2021-10-25_16-21-37.jpg)



![Xnip2021-10-25_16-21-53](Servlet/Xnip2021-10-25_16-21-53.jpg)



实现抽象类:

```java
@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("do get method accessed");
        PrintWriter writer = resp.getWriter();
        writer.println("Hello Servlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
```



















## 6. 在web.xml进行映射

- 在web.xml中写上servlet的信息和映射路径

![Xnip2021-10-25_16-33-07](Servlet/Xnip2021-10-25_16-33-07.jpg)













## 7. 配置Tomcat

![Xnip2021-10-25_16-35-30](Servlet/Xnip2021-10-25_16-35-30.jpg)



![Xnip2021-10-25_16-35-57](Servlet/Xnip2021-10-25_16-35-57.jpg)



配置项目发布路径:

![Xnip2021-10-25_16-37-34](Servlet/Xnip2021-10-25_16-37-34.jpg)



![Xnip2021-10-25_16-38-23](Servlet/Xnip2021-10-25_16-38-23.jpg)













# 三、Servlet原理

![Xnip2021-11-04_14-19-49](Servlet/Xnip2021-11-04_14-19-49.jpg)

1. 客户端(浏览器)发送请求给服务器并传到web容器中(Tomcat)
2. 如果是首次访问，则需要编译Servlet对应的.java文件，生成对应的.class文件
3. Servlet中的service方法接收请求，我们可以重新这些方法并处理请求，之后再将响应交给Servlet
4. 最后原路返回

















# 四、Map映射

- 一个Servlet文件可以映射到一个路径上

![Xnip2021-11-04_14-36-55](Servlet/Xnip2021-11-04_14-36-55.jpg)



- 一个Servlet文件可以映射到多个路径上

![Xnip2021-11-04_14-38-47](Servlet/Xnip2021-11-04_14-38-47.jpg)



- 一个Servlet文件可以指定通用映射路径

![Xnip2021-11-04_14-43-00](Servlet/Xnip2021-11-04_14-43-00.jpg)



- 默认请求路径(其会直接取代index.jsp)

![Xnip2021-11-04_14-47-31](Servlet/Xnip2021-11-04_14-47-31.jpg)





- 一个Servlet文件可以指定后缀或前缀(*前面不能加具体的项目映射路径)

![Xnip2021-11-04_14-51-21](Servlet/Xnip2021-11-04_14-51-21.jpg)



![Xnip2021-11-04_14-51-52](Servlet/Xnip2021-11-04_14-51-52.jpg)

**注意：**如果有具体的路径映射，则先行访问，优先级为: 

具体路径映射 > 通配符默认访问路径 > index.jsp





写一个自定义的404:

![Xnip2021-11-04_14-58-35](Servlet/Xnip2021-11-04_14-58-35.jpg)



写好路径映射:

![Xnip2021-11-04_15-01-07](Servlet/Xnip2021-11-04_15-01-07.jpg)



![Xnip2021-11-04_15-01-46](Servlet/Xnip2021-11-04_15-01-46.jpg)

































