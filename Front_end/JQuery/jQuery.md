# 一、jQuery介绍

- jQuery: JavaScript和Query，是辅助JavaScript开发的js类库
- 核心思想: write less, do more(写得更少，做得更多)，可以解决很多浏览器的兼容问题
- jQuery开源免费，语法简单

****













# 二、Hello jQuery

- jQuery对象都默认以"$"开头
- 使用jQuery之前需要独立写一个script标签导入jQuery才行

Eg:

![Xnip2021-11-29_19-18-58](jQuery.asset/Xnip2021-11-29_19-18-58.jpg)

- 其中$("")就相当于通过id获取标签对象，但jQuery对象要以"$"开头



注意：

- 使用jQuery时一定要先引入jQuery库
- jQuery中的"$"其实是一个函数

jQuery src Eg:

![Xnip2021-11-29_19-33-47](jQuery.asset/Xnip2021-11-29_19-33-47.jpg)



压缩版本和不压缩版本的区别:

![Xnip2021-11-29_19-35-07](jQuery.asset/Xnip2021-11-29_19-35-07.jpg)



![Xnip2021-11-29_19-35-18](jQuery.asset/Xnip2021-11-29_19-35-18.jpg)

- 在实际部署中一般都使用压缩的版本，可以加快加载的速度





- 为按钮标签添加响应函数的过程：
    - 使用jQuery查询到标签对象$("#id")
    - 通过该标签对象调用click方法，在其中定义函数：对象.click(function() {});













## 1. jQuery的核心函数

$是jQuery的核心函数，通过传入不同的参数，会有不同的作用



- 当传入的参数为函数时

表示页面加载完成之后，相当于window.onload = function() {}



- 参数为HTML格式的字符串时

会创建这个字符串，可以再使用apendTo()传入到body标签中

Eg:

![Xnip2021-11-29_19-54-51](jQuery.asset/Xnip2021-11-29_19-54-51.jpg)



- 参数为选择器字符串时
    - $("#id"): 根据id查询标签对象
    - $("标签名"): 根据知道的标签名查询标签对象
    - $(".class属性"): 类选择器，根据class属性查询标签对象

![Xnip2021-11-29_19-58-26](jQuery.asset/Xnip2021-11-29_19-58-26.jpg)







- 参数为DOM对象时

会将该DOM对象转换为jQuery对象

Eg:

![Xnip2021-11-29_20-01-50](jQuery.asset/Xnip2021-11-29_20-01-50.jpg)















## 2. 区分jQuery对象和DOM对象



DOM对象

- 通过getElementById/getElementsByName/getElementsByTagName/createElement方法创建的都是DOM对象

通过alter输出的形式为: [object HTMLXXX]



jQuery对象

- 通过API创建/包装DOM对象/API查询的对象都是jQuery对象

通过alter输出的形式: [object Object]

















## 3. jQuery对象的本质

- jQuery对象是一个DOM对象数组(数组中的元素都是DOM对象) + jQuery提供的一系列功能函数

















## 4. jQuery对象和DOM对象的区别

- jQuery不能使用DOM对象的属性的方法
- DOM不能使用JQuery对象的属性和方法

















## 5. jQuery和DOM对象转换(重点)

- 通过核心函数可以将DOM对象转换为jQuery对象
- jQuery对象通过下标可以转换为DOM对象













## 6. 基础选择器

- id选择器: 根据id查找标签对象

$(#id_val)

- .class选择器: 根据class查找标签对象

$(.class)

- element选择器: 根据标签名查找标签对象

$(table_name)

- *选择器: 匹配所有的元素
- selector1, selector2组合选择器: 合并多个选择器的结果并返回













