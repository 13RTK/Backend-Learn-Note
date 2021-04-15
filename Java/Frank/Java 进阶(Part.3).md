# 1.Java Collections Framework

java集合框架: 继承自Collection(一个接口)





Collection包含三种集合

- 有序列表(ordered list)
- 映射表(maps)
- 集(sets)



Collection的三个继承接口：

- List
- Queue
- Set



![Xnip2021-04-14_15-26-43](Java adv/Xnip2021-04-14_15-26-43.jpg)













maps下有一个Map类

![Xnip2021-04-14_15-27-50](Java adv/Xnip2021-04-14_15-27-50.jpg)

















## 1) List

- 继承自Collections





### 1.ArrayList

- 属于java.util包下，比起传统数组，其不需要考虑如何扩容的问题





**注意：**"E"为泛型，指代任何类型的对象



相关方法



构造方法:

- ArrayList(): 创建一个空的列表，并初始化容量为10





实例方法:

- add(E e): 将**与泛型类型相同**的对象/值添加到列表中
- add(int index, E e): 将对象/值添加到指定的索引位置



**对于对象：**如果将泛型指定为某个对象，且改对象重写了toString()方法，那么输出ArrayList对象时可以通过toString的方式输出集合中的对象









不推荐的方法(不安全):

![Xnip2021-04-14_21-18-52](Java adv/Xnip2021-04-14_21-18-52.jpg)











目前常用的方式(泛型限定)

![Xnip2021-04-14_21-24-39](Java adv/Xnip2021-04-14_21-24-39.jpg)

















