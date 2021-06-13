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











