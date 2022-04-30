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
>
> 一个码点对应一个字符，但一个码点不一定对应一个代码单元

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

注意:

> 整数被0除会产生异常，浮点数被0除会得到Double.INFINITY或者NaN





### 1) 数学函数/常量

幂运算:

```java
double y = Math.pow(x, a)
```

y将被设置为x的a次幂，注意返回类型为double



整数取余:

```java
int n = Math.floorMod(-1, 2);
```



Eg:

![Xnip2022-04-27_14-35-26](JavaCore I.assets/Xnip2022-04-27_14-35-26.jpg)





三角函数:

Math.sin

Math.cos

Math.tan

Math.atan

Math.atan2





指数函数和反函数(自然对数/10为底的对数):

Math.exp

Math.log

Math.log10



表示PI和e的常量:

Math.PI

Math.E





Eg:

![Xnip2022-04-27_14-41-55](JavaCore I.assets/Xnip2022-04-27_14-41-55.jpg)

<hr>







### 2) 数值类型转换(自动类型转换)

使用两个数值进行二元运算时，要先将两个数值转换为同一类型，然后再进行计算

![IMG_E07D59C7FADF-1](JavaCore I.assets/IMG_E07D59C7FADF-1.jpeg)

<hr>







### 3) 强制类型转换

对浮点数进行舍入运算，可以使用Math.round方法:

```java
double x = 9.997;
int nx = (int) Math.round(x);
```



> 如果类型转换时超出了目标类型的表示范围，结果就会截断为完全不同的值

<hr>









### 4) 位运算符

&(and): 按位与。对应的bit都为1才为1

|(or): 按位或。有一个bit为1都为1

^(xor): 异或。对应bit都为1，则为0；否则为1(与&相反)



```
>>: 将所有的bit右移
<<: 将bit左移
>>>: 不带符号位填充高位
```

<hr>











### 5) 枚举类型

使用的时候，通过枚举类进行调用即可

![Xnip2022-04-27_14-55-58](JavaCore I.assets/Xnip2022-04-27_14-55-58.jpg)



枚举类和普通类类似，只不过字段固定(可以有方法)

<hr>











## 6. 字符串

> Java没有内置的字符串类型，而是在标准的Java库里提供了一个String预定义类





### 1) 子串

通过subsring即可，其中第二个参数是不想复制的第一个位置

<hr>







### 2) 拼接

Java可以使用+来连接字符串



如果需要将多个字符串进行拼接，且用定界符分隔的话，可以使用静态方法join:

![Xnip2022-04-27_15-02-32](JavaCore I.assets/Xnip2022-04-27_15-02-32.jpg)

<hr>











### 3) 不可变字符串

- Java字符串中的字符不能被修改，所以需要在原字符串的基础上进行提取，之后再进行拼接

> 拼接后的字符串是一个新的字符串



不可变字符串有利于编译器共享字符串

<hr>









### 4) 检测字符串是否相等

- 使用equals方法即可
- 如果想要检测相等时忽略大小写，则可以使用equalsIgnoreCase方法



- 使用"=="只能检测两个字符串是否相等，即判断两个字符串是否处于内存中的同一个位置处



> Java中只有字符串常量(字面量)是共享的，而使用"+"/substring等操作产生的结果不是共享的

<hr>









### 5) 空串/NULL

检查一个字符串既不是空串也不是null:

```java
if (str != null && str.length() > 0)
```



![Xnip2022-04-28_15-17-30](JavaCore I.assets/Xnip2022-04-28_15-17-30.jpg)

<hr>









### 6) 码点/代码单元

- char类型是一个采用UTF-16编码表示Unicode码点的代码单元
- 大多数常用的Unicode字符可以使用一个代码单元，辅助字符则需要两个代码单元



String实例的length方法：

- 返回字符串需要的**代码单元数量**，即Unicode code unit(而不是字符数量)
- 可以使用codePointCount方法获取对应范围内的码点数量(一个字符对应一个码点)

![Xnip2022-04-28_15-38-24](JavaCore I.assets/Xnip2022-04-28_15-38-24.jpg)

<hr>







### 7) String API



- char charAt(int index)

返回给定索引位置处的**代码单元**

- int codePointAt(int index)

返回给定位置处的码点

- int offsetByCodePoints(int startIndex, int cpCount)

返回从startIndex处开始，移动cpCount后的**码点索引**

- int compareTo(String other)

按照字典序比较字符串，如果当前字符串对象在参数对象前面，则返回一个负数，反之返回正数，相等则为0

- IntStream codePoints()

将字符串的码点作为一个流返回

- new String(int[] codePoints, int offset, int count)

将码点构造为一个字符串







- boolean startsWith(String prefix)

判断字符串是否以prefix字符串开头

- boolean endsWith(String suffix)

判断字符串是否以suffix字符串结尾

- int codePointCount(int startIndex, int endIndex)

返回参数区间内的码点数量

- String replace(CharSequence oldString, CharSequence newString)

用newString代替原始字符串中所有的oldString，参数可以是String/StringBuilder实例

- String trim()

删除原字符串头/尾的空格，然后返回一个新的字符串

- String join(CharSequence delimiter, CharSequence... elements)

返回一个新的字符串，并用给定的定界符连接所有的元素



Eg:

![Xnip2022-04-28_15-54-03](JavaCore I.assets/Xnip2022-04-28_15-54-03.jpg)

<hr>











### 8) 构建字符串

- 对于较短的字符构建字符串，或者来自按键/文件中的单词，使用字符串连接的话效率会很低的
- 使用StringBuilder就能解决这个问题



> StringBuilder自JDK5.0被引入，其前身为StringBuffer
>
> StringBuffer的效率较低，但其是线程安全的，适用于多线程的情况
>
> 如果所有字符串都在一个单线程中处理，则使用StringBuilder即可



相关方法:

![IMG_D113AD51C3BA-1](JavaCore I.assets/IMG_D113AD51C3BA-1.jpeg)

<hr>







## 7. 输入输出



### 1) 输入

读取"标准输入流"的过程:

- 构造一个Scanner对象，并与"标准输入流"System.in关联

```java
Scanner in = new Scanner(System.in);
```



读取一行输入:

```java
String name = in.nextLine();
```



读取下一个整数:

```java
int age = in.nextInt();
```



读取下一个浮点数:

```java
double weight = in.nextDouble();
```



Code:

![Xnip2022-04-29_21-15-04](JavaCore I.assets/Xnip2022-04-29_21-15-04.jpg)





Scanner对应的实例方法:

![IMG_EF3BD20FE6C4-1](JavaCore I.assets/IMG_EF3BD20FE6C4-1.jpeg)

<hr>







### 2) 格式化输出

- Java SE 5.0中沿用了C库函数中的printf方法



Eg:

```java
System.out.printf("%8.2f", x);
```



用于printf的转换符:

![IMG_CBB8ADF052D2-1](JavaCore I.assets/IMG_CBB8ADF052D2-1.jpeg)



用于printf的标志:

![IMG_685D2F7E2DF7-1](JavaCore I.assets/IMG_685D2F7E2DF7-1.jpeg)

<hr>





### 3) 读写文件

- 想要对文件进行读取，则需要用一个File对象构造一个Scanner对象

Eg:

```java
Scanner in = new Scanner(Paths.get("myfile.txt"), "UTF-8");
```



- 想要写文件则需要构造一个PrintWriter对象，在其构造器中提供文件名即可

Eg:

```java
PrintWriter out = new PrintWriter("myfile.txt", "UTF-8");
```



因为如果用一个不存在的文件构造Scanner会抛出异常，所以我们需要在main方法中加上throw标记

```java
public static void main(String[] args) throw IOException {
  Scanner in = new Scanner(Paths.get("file.txt"), "UTF-8");
  ...
}
```

<hr>







### 4) 控制流程













