# Day01

![570CBB13CB12830D3BE2ADF76F936F0B](Algorithem/570CBB13CB12830D3BE2ADF76F936F0B.jpg)



![62B55035199D59789B35703CF1952ED6](Algorithem/62B55035199D59789B35703CF1952ED6.jpg)

- 需要注意处理分钟相减时会出现负数的情况，可以使用Math类中的abs方法取得绝对值
- 如果结束时的分钟数小于开始时的分钟数，则需要减少一个小时，且分钟数需要重新计算

****





# Day02

![Xnip2021-03-27_19-10-54](Algorithem/Xnip2021-03-27_19-10-54.jpg)



![Xnip2021-03-27_19-17-14](Algorithem/Xnip2021-03-27_19-17-14.jpg)

- 尝试过用Collections.sort方法，排序列表的副本得到最大和最小元素，但没能通过

- 需要研究一下Colletions.sort方法对<String>列表的的处理方式和结果

****







# Day03



![Xnip2021-03-28_18-30-36](Algorithem/Xnip2021-03-28_18-30-36.jpg)



![Xnip2021-03-28_18-32-16](Algorithem/Xnip2021-03-28_18-32-16.jpg)

- 第一次发现面向对象的思想有这么好用，对面向对象有了更深的理解
- 这道题用面向过程的方法暂时还真想不出什么合适的解决方法

****







# Day04

![Xnip2021-03-29_18-15-03](Algorithem/Xnip2021-03-29_18-15-03.jpg)





![Xnip2021-03-29_19-38-48](Algorithem/Xnip2021-03-29_19-38-48.jpg)

- 通过遍历的方式先将全部素数挑出
- 再对得到的素数进行一对一对的验证

****





# Day05

![7D7C0794FCCB1E2868F6379BE08E6B56](Algorithem/7D7C0794FCCB1E2868F6379BE08E6B56.jpg)



![Xnip2021-03-30_18-47-10](Algorithem/Xnip2021-03-30_18-47-10.jpg)

- 正则分割简直是神器，不需要再担心怎么处理其他符号了


****





# Day06

![Xnip2021-03-31_11-34-11](Algorithem/Xnip2021-03-31_11-34-11.jpg)



![Xnip2021-03-31_11-32-18](Algorithem/Xnip2021-03-31_11-32-18.jpg)

- 刚开始打算直接用StringBuilder里的reverse方法，结果发现reverse太强大了，处理的最小单位为单个字符，把单词内容都改变了
- 于是使用了昨天用到的split方法，做这类题十分方便

****











# Day07 

![Xnip2021-04-01_11-00-22](Algorithem/Xnip2021-04-01_11-00-22.jpg)



![Xnip2021-04-01_10-59-07](Algorithem/Xnip2021-04-01_10-59-07.jpg)

- char类型通过ASCI转换为int类型后的数值是唯一的，这一特性在标记字符时非常有用


****







# Day08

![6BC16651-A153-4BFD-ABD9-ACD6071A7B40](Algorithem/6BC16651-A153-4BFD-ABD9-ACD6071A7B40.png)





![Xnip2021-04-02_12-46-04](Algorithem/Xnip2021-04-02_12-46-04.jpg)



![Xnip2021-04-02_12-47-53](Algorithem/Xnip2021-04-02_12-47-53.jpg)

****







# Day09

![Xnip2021-04-03_14-06-08](Algorithem/Xnip2021-04-03_14-06-08.jpg)





![Xnip2021-04-03_14-09-06](Algorithem/Xnip2021-04-03_14-09-06.jpg)

- 刚开始没有思路，总想着推导出表达式，最后还是通过遍历尝试的方式做
- 符合添加的数据有多个的话，需要较大的一个，所以需要通过倒序遍历进行测试
- 之前题目读错了，一直报错，以后需要细心

****















# Day10

![Xnip2021-04-04_13-42-49](Algorithem/Xnip2021-04-04_13-42-49.jpg)



## 思路一

![Xnip2021-04-04_13-44-11](Algorithem/Xnip2021-04-04_13-44-11.jpg)



![Xnip2021-04-04_13-45-39](Algorithem/Xnip2021-04-04_13-45-39.jpg)





## 思路二

![Xnip2021-04-04_13-47-13](Algorithem/Xnip2021-04-04_13-47-13.jpg)



![Xnip2021-04-04_13-47-56](Algorithem/Xnip2021-04-04_13-47-56.jpg)

- 题目的意思只是字面上的，实际实现时可以有多种解法，不应该被题目所束缚
- C语言的数组虽然不能扩容，但可以手动分配足够的冗余空间作为缓冲区使用

****







# Day11

![Xnip2021-04-05_10-52-30](Algorithem/Xnip2021-04-05_10-52-30.jpg)





![Xnip2021-04-05_12-03-48](Algorithem/Xnip2021-04-05_12-03-48.jpg)



![Xnip2021-04-05_11-30-10](Algorithem/Xnip2021-04-05_11-30-10.jpg)

- 在处理反转数字时，一定要注意处理溢出
- int中的最小值Integer.MIN很特殊，取绝对值还是本身，且最大值Integer.MAX加一后和它相等，处理时需要注意

****







# Day12

![Xnip2021-04-06_11-11-39](Algorithem/Xnip2021-04-06_11-11-39.jpg)



![Xnip2021-04-06_11-13-30](Algorithem/Xnip2021-04-06_11-13-30.jpg)



![Xnip2021-04-06_11-14-07](Algorithem/Xnip2021-04-06_11-14-07.jpg)

- My idea is to work, but it was to exceed the memory limit.
- My idea doesn't have reusability, even the mark method is good, but it can be used to solve this problem

****











# Day13



![Xnip2021-04-07_11-09-02](Algorithem/Xnip2021-04-07_11-09-02.jpg)

Simple Ver.

![Xnip2021-04-07_11-18-07](Algorithem/Xnip2021-04-07_11-18-07.jpg)



![Xnip2021-04-07_11-29-50](Algorithem/Xnip2021-04-07_11-29-50.jpg)



Awesome Ver.

![Xnip2021-04-07_13-11-16](Algorithem/Xnip2021-04-07_13-11-16.jpg)



![Xnip2021-04-07_14-15-01](Algorithem/Xnip2021-04-07_14-15-01.jpg)

****







# Day14

![331E5FF8-6A99-4267-B6AF-D22D668B01D9](Algorithem/331E5FF8-6A99-4267-B6AF-D22D668B01D9.png)



![Xnip2021-04-08_11-42-38](Algorithem/Xnip2021-04-08_11-42-38.jpg)





![Xnip2021-04-08_11-43-20](Algorithem/Xnip2021-04-08_11-43-20.jpg)









- The core of binary search is to compare the pivot with the boundary value.
- For the rotated array, part of it must be ordered, so we can the sort pattern of the before part and the after part.

****







# Day15

![Xnip2021-04-09_11-20-21](Algorithem/Xnip2021-04-09_11-20-21.jpg)





![Xnip2021-04-09_12-28-16](Algorithem/Xnip2021-04-09_12-28-16.jpg)



![Xnip2021-04-09_11-21-02](Algorithem/Xnip2021-04-09_11-21-02.jpg)





![Xnip2021-04-09_12-26-24](Algorithem/Xnip2021-04-09_12-26-24.jpg)

****





# Day16

![Xnip2021-04-10_18-53-17](Algorithem/Xnip2021-04-10_18-53-17.jpg)



![Xnip2021-04-10_19-01-28](Algorithem/Xnip2021-04-10_19-01-28.jpg)

- It's my first time to know what is "ugly number", so reference some excellent method.

****







# Day17

![Xnip2021-04-11_12-44-16](Algorithem/Xnip2021-04-11_12-44-16.jpg)







![Xnip2021-04-11_12-43-48](Algorithem/Xnip2021-04-11_12-43-48.jpg)





![Xnip2021-04-11_12-53-18](Algorithem/Xnip2021-04-11_12-53-18.jpg)



![Xnip2021-04-11_13-02-24](Algorithem/Xnip2021-04-11_13-02-24.jpg)

- Even I solve it, but my method isn't efficient, and l also stay in traverse all the numbers.
-  l need to analyze the principle before figuring out problems.

***











# Day18



![6BC16651-A153-4BFD-ABD9-ACD6071A7B40](Algorithem/6BC16651-A153-4BFD-ABD9-ACD6071A7B40.jpg)



![Xnip2021-04-12_12-32-10](Algorithem/Xnip2021-04-12_12-32-10.jpg)

- It's my first time using lambda expression in solution, I still can't understand it absolutely now, but can simply use it.

****





# Day19

![Xnip2021-04-13_12-33-36](Algorithem/Xnip2021-04-13_12-33-36.jpg)





![Xnip2021-04-13_12-33-04](Algorithem/Xnip2021-04-13_12-33-04.jpg)



![Xnip2021-04-13_12-42-57](Algorithem/Xnip2021-04-13_12-42-57.jpg)

- Form drive

****









# Day20

![Xnip2021-04-14_10-56-49](Algorithem/Xnip2021-04-14_10-56-49.jpg)



![Xnip2021-04-14_10-56-24](Algorithem/Xnip2021-04-14_10-56-24.jpg)





![Xnip2021-04-14_11-09-57](Algorithem/Xnip2021-04-14_11-09-57.jpg)

****





# Day21

![Xnip2021-04-15_13-16-34](Algorithem/Xnip2021-04-15_13-16-34.jpg)



![Xnip2021-04-15_13-46-56](Algorithem/Xnip2021-04-15_13-46-56.jpg)

****







# Day22

![Xnip2021-04-16_18-37-39](Algorithem/Xnip2021-04-16_18-37-39.jpg)



First method:

![Xnip2021-04-16_18-39-04](Algorithem/Xnip2021-04-16_18-39-04.jpg)





Second method:

![Xnip2021-04-16_18-39-23](Algorithem/Xnip2021-04-16_18-39-23.jpg)





Third method:

![Xnip2021-04-16_18-39-38](Algorithem/Xnip2021-04-16_18-39-38.jpg)

****







# Day23

![Xnip2021-04-17_12-29-26](Algorithem/Xnip2021-04-17_12-29-26.jpg)





![Xnip2021-04-17_12-45-25](Algorithem/Xnip2021-04-17_12-45-25.jpg)

****









# Day24

![Xnip2021-04-18_13-48-10](Algorithem/Xnip2021-04-18_13-48-10.jpg)





![Xnip2021-04-18_16-25-10](Algorithem/Xnip2021-04-18_16-25-10.jpg)

****





# Day25

Day20优化



![Xnip2021-04-19_12-29-14](Algorithem/Xnip2021-04-19_12-29-14.jpg)

****











# Day26

![Xnip2021-04-20_11-19-32](Algorithem/Xnip2021-04-20_11-19-32.jpg)



![Xnip2021-04-20_12-24-35](Algorithem/Xnip2021-04-20_12-24-35.jpg)









