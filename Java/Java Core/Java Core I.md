# 一、Java基础程序设计结构



## 1. 一个简单的Java应用程序

Eg:

![Xnip2022-04-24_15-29-00](JavaCore I.assets/Xnip2022-04-24_15-29-00.jpg)

- Java区分大小写(case sensitive)



分析code:

关键字public被称为**访问修饰符**(access modifier)

**修饰符用来控制程序的其他部分对当前这段程序的访问级别**



- 标准的命名规范:

类名是以大写字母开头的**名词**

如果由多个名词组成，那么每个单词的第一个字母都应该大写才行(大驼峰命名法/CamelCase)



**源文件的文件名**必须和**公共类的名字**相同



- 编译后的字节码文件会与源文件存储在同一个目录下
- 根据语言规范，main方法必须声明为public
- 每个Java应用程序都必须有一个main方法

<hr>









## 2. 注释

在Java中有3种注释，其中最常用的是//，其可注释的内容从//开始到本行结尾



- 如果需要长篇的注释，则可以如下注释将一段比较长的内容括起来

```java
/*

*/
```



最后一种可以用来自动地生成文档

```java
/**
*/
```



Code

![Xnip2022-04-24_15-50-19](JavaCore I.assets/Xnip2022-04-24_15-50-19.jpg)

注意:

在Java中，/**/注释不能相互嵌套

<hr>








## 3. 数据类型

- Java是一种强类型语言

> 这意味着**必须**为每个变量声明一个类型



8种基本类型(primitive type):

- 4种整形
- 2种浮点型
- 1种字符类型
- 1种boolean类型

<hr>







### 1) 整型

| Type  | Storage |          length          |
| :---: | ------- | :----------------------: |
|  int  | 4byte   | -2^31~2^31 - 1(超过20亿) |
| short | 2byte   |     -2^15 ~ 2^15 - 1     |
| long  | 8byte   |     -2^63 ~ 2^63 - 1     |
| byte  | 1byte   |        -128 ~ 127        |



- 在Java中，整型的范围与运行Java的机器无关，这样就解决了移植的问题
- long类型的数据后面需要添加一个L后缀
- 十六进制数有一个前缀"0x"或者"0X"；八进制数有一个前缀"0"



从Java7开始:

- 加上前缀0B/0b可以写二进制数
- 可以为数字字面量加下划线: 1_000_000(只是为了让人更易读)



Eg:

![Xnip2022-04-25_15-14-35](JavaCore I.assets/Xnip2022-04-25_15-14-35.jpg)

<hr>







### 2) 浮点类型

浮点类型可以用于表示有小数部分的数值

|  type  | storage |     length      |
| :----: | :-----: | :-------------: |
| float  |  4byte  | 有效位数有6~7位 |
| double |  8byte  | 有效位数有15位  |



float类型的数值需要加上后缀F/f，浮点数值没有的话默认为double类型

- 十六进制表示法:

0.125=2^-3^ -> 0x1.0p-3，其中p代表指数，指数的**基数为2，不是10**





- 所有的浮点数遵循IEEE754标准



溢出/出错的三个特殊情况下的浮点数值:

- 正无穷大(Double.POSITIVE_INFINITY)
- 负无穷大(Double.NEGATIVE_INFINITY)
- NaN(Double.NaN)



检测一个值是否为Double.NaN，需要使用Double.isNaN方法

Eg:

![Xnip2022-04-25_15-23-44](JavaCore I.assets/Xnip2022-04-25_15-23-44.jpg)

<hr>









### 3) char类型	

- char类型原本表示单个字符，**但现在不是了**
- 现在有些Unicode字符用一个char值描述，其余则使用两个char值
- char类型的字面量需要用单引号括起来

> char类型的值可以表示为16进制值，其范围从\u0000到\uffff，共2^16^ - 1



除了\u之外，还有其他的转移序列可以表示不同的意思

![IMG_77B42C580EC0-1](JavaCore I.assets/IMG_77B42C580EC0-1.jpeg)





Eg:

![Xnip2022-04-25_21-11-17](JavaCore I.assets/Xnip2022-04-25_21-11-17.jpg)

**注意**：Unicode转移序列会在解析代码之前就得到处理，所以一定要注意注释中的转移序列

<hr>











### 4) Unicode/char

- 从Java SE5.0开始，**码点(code point)是指一个编码表中的某个字符对应的代码值**
- 在Unicode标准中，码点采用十六进制书写，并加上前缀"U+"
- Unicode的码点可以分为17个代码级别(code plane)
- UTF-16编码中，采用不同长度的编码表示所有的Unicode码点；在基本的多语言级别中，每个字符用16位表示，称为代码单元(code unit)；辅助字符则采用一对连续的代码单元进行encode(即两个16位的代码单元)

> Java中，char类型描述了UTF-16编码中的一个代码单元(16bit)

<hr>







## 4. 变量



### 1) 初始化

- 声明一个变量后，必须用赋值语句对变量进显式初始化
- 变量的声明最好靠近变量第一次使用的地方

<hr>







### 2) 常量

- 在Java中使用`final`关键字标记常量

Code:

```java
public class Constants {
  public static void main(String[] args) {
    final double CM_PER_INCH = 2.54;
    double paperWidth = 8.5;
    double paperHeight = 11;
    System.out.println("Paper size int centimeters: " + paparWidth * CM_PER_INCH);
  }
}
```



- `final`表示这个变量只能够被赋值一次。赋值后就不能再更改了，一般将常量名全大写，且单词之间用下划线隔开

<hr>









## 5. 运算符





















