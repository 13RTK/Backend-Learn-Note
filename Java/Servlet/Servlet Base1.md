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

****























# 五、ServletContext

- 在Web容器初始化的时候，会为每个Serlvet程序都创建一个ServletContext对象，用其来控制整个Servlet项目
- 以前程序之间交互的方式: 共同读写一个文件，即磁盘I/O:

![Xnip2021-11-06_16-26-33](Servlet/Xnip2021-11-06_16-26-33.jpg)



- 而有了ServletContext之后:

![Xnip2021-11-06_16-28-47](Servlet/Xnip2021-11-06_16-28-47.jpg)





## 1. 共享数据

- 通过一个程序写入数据到ServletContext
- 通过另一个程序显示之前写入的数据



流程图:

![Xnip2021-11-08_09-05-38](Servlet/Xnip2021-11-08_09-05-38.jpg)



对应类的关系:

![Xnip2021-11-08_09-15-01](Servlet/Xnip2021-11-08_09-15-01.jpg)



步骤一:

- 获取ServletContext对象，传入属性

![Xnip2021-11-08_09-22-55](Servlet/Xnip2021-11-08_09-22-55.jpg)





步骤二:

- 通过ServletContext对象，通过getAttribute方法获取对应的属性
- 将属性打印出来

![Xnip2021-11-08_09-24-15](Servlet/Xnip2021-11-08_09-24-15.jpg)





步骤三:

- 在web.xml中注册对应的虚拟路径映射

![Xnip2021-11-08_09-25-55](Servlet/Xnip2021-11-08_09-25-55.jpg)







步骤四:

- 在Tomcat中部署对应的项目
- 注意要将不必要的项目步骤删除，不然每次启动都会重新构建所有的项目

![Xnip2021-11-08_09-26-51](Servlet/Xnip2021-11-08_09-26-51.jpg)



步骤五:

- 先进入Servlet01，该程序传入一个属性到ServletContext对象
- 再进入Servlet02，该程序从ServletContext中获取对应的属性并打印

![Xnip2021-11-08_09-28-54](Servlet/Xnip2021-11-08_09-28-54.jpg)

















## 2. 获取初始化参数

- 通过ServletContext对象可以设置/获取到该web应用的初始化参数
- 设置/获取的参数都来自于web应用的配置(web.xml)

![Xnip2021-11-08_21-04-08](Servlet/Xnip2021-11-08_21-04-08.jpg)



- 通过ServletContext对象使用getInitParameter方法，获取参数对应的配置名的值


![Xnip2021-11-08_21-06-36](Servlet/Xnip2021-11-08_21-06-36.jpg)











## 3. 请求转发

- 通过ServletContext对象调用getRequestDispatcher()方法，传入转发到的虚拟路径，返回值为一个RequestDispatcher对象
- 之后通过该RequestDispatcher对象调用forward()方法进行转发即可

![Xnip2021-11-08_21-42-50](Servlet/Xnip2021-11-08_21-42-50.jpg)

- 记得在web.xml中注册虚拟路径
- 转发时路径不会改变，状态码依然为200

![Xnip2021-11-08_21-49-01](Servlet/Xnip2021-11-08_21-49-01.jpg)





与重定向的区别:

![Xnip2021-11-08_21-56-55](Servlet/Xnip2021-11-08_21-56-55.jpg)











## 4. 读取资源文件

- 平时读取时:

创建一个Properties对象，根据绝对路径创建一个配置文件的输入流，用Properties的load方法加载这个流

最后通过getProperties获取键对应的值



- 通过ServletContext:

通过项目运行后的相对路径加载配置文件为一个输入流，将该输入流用Properties对象的load方法加载

之后通过Properties的getProperties方法获取键对应的值



流程:

### 1) 获取相对路径

- 创建一个配置文件(一般在resources目录下)，**运行后Servlet程序和配置文件都在/WEB-INF/classes路径下**

![Xnip2021-11-22_20-49-41](Servlet/Xnip2021-11-22_20-49-41.jpg)

- 所以我们获取该配置文件的路径应该为:

```java
InputStream resourceGetter = servletContext.getResourceAsStream("/WEB-INF/classes/db.properties");
```





- 其中classes被称为类路径(classpath)，最前面的"/"代表当前项目(图中的servlet-02)















### 2) 加载文件流/读取文件

![Xnip2021-11-22_20-52-27](Servlet/Xnip2021-11-22_20-52-27.jpg)





### 3) 注册Servlet程序名称/设置虚拟路径映射

```xml
<servlet>
    <servlet-name>getResource</servlet-name>
  	<servlet-class>com.alex.ContextGetSource</servlet-class>
</servlet>
<servlet-mapping>
		<servlet-name>getResource</servlet-name>
		<url-pattern>/getResource</url-pattern>
</servlet-mapping>
```

****























# 六、











































