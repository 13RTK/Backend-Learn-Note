# Java Collections Framework

java集合框架: 继承自Collection(一个接口)









# Collection

属于java.util包下，是Iterable接口的子接口



规范的抽象方法:

- boolean add(E e);
- boolean addAll(Collection<? extends E> c);
- void clear();
- boolean contains(Object o);
- boolean containsAll(Collection<?> c);
- boolean equals(Object o);
- int hashCode();
- boolean isEmpty();
- Iterator<E> iterator();
- boolean remove(Object o);
- boolean removeAll(Collection<?> c);
- boolean retainAll(Collection<?> c);
- int size();
- Object[] toArray();
- <T> T[] toArray(T[] a);



- default Stream<E> parallelStream()	
- default boolean removeIf(Predicate<? super E> filter)
- default Spliterator<E> spliterator()
- default Stream<E> stream()













Collection包含三种集合

- 有序列表(ordered list)
- 映射表(maps)
- 集(sets)



Collection的三个子接口：

- List
- Queue
- Set



![Xnip2021-04-14_15-26-43](Java adv/Xnip2021-04-14_15-26-43.jpg)















## 1) List

- 继承自Collections，是一个接口

其下的实现类:

1. ArrayList
2. LinkedList
3. Vector
4. Stack



List接口规范的抽象方法:

- void add(int index, E element);
- boolean add(E e);
- boolean addAll(int index, Collection<? extends E>c);
- boolean addAll(Collection<? extends E> c);
- void clear();
- boolean contains(Object o);
- boolean containsAll(Collection<?> c);
- boolean equals(Object o);
- E get(int index);
- int hashCode();
- int indexOf(Object o);
- boolean isEmpty();
- Iterator<E> interator();
- int lastIndexOf(Object o);



- E remove(int index);
- boolean remove(Object o);
- boolean removeAll(Collection<?> c);
- boolean retainAll(Collection<?> c);
- E set(int index, E element);
- int size();
- List<E> subList(int fromIndex, int toIndex);
- Object[] toArray;
- <T> T[] toArray(T[] a);

























### 1.ArrayList

- 数组集合，是List的实现类，属于java.util包下，比起传统数组，其不需要考虑如何扩容的问题
- 在查找和修改指定位置的元素时非常方便(可根据索引值快速定位)





**注意：**"E"为泛型，指代任何类型的对象



相关方法

构造方法:

- ArrayList(): 创建一个空的列表，并初始化容量为10
- ArrayList(int initialCapacity): 创建一个空的对象集合，并指定其初始化容量(未指定时默认为10)
- ArrayList(Collection <? extends E> c): 接收一个Collection对象，并以此创建一个新的ArrayList集合对象



实例方法:



#### 增加

- void add(E e): 将**与泛型类型相同**的对象/值添加到列表中
- boolean add(int index, E e): 将对象/值添加到指定的索引位置，**如果指定的索引位置超出了列表范围，则添加失败**
- boolean addAll(Collection<? extends E> c): 接收一个Collection实现类对象，**将其添加到调用集合之后**
- boolean addAll(int index, Collection<? extends E> c): 将参数集合**添加到指定的索引处**，不得超出原列表的范围



![Xnip2021-05-18_12-50-40](Java adv/Xnip2021-05-18_12-50-40.jpg)



![Xnip2021-05-18_12-53-08](Java adv/Xnip2021-05-18_12-53-08.jpg)









#### 删除

- void clear(): 清空列表
- E remove(int index): 移除指定位置的元素，并**返回移除的元素**
- boolean remove(Object o): 移除指定的元素，**返回移除结果**
- boolean removeAll(Collection<?> c): **移除其中的一个子列表**

![Xnip2021-05-18_12-58-27](Java adv/Xnip2021-05-18_12-58-27.jpg)



![Xnip2021-05-18_13-00-21](Java adv/Xnip2021-05-18_13-00-21.jpg)









#### 修改

- void replaceAll(): 含新特性，暂时不用
- E set(int index, E element): 将**指定索引位置**的元素**替换为指定的元素**



![Xnip2021-05-18_13-04-31](Java adv/Xnip2021-05-18_13-04-31.jpg)













#### 查找

- boolean contains(Object o): 在列表中**查找指定元素**，**返回查找结果**
- E get(int index): 获取**指定索引处的**元素，**返回该元素**
- int indexOf(Object o): 返回指定元素**在列表中第一次出现的索引值**，**没有找到则返回-1**
- int lastIndexOf(Object o): 返回指定元素**在列表中最后一次出现的索引值**，**没有找到则返回-1**
- boolean retainAll(Collection<?> c): 取调用列表和参数列表中的**交集元素，并都写入到调用列表中**
- List<E> subList(int fromIndex, int toIndex): 指定起始和结束位置，**将范围内的元素写入并返回为一个List集合**[from, to)



![Xnip2021-05-18_13-08-17](Java adv/Xnip2021-05-18_13-08-17.jpg)



![Xnip2021-05-18_13-10-57](Java adv/Xnip2021-05-18_13-10-57.jpg)



![Xnip2021-05-19_08-46-07](Java adv/Xnip2021-05-19_08-46-07.jpg)















#### 其他

- Object clone(): 克隆并返回一个**Object对象**
- void ensureCapacity(int minCapacity): 限定ArrayList的的**起始容量**(防止浪费)
- isEmpty(): 判断该集合**是否为空**
- Iterator<E> iterator(): 返回一个该集合的**迭代器**(用于遍历集合)



- int size(): 返回该集合的长度
- Object[] toArray(): 将该集合返回为一个**Object数组**
- <T> T[] toArray(T[] a): 接收一个泛型数组，将该集合**返回为一个泛型数组**
- void trimToSize(): 将该集合的容量修改为当前的元素数量(**应当在对列表对象操作结束后使用**)

![Xnip2021-05-18_13-16-56](Java adv/Xnip2021-05-18_13-16-56.jpg)



![Xnip2021-05-18_15-56-50](Java adv/Xnip2021-05-18_15-56-50.jpg)



![Xnip2021-05-19_08-40-22](Java adv/Xnip2021-05-19_08-40-22.jpg)













**对于对象：**如果将泛型指定为某个对象，且改对象重写了toString()方法，那么输出ArrayList对象时可以通过toString的方式输出集合中的对象



exam:

![Xnip2021-05-18_16-00-18](Java adv/Xnip2021-05-18_16-00-18.jpg)









不推荐的方法(不安全):

![Xnip2021-04-14_21-18-52](Java adv/Xnip2021-04-14_21-18-52.jpg)











目前常用的方式(泛型限定)

![Xnip2021-04-14_21-24-39](Java adv/Xnip2021-04-14_21-24-39.jpg)

****





















### 2.LinkedList

- 属于java.util下，是List的实现类，使用链式存储方式
- 在增加和删除时比较方便(只需要改变两个节点的关系，不需要像数组那样移动之后的所有元素)






相关方法



构造方法:

- LinkedList(): 创建一个空的集合
- LinkedList(Collection <? extends E> c): 接收一个Collection对象，并**以此创建一个新的LinkedList集合对象**





实例方法:



#### 增加

- void add(int index, E element): 在**指定的索引位置**，插入指定的元素
- boolean add(E e): **在末尾**插入一个元素，并返回插入成功与否的结果
- boolean addAll(int index, Collection<? extends E> c): 在调用对象的**指定索引位置**，插入一个Collection集合
- boolean addAll(Collection<? extends E> c): 在调用对象后面插入一个Collection集合
- void addFirst(E e): 将一个元素插入到**调用列表的开头**
- void addLast(E e): 将一个元素插入到**调用列表的末尾**



- void push(E e): 将一个元素压入该链表集合**模拟的栈中**



- boolean offer(E e): 将一个元素添加至链表集合的末尾
- boolean offerFirst(E e): 将一个元素添加至链表集合的开头
- boolean offerLast(E e): 将一个元素添加到链表聚合的末尾





![Xnip2021-05-19_09-21-27](Java adv/Xnip2021-05-19_09-21-27.jpg)





![Xnip2021-05-19_09-23-10](Java adv/Xnip2021-05-19_09-23-10.jpg)















#### 删除

- void clear(): 清空链表集合
- E remove(): 移除链表集合的**头元素**
- E remove(int index): 移除**指定索引位置**的元素
- boolean remove(Object o): 移除指定的元素，返回移除成功与否的结果
- E removeFirst(): 移除第一个元素，返回移除的元素
- E removeLast(): 移除最后一个元素，返回移除的元素

![Xnip2021-05-19_09-25-49](Java adv/Xnip2021-05-19_09-25-49.jpg)



![Xnip2021-05-19_09-26-55](Java adv/Xnip2021-05-19_09-26-55.jpg)













#### 删除与查找

- E poll(): **查找并移除**链表集合中的第一个元素，并返回该元素
- E pollFirst(): 查找并移除链表集合中的第一个元素，并返回该元素，对象为空则返回null
- E pollLast(): 查找并移除链表集合中的最后一个元素，并返回该元素，对象为空则返回null
- E pop(): 弹出一个链表集合**模拟的栈中**的元素，并返回该元素

![Xnip2021-05-19_09-32-24](Java adv/Xnip2021-05-19_09-32-24.jpg)















#### 修改

- int set(int index, E element): 将**指定索引位置**的元素，修改为指定的值







#### 查找

- boolean contains(Object o): 在链表集合中查找指定的元素，**返回查找的结果**
- E element(): **查找并返回**链表集合的头元素
- E get(int index): 查找并返回指定索引位置的元素
- E getFirst(): 获取第一个元素
- E getLast(): 获取最后一个元素
- int indexOf(Object o): 获取指定元素**第一次出现**在集合中的索引，**如未找到则返回-1**
- int lastIndexOf(Object o): 获取指定元素**最后一次**出现在集合中的索引，**如未找到则返回-1**



- E peek(): 返回列表中的头元素
- E peekFirst(): 返回列表的第一个元素
- E peekLast(): 返回列表的最后一个元素



![Xnip2021-05-19_09-36-00](Java adv/Xnip2021-05-19_09-36-00.jpg)



![Xnip2021-05-19_09-39-14](Java adv/Xnip2021-05-19_09-39-14.jpg)





















#### 其他

- Object clone(): 复制该集合并返回一个**Object对象**
- Object[] toArray(): 将该集合返回为一个**Object数组**
- <T> T[] toArray(T[] a): 将该集合返回一个**泛型数组**



![Xnip2021-05-19_09-43-04](Java adv/Xnip2021-05-19_09-43-04.jpg)

****













### 3.Stack

- 属于java.util包下，用于创建和模拟栈，继承了Vector类





相关方法



构造方法:

Stack(): 创建一个空的栈





实例方法:



#### 增加

- E push(E item): 将一个元素压到栈顶，并返回其值



#### 删除

- E pop(): 将栈顶的元素弹出，并返回其值



#### 查找

- E peek(): 返回栈顶的元素
- int search(Object o): 搜索参数在栈中的位置，返回从栈顶(1)开始的位置



#### 其他

- boolean empty(): 判断该栈对象是否为空





exam:

![Xnip2021-05-20_14-40-53](../../Algorithem/Algorithem/Xnip2021-05-20_14-40-53.jpg)

****







### 4.Vector

- 属于java.util包下，是List的实现类，用于创建和处理可增长对象数组(即不用考虑容量)









相关方法

(与ArrayList大致相同)

****













### 5.Iterator

- 属于java.util包下，用于遍历任何一个Collection对象(及其子类、实现类)





#### 使用方式

- 通过任何一个Collection对象调用Iterator()方法，获取一个iterator对象
- 通过while循环，输出遍历的值，获取通过remove()在遍历中删除值





#### 相关方法



实例方法

- boolean hasNext(): 判断**下一位置**是否还有元素(常作为while循环的条件)
- E next(): 返回下一位元素
- default void remove(): 删除"next()"对应的元素





exam:

![Xnip2021-05-20_15-04-26](../../Algorithem/Algorithem/Xnip2021-05-20_15-04-26.jpg)

















#### 优缺点



- 传统数组:

遍历时可以读取也可以修改，但需要索引值和数组长度



- for each增强for循环


**只适用于读取**，不能修改(对于基本类型)，其不需要索引值和数组长度

**注：其本身也是一个小型的迭代器**



- iterator迭代器

可以读取，**常用于集合修改**，但**删除时只能调用迭代器的方法，不能使用原集合的remove()方法**



使用原集合的remove()方法会抛出异常:

exam:

![Xnip2021-05-23_16-28-10](Java adv/Xnip2021-05-23_16-28-10.jpg)





不应该嵌套使用迭代器

exam:

![Xnip2021-05-23_16-34-58](Java adv/Xnip2021-05-23_16-34-58.jpg)













#### 优先使用场景

- 对于顺序存储结构:

fori, for each, iterator三者性能差不多



- 对于链式存储结构:

for each, iterator性能更好

****













## 2) Set

属于java.util包下，是Collection的继承接口，本身也属于接口



其下的实现类:

1. HashSet
2. LinkedHashSet



子接口:

1. SortedSet: 实现类(TreeSet)





Set接口规范的抽象方法:

- boolean add(E e);
- boolean addAll(Collection<? extends E> c);
- void clear();
- boolean contains(Object o);
- boolean containsAll(Collection<?> c);
- boolean equals(Object o);
- int hashCode();	
- boolean isEmpty();	
- Iterator<E> iterator();
- boolean remove(Object o);
- boolean removeAll(Collection<?> c);	
- boolean retainAll(Collection<?> c);	
- int size();	
- Object[] toArray();	
- <T> T[] toArray(T[] a);



​	默认方法:

- default Spliterator<E> spliterator();









### 1.HashSet

- 用于创建和处理哈希集合，属于java.util包下
- 每个元素有想对应的Hash值，集合中的元素不会重复，**添加和输入都没有顺序**







相关方法:



构造方法:

- HashSet(): 创建一个空的集合对象，该对象的初始容量为16，加载系数为0.75	
- HashSet(int initialCapacity): 创建一个空的集合对象，并指定初始容量，加载系数默认为0.75
- HashSet(int initialCapacity, float loadFactor): 创建一个空的集合对象，指定初始容量和加载系数
- HashSet(Collection<? extends E> c): 以一个Collection对象为参数，创建一个集合对象





实例方法:



#### 增加

- boolean add(E e): 将**与泛型类型相同**的对象/值添加到集合中



#### 删除

- boolean remove(Object o): 移除指定的元素，**返回移除结果**
- void clear(): 清空集合

#### 查询

- boolean contains(Object o): 在列表中**查找指定元素**，**返回查找结果**

![Xnip2021-05-24_15-28-04](Java adv/Xnip2021-05-24_15-28-04.jpg)











#### 其他

- Object clone(): 克隆并返回一个**Object对象**
- boolean isEmpty(): 判断该集合**是否为空**
- Iterator<E> iterator(): 返回一个该集合的**迭代器**(用于遍历集合)
- int size(): 返回该集合的长度

![Xnip2021-05-24_15-32-09](Java adv/Xnip2021-05-24_15-32-09.jpg)



















### 2.LinkedHashSet

- 属于java.util包下，用于创建和处理有序的集合(继承自HashSet)
- 相比HashSet，其输入输出都有顺序





方法:

- 同HashSet







exam:

![Xnip2021-05-30_18-34-57](Java adv/Xnip2021-05-30_18-34-57.jpg)















# Map

- 属于java.util包下，创建和操作映射，是一个接口




![Xnip2021-04-14_15-27-50](Java adv/Xnip2021-04-14_15-27-50.jpg)







规范的抽象方法:

- void  clear();
- boolean containsKey(Object key)
- boolean containsValue(Object value)	
- static <K,V> Map.Entry <K,V> entry(K k, V v);	
- Set<Map.Entry<K,V>>	entrySet()	
- boolean equals(Object o)	
- V get(Object key)	
- int hashCode()
- boolean isEmpty()	
- Set<K> keySet()	
- V put(K key, V value)
- void putAll(Map<? extends K,? extends V> m)
- V remove(Object key)	
- int size()	
- Collection<V> values()









## 1) HashMap

- 属于java.util包下，是Map的一个实现类
- 用于创建和操作哈希映射表





相关方法:



构造方法

- HashMap(): 创建一个空的哈希映射对象，默认容量为16，默认加载参数为0.75
- HashMap(int initialCapacity): 创建一个**指定初始容量大小**的哈希映射集合对象。
- HashMap(int initialCapacity, float loadFactory): 指定初始容量大小和加载参数
- HashMap(Map<? extends K, ? extends V> m): 接收一个Map对象，以此创建一个新的HashMap映射对象





实例方法





### 增加

- V put(K key, V value): 向映射添加键值对
- void putAll(Map<? extends K, ? extends V> m): 将一个映射添加到哈希映射对象中

**注意：通过重复添加的方式可以覆盖某个key对应的值，但不推荐这么做**





exam:

![Xnip2021-06-02_20-39-36](Java adv/Xnip2021-06-02_20-39-36.jpg)









### 删除

- V remove(Object key): 根据键，**删除对应的键值对**，并返回删除的结果
- boolean remove(Object key, Object value): 输出指定的键值对，返回删除的结果
- void clear(): 清空映射对象



exam:

![Xnip2021-06-02_20-43-08](Java adv/Xnip2021-06-02_20-43-08.jpg)









### 修改

- V replace(K key, V value): 修改对应键位置上的键值
- boolean replace(K key, V oldValue, V newValue): 将对应键位置上的值，修改为新的值



exam:

![Xnip2021-06-02_20-59-32](Java adv/Xnip2021-06-02_20-59-32.jpg)









### 查询

- boolean containsKey(Object key): 查询指定的键
- boolean containsValue(Object value): 查询指定的值
- V get(object key): 获取对应键的值
- Set<K> keySet(): 获取所有的键，返回一个集合
- int size(): 获取大小
- Collection<V> values(): 获取所有的值，返回一个集合

**注意：如果指定的key不存在，则返回null**







exam:

![Xnip2021-06-02_21-09-13](Java adv/Xnip2021-06-02_21-09-13.jpg)













### 其他

- Object clone(): 获取一个对象的克隆
- boolean isEmpty(): 判断对象是否为空
- Set<Map.Entry<K, V>> entrySet(): 返回一个Set集合，用于获取迭代器遍历



exam:

![Xnip2021-06-02_21-21-17](Java adv/Xnip2021-06-02_21-21-17.jpg)











## 2) Set->Iterator



- 通过entrySet方法获取一个Set集合，该集合的泛型为:Map<Entry<K, V>>



```java
Set<Map.Entry<K, V>> entrySet = hashMap.entrySet();
```







- 再通过该集合获取迭代器

```java
Iterator iterator = entrySet.Iterator();
```



![Xnip2021-06-10_14-49-40](Java adv/Xnip2021-06-10_14-49-40.jpg)















## 3) LinkedHashMap

- 基本同HashMap一致
- 与HashMap的不同:输出时有顺序















