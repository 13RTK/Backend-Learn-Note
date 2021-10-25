# 一、定义

- 是sun公司发明的用来开发动态web的技术，其提供了一个Servlet接口
- 开发servlet的步骤:
  - 编写一个实现Servlet接口的类
  - 将该类部署到web服务器中

- 实现了Servlet接口的Java程序称为Servlet

****













# 二、Hello Servlet





1. 构建一个普通的Maven项目，删除其中的src目录:

![Xnip2021-10-25_14-37-14](Servlet/Xnip2021-10-25_14-37-14.jpg)



![Xnip2021-10-25_14-40-02](Servlet/Xnip2021-10-25_14-40-02.jpg)



尽可能在主项目POM中导入所有的依赖:

![Xnip2021-10-25_14-44-30](Servlet/Xnip2021-10-25_14-44-30.jpg)



![Xnip2021-10-25_14-46-12](Servlet/Xnip2021-10-25_14-46-12.jpg)









2. 创建一个子module

- 其中属于子module的pom.xml文件中会继承父项目的依赖，这样就不需要重复导入Maven配置了

![Xnip2021-10-25_15-08-35](Servlet/Xnip2021-10-25_15-08-35.jpg)



![Xnip2021-10-25_15-13-21](Servlet/Xnip2021-10-25_15-13-21.jpg)



























