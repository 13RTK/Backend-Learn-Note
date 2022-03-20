# 一、理论

业务中，我们通常参照一下流程来实现:

![点击查看源网页](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fs4.51cto.com%2Fwyfs02%2FM00%2F8B%2FBA%2FwKioL1hXU8vRX8elAAA2bXqAxMs799.png&refer=http%3A%2F%2Fs4.51cto.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641788288&t=1658084627505c12812596d8ca1b9885)



而在之前使用Servlet实现表示层的过程中，几个小功能通常需要我们编写好几个Servlet程序才能实现

SpringMVC就是为了解决这个问题而诞生的表示层框架(在SSH时期是Struts)，MVC的释义:

- M: model，即对应的数据模型(实体类)
- V: view，即前端的页面
- C: controller，即控制器(代替了Servlet来处理各种请求)

![点击查看源网页](https://pics5.baidu.com/feed/d0c8a786c9177f3e5707320277aeb1c39f3d5677.jpeg?token=e4c298063f4efa11fd0f94c2760c252b&s=782834721BC044435C55F4CA0000E0B1)



SpringMVC将这三种进行了解耦，最后将View和Model结合起来进行渲染(即Thymeleaf)

<hr>









# 二、配置

- 目前SpringMVC不支持Tomcat 10，所以需要使用Tomcat9



## 1. 依赖

我们需要引入名为spring-webmvc的依赖，其包括了之前Spring的一些依赖

Eg:

![Xnip2022-03-18_21-05-13](SpringMVC.assets/Xnip2022-03-18_21-05-13.jpg)

<hr>











## 2. 配置文件

- 这里我们就不再需要web.xml了，只需要一个AbstractAnnotationConfigDispatcherServletInitializer的实现子类即可
- 其中需要重写三个方法:
    - 其中getRootConfigClasses方法是用来获取Spring基础配置类的(Service层)
    - getServletConfigClasses方法是用来获取与web相关的配置的(Controller，DispatcherServlet等)



为了取代web.xml，我们需要让Tomcat使用DispatcherServlet:

![img](https://img2018.cnblogs.com/blog/738818/201906/738818-20190617214214614-761905677.png)



- 为了能够成功地重写这三个方法，我们需要创建两个配置类

Eg:

![Xnip2022-03-18_21-10-32](SpringMVC.assets/Xnip2022-03-18_21-10-32.jpg)





记得添加"/"映射路径

![Xnip2022-03-18_22-45-36](SpringMVC.assets/Xnip2022-03-18_22-45-36.jpg)

<hr>







# 三、配置Controller和ViewResolver





## 1. 逻辑

- 之前我们配置了DispatcherServlet，现在所有的请求都会经由它同一分配

Eg:

![点击查看源网页](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg2018.cnblogs.com%2Fblog%2F1190675%2F201812%2F1190675-20181203121258033-524477408.png&refer=http%3A%2F%2Fimg2018.cnblogs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642058685&t=13fd16796a8b5ed58762c9947f15681f)



- DispatcherServlet会根据对应的路径选择合适的Controller处理，处理完成后会返回一个ModelAndView对象(即前端页面)
- 之后视图解析器会将该对象处理为一个View并交由DispatcherServlet返回给Client
- 我们其实只需要写Controller和ViewResolver即可

<hr>











## 2. Controller/ViewResolver

- 我们这里选择Thymeleaf作为视图解析器，依赖如下

```xml
<dependency>
  <groupId>org.thymeleaf</groupId>
  <artifactId>thymeleaf-spring5</artifactId>
  <version>3.0.12.RELEASE</version>
</dependency>
```



- 视图解析器在Servlet层，所以我们需要写在WebConfig类中
- 同样我们需要使用ComponentScan注册Controller
- 再使用@EnableMVC标记为Web

Eg:

![Xnip2022-03-18_22-46-38](SpringMVC.assets/Xnip2022-03-18_22-46-38.jpg)





- 在Controller中，在方法上使用@RequestMapping设置对应的路径
- 方法中直接返回一个ModelAndView对象即可，参数即为我们构建的View的文件名(在WebConfig中会将对应的路径和后缀拼接完整)

Eg:

![Xnip2022-03-18_22-50-03](SpringMVC.assets/Xnip2022-03-18_22-50-03.jpg)





- 同样，我们可以往model中传入对应的数据
- 之后结合Thymeleaf就能渲染出完整页面了

Eg:

![Xnip2022-03-18_22-54-03](SpringMVC.assets/Xnip2022-03-18_22-54-03.jpg)

<hr>







## 3. 解析静态资源

- 我们需要让Web配置类实现WebMvcConfigurer接口，并添加@EnableWEbMvc注解

Eg:

![Xnip2022-03-18_23-06-28](SpringMVC.assets/Xnip2022-03-18_23-06-28.jpg)



![Xnip2022-03-18_23-06-40](SpringMVC.assets/Xnip2022-03-18_23-06-40.jpg)

- 这里addResourceHandlers方法自动添加了对应的路径前缀

Eg:

![Xnip2022-03-18_23-08-20](SpringMVC.assets/Xnip2022-03-18_23-08-20.jpg)

<hr>











# 四、RequestMapping注解参数

其中的参数:

![Xnip2022-03-19_14-58-38](SpringMVC.assets/Xnip2022-03-19_14-58-38.jpg)





## 1. value/path

- 其中name和path是相同的，都表示该方法对应的访问
- 该属性为数组类型，所以可以让多种路径都经过它处理，同时还可以使用通配符
    - ?: 代表任意一个字符
    - *: 代表任意多个字符
    - **: 代表当前目录下的任意目录



Eg:

![Xnip2022-03-19_15-10-45](SpringMVC.assets/Xnip2022-03-19_15-10-45.jpg)





- 该注解放在类上的话，就会为所有的方法加上前缀

Eg:![Xnip2022-03-19_15-15-30](SpringMVC.assets/Xnip2022-03-19_15-15-30.jpg)

<hr>









## 2. method

- 该属性可以用来限制请求的类型

Eg:

![Xnip2022-03-19_15-17-50](SpringMVC.assets/Xnip2022-03-19_15-17-50.jpg)





这些请求方法都有对应的衍生注解:
Eg:

![Xnip2022-03-19_15-20-27](SpringMVC.assets/Xnip2022-03-19_15-20-27.jpg)

<hr>









## 3. params

- 通过该属性可以限定请求中所携带属性的类型，同时可以使用!取反或者关系运算符

Eg:

![Xnip2022-03-19_15-24-27](SpringMVC.assets/Xnip2022-03-19_15-24-27.jpg)

<hr>













## 4. header属性

- 其用法和params差不多，但限定的是请求头中的内容

Eg:

![Xnip2022-03-19_15-28-22](SpringMVC.assets/Xnip2022-03-19_15-28-22.jpg)

- 该示例中，我们拦截了一切带有connection请求头的request

<hr>









# 五、@RequestParam和@RequestHeader



## 1. @RequestParam

- 该注解写在Controller方法的参数列表中，可以用来直接获取对应的参数

![Xnip2022-03-19_15-43-29](SpringMVC.assets/Xnip2022-03-19_15-43-29.jpg)



其他参数:

![Xnip2022-03-19_15-44-09](SpringMVC.assets/Xnip2022-03-19_15-44-09.jpg)



- 其中value和name是可以互换使用的
- 在没加上required属性的情况下，默认为true(必须要有才能访问)
- 我们也可以使用defaultValue属性，手动为其添加对应的默认值

Eg:

![Xnip2022-03-19_15-46-48](SpringMVC.assets/Xnip2022-03-19_15-46-48.jpg)







- 如果请求的参数和方法中的参数同名的话，其实可以直接获取
- 更有甚者，如果请求参数中的内容和方法中的实体类对应的话，**可以直接包装为一个实体类**
- 该实体类甚至不需要注册为bean

Eg:

![Xnip2022-03-19_15-51-25](SpringMVC.assets/Xnip2022-03-19_15-51-25.jpg)

<hr>













## 2. @RequestHeader

Eg:

![Xnip2022-03-19_15-56-01](SpringMVC.assets/Xnip2022-03-19_15-56-01.jpg)



![Xnip2022-03-19_15-55-44](SpringMVC.assets/Xnip2022-03-19_15-55-44.jpg)

<hr>









# 六、@CookieValue和@SessionAttribute

这两个注解所拥有的属性和前面的@RequestMapping和@RequestHeader差不多





## 1. @CookieValue

- 通过在控制器对应的方法参数中添加该注解即可获取对应的Cookie值

Eg:

![Xnip2022-03-19_16-15-30](SpringMVC.assets/Xnip2022-03-19_16-15-30.jpg)



- 注意，这里我们使用了HttpServletResponse对象实例来存放对应的Cookie

<hr>









## 2. @SessionAttribute

- 注意我们需要在参数列表中使用HttpSession来自动获取请求中的Session
- 设置Session的方法为"setAttribute"

Eg:

![Xnip2022-03-19_16-21-56](SpringMVC.assets/Xnip2022-03-19_16-21-56.jpg)

<hr>









# 七、请求转发和重定向

记得要将需要请求转发/重定向的方法对应的返回值改为String



- 请求转发只需要在需要在需要转发的方法return中写上: "forward:forwardPath"即可

Eg:

![Xnip2022-03-19_16-32-05](SpringMVC.assets/Xnip2022-03-19_16-32-05.jpg)





- 重定向则只需要写上: "redirect:path"即可

Eg:

![Xnip2022-03-19_16-33-49](SpringMVC.assets/Xnip2022-03-19_16-33-49.jpg)

<hr>











# 八、BeandeWeb作用域/生命周期

在Spring中，Bean的作用域包括了singleton和prototype，但在SpringMVC中会进一步细分:

- request: 每次http请求都会生成一个新的实例，请求结束后bean也会消失
- session: 对于每个会话来说都会生成一个新的实例(关闭浏览器就是关闭一个回话)



Eg:

@RequestScope注解下，每次请求都会创建新的实例

![Xnip2022-03-19_18-09-52](SpringMVC.assets/Xnip2022-03-19_18-09-52.jpg)





@SessionScope注解下，每个会话都会有一个新的实例

Eg:

![Xnip2022-03-19_18-11-52](SpringMVC.assets/Xnip2022-03-19_18-11-52.jpg)







- 默认情况下会使用单例模式，所以不管是否为同一个请求或者同一个回话，其都只会返回一个实例对象







