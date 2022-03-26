# 一、初入

好处:

- 比起之前SSM阶段，SpringBoot可以直接省去编写配置类的步骤，通过started整合相关的框架、依赖和工具(Tomcat、Mybatis)





- 创建一个SpringBoot项目

![Xnip2022-03-25_11-10-12](SpringBoot.assets/Xnip2022-03-25_11-10-12.jpg)



![Xnip2022-03-25_11-10-50](SpringBoot.assets/Xnip2022-03-25_11-10-50.jpg)

- 该面板中可以选择项目使用的依赖和其他的框架





SpingBoot为我们设置好了基本的配置，不需要配置类就能实现实体类的自动注入

![Xnip2022-03-25_12-26-55](SpringBoot.assets/Xnip2022-03-25_12-26-55.jpg)

<hr>









# 二、项目文件目录

- Application

SpringBootApplication注解中实现了自动扫描，所以不需要我们自己编写配置类

![Xnip2022-03-25_12-38-25](SpringBoot.assets/Xnip2022-03-25_12-38-25.jpg)





- maven配置

![Xnip2022-03-25_12-40-13](SpringBoot.assets/Xnip2022-03-25_12-40-13.jpg)

第一个依赖是SpringBoot的核心依赖，第二个依赖是SpringBoot的测试依赖

其中的插件是用于打包的





- properties

![Xnip2022-03-25_12-41-48](SpringBoot.assets/Xnip2022-03-25_12-41-48.jpg)

该文件中编写的是SpringBoot的配置



- gitignore

![Xnip2022-03-25_12-42-56](SpringBoot.assets/Xnip2022-03-25_12-42-56.jpg)

其中展示的是git忽略的目录







