# 一、语法



## 1. 准备工作

JS在HTML中有两种导入方式:

- 在`head`标签中添加`script`标签后在标签内编写JS代码
- 以单独的js文件的方式，在`script`标签中通过`src`属性指定js的路径

![Xnip2022-07-03_21-24-32](JS.assets/Xnip2022-07-03_21-24-32.jpg)





注意:

> 将script放在</body>标签之前**能够使得浏览器更快地加载页面**

- 这里我们没有在`script`标签内指定`type="text/javascript"`这个属性，因为脚本默认是JS，所以不需要指定

<hr>













## 2. 语法



### 语句

- 每个语句放在不同的行上就可以隔开，想要放在一行则需要使用`;`隔开，最好在每句末尾都加上`;`

<hr>





### 注释

```js
// 单行
<!-- 同样是单行注释(不推荐)

/*
多行
*/
```

<hr>









### 变量

为变量赋值:

```js
mood = "happy";
age = 33;
```



通过弹窗显示两个变量:

```js
alert(mood);
alert(age);
```



![Xnip2022-07-03_21-33-20](JS.assets/Xnip2022-07-03_21-33-20.jpg)





- JS中允许直接对变量赋值而不声明，赋值操作将自动声明该变量
- 提前声明是一个好习惯:

```js
var mood;
var age;

// 通过一条语句一次声明多个变量:

var mood, age;

// 同时完成声明和赋值:

var mood = "happy";
var age = 33;

// 或者这样:
var mood = "happy", age = "33";
```





注意:

- JS中区分大小写
- JS中不允许变量名包含空格或者标点符号(除了$)
- 变量名命名规则同C语言(小驼峰)或者大驼峰

<hr>











### 数据类型

js是一种弱类型语言，`可以任意修改变量的类型`





JS中的数据类型:

- 字符串
- 数值
- 布尔值

<hr>















### 数组

通过`Array`关键字可以声明一个数组，并制定数组的初始长度:

```js
var beatles = Array(4);
```

- 也可以在声明数组时不给出数组的长度:

```js
var beatles = Array();
```





设置/填充数组操作需要通过下标来完成:

```js
array_name[idx] = val;
```





- 可以在声明数组的同时对其进行填充操作:

```js
var beatles = Array("John", "Paul", "George", "Ringo");

// 可以直接省略Array关键字，该用方括号:

var beatles = ["John", "Paul", "George", "Ringo"];
```

js中数组可以存法不同类型的变量、值

> 数组中还可以是其他的数组

<hr>











### 对象

创建对象:

```js
var lennon = Object();
lennon.name = "John";
lennon.year = 1940;
lennon.living = false;
```



也可以通过花括号的语法创建对象:

```js
var lennon = { name: "John", yaer: 1940, living: false };
```



Eg:

![Xnip2022-07-03_21-50-17](JS.assets/Xnip2022-07-03_21-50-17.jpg)

<hr>







## 3. 操作



注意:

> JS中`===`才是严格相等，同理`!==`才是严格不相等

















