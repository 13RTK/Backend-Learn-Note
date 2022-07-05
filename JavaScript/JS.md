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

<hr>













## 4. 函数

定义函数的语法:

```js
function func_name(arguments) {
  statements;
}
```





JS的函数中不需要指定返回类型，直接使用`return`语句返回即可

我们可以使用一个变量来接收返回值

> 函数还可以视为一种数据类型(类)



Eg:

![Xnip2022-07-04_22-01-09](JS.assets/Xnip2022-07-04_22-01-09.jpg)

<hr>











## 5. 对象

- 对象也是JS中的一种数据类型



对象中可以有`属性|字段`和`方法|函数`

创建一个对象实例的语法:

```js
var instance = new Object;
```





### 1) 内建对象

> js中预先定义好的对象就是内建对象

- 数组就是一个内建对象，通过其`length`属性我们就可以获取它的长度
- Math对象可以直接使用：`round方法`、`floor方法`、`ceil方法`等等

<hr>









### 2) 宿主对象(host object)

> 部分对象由JS的运行环境提供，这些对象称为宿主对象(浏览器等等)

通过`document`对象，我们可以获取页面中的任何一个元素的信息

<hr>















# 二、DOM



## 1. DOM中的D

> D即document

<hr>







## 2. DOM中的O

> O即Object



JS中有三种对象:

- 用户定义对象
- 内建对象
- 宿主对象





在JS的最初版本中，最基础的是`window`对象

> window对象对应着整个浏览器窗口，其对应的属性和方法称为`BOM`(Browser Object Model)

现在不需要再使用该对象了，专心于`document`对象即可

<hr>















## 3. DOM中的M

> M即Model



DOM的思想就是将每个页面视作一个文档，其中每个文档都可以用"树"表示

Eg:

![IMG_CA12336A7C3C-1](JS.assets/IMG_CA12336A7C3C-1.jpeg)



![IMG_70A0FCDE4279-1](JS.assets/IMG_70A0FCDE4279-1.jpeg)

<hr>

















## 4. 节点

> DOM中，文档是节点构成的集合





### 1) 元素节点

> DOM的原子是`元素节点`

<hr>







### 2) 文本节点

> 包含文本的节点就是一个文本节点

<hr>













### 3) 属性节点

Eg:

```html
<p title="a gentle reminder">Don't forget to buy this stuff.</p>
```



- 其中`title`就是一个`属性节点`

<hr>















### 4) 获取元素

获取元素节点的三种DOM方法:

- 通过元素ID
- 通过标签名
- 通过类名





1. getElementById

通过该方法可以获取有指定id值的元素节点对应的对象

Syntax:

```js
document.getElementById("id");
```



Eg:

![Xnip2022-07-04_22-36-04](JS.assets/Xnip2022-07-04_22-36-04.jpg)

<hr>











2. getElementsByTagName

通过该方法，可以通过标签的名字返回一个对象数组

syntax:

```js
document.getElementsByTagName("tagName");
```



Eg:

![Xnip2022-07-04_22-36-04](JS.assets/Xnip2022-07-04_22-36-04.jpg)



该方法返回的对象可以用一个变量来代替

我们可以通过for循环来获取其中的每个对象

Eg:

![Xnip2022-07-04_22-42-24](JS.assets/Xnip2022-07-04_22-42-24.jpg)













