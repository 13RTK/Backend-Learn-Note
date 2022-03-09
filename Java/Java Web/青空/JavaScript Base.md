# 一、认识和引入

- JavaScript文件需要在浏览器中运行，不能单独运行(node.js除外)
- 在head标签内使用script标签引入即可

syntax:

```html
<script src="js_path"></script>
```







Eg:

![Xnip2022-03-09_15-58-32](JavaScript.assets/Xnip2022-03-09_15-58-32.jpg)



因为var存在设计缺陷，这里我们使用let来定义变量



在控制台打印的两种方法

- console.log
- console.info



弹窗信息:

- window.alert

![Xnip2022-03-09_16-00-51](JavaScript.assets/Xnip2022-03-09_16-00-51.jpg)

<hr>









## 1. 基础数据类型

- 对于每个变量，**我们可以使用typeof获取其类型名称**:

Eg:

![Xnip2022-03-09_16-04-50](JavaScript.assets/Xnip2022-03-09_16-04-50.jpg)





- number: 数值
- string: 字符串
- boolean: 同java



其余特殊类型(一般表示错误)

- undefined: 未定义的，表示该变量未赋值
- null: 同java
- NaN: not allow number，一般表示错误的计算结果

Eg:

![Xnip2022-03-09_16-09-23](JavaScript.assets/Xnip2022-03-09_16-09-23.jpg)

<hr>











# 二、JavaScript函数

- 定义:

syntax:

```javascript
function function_name(param1, param2...) {
  ...
  return returnObj;
}
```



Eg:

![Xnip2022-03-09_16-34-27](JavaScript.assets/Xnip2022-03-09_16-34-27.jpg)





## 1. 参数和返回类型

- js里只需要写参数名称即可，不需要指定类型
- 同样，函数只需要写名称，不需要指定返回值的类型，return语句可有可无

Eg:

![Xnip2022-03-09_16-36-37](JavaScript.assets/Xnip2022-03-09_16-36-37.jpg)





- 函数也可以赋给一个变量

Eg:

![Xnip2022-03-09_17-11-07](JavaScript.assets/Xnip2022-03-09_17-11-07.jpg)





- 匿名函数

我们可以不指定函数的名称，但将其赋给一个变量，使用该变量来调用函数

Eg:

![Xnip2022-03-09_17-14-03](JavaScript.assets/Xnip2022-03-09_17-14-03.jpg)







- 函数也可以作为参数传入

Eg:

![Xnip2022-03-09_17-16-23](JavaScript.assets/Xnip2022-03-09_17-16-23.jpg)

- 这里我们定义了一个匿名函数，将其传递给了变量funcVar1
- 我们调用funcVar1，并向其传入了一个匿名函数作为参数









## 2. 匿名函数



- 匿名函数的lambda表达式(参数为一个函数)

```javascript
function func(param) {
  param();
}

func((参数) => {
  函数体
})
```

Eg:

![Xnip2022-03-09_17-19-35](JavaScript.assets/Xnip2022-03-09_17-19-35.jpg)









- 回调函数

其实就是lambda表达式，但是该匿名函数有参数

```javascript
function func(param) {
  praram("匿名函数的参数");
}

func(param => {
  console.info("这里是匿名函数的定义，其传入的参数为: " + param)
})
```

Eg:

![Xnip2022-03-09_17-23-42](JavaScript.assets/Xnip2022-03-09_17-23-42.jpg)

<hr>















# 三、数组

- JavaScript数组的写法和Python一样，其本质上是一个动态的列表
- 如果设置一个下标超过该数组的元素，该数组会扩容到该添加元素的索引位置处

Eg:

![Xnip2022-03-09_17-47-09](JavaScript.assets/Xnip2022-03-09_17-47-09.jpg)



- 数组中的元素可以不同:

![Xnip2022-03-09_17-48-42](JavaScript.assets/Xnip2022-03-09_17-48-42.jpg)







## 1) 相关方法/字段

- length: 数组的长度
- push: 加入元素
- pop: 弹出栈顶元素，并返回它
- fill: 填充数组，可以指定范围
- map: 返回一个处理后的新数组，会保留原数组

Eg:

![Xnip2022-03-09_17-52-56](JavaScript.assets/Xnip2022-03-09_17-52-56.jpg)

<hr>











# 四、对象

创建对象有两种方法:

```javascript
let obj = new Object()
let obj = {}
```

- 推荐使用第二种
- 我们可以直接设置其对应的字段或者其所含有的函数

Eg:

![Xnip2022-03-09_17-57-35](JavaScript.assets/Xnip2022-03-09_17-57-35.jpg)







- 同Java一样，我们同样可以使用this关键字来指定对象内的属性
- 我们也可以在创建对象的时候直接定义其属性和函数

Eg:

![Xnip2022-03-09_18-01-25](JavaScript.assets/Xnip2022-03-09_18-01-25.jpg)

<hr>













# 五、事件

常用的事件:

- onclick: 点击事件
- oninput: 内容输入事件
- onsubmit: 内容提交事件



为html元素绑定事件的做法为在对应元素内添加事件属性，将其赋值为一个js函数即可

```html
<input type="password" oninput=func_name()>
```



Eg:

![Xnip2022-03-09_18-47-52](JavaScript.assets/Xnip2022-03-09_18-47-52.jpg)



但想要修改对应标签的值/样式的话，还需要DOM对象

<hr>











# 六、Document对象

- ​	加载网页时，浏览器会创建页面的Document对象，它会将所有页面中的元素都映射为JS对象，这样我们就能通过JS操纵页面的元素



场景:

绑定输入事件，如果当前密码长度不合法，输入框变为红色



Eg:

![Xnip2022-03-09_20-23-07](JavaScript.assets/Xnip2022-03-09_20-23-07.jpg)

- 传入当前标签对象，获取value属性的长度来判断其是否合理
- 通过设置和取消其class属性来控制边框的样式



- 当然，事件本身也是标签对象的一个属性，我们同样可以进行设置:

![Xnip2022-03-09_20-32-07](JavaScript.assets/Xnip2022-03-09_20-32-07.jpg)

<hr>















# 七、发送XHR请求

- 在js中创建一个XMLHttpRequest对象
- 使用该对象调用open方法，传入两个参数，第一个参数为请求方法(GET/POST)，第二个参数为请求的URL
- 最后再使用该对象调用sned方法即可

Eg:

![Xnip2022-03-09_21-24-04](JavaScript.assets/Xnip2022-03-09_21-24-04.jpg)



![Xnip2022-03-09_21-23-52](JavaScript.assets/Xnip2022-03-09_21-23-52.jpg)

