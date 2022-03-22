# 一、初入

- Ajax: **A**synchronous **J**avascript **A**nd **X**ML(异步JavaScript和XML)，其目标就是实现页面的数据动态更新，其只是一个概念而不是一个具体的框架

Eg:

![AJAX](https://www.runoob.com/wp-content/uploads/2013/09/ajax-yl.png)



其实现的方式是通过JQuery中的函数:

- $.get()函数中，第一个参数为对应的程序路径，第二个参数为对从后端获取的对象进行的操作(将该对象对应的值填入到前端页面中)

```javascript
function updateData() {
    //美元符.的方式来使用Ajax请求，这里使用的是get方式，第一个参数为请求的地址（注意需要带上Web应用程序名称），第二个参数为成功获取到数据的方法，data就是返回的数据内容
  	$.get("/mvc/data", function (data) {   //获取成功执行的方法
        window.alert('接受到异步请求数据：'+JSON.stringify(data))  //弹窗展示数据
        $("#username").text(data.name)   //这里使用了JQuery提供的选择器，直接选择id为username的元素，更新数据
        $("#age").text(data.age)
    })
}
```



如果我们需要读取前端发送给我们的JSON格式数据，那么这个时候就需要添加`@RequestBody`注解：

```java
@RequestMapping("/submit")
@ResponseBody
public String submit(@RequestBody JSONObject object){
    System.out.println("接收到前端数据："+object);
    return "{\"success\": true}";
}
```

这样，我们就实现了前后端使用JSON字符串进行通信。























