# 一、获取/安装

- Official site:

![Xnip2021-10-10_20-36-21](Tomcat Base/Xnip2021-10-10_20-36-21.jpg)



- Download Options:

![Xnip2021-10-10_20-38-30](Tomcat Base/Xnip2021-10-10_20-38-30.jpg)















# 二、配置/启动



- 文件结构

![Xnip2021-10-10_21-02-18](Tomcat Base/Xnip2021-10-10_21-02-18.jpg)









## 1. 配置



### 1) /bin目录下的应用

![Xnip2021-10-10_21-05-57](Tomcat Base/Xnip2021-10-10_21-05-57.jpg)



- Unix下启动程序

```shel
./program_name
```



Eg:

![Xnip2021-10-10_21-06-54](Tomcat Base/Xnip2021-10-10_21-06-54.jpg)







### 2) /conf目录下的应用

![Xnip2021-10-10_21-08-49](Tomcat Base/Xnip2021-10-10_21-08-49.jpg)



- 通过server.xml查看/修改配置

![Xnip2021-10-10_21-10-54](Tomcat Base/Xnip2021-10-10_21-10-54.jpg)



![Xnip2021-10-10_21-12-00](Tomcat Base/Xnip2021-10-10_21-12-00.jpg)







### 3) /lib目录下的应用

- 一些依赖jar包

![Xnip2021-10-10_21-13-19](Tomcat Base/Xnip2021-10-10_21-13-19.jpg)



















### 4) /webapps目录下的应用

- 其中每一个文件夹代表一个应用
- 当通过默认域名访问Tomcat时，默认访问ROOT目录下的index.jsp文件

![Xnip2021-10-10_21-16-07](Tomcat Base/Xnip2021-10-10_21-16-07.jpg)









## 2. 启动

- Unix下使用./直接启动即可
- 启动后，通过**conf目录的server.xml配置文件**中**指定的host和端口号**即可访问

Eg:

![Xnip2021-10-10_21-18-19](Tomcat Base/Xnip2021-10-10_21-18-19.jpg)

- 每次修改server.xml文件后都需要重启服务器



DNS解析顺序:

本地 -> DNS服务器



**拓展**

- 注意如果我们在server.xml中修改了默认的主机名，**则需要将该域名写入系统的host文件中才行，不然无法解析！**



Eg:

![Xnip2021-10-10_21-23-59](Tomcat Base/Xnip2021-10-10_21-23-59.jpg)



- Mac OS上host文件的路径: /etc/hosts

![Xnip2021-10-10_21-26-16](Tomcat Base/Xnip2021-10-10_21-26-16.jpg)





- 正常访问

![Xnip2021-10-10_21-27-51](Tomcat Base/Xnip2021-10-10_21-27-51.jpg)







## 3. 发布自己的网站



- Tomcat网站的默认存放位置: webapps(可通过conf/server.xml文件修改)。默认会访问ROOT文件夹中的index.jsp
- 我们通过在webapps目录下创建一个文件夹，并创建对应的index.jsp或者index.html即可通过路径直接访问

```mysql
-- webapps
	- ROOT
		- WEB-INF
			- web.xml
		- index.jsp
	- JavaWeb_01_war
		- WEB-INF
			- web.xml
		- index.html
```



![Xnip2021-10-11_20-43-11](Tomcat Base/Xnip2021-10-11_20-43-11.jpg)



![Xnip2021-10-11_20-44-11](Tomcat Base/Xnip2021-10-11_20-44-11.jpg)





