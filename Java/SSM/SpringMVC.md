# 一、理论

业务中，我们通常参照以下流程来实现:

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

<hr>













# 九、RestFul风格

- 其作用是利用http协议的特性，将参数通过URL拼接传给服务器



Eg:

我们的请求为:

`http://localhost:8080/mvc/index/abc`



- 只需要在Controller对应的方法中，在RequestMapping注解的路径后添加一个{"路径变量名"}
- 在方法的参数中添加一个使用了@PathVariable注解的形参即可(注解中的属性为其对应的路径变量名)

Eg:

![Xnip2022-03-20_14-47-23](SpringMVC.assets/Xnip2022-03-20_14-47-23.jpg)

<hr>













## 1. 不同的请求方法

按照功能，我们可以对URL划分:

- POST : .../mvc/index 添加用户信息，携带表单
- GET: .../mvc/index/{id} 获取用户信息，id直接放在URL里
- PUT: .../mvc/index 修改用户信息，携带表单
- DELETE: .../mvc/index/{idx} 删除用户信息，id放在URL里



对应的请求映射方法:

![Xnip2022-03-20_15-05-21](SpringMVC.assets/Xnip2022-03-20_15-05-21.jpg)

<hr>













# 十、拦截器

拦截器的作用与Filter类似，但Filter作用于Servlet之前

而拦截器会在Servlet与RequestMapping之间，即DispatcherServlet在将请求交给对应的Controller之前先交给拦截器

- 拦截器只会拦截所有Controller中定义的请求映射路径，不会拦截静态资源

Eg:

![点击查看源网页](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fupload-images.jianshu.io%2Fupload_images%2F4685968-ca4e9021f653c954.png%3FimageMogr2%2Fauto-orient%2Fstrip%257CimageView2%2F2%2Fw%2F1240&refer=http%3A%2F%2Fupload-images.jianshu.io&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642340637&t=70d3dd6b52ae01ac76c04d99e6bd95ed)

<hr>









## 1. 创建拦截器

- 我们需要创建一个类，其需要实现HandlerInterceptor接口(该接口中都是默认方法)

Eg:

```java
public class MainInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("我是处理之前");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("我是处理之后");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("我是完成之后");
    }
}
```







创建之后，我们需要在Web配置类中重写**addInterceptor**方法，使用其提供的参数"registy"

调用**addInterceptor**方法传入一个对应的拦截器实例对象

调用**addPatterns**方法设置其拦截的路径规则

调用**excludePathPatterns**方法设置不进行连接的路径



Eg:

![Xnip2022-03-20_15-49-31](SpringMVC.assets/Xnip2022-03-20_15-49-31.jpg)





- 在整个流程结束后才会调用afterCompletion方法
- 如果在preHandle方法中直接返回false，则会直接终止流程

Eg:

![Xnip2022-03-20_15-53-10](SpringMVC.assets/Xnip2022-03-20_15-53-10.jpg)





- 如果在放行后，controller对应方法处理中抛出了异常的话，则不会执行postHandle方法
- 但之后还是会执行afterCompletion方法

<hr>







## 2. 多级拦截器

- 创建一个SubInterceptor类，同样在Web配置类中调用addInterceptor方法进行注册

Eg:

![Xnip2022-03-20_15-59-06](SpringMVC.assets/Xnip2022-03-20_15-59-06.jpg)



![Xnip2022-03-20_15-59-14](SpringMVC.assets/Xnip2022-03-20_15-59-14.jpg)

- 拦截器拦截的顺序就是其在web配置类中注册的顺序

Eg:

![Xnip2022-03-20_16-01-47](SpringMVC.assets/Xnip2022-03-20_16-01-47.jpg)



如果前面的拦截器在preHandle中返回了false，那么和单个拦截器一样便不再继续了

<hr>









# 十一、自定义异常处理

如果Controller的方法中出现了异常，那么其会默认出现一个页面

Eg:

![Xnip2022-03-20_16-15-19](SpringMVC.assets/Xnip2022-03-20_16-15-19.jpg)



如果不想在出现异常的时候返回默认页面，而是我们自己的页面呢？



- 首先创建一个Controller类用来处理异常，**该类需要使用@ControllerAdvice注解**
- **其中的方法使用@ExceptionHandler注解**，其中的**属性代表该方法处理的异常种类**
- 在方法中可以将异常对象放入参数列表中

Eg:

![Xnip2022-03-20_16-21-08](SpringMVC.assets/Xnip2022-03-20_16-21-08.jpg)

<hr>













# 十二、文件上传下载



## 1. 文件上传

- 在SpringMVC中，我们需要在Web配置中注册一个Resolver的bean





在配置前我们需要引入依赖，该依赖还包含了commons.io

Eg:

```xml
<dependency>
  <groupId>commons-fileupload</groupId>
  <artifactId>commons-fileupload</artifactId>
  <version>1.4</version>
</dependency>
```



配置一个Resolver:

```java
@Bean("multipartResolver")
public CommonsMultipartResolver commonsMultipartResolver() {
  CommonsMultipartResolver resolver = new CommonsMultipartResolver();
  resolver.setMaxUploadSize(1024 * 1024 * 10);
  resolver.setDefaultEncoding("UTF-8");

  return resolver;
}
```









- 之后我们只需要编写对应的Controller方法即可

Eg:

```java
@RequestMapping(value = "/upload", method = RequestMethod.POST)
@ResponseBody
public String upload(@RequestParam CommonsMultipartFile file) throws IOException {
  File fileObj = new File("/Users/alex/Desktop/test.html");
  file.transferTo(fileObj);
  System.out.println("用户上传的文件已保存到：" + fileObj.getAbsolutePath());
  return "文件上传成功！";
}
```





- 在前端页面中设置一个上传表单:

Eg:

![Xnip2022-03-21_13-38-04](SpringMVC.assets/Xnip2022-03-21_13-38-04.jpg)

<hr>











## 2. 文件下载

- 对于下载，我们只需要使用HttpServletResponse实例，设置响应的内容类型
- 通过该响应获取一个输出流，为传输给用户的文件建立一个输入流
- 最后通过commons.io中的copy方法将输入流复制给输出流即可

Eg:

```java
@RequestMapping(value = "/download", method = RequestMethod.GET)
@ResponseBody
public void download(HttpServletResponse response) {
  response.setContentType("multipart/form-data");

  try (FileInputStream fileInputStream = new FileInputStream("/Users/alex/Desktop/test.html")) {
    ServletOutputStream fileOutputStream = response.getOutputStream();
    IOUtils.copy(fileInputStream, fileOutputStream);
  } catch (IOException e) {
    e.printStackTrace();
  }
}
```















