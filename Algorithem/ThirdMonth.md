# Day61



## Tag: Math, String

![Xnip2021-05-25_16-51-13](Algorithem/Xnip2021-05-25_16-51-13.jpg)



![Xnip2021-05-25_16-59-40](Algorithem/Xnip2021-05-25_16-59-40.jpg)

****









# Day62

## Tag: Implement of string



![Xnip2021-05-26_19-40-31](Algorithem/Xnip2021-05-26_19-40-31.jpg)

****











# Day63

## Tag: Implement of KMP

![Xnip2021-05-27_15-23-55](Algorithem/Xnip2021-05-27_15-23-55.jpg)





![Xnip2021-05-27_15-24-35](Algorithem/Xnip2021-05-27_15-24-35.jpg)

****











# Day64

## Tag: Dynamic Programming



![Xnip2021-05-28_18-08-49](Algorithem/Xnip2021-05-28_18-08-49.jpg)



![Xnip2021-05-28_18-36-40](Algorithem/Xnip2021-05-28_18-36-40.jpg)



![Xnip2021-05-28_18-38-38](Algorithem/Xnip2021-05-28_18-38-38.jpg)

****









# Day65

## Tag: Array, Hash



![Xnip2021-05-29_10-29-16](Algorithem/Xnip2021-05-29_10-29-16.jpg)





![Xnip2021-05-29_10-28-30](Algorithem/Xnip2021-05-29_10-28-30.jpg)





![Xnip2021-05-29_10-29-55](Algorithem/Xnip2021-05-29_10-29-55.jpg)

****













# Day66

## Tag: Bit Manipulation, Math



![Xnip2021-05-30_15-41-00](Algorithem/Xnip2021-05-30_15-41-00.jpg)



![Xnip2021-05-30_15-46-44](Algorithem/Xnip2021-05-30_15-46-44.jpg)

****













# Day67

## Tag: Bit Manipulation



![Xnip2021-05-31_14-37-46](Algorithem/Xnip2021-05-31_14-37-46.jpg)



![Xnip2021-05-31_14-37-18](Algorithem/Xnip2021-05-31_14-37-18.jpg)

****

















# Day68

## Tag: Math, String

![Xnip2021-06-01_13-12-25](Algorithem/Xnip2021-06-01_13-12-25.jpg)





![Xnip2021-06-01_13-11-49](Algorithem/Xnip2021-06-01_13-11-49.jpg)

****















# Day69

## Tag: Greedy, Array



![Xnip2021-06-02_13-25-22](Algorithem/Xnip2021-06-02_13-25-22.jpg)



![Xnip2021-06-02_13-25-40](Algorithem/Xnip2021-06-02_13-25-40.jpg)



![Xnip2021-06-02_13-25-52](Algorithem/Xnip2021-06-02_13-25-52.jpg)

****











# Day70

## Tag: Greedy Array



![Xnip2021-06-03_14-08-23](Algorithem/Xnip2021-06-03_14-08-23.jpg)



![Xnip2021-06-03_14-09-00](Algorithem/Xnip2021-06-03_14-09-00.jpg)



![Xnip2021-06-03_14-09-22](Algorithem/Xnip2021-06-03_14-09-22.jpg)

****









# Day71

## Tag: Linked List(Cycle Day43)



![Xnip2021-06-04_17-40-39](Algorithem/Xnip2021-06-04_17-40-39.jpg)



![Xnip2021-06-04_17-41-33](Algorithem/Xnip2021-06-04_17-41-33.jpg)



![Xnip2021-06-04_17-47-27](Algorithem/Xnip2021-06-04_17-47-27.jpg)

****











# Day72

## Tag: Array, Two Pointers



![Xnip2021-06-05_17-53-20](Algorithem/Xnip2021-06-05_17-53-20.jpg)



![Xnip2021-06-05_18-18-55](Algorithem/Xnip2021-06-05_18-18-55.jpg)

****







# Day73

## Tag: Two Pointers, LinkedList



![Xnip2021-06-06_14-25-40](Algorithem/Xnip2021-06-06_14-25-40.jpg)



![Xnip2021-06-06_14-56-51](Algorithem/Xnip2021-06-06_14-56-51.jpg)



![Xnip2021-06-06_14-57-01](Algorithem/Xnip2021-06-06_14-57-01.jpg)

****













# Day74

## Tag: Linked List



![Xnip2021-06-07_09-47-17](Algorithem/Xnip2021-06-07_09-47-17.jpg)



![Xnip2021-06-07_09-59-15](Algorithem/Xnip2021-06-07_09-59-15.jpg)

题意：

将一个链表进行重组，重组后的节点顺序为开头节点->末尾节点->开头节点.next->末尾节点.previous...



思路：

1. 首先通过快慢双指针的方式将链表分为两部分
2. 再将后半部分进行反转
3. 最后再依次交叉将两个链表的每个节点进行重组

****











# Day75

## Tag: String



![Xnip2021-06-08_13-58-42](Algorithem/Xnip2021-06-08_13-58-42.jpg)



![Xnip2021-06-08_14-09-12](Algorithem/Xnip2021-06-08_14-09-12.jpg)



题意:

返回两个参数字符串所表示的数字相加的和，并同样以字符串的方式返回，强调不能使用包装类直接将字符串转换为数值(不然就没意思了)









思路:

- 从两个字符串的末尾位置开始，每次取得当前值(curSum)和下一位的进位(digit)，再把当前值添加到一个StringBuilder对象中
- 当有一个字符串被遍历完成后，再单独检测两个字符串是否有剩余部分，再将剩余部分进行添加
- 最后为了防止"9"+"1"这样的字符串返回为"0"，我们还需要检测digit，将最后一个进位添加进去

****











# Day76

## Tag: Dynamic Programming



![Xnip2021-06-09_10-39-49](Algorithem/Xnip2021-06-09_10-39-49.jpg)



![Xnip2021-06-09_10-53-16](Algorithem/Xnip2021-06-09_10-53-16.jpg)





题意:

题目看起来是获取子数组，其实是从中挑取一些元素，保证这些元素的和最大，并返回最大值





思路:

- 初始化结果值为测试数组的第一个元素，从第二个元素开始遍历
- 每个位置的值，都作为0到当前索引位置处的子数组中的最大求和值，将前一个值与0进行判断，小于0就不添加，将num[i]设为0
- 再将结果值与当前子数组最大求和值对比，以更新结果值

****











# Day77

## Tag: Dynamic Programming, Array



![Xnip2021-06-10_10-35-18](Algorithem/Xnip2021-06-10_10-35-18.jpg)



![Xnip2021-06-10_10-46-08](Algorithem/Xnip2021-06-10_10-46-08.jpg)



![Xnip2021-06-10_10-46-53](Algorithem/Xnip2021-06-10_10-46-53.jpg)





题意:

获取一个连续的子数组，该数组所有元素之和应该是最大的



思路:



方法一: 暴力就完事



- 直接遍历数组，获取元素之和，一次遍历后就将开始的索引位置前移，之后再从更新后的开始位置再次进行遍历，每次遍历后都与curMaxSum比较，最后返回curMaxSum即可

(效率很低，不推荐)







方法二: 动态规划



- 只遍历一次数组，在处理每个元素之前，都判断一下当前的子数组之和curSum
- 如果该元素小于0，则没必要保留其值，直接将其更新为当前索引位置的值即可；如果大于等于0，则将当前索引位置的值加入

该方法总结为: 小于0，只会让之后的正值变小，所以不用保留

****













# Day78

## Tag: Dynamic Programming, Package



![Xnip2021-06-11_12-51-12](Algorithem/Xnip2021-06-11_12-51-12.jpg)



![Xnip2021-06-11_13-33-35](Algorithem/Xnip2021-06-11_13-33-35.jpg)



题意:

给定一个数组作为硬币，再给你一个金额值"amount"，返回能用数组中任意硬币凑成amount所需的最少的硬币数，如无法凑出则返回-1(同一个硬币可以无限次重复使用)







思路:

- 创建一个长度为amount + 1的数组(coinNum)，其索引代表目标金额，索引所在的值为凑齐目标金额所需的硬币数量
- 将其中的每个元素初始化为amount+1，再遍历硬币数组，如果硬币数组中的元素小于当前的目标金额(coinNum的当前索引)，则更新当前的硬币数(coinNum[curAmount])
- 最后将索引为amount的元素与初始值比较，如果相等则说明未被更改，没有凑齐的方法，返回-1；否则返回coinNum[amount]

****











# Day79

## Tag: Array



![Xnip2021-06-12_16-42-39](Algorithem/Xnip2021-06-12_16-42-39.jpg)



![Xnip2021-06-12_16-49-37](Algorithem/Xnip2021-06-12_16-49-37.jpg)







思路:

- 初始化结果数组的长度为参数数组的宽乘高(matrix.length * matrix[0].length)

- 初始化四个边界，再将一次循环拆分为四次取值，每一边结束后就更新对应的边界值，再判断是否已经到达最后位置

****









# Day80

## Tag: Binary Search



![Xnip2021-06-13_15-34-29](Algorithem/Xnip2021-06-13_15-34-29.jpg)



![Xnip2021-06-13_15-34-43](Algorithem/Xnip2021-06-13_15-34-43.jpg)

****













# Day81

## Tag: StackTrackElement



![Xnip2021-06-14_14-14-22](Algorithem/Xnip2021-06-14_14-14-22.jpg)





![Xnip2021-06-14_14-14-54](Algorithem/Xnip2021-06-14_14-14-54.jpg)





题意: 

- 在除了main方法的每个方法内(method1, method2...)，返回调用该方法的方法名称(谁调用了我？)



思路:

- 这里需要使用堆栈跟踪StackTraceElement类中的相关知识，简单说明一下StackTraceElement:

堆栈跟踪会以栈的方式存储类和类中的方法之间，调用时的相关信息，最后使用的类以及其方法的信息被存在栈顶



- 具体的使用:

首先通过Thread类获取当前的线程对象，再通过该对象调用getStackTrace()方法，获取一个StackTraceElement对象数组



- 实例:

以题目中的method1为例，我们不难看出，调用它的方法是main，我们通过上述的过程获取一个数组后，可以从数组中获取到对应的对象。

前面说过: 以栈的方式存储被调用的方法，所以如果以数组中的第一个元素(索引为0)为准，使用getMethodName()方法，那么获取的值为最后一次使用的"getStackTrace()"，使用getClassName()方法则会获取类名"Thread"

这时往后推一位，以第二个元素为准(索引为1)，其对应的方法名和类名即为"method1()"和"Solution"



最后再往后推一位，即为我们所需要的(索引为2)，其类名和方法名为"Solution"和"main()"

****











# Day82

## Tag: Binary Search, Map



![Xnip2021-06-15_10-33-34](Algorithem/Xnip2021-06-15_10-33-34.jpg)



![Xnip2021-06-15_10-36-10](Algorithem/Xnip2021-06-15_10-36-10.jpg)



![Xnip2021-06-15_10-44-34](Algorithem/Xnip2021-06-15_10-44-34.jpg)



![Xnip2021-06-15_11-04-30](Algorithem/Xnip2021-06-15_11-04-30.jpg)





题意:

程序会从键盘接收十次输入，这十次输入作为字符串保存在一个字符串集合中，现在给你一个包含26个字母的字符集合，想办法计算出输入字符集合中每种字符的数量，并以指定的形式输出





思路一: 暴力

- 直接创建一个容量为26的int数组charCounter，用于记录对应字符的数量，遍历字符串集合，再对集合中的每个字符串的每个字符进行遍历，每个字符再和遍历包含26字母的结果进行比较，成功匹配后便将"charCounter"对应索引位置的值自增
- 最后输出同一索引位置的"charCounter"和"alphabet"的元素

不推荐，假设共输入了n个字符，每个字符最坏需要匹配26次，复杂度为O(n的26次方)！



思路二: 二分

- 将每个字符与"alphabet"匹配的过程改为二分查找即可

复杂度为O(n的log n次方)





思路三: map映射

- 题目要求输出每个字母和其出现的次数，很容易就想到map中的键值对
- 将键设为26个字母，再将对应的值自增即可

****















# Day83

## Tag: Dynamic Programming, Recursive



![Xnip2021-06-16_14-16-38](Algorithem/Xnip2021-06-16_14-16-38.jpg)











题意:

给你一个m*n的网格，从最左上方开始走，有多少种走法可以到达最右下角?





思路:

- 观察归纳一下: 对于从每一个位置开始，到右下角的走法，都可以拆分成其下一行和右一列两个位置处到右下角的走法，这是类似二叉树的结构，于是很容易想到递归，但单纯的递归会超时



- 仔细分析一波，发现每次拆分后的两个部分中有些位置会被重复计算，于是又想到可以记录下位置是否已经被计算，从而避免重复(同Algorithm Day 64)
- 记录网格可以使用二维数组，又因为每个位置到右下角的方法都>=0，所以将用于记录的数组元素初始化为负数，通过在每次拆分递归前，都检测一下下一行/列位置对应的记录数组元素是否负，从而避免重复计算

****









# Day84

## Tag: Factory Pattern



![Xnip2021-06-17_11-19-40](Algorithem/Xnip2021-06-17_11-19-40.jpg)





![Xnip2021-06-17_11-20-10](Algorithem/Xnip2021-06-17_11-20-10.jpg)







题意:

题目指明了要使用getCatByKey()方法来创建Cat对象，而其在静态类CatFactory中，所以直接通过类进行调用而不需创建一个CatFactory实例对象







工厂设计模式:

平时想要创建对象时，都是调用对应类的构造方法，需要区分每个类，比较麻烦，使用工厂设计模式则只需要调用一个方法即可，具体创建的对象类型由参数决定

****











# Day85

## Tag: Factory Pattern



![Xnip2021-06-18_22-50-21](Algorithem/Xnip2021-06-18_22-50-21.jpg)







![Xnip2021-06-18_22-46-37](Algorithem/Xnip2021-06-18_22-46-37.jpg)

****









# Day86

## Tag: max common divisor



![Xnip2021-06-19_11-22-22](Algorithem/Xnip2021-06-19_11-22-22.jpg)



![Xnip2021-06-19_11-21-31](Algorithem/Xnip2021-06-19_11-21-31.jpg)

- 相减就完事

****















# Day87

## Tag: Java Thread



![Xnip2021-06-20_12-19-24](Algorithem/Xnip2021-06-20_12-19-24.jpg)



![Xnip2021-06-20_12-38-25](Algorithem/Xnip2021-06-20_12-38-25.jpg)

- join(): 只执行当前线程，其他线程需要等待其运行结束



















