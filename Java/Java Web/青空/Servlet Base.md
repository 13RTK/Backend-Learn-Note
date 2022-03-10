# 一、创建一个Servlet

- Servle是JavaEE的一个标准，编写后可交由web服务器运行(Tomcat)



创建一个类，实现Servlet接口即可，再使用WebServlet注册其访问路径(注册的方式仅限新版)

```java
@WebServlet("/test")
public class TestServlet implements Servlet {
  @Override
  ...
}
```



Eg:

![Xnip2022-03-10_10-09-53](Servlet.assets/Xnip2022-03-10_10-09-53.jpg)





## 1. 注册Servlet的两种方式

- 使用注解是最简单的一种
- 同时也可以使用web.xml文件

Eg:

![Xnip2022-03-10_10-16-09](Servlet.assets/Xnip2022-03-10_10-16-09.jpg)







自定义访问的路径名称:

![Xnip2022-03-10_10-23-59](Servlet.assets/Xnip2022-03-10_10-23-59.jpg)



![Xnip2022-03-10_10-24-18](Servlet.assets/Xnip2022-03-10_10-24-18.jpg)



![Xnip2022-03-10_10-24-25](Servlet.assets/Xnip2022-03-10_10-24-25.jpg)





## 2. 将静态资源放在webapp目录下

- 如果有index.html，项目启动后会自动访问该页面

Eg:

![Xnip2022-03-10_10-36-02](Servlet.assets/Xnip2022-03-10_10-36-02.jpg)

<hr>













# 二、使用HttpServlet

Servlet下的实现抽象类:

- GenericServlet: 没有重写service方法



HttpServlet: 继承了GenericServlet的一个抽象类，需要我们重写service方法，否则会出现方法不可用的提示

Eg:

![Xnip2022-03-10_10-58-30](Servlet.assets/Xnip2022-03-10_10-58-30.jpg)



![Xnip2022-03-10_10-57-23](Servlet.assets/Xnip2022-03-10_10-57-23.jpg)



![Xnip2022-03-10_10-58-02](Servlet.assets/Xnip2022-03-10_10-58-02.jpg)



![Xnip2022-03-10_10-58-09](Servlet.assets/Xnip2022-03-10_10-58-09.jpg)



- 为了让页面正常访问，我们需要重写doGet方法
- 使用setContentType设置返回的数据类型和编码格式
- 使用getWrite获取一个Writer对象

Eg:

```java
@WebServlet("/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().print("<h1>Servlet</h1>");
    }
}
```



![Xnip2022-03-10_11-02-17](Servlet.assets/Xnip2022-03-10_11-02-17.jpg)

<hr>













# 三、WebServlet注解

source code

![Xnip2022-03-10_11-11-31](Servlet.assets/Xnip2022-03-10_11-11-31.jpg):



- name:
- value: 指定servlet的路径
- urlPatterns: 同value
- loadOnStartup: Servlet的加载顺序



可以使用通配符来指定任意文件类型，或者所有文件都由该Servlet处理

Eg:

![Xnip2022-03-10_11-16-44](Servlet.assets/Xnip2022-03-10_11-16-44.jpg)



通过loadOnStartUp可以指定Servlet的加载优先级(如果为负，则不会在部署前加载)，init方法在部署前就会运行一次

Eg:

![Xnip2022-03-10_11-21-00](Servlet.assets/Xnip2022-03-10_11-21-00.jpg)

<hr>











# 四、POST登陆

- 创建一张user表，创建对应的对象和mapper

Eg:

![Xnip2022-03-10_14-32-23](Servlet.assets/Xnip2022-03-10_14-32-23.jpg)



![Xnip2022-03-10_14-32-44](Servlet.assets/Xnip2022-03-10_14-32-44.jpg)



![Xnip2022-03-10_14-33-38](Servlet.assets/Xnip2022-03-10_14-33-38.jpg)





- 编写登陆页面
- 创建一个form表单，**指定其method和action属性**
- 其中**action属性的值对应处理该表单的Servlet程序的注册路径**
- 注意在input标签内添加对应的name属性值，方便之后从Map中取出

Eg:

![Xnip2022-03-10_14-35-39](Servlet.assets/Xnip2022-03-10_14-35-39.jpg)





- 编写对应的Servlet程序
- 调用HttpServletRequest对象的getParameterMap方法，返回所有的Map键值对
- 通过该Map获取对应的值即可

Eg:

![Xnip2022-03-10_14-39-57](Servlet.assets/Xnip2022-03-10_14-39-57.jpg)

<hr>











# 五、下载文件

- 在前端页面中添加一个a标签作为下载链接
- 修改herf为对应的Servlet程序的注册路径，download标签为用户下载后的文件名

Eg:

```html
<a href="file" download="Irene.jpg">Download File</a>
```



- 创建一个Servlet程序，其注册路径对应a标签中的href属性，**注意重写的是doGet方法**
- 先通过HttpServletResponse对象调用setContentType方法，设置对应的文件类型
- 获取文件的输入流，通过HttpServletResponse对象获取response的输出流
- 通过commons.io工具类中的copy方法将输入流复制到输出流即可



不同文件类型对应的响应头:

![Xnip2022-03-10_15-48-49](Servlet.assets/Xnip2022-03-10_15-48-49.jpg)





Eg:

![Xnip2022-03-10_15-49-41](Servlet.assets/Xnip2022-03-10_15-49-41.jpg)

<hr>











# 六、上传文件



## 1. 前端部分

- 需要创建一个form表单，方法设置为post，action指定对应的Servlet程序路径
- 使用enctype属性，使得文件可以分段传输
- 创建一个file类型的input标签，注意设置其name属性，后面Servlet程序会用到

```html
<form method="post" action="file" enctype="multipart/form-data">
  <div>
    <input type="file" name="upload-file">
  </div>
  <div>
    <button type="submit">上传</button>
  </div>
</form>
```

<hr>









## 2. 后端部分

- 重写doPost方法，与服务端接收文件的路径建立文件输出流
- 通过HttpServletRequest对象调用getPart方法，传入前端设置的input中的name属性值
- 调用apache.commons.io中的copy方法，将输入流复制给输出流
- 注意要在该Servlet程序中添加@MultipartConfig注解后，才能支持文件分段

Eg:

```java
@MultipartConfig
@WebServlet("/file")
public class DownUpLoadFile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream("/Users/alex/Desktop/Irene.jpg")) {
            Part part = req.getPart("upload-file");
            IOUtils.copy(part.getInputStream(), fileOutputStream);

            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("Upload success!");
        }
    }
}
```



Eg:

![Xnip2022-03-10_16-12-12](Servlet.assets/Xnip2022-03-10_16-12-12.jpg)



![Xnip2022-03-10_16-12-19](Servlet.assets/Xnip2022-03-10_16-12-19.jpg)

<hr>







# 七、XHR请求



## 1. 前端

- 在html中加上一个div标签用来表示时间，添加一个按钮用来更新时间
- 将该按钮绑定一个onclick事件，用该事件对应的js文件来更新div中的值
- 需要注意的是，通过Servlet程序返回的数据可以通过XHR对象直接获取

Eg:



HTML:

```html
<div id="time"></div>
<br>
<button onclick="updateTime()">更新数据</button>
<script>
  updateTime()
</script>
```



JS:

```javascript
function updateTime() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById("time").innerText = xhr.responseText
        }
    };
    xhr.open('GET', 'time', true);
    xhr.send();
}
```

- 这里通过getElementById函数获取id为"time"的标签对象
- 再调用innerText字段以修改时间，赋值则通过XHR对象的responseText字段值
- 其中open方法中的第二个参数对应Servlet的注册路径





## 2. 后端

- 创建一个Servlet，创建一个SampleDateFormat对象，用来格式化当前的时间并返回为字符串
- 最后通过Writer传出，可使用XHR对象的responseText字段获取它

```java
@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String date = dateFormat.format(new Date());
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(date);
    }
}
```



Eg:

![Xnip2022-03-10_16-53-50](Servlet.assets/Xnip2022-03-10_16-53-50.jpg)



![Xnip2022-03-10_16-54-23](Servlet.assets/Xnip2022-03-10_16-54-23.jpg)

<hr>





# 八、重定向/请求转发



## 1. 重定向

- 重定向可以直接定向到任意URL
- 使用HttpServletResponse对象调用sendRedirect方法，传入需要重定向的Servlet程序对应的注册路径
- 该方法会将响应的状态码设置为302，并在响应头中添加一个Location属性，该属性表明重定向的网址

Eg:

```java
resp.sendRedirect("time");
```



![Xnip2022-03-10_21-01-08](Servlet.assets/Xnip2022-03-10_21-01-08.jpg)



![Xnip2022-03-10_21-01-28](Servlet.assets/Xnip2022-03-10_21-01-28.jpg)

<hr>







## 2. 请求转发

- 请求转发只能进行域内的转发，即仅限当前项目内的Servlet程序
- 通过HttpServletRequest对象调用getRequestDispatcher()方法，参数为转发的Servlet程序对应的注册路径，会返回一个RequestDispatcher对象
- 再通过该RequestDispatcher对象调用forward方法，传入对应的request和response

Eg:

```java
RequestDispatcher dispatcherType = req.getRequestDispatcher("/time");
dispatcherType.forward(req, resp);
```



![Xnip2022-03-10_21-16-16](Servlet.assets/Xnip2022-03-10_21-16-16.jpg)

- 此处的状态码为405，因为LoginServlet重写的方法为doPost，而转发到的TimeServlet只重写了doGet，所以方法不可以用

<hr>









































