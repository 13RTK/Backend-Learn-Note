# 一、概念



## Java Web

- Java Web: 通过java编写的可以通过浏览器访问的程序的总称
- Java Web是基于请求和响应来开发的







## 请求

- 请求是指client发送给server数据(request)







## 响应

- server向client回传数据

![Xnip2021-12-03_22-40-32](Java Web.asset/Xnip2021-12-03_22-40-32.jpg)

<hr>

















# 二、Web资源的分类

按照技术和呈现的效果不同，分为静态资源和动态资源



静态:

html/css/js/txt/mp4/jpg...



动态:

jsp/servlet程序

<hr>

















# 三、常用的Web服务器

web容器就是web服务器



- Tomcat(最常用): 免费，由Apache提供，一种轻量级的web容器
- Jboss: 纯Java的EJB服务器，开源免费
- GlassFish: 应用很少，由Oracle开发
- Resin: 由CAUCHO开发，收费，由Java开发，对servlet/jsp提供了良好的支持，性能优良
- WebLogic: Oracle的产品，收费web服务器中应用最广泛的，适合大公司

<hr>















# 四、Tomcat和Servlet/jsp的版本对应关系

![Xnip2021-12-04_21-22-29](Java Web.asset/Xnip2021-12-04_21-22-29.jpg)

<hr>















# 五、Tomcat使用



## 1. 目录

- bin: 存放可执行程序
- conf: 存放配置文件
- lib: 存放依赖jar包
- logs: 存放运行时输出的日志文件
- temp: 存放运行时产生的临时数据
- webapps: 存放部署的项目/工程
- work: 存放运行时jsp翻译为servlet的源码，和session序列化(钝化)的目录



![Xnip2021-12-04_21-28-47](Java Web.asset/Xnip2021-12-04_21-28-47.jpg)























## 2. 启动Tomcat

- 打开bin目录中的startup.sh(mac)/startup.bat(win)文件即可



测试:

- 

在浏览器地址栏中输入

```
http://localhost:8080
http://127.0.0.1:8080
```



- 使用catalina run命令可以在其中失败时显示错误信息

















## 3. 关闭Tomcat

- 运行shutdown.sh即可















## 4. 修改默认端口

- Tomcat默认端口为:8080



可通过conf文件夹中的server.xml文件修改

![Xnip2021-12-04_21-40-34](Java Web.asset/Xnip2021-12-04_21-40-34.jpg)

<hr>

















## 5. 部署web工程





### 1) 第一种方式

- 将工程目录复制到Tomcat的webapps目录下

![Xnip2021-12-05_21-22-49](Java Web.asset/Xnip2021-12-05_21-22-49.jpg)

- 输入localhost:8080后默认会进入ROOT目录中

![Xnip2021-12-05_21-25-11](Java Web.asset/Xnip2021-12-05_21-25-11.jpg)



- 想要访问我们自己的项目的话，直接在后面写上文件路径即可

Eg:

![Xnip2021-12-05_21-26-51](Java Web.asset/Xnip2021-12-05_21-26-51.jpg)







### 2) 第二种方式

- 在Tomcat的conf/Catalina/localhost目录下，创建一个xml文件即可，对应的语法如下

```xml
<Context path="/map_path" docBase="file_path"/>
```





- 其中path属性为浏览器中输入的地址
- docBase为文件的真实地址

Eg:

























#### 1) 双击打开和通过Tomcat的不同点

- 直接双击打开时使用的协议为file，会直接读取协议名后面的文件路径，会直接访问磁盘文件，而不会经过网络

![Xnip2021-12-05_22-10-47](Java Web.asset/Xnip2021-12-05_22-10-47.jpg)



- 而通过Tomcat需要浏览器向服务器传递ip/工程路径/文件路径








#### 2) 默认访问

- 如果没有指定工程名，那么默认访问ROOT目录下的index.jsp文件
- 如果指定了工程名，但没有指定文件路径，那么默认访问其下的index.html文件

<hr>



















## 6. IDEA中使用Tomcat

- 配置好对应路径即可

![Xnip2021-12-05_22-19-54](Java Web.asset/Xnip2021-12-05_22-19-54.jpg)











### 1. 动态web工程目录介绍

![Xnip2021-12-05_22-24-11](Java Web.asset/Xnip2021-12-05_22-24-11.jpg)

- webapp:

存放web工程的资源文件(html, css, js等等)

- src:

存放源代码

- WEB-INF

是一个受保护的目录，浏览器无法直接访问

- lib

存放第三方jar包(maven项目中没有)

- web.xml

整个web工程的配置文件，可以配置很多组件(Servlet, Filter, Listener, Session等等)

















### 2. 在IDEA中修改Tomcat配置

Eg:

![Xnip2021-12-05_22-38-07](Java Web.asset/Xnip2021-12-05_22-38-07.jpg)





![Xnip2021-12-05_22-40-43](Java Web.asset/Xnip2021-12-05_22-40-43.jpg)

<hr>



















# 六、Servlet

- Servlet是Java EE规范之一。规范就是接口
- Servlet是Java Web三大组件之一。三大组件: Servlet程序、Filter过滤器、Listener监听器
- Servlet是运行在服务器上的一个Java程序，它可以接受客户端发送的请求，并响应数据给client







## 6.1 第一个servlet程序

- 首先在Tomcat中给定一个项目的工程路径

Eg:

![Xnip2021-12-06_22-05-17](Java Web.asset/Xnip2021-12-06_22-05-17.jpg)







- 创建一个Servlet程序实现Servlet接口
- 实现service方法，处理请求并响应

Eg:

![Xnip2021-12-06_22-08-57](Java Web.asset/Xnip2021-12-06_22-08-57.jpg)





- 在web.xml中配置Servlet程序的访问地址

Eg:

![Xnip2021-12-06_22-14-55](Java Web.asset/Xnip2021-12-06_22-14-55.jpg)



![Xnip2021-12-06_22-33-45](Java Web.asset/Xnip2021-12-06_22-33-45.jpg)









## 6.2 url到Servlet的过程

![Xnip2021-12-06_22-37-34](Java Web.asset/Xnip2021-12-06_22-37-34.jpg)



通过ip找到server，通过端口号定位到对应的应用，通过工程路径定位到Tomcat中的工程

通过资源路径在工程对应的web.xml配置文件中找到工程下对应的Servlet程序









## 6.3 Servlet的生命周期

调用方法的顺序:

1. 执行Servlet构造方法
2. 执行init初始化方法

这两步只在第一次访问时，在创建Servlet程序的时候才会使用



3. 执行Service方法

每次访问都会执行

4. 执行destroy方法销毁

只有停止工程时才会执行(关闭Tomcat)

Eg:

![Xnip2021-12-06_22-47-29](Java Web.asset/Xnip2021-12-06_22-47-29.jpg)















## 6.4 请求分发

- 通过Service方法提供的ServletRequest对象
- 将其转换为HttpServletRequest对象
- 调用getMethod方法获取请求的方法
- 最后根据返回的请求方法进行请求分发处理

Eg:

![Xnip2021-12-06_22-58-27](Java Web.asset/Xnip2021-12-06_22-58-27.jpg)



![Xnip2021-12-06_23-03-06](Java Web.asset/Xnip2021-12-06_23-03-06.jpg)

最好将两种请求的处理封装为两个独立的方法

















## 6.5 通过继承HttpServlet实现Servlet程序

- 编写一个类继承HttpServlet类
- 根据业务需要重写doGet和doPost方法
- 到web.xml中配置访问地址

![Xnip2021-12-06_23-07-05](Java Web.asset/Xnip2021-12-06_23-07-05.jpg)











## 6.6 Servlet类的继承体系

![Xnip2021-12-06_23-11-11](Java Web.asset/Xnip2021-12-06_23-11-11.jpg)























# 七、ServletConfig类

- 可以获取Servlet程序的别名: Servlet-name的值
- 获取初始化参数init-param

- 获取ServletContext对象

![Xnip2021-12-06_23-19-17](Java Web.asset/Xnip2021-12-06_23-19-17.jpg)



