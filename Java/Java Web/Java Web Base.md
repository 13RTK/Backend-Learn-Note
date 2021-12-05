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





























































