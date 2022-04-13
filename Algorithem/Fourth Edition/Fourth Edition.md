# 一、并查集(Union-Find)

设计算法解决问题的步骤:

- 对问题建模
- 寻找对应的算法
- 判断运行时间和内存占用
- 如果不符合要求则找出原因
- 找到定位问题的方法
- 对程序迭代直到满足要求

<hr>



## 1. 动态连通性(dynamic-connectivity)

要求:

给你包含N对象的集合，要求首先两个方法:

- Union: 连接两个对象
- Find/Connected query: 判断两个对象是否连接(间接/直接)





连接是一种等价关系，其具有三个特性:

- 自反性(reflexive): p和p本身是连接的
- 对称性(symmetric): 如果p连接了q，那么q也连接了p
- 传递性(transitive): 如果p连接了q并且q连接了r，那么p连接了r



在一个集合里的所有对象都是相互连接的，这样的一个集合称为连通分量(connected component)

Eg:

![Xnip2022-03-19_21-18-25](Algorithm Fourth.assets/Xnip2022-03-19_21-18-25.jpg)





我们的目标程序:

- 编写一个Union-Find类
- 实现一个构造方法和四个成员方法

```java
public class UF

// 构造函数，将N个对象传入一个数据结构中
UF(int N) 

// 连接p和q两个对象
void union(int p, int q)

// 判读这两个对象是否彼此连接
boolean connected(int p, int q)
  
// 查找对应对象连通分量的标识符
int find(int p)
  
// 返回当前连通分量的个数
int count()
```



Eg:

![Xnip2022-03-19_21-24-16](Algorithm Fourth.assets/Xnip2022-03-19_21-24-16.jpg)

<hr>









## 2. 快速查找(quick find)





### 1) 分析



数据结构:

- 一个长度为N的整数数组用来存放对象
- 如果两个对象彼此连接，那么它们就有相同的元素值

Eg:

![Xnip2022-03-19_21-27-10](Algorithm Fourth.assets/Xnip2022-03-19_21-27-10.jpg)



查找:

- 检查两个对象对应的数组元素是否相同



连接:

- 将两个分量合并在一起，将整个集合中id[p]改为id[q]



Eg:

![Xnip2022-03-19_21-29-23](Algorithm Fourth.assets/Xnip2022-03-19_21-29-23.jpg)

<hr>











### 2) Java实现

Eg:

```java
public class QuickFindUF {
    private int[] id;

    public QuickFindUF(int n) {
        id = new int[n];

        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }
}
```

<hr>











### 3) 消耗分析

复杂度分析:

| algorithm  | initialize | union | find/connected |
| :--------: | :--------: | :---: | :------------: |
| quick-find |     N      |   N   |       1        |



- 其中连接操作的用时消耗太大: 它通常是平方级别的(两个大小为N的集合彼此连接)

<hr>














## 3. 快速合并(quick union)

### 1) 分析

- 在调用union方法时，将第一个参数连接到第二个参数所在树的根节点上
- 通过修改对象所在数组中对应位置处对应的元素值即可将其模拟为在同一颗树上

Eg:

![Xnip2022-03-20_21-41-23](Algorithm Fourth.assets/Xnip2022-03-20_21-41-23.jpg)

<hr>











### 2) Java实现

```java
public class QuickUnionUF {
  public int[] id;

  public QuickUnionUF(int n) {
    id = new int[n];

    for (int i = 0; i < id.length; i++) {
      id[i] = i;
    }
  }

  private int getRoot(int idx) {
    while (idx != id[idx]) {
      idx = id[idx];
    }

    return idx;
  }

  public void union(int p, int q) {
    int pRoot = getRoot(p);
    int qRoot = getRoot(q);

    id[pRoot] = qRoot;
  }

  public boolean connected(int p, int q) {
    int pRoot = getRoot(p);
    int qRoot = getRoot(q);

    return pRoot == qRoot;
  }
}
```





![Xnip2022-03-20_22-02-10](Algorithm Fourth.assets/Xnip2022-03-20_22-02-10.jpg)

<hr>









### 3) 消耗

- 比起Quick Find，Quick Union在union操作上其实没好多少，只不过把遍历数组变为了回溯整棵树而已
- 对应的，其在connected上的用时反而上升到了N

|  algorithm  | initialize | union | find/connected |
| :---------: | :--------: | :---: | :------------: |
| quick-Union |     N      |   N   |       N        |

<hr>









## 4. 快速合并优化



### 1) 加权快速合并



#### 1. 理论

- 为了解决union操作后，树太高的问题，我们可以想办法在连接时让"矮"的树接在"高"的树的根节点上
- 这里我们只需要再多为每个位置上的节点维护一个数组代表其所在树对应的高度即可

![Xnip2022-03-21_21-16-05](Algorithm Fourth.assets/Xnip2022-03-21_21-16-05.jpg)







#### 2. 设计/Java实现

- 额外维护一个size数组，其中每个元素代表其位置的对象作为根节点时树中节点的数量
- 在union操作时，首先判断两个相连节点对应的根节点的树的节点数量，确保将矮的树接在高的树上，并且更新连接后的树的高度

![Xnip2022-03-21_21-20-24](Algorithm Fourth.assets/Xnip2022-03-21_21-20-24.jpg)



Code:

```java
public class WeightedQuickUnionUF {
    public int[] id;
    public int[] size;

    public WeightedQuickUnionUF(int n) {
        id = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    private int root(int idx) {
        while (idx != id[idx]) {
            idx = id[idx];
        }

        return idx;
    }

    public boolean connected(int q, int p) {
        return root(q) == root(p);
    }

    public void union(int q, int p) {
        int qRoot = root(q);
        int pRoot = root(p);

        if (qRoot == pRoot) {
            return;
        }

        if (size[qRoot] > size[pRoot]) {
            id[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        } else {
            id[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }
    }
```



Eg:

![Xnip2022-03-21_22-09-05](Algorithm Fourth.assets/Xnip2022-03-21_22-09-05.jpg)













#### 3. 消耗分析

因为每次合并的时候，树中节点的数量至少翻倍(因为两个树合并时其中一颗树的节点数至少会≥它)，又因为节点一共有n个，所以当节点数翻倍logn次后，树的节点刚好为N，此时所有的节点都在一颗树中了

所以每次union的消耗为logn(以2为底的对数)，同样的，因为root操作也将为了logn，所以connected操作也降为了logn

|      algorithm      | initialize | union | find/connected |
| :-----------------: | :--------: | :---: | :------------: |
| weighted-quick-find |     N      | logn  |      logn      |

<hr>











### 2) 压缩路径



#### 1. 理论

- 在获取root的过程中，我们可以将每次检查过的节点直接连在根节点上从而进一步压缩树的高度

Eg:

![Xnip2022-03-21_22-01-30](Algorithm Fourth.assets/Xnip2022-03-21_22-01-30.jpg)









#### 2. 实现

- 只需要在root方法中添加一行即可

Eg:

![Xnip2022-03-21_22-03-13](Algorithm Fourth.assets/Xnip2022-03-21_22-03-13.jpg)

<hr>











#### 3. 消耗分析

|     algorithm      | initialize | union | find/connected |
| :----------------: | :--------: | :---: | :------------: |
| UF + Path Compress |     N      | logn  |      logn      |

<hr>









## 5. 消耗总结

|      algorithm      | initialize | union | find/connected |
| :-----------------: | :--------: | :---: | :------------: |
|     quick-find      |     N      |   N   |       1        |
|     quick-union     |     N      |   N   |       N        |
| weighted-quick-find |     N      | logn  |      logn      |
| UF + Path Compress  |     N      | logn  |      logn      |

Union-Find不可能设计出线性复杂度的算法，但在快速合并的基础上结合加权和路径压缩的话，其实很接近线性复杂度了

<hr>











## 6. 渗透问题以及衍生应用(Percolation)

物理系统模型:

- N * N的矩形方格
- 每个方格开启的概率为P
- 如果顶部到底部是能够通过方格连通的话，那么该系统就是渗透的



![Xnip2022-03-22_20-41-50](Algorithm Fourth.assets/Xnip2022-03-22_20-41-50.jpg)



该问题的现实应用:

- 判断电路是否导通
- 流体分析
- 判断两人是否被连接在同一个社交网络中

![Xnip2022-03-22_20-45-58](Algorithm Fourth.assets/Xnip2022-03-22_20-45-58.jpg)







### 1) 对应的数学问题

当N足够大的时候，如果随机开放矩形，最终开放矩形的百分比到达一个阈值P时，该系统几乎是导通的，问题是这个阈值是多少?

![Xnip2022-03-22_20-49-36](Algorithm Fourth.assets/Xnip2022-03-22_20-49-36.jpg)

<hr>











### 2) 蒙特卡洛模拟

- 为了解决前面的数学问题，这里可以通过蒙特卡洛模拟来计算:
    - 初始化一个N * N的全封闭网格
    - 随机开放网格直到顶部和底部可以连通

**注意：网格有三种状态，即开放(连接顶部)、空(没有连接顶部但开放)和封闭**

![Xnip2022-03-22_20-53-18](Algorithm Fourth.assets/Xnip2022-03-22_20-53-18.jpg)

<hr>













### 3) 利用动态连通性解决渗透问题

创建一个0到n^2^-1的数组，利用加权快速合并进行判断:

- 这种解法需要调用N^2^次Connected方法

![Xnip2022-03-22_20-59-16](Algorithm Fourth.assets/Xnip2022-03-22_20-59-16.jpg)





优化:

- 将顶部和底部的节点都分别接入一个虚拟的头、尾节点
- 这样只需要判断头尾节点是否连通即可

![Xnip2022-03-22_21-01-59](Algorithm Fourth.assets/Xnip2022-03-22_21-01-59.jpg)





- 模拟开启节点的操作:

开启节点后将其与四周的空节点进行连接即可

Eg:

![Xnip2022-03-22_21-03-55](Algorithm Fourth.assets/Xnip2022-03-22_21-03-55.jpg)

<hr>












## 7. 练习



### 1) 渗透问题

实现如下类和其中的方法:

![Xnip2022-03-26_19-42-15](Algorithm Fourth.assets/Xnip2022-03-26_19-42-15.jpg)



示意图:

![Xnip2022-03-26_20-12-06](Algorithm Fourth.assets/Xnip2022-03-26_20-12-06.jpg)



说明:

- 该类的构造方法应该接收一个数字n，以此为基础模拟一个n * n的矩形网格，其中每个网格在初始化的时候都是关闭的
- open方法将参数对应网格变为开启状态
- isOpen方法判断参数对应的网格是否为开启状态
- isFull方法判断参数对应网格是否灌满水(与顶部相连)
- numberOfOpenSites方法返回处于开启状态的网格数
- percolates方法判断当前矩阵是否处于渗透状态(水能否从顶部流到底部)



思路:

- 因为网格大致来说只处于两种状态: 开启和关闭，所以每个网格的状态可以用一个boolean类的值表示
- 相应的，所有的网格则可以用个二维的boolean数组表示
- 那么如何表示网格相同的状态呢？这里我们可以利用之前加权快速合并的内容:
- 如果两个部分的网格相连，则将数量小的连接到数量大的根节点处(更新对应的数组元素为较大树的根节点值)，具体的实现其实只需要使用jar包中为我们提供的WeightedQuickUnionUF类即可
- 但观察这个类的源码我们会发现一个问题: 它是基于一维数组的呀，我们用来表示状态的boolean数组是个二维的呀
- 所以这里涉及到二维数组到一维数组索引的转换，这里我们将其抽象为一个方法: mapTwoDToOneD



- 有了上面的前提后，我们再看isFull方法，如果要判断是否灌满水的话就需要判断当前网格是否与顶部相连，但顶部有n个格子呀，难道要一一判断？
- 同样的，判断是否相通的时候，难道我们也要将底部的n个格子与顶部的n个格子一一对应？
- 其实之前的percolation优化里就提到了解决思路: 即创建一个虚拟头节点作为第一行节点的父节点，再创建一个虚拟尾节点作为最后一行的父节点
- 这样一来，在判断是否灌满时，只需要判断网格所在树的根节点是否与虚拟头节点相连即可，判断是否相通时也只需要判断头尾节点是否相连即可



- 至此，我们解决了isOpen，isFull和percolates方法，至于numberOfOpenSites方法，其实只需要在每次调用open方法打开一个网格时递增一个全局变量即可，最后让numberOfOpenSites返回这个变量
- 最后就是open方法了。对于每个二维坐标，首先需要判断当前坐标是否合法
- 之后再判断其是否已经打开了，如果在操作前就已经打开了，那么便不再需要之后的操作了
- 如果未打开，则将其对应的boolean元素设置为true，之后就需要考虑其与周围元素相连的问题了
- 如果周围有开启的元素，则两者在一维中的元素进行union操作即可(由WeightedQuickUnionUF类实现)



- 最后总结一下构造方法: 创建一个n * n的二维boolean数组代表每个网格对应的状态
- 创建一个WeightedQuickUnionUF实例，传入n * n + 2(多两个虚拟节点)
- 创建一个全局变量用于统计开启的网格数
- 实际操作中，因为要判断边界，所以我们还需要一个变量存储边界n，因为虚拟节点经常被使用，所以我们还需要存储头尾节点的索引值

Code:

![Xnip2022-03-26_20-11-13](Algorithm Fourth.assets/Xnip2022-03-26_20-11-13.jpg)

<hr>











### 2) 蒙特卡洛模拟

实现如下类:

![Xnip2022-03-26_20-13-24](Algorithm Fourth.assets/Xnip2022-03-26_20-13-24.jpg)

我们需要根据该类计算出渗透阈值



计算公式如图:

![Xnip2022-03-26_20-13-33](Algorithm Fourth.assets/Xnip2022-03-26_20-13-33.jpg)

其中$\overline{x}$代表样本平均数，其所取的样本为每次计算的结果处以次数

S^2^代表标准差的平方

其中样本平均数还存在上下两个偏移量值

最终结果我们应该在main方法中输出样本平均数、标准差和渗透阈值的上下两个偏移量:

![Xnip2022-03-26_20-19-25](Algorithm Fourth.assets/Xnip2022-03-26_20-19-25.jpg)



说明:

- 其中main方法接受两个参数，第一个是矩形的边界n，一个是实验的次数
- PercolationStats构造方法接收上述的两个参数
- mean方法返回我们所需的方差
- stddv方法返回标准差
- confidenceHi返回渗透阈值的上边界
- confidenceLo返回渗透阈值的下边界



思路:

- 其中每次的实验其实可以由前面一道题目的Percolation类来完成，只需要随机获取一个二维坐标并开放对应的网格直到上下相通为止
- 最后返回相通时开启网格的数量 / 网格总数，这就是一次实验的结果了
- 又因为有多次结果，所以我们需要创建一个对应长度的数组来存放每次的结果
- 其中mean和stddev其实在jar包里有对应的实现，我们直接使用即可
- 通过这两个值其实就能很轻松的在confidenceHi和confidenceLo方法中获取两个边界值了
- 最后在main方法中获取两个命令行参数，调用构造方法后输出我们需要的值即可



Code:

![Xnip2022-03-26_20-31-54](Algorithm Fourth.assets/Xnip2022-03-26_20-31-54.jpg)

<hr>







# 二、栈与队列

栈拥有的方法:

- isEmpty: 栈是否为空
- push将元素压入栈
- pop弹出栈顶元素





## 1. 栈的简单实现



### 1) 使用链表实现字符串栈



1. isEmpty:

判断指向栈顶的节点是否为null

```java
public boolean isEmpty() {
  return topNode == null;
}
```





2. push:

将元素压入栈

- 这里我们将栈顶元素更新为新的链表头节点

```java
public void push(String item) {
  Node newNode = new Node();
  nextNode.item = item;
  
  newNode.next = topNode;
  topNode = newNode;
}
```





3. pop:

返回指向栈顶的节点对应的值

- 首先获取栈顶元素，再更新栈顶元素对应的节点

```java
public String pop() {
  String popString = topNode.item;
  topNode = topNode.next;
  
  return popString;
}
```



Code:

```java
public class LinkedStackOfStrings {
    Node topItem = null;

    private static class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return topItem == null;
    }

    public void push(String item) {
        Node curTopItem = new Node();
        curTopItem.item = item;

        curTopItem.next = topItem;
        topItem = curTopItem;
    }

    public String pop() {
        String curTopItem = topItem.item;

        topItem = topItem.next;
        return curTopItem;
    }
}
```

<hr>











### 2) 使用数组实现字符串栈

缺陷：需要预先指定数组的长度



1. isEmpty:

- 判断索引是否指向0

```java
public boolean isEmpty() {
  return index == 0;
}
```



2. push:

- 将入栈元素添加到数组对应的位置中

```java
public void push(String item) {
  array[index] = item;
  index++;
}
```



3. pop:

- 首先递减index，然后返回index指向的数组元素

```java
public String pop() {
  index--;
  return array[index];
}
```



对pop的改进:

- 我们暂时只是更新了对应的索引值，但数组中的元素还依然存在且占用空间，所以我们需要手动地将弹出的元素设置为null才能节省空间:

```java
public String pop() {
  index--;
  String popString = array[index];
  array[index] = null;
  
  index--;
  return popString;
}
```



Code:

```java
public class FixedCapacityStackOfStrings {
    int idx = 0;
    String[] array;

    public FixedCapacityStackOfStrings(int capacity) {
        this.array = new String[capacity];
    }

    public void push(String item) {
        array[idx] = item;
        idx++;
    }

    public String pop() {
        idx--;
        String topItem = array[idx];
        array[idx] = null;

        return topItem;
    }

    public boolean isEmpty() {
        return idx == 0;
    }
}
```

<hr>













## 2. 修改数组大小



### 1) grow

- 在push上进行修改
- 如果当前索引值等于数组长度，则扩容为原来的两倍

Code:

```java
public void push(String item) {
  if (index == array.length) {
    resizeArray(array.length * 2);
  }
  
  array[index] = item;
  index++;
}
```



resize方法:

```java
private void resizeArray(int capacity) {
  String[] newArray = new String[capacity];

  for (int i = 0; i < idx; i++) {
    newArray[i] = strArray[i];
  }

  strArray = newArray;
}
```

<hr>









### 2) shrink

- 参照grow的思路，如果index为数组长度 / 2，则减少长度
- 但这样做的话，如果反复进行push和pop的话，就会反复调用resize方法:

![Xnip2022-04-01_10-42-58](Algorithm Fourth.assets/Xnip2022-04-01_10-42-58.jpg)





- 为了避免这种情况，我们将阈值调整为length / 4

Code:

```java
public String pop() {
  idx--;
  String popString = strArray[idx];
  strArray[idx] = null;

  if (idx == strArray.length / 4) {
    resizeArray(strArray.length / 2);
  }

  return popString;
}
```







ResizingArrayOfStackStrings实现Code:

```java
public class ResizingArrayStackOfStrings {
    String[] strArray;
    int idx = 0;

    public ResizingArrayStackOfStrings() {
        this.strArray = new String[1];
    }

    public boolean isEmpty() {
        return idx == 0;
    }

    public void push(String item) {
        if (idx == strArray.length) {
            resizeArray(strArray.length * 2);
        }

        strArray[idx] = item;
        idx++;
    }

    public String pop() {
        idx--;
        String popString = strArray[idx];
        strArray[idx] = null;

        if (idx == strArray.length / 4) {
            resizeArray(strArray.length / 2);
        }

        return popString;
    }

    private void resizeArray(int capacity) {
        String[] newArray = new String[capacity];

        for (int i = 0; i < idx; i++) {
            newArray[i] = strArray[i];
        }

        strArray = newArray;
    }
}
```

<hr>











## 3. 队列



### 1) 用链表实现



- isEmpty

通过头节点判断

```java
public boolean isEmpty() {
  return this.first = null;
}
```



- enqueue

将元素入队，尾插法

```java
public void enqueue(String item) {
  Node oldLast = this.last;
  last = new Node();
  last.item = item;
  
  if (this.isEmpty()) {
    this.first = this.last;
  } else {
    oldLast.next = this.last;
  }
}
```



- dequeue

队头元素出队

```java
public String dequeue() {
  String item = first.item;
  first = first.next;
  
  if (this.isEmpty()) {
    this.last = null;
  }
  
  return item;
}
```



Code:

```java
public class LinkedQueueOfStrings {
    private static class Node {
        String item;
        Node next;
    }

    Node first;
    Node last;
    int size;

    public void enqueue(String item) {
        Node oldLast = this.last;

        this.last = new Node();
        this.last.item = item;

        if (this.isEmpty()) {
            this.first = this.last;
        } else {
            oldLast.next = this.last;
        }

        this.size++;
    }


    public String dequeue() {
        String removeItem = this.first.item;
        this.first = this.first.next;

        if (this.isEmpty()) {
            this.last = null;
        }

        return removeItem;
    }


    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.size;
    }
}
```

<hr>

























## 4. Generic泛型

- 如果还按照之前的方式实现栈/队列的话，我们一次只能针对一种类型设计
- 如果有多个类需要使用栈/队列呢？针对每个类型创建一个对应的实现类？

![Xnip2022-04-02_20-40-59](Algorithm Fourth.assets/Xnip2022-04-02_20-40-59.jpg)



- 引入泛型就可以轻松解决该问题





### 1) 链表实现



Code:

```java
public class GenericStack<T> {
    private class Node {
        T item;
        Node next;
    }

    Node topNode;
    int size = 0;

    public boolean isEmpty() {
        return topNode == null;
    }

    public void push(T item) {
        Node curTopNode = new Node();
        curTopNode.item = item;

        curTopNode.next = topNode;
        topNode = curTopNode;

        this.size++;
    }

    public T pop() {
        T popItem = topNode.item;
        topNode = topNode.next;

        this.size--;

        return popItem;
    }

    public int getSize() {
        return this.size;
    }
}
```

- 因为范型的本质还是强制类型转换，所以不能使用基础类型，需要使用对应的包装类
- 注意：Java中不允许创建范型数组(所以数组实现中会出现强制类型转换)







### 2) 数组实现



Code:

```java
public class GenericFixedCapacityStack<T> {
    private T[] array;
    private int idx;

    public GenericFixedCapacityStack() {
        this.array = (T[]) new Object[1];
        this.idx = 0;
    }

    public boolean isEmpty() {
        return idx == 0;
    }

    public void push(T item) {
        if (idx == array.length) {
            resizeArray(idx * 2);
        }

        array[idx] = item;
        idx++;
    }

    public T pop() {
        idx--;
        T popItem = array[idx];
        array[idx] = null;

        if (idx == array.length / 4) {
            resizeArray(array.length / 2);
        }

        return popItem;
    }

    private void resizeArray(int capacity) {
        Object [] newArray = new Object[capacity];

        for (int i = 0; i < idx; i++) {
            newArray[i] = array[i];
        }

        array = (T[]) newArray;
    }

    public int size() {
        return this.array.length;
    }
}
```

<hr>













## 5. Iterator迭代器

- 对于不同的数据类型，遍历的方式不同
- 我们可以让对应的类实现Iterable接口中的方法:
- 其中的iterator方法可以返回一个Iterator实例

![Xnip2022-04-02_21-13-26](Algorithm Fourth.assets/Xnip2022-04-02_21-13-26.jpg)





- 通过Iterator实例，我们可以调用hasNext和next方法来实现遍历

![Xnip2022-04-02_21-14-33](Algorithm Fourth.assets/Xnip2022-04-02_21-14-33.jpg)





- 在之前的Generic类上实现Iterable<T>接口，在重写的方法中返回一个实现了Iterator<T>接口的迭代器实例即可



Code:

```java
public class GenericStack<T> implements Iterable<T> {
    @Override
    public Iterator<T> iterator() {
        return new ListNodeIterator();
    }

    private class ListNodeIterator implements Iterator<T> {
        private Node curNode = topNode;

        @Override
        public boolean hasNext() {
            return curNode != null;
        }

        @Override
        public T next() {
            T item = curNode.item;
            curNode = curNode.next;

            return item;
        }
    }

    private class Node {
        T item;
        Node next;
    }

    Node topNode;
    int size = 0;

    public boolean isEmpty() {
        return topNode == null;
    }

    public void push(T item) {
        Node curTopNode = new Node();
        curTopNode.item = item;

        curTopNode.next = topNode;
        topNode = curTopNode;

        this.size++;
    }

    public T pop() {
        T popItem = topNode.item;
        topNode = topNode.next;

        this.size--;

        return popItem;
    }

    public int getSize() {
        return this.size;
    }
}
```

<hr>













## 6. 应用



### 1)  Dijkstra(迪杰斯特拉) two-stack algorithm

利用两个栈，计算常规的算式:

![Xnip2022-04-03_20-40-13](Algorithm Fourth.assets/Xnip2022-04-03_20-40-13.jpg)



- 创建两个栈，一个用来存放数字，一个存放运算符
- 遇到右括号则弹出两个数并进行与运算符对应的操作

Code:

```java
public static void plusAndMultiply(String input) {
  GenericStack<Character> signStack = new GenericStack<>();
  GenericStack<Integer> numStack = new GenericStack<>();

  for (char curChar : input.toCharArray()) {
    if (Character.isDigit(curChar)) {
      numStack.push(Integer.valueOf((String.valueOf(curChar))));
      //                System.out.println("number push, number is " + curChar);

    } else if (curChar == '+' || curChar == '*') {
      signStack.push(curChar);
      //                System.out.println("sign push, sign is " + curChar);

    } else if (curChar == ')') {
      int num1 = numStack.pop();
      int num2 = numStack.pop();

      char operator = signStack.pop();

      if (operator == '*') {
        numStack.push(num1 * num2);
        //                    System.out.println("Multiply: " + (num1 * num2));
      } else if (operator == '+') {
        numStack.push(num1 + num2);
        //                    System.out.println("Plus: " + (num1 + num2));
      }
    }
  }

  System.out.println("The result of input equation is: " + numStack.pop());
}
```

<hr>









### 2) 进制转换

- 利用栈的性质可将每次的余数存放在栈中，最后按照弹出顺序获取的组合就是转换后的数



Code:

```java
public static void decimalToBinary(int decimalInt) {
  GenericStack<Integer> stack = new GenericStack<>();

  while (decimalInt > 0) {
    stack.push(decimalInt % 2);
    decimalInt /= 2;
  }

  while (!stack.isEmpty()) {
    System.out.print(stack.pop());
  }
}
```

<hr>











## 7. 作业



### 1) 实现一个双端队列



要求:

![Xnip2022-04-04_11-16-04](Algorithm Fourth.assets/Xnip2022-04-04_11-16-04.jpg)



![Xnip2022-04-04_11-16-19](Algorithm Fourth.assets/Xnip2022-04-04_11-16-19.jpg)



思路:

- 因为需要从队尾和队头出入队列，所以简单的单链表是不行的，需要一个双向链表
- 这里的code可以参照LRU



Code:

```java
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
        Node pre;
    }

    private final Node head;
    private final Node tail;
    private int size;

    public Deque() {
        this.head = new Node();
        this.tail = new Node();

        this.head.next = this.tail;
        this.tail.pre = this.head;

        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newHead = new Node();
        newHead.item = item;
        newHead.next = this.head.next;
        newHead.pre = this.head;

        this.head.next.pre = newHead;
        this.head.next = newHead;
        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newTail = new Node();
        newTail.item = item;
        newTail.pre = this.tail.pre;
        newTail.next = this.tail;

        this.tail.pre.next = newTail;
        this.tail.pre = newTail;

        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        Item removeFirstItem = this.head.next.item;
        removeNode(this.head.next);

        return removeFirstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        Item removeLastItem = this.tail.pre.item;
        removeNode(this.tail.pre);

        return removeLastItem;
    }

    private void removeNode(Node curNode) {
        curNode.next.pre = curNode.pre;
        curNode.pre.next = curNode.next;

        this.size--;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        Node curNode = head.next;

        @Override
        public boolean hasNext() {
            return curNode.next != null;
        }

        @Override
        public Item next() {
            if (curNode.next == null) {
                throw new NoSuchElementException();
            }

            Item item = curNode.item;
            curNode = curNode.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * For test
     *
     * @param args
     */
    public static void main(String[] args) {
    }
}
```

<hr>







### 2) 实现一个随机队列

![Xnip2022-04-04_12-30-01](Algorithm Fourth.assets/Xnip2022-04-04_12-30-01.jpg)



![Xnip2022-04-04_12-30-51](Algorithm Fourth.assets/Xnip2022-04-04_12-30-51.jpg)

- 实现队列可以使用链表或者数组，但这里我们需要均摊操作时间，所以这里只有使用数组
- 使用数组的话，我们就需要考虑动态更新容量，即在enqueue和dequeue时实现resize
- 在使用迭代器遍历的时候，我们还需要以随机的顺序进行遍历，所以在创建迭代器的时候还需要打乱其中的元素顺序



- Iterator内部类方法:

```java
private class RandomizedQueueIterator implements Iterator<Item> {
  private Item[] randomQueue;
  private int curIdx;

  public RandomizedQueueIterator() {
    randomQueue = (Item[]) new Object[size];
    for (int i = 0; i < size; ++i) {
      randomQueue[i] = array[i];
    }

    shuffle(randomQueue);
    curIdx = 0;
  }

  public boolean hasNext() {
    return curIdx < randomQueue.length;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

  public Item next() {
    if (!this.hasNext()) {
      throw new NoSuchElementException();
    }

    Item item = randomQueue[curIdx];
    curIdx++;
    return item;
  }

  // Shuffle all element to random
  private void shuffle(Item[] arr) {
    for (int i = 1; i < arr.length; i++) {
      int idx = StdRandom.uniform(i + 1);
      swap(arr, idx, i);
    }
  }
}
```



完整code:

```java
package part1.two.stackandqueue.work;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size;

    public RandomizedQueue() {
        array = (Item[]) new Object[1];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (size == array.length) {
            resize(2 * array.length);
        }

        array[size] = item;
        size++;
    }

    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        int idx = StdRandom.uniform(size);
        Item item = array[idx];
        array[idx] = null;

        swap(array, idx, size - 1);

        size--;
        if (size > 0 && size == array.length / 4) {
            resize(array.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int idx = StdRandom.uniform(size);
        return array[idx];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] randomQueue;
        private int curIdx;

        public RandomizedQueueIterator() {
            randomQueue = (Item[]) new Object[size];
            for (int i = 0; i < size; ++i) {
                randomQueue[i] = array[i];
            }

            shuffle(randomQueue);
            curIdx = 0;
        }

        public boolean hasNext() {
            return curIdx < randomQueue.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = randomQueue[curIdx];
            curIdx++;
            return item;
        }

        // Shuffle all element to random
        private void shuffle(Item[] arr) {
            for (int i = 1; i < arr.length; i++) {
                int idx = StdRandom.uniform(i + 1);
                swap(arr, idx, i);
            }
        }
    }

    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];

        if (size >= 0) {
            System.arraycopy(array, 0, newArray, 0, size);
        }

        array = newArray;
    }

    private void swap(Item[] arr, int left, int right) {
        if (left == right) {
            return;
        }

        Item temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    /**
     * For test
     *
     * @param args
     */
    public static void main(String[] args) {

    }
}
```

<hr>











# 三、基础排序



## 1) 排序的规则

- 排序是根据实例的某个值进行的

![Xnip2022-04-04_14-29-39](Algorithm Fourth.assets/Xnip2022-04-04_14-29-39.jpg)



示例：

传入一个目录路径，将目录下所有的文件名进行排序

![Xnip2022-04-04_14-30-31](Algorithm Fourth.assets/Xnip2022-04-04_14-30-31.jpg)







- 在sort方法中，我们需要知道在没给出类型的情况下，比较哪一个数据的类型
- 我们需要使用回调功能: 在sort方法中，回调了传入对象的compareTo()方法
- Java中对回调的实现是通过接口得到的



在需要排序的类上实现Comparable接口

Code:

```java
import java.io.File;

public class FileSorter implements Comparable<File>{
    @Override
    public int compareTo(File o) {
        return o.getName().length();
    }
}
```



需要排序的对象需要有以下三种性质:

- Antisymmetric: 非对称性，如果a <= b且 b <= a，则a = b
- Transitivity: 传递性，如果a <= b 且 b <= c，则 a <= c
- Totality: 整体性，两个数之间只有 < , >, = 三种关系





按照日期类对Comparable排序的实现:

![Xnip2022-04-04_16-24-18](Algorithm Fourth.assets/Xnip2022-04-04_16-24-18.jpg)





- 实现两个方法用来判断数组是否有序:



Less:

![Xnip2022-04-04_16-25-37](Algorithm Fourth.assets/Xnip2022-04-04_16-25-37.jpg)



swap:

![Xnip2022-04-04_16-25-59](Algorithm Fourth.assets/Xnip2022-04-04_16-25-59.jpg)





测试数组是否有序:

![Xnip2022-04-04_16-26-34](Algorithm Fourth.assets/Xnip2022-04-04_16-26-34.jpg)

<hr>









## 2) 选择排序selection sort

Principle:

- 每次遍历未排序部分中的最小值，将其交换到当前未排序部分的开头位置



不变性:

- 指针左边的一定是排好序的
- 右边的元素里没有一个小于左边的元素

![Xnip2022-04-04_20-02-39](Algorithm Fourth.assets/Xnip2022-04-04_20-02-39.jpg)



Code:

```java
public void selectionSort(int[] nums) {
  for (int left = 0; left < nums.length - 1; left++) {
    int curMin = nums[left];
    int curMinIdx = left;

    for (int curIdx = left + 1; curIdx < nums.length; curIdx++) {
      if (curMin > nums[curIdx]) {
        curMin = nums[curIdx];
        curMinIdx = curIdx;
      }
    }

    swap(nums, left, curMinIdx);
  }
}

private void swap(int[] nums, int left, int right) {
  int temp = nums[left];
  nums[left] = nums[right];
  nums[right] = temp;
}
```





- 时间复杂度为O(n^2^)，其需要进行大约 N^2^ / 2次比较，以及N次交换


![Xnip2022-04-04_20-06-23](Algorithm Fourth.assets/Xnip2022-04-04_20-06-23.jpg)

<hr>









## 3) 插入排序insertion sort

Principle:

- 遍历每个元素，保证其自身以及左边的元素是有序的
- 通过与其左边的元素比较判断左边是否有序



不变性:

- 左边以及当前位置是有序的
- 右边未处理

![Xnip2022-04-04_20-21-12](Algorithm Fourth.assets/Xnip2022-04-04_20-21-12.jpg)





Code:

```java
public void insertionSort(int[] nums) {
  for (int startIdx = 0; startIdx < nums.length; startIdx++) {
    for (int curIdx = startIdx; curIdx >= 1; curIdx--) {
      if (nums[curIdx] < nums[curIdx - 1]) {
        swap(nums, curIdx, curIdx - 1);
      } else {
        break;
      }
    }
  }
}

private void swap(int[] nums, int left, int right) {
  int temp = nums[left];
  nums[left] = nums[right];
  nums[right] = temp;
}
```







复杂度:

- 时间复杂度为O(n^2^)，其中需要1/4 N^2^ 次比较，1/4 N^2^次交换







比较:

- 如果数组已经是有序的了，那么插入排序的用时是线性的
- 如果是倒序的，那么插入排序的用时和选择排序相同 ，但会进行更多次的交换操作

![Xnip2022-04-04_20-35-05](Algorithm Fourth.assets/Xnip2022-04-04_20-35-05.jpg)

<hr>











## 4) 希尔排序shell sort





- 每次进行一次h-排序，h是指有序的递增子序列长度，最后进行插入排序即可

![Xnip2022-04-05_14-50-15](Algorithm Fourth.assets/Xnip2022-04-05_14-50-15.jpg)



理论：

一个g-排序的数组在h-排序后依然是g-排序的

![Xnip2022-04-05_14-53-05](Algorithm Fourth.assets/Xnip2022-04-05_14-53-05.jpg)



目前较好的h取值:

![Xnip2022-04-05_14-54-09](Algorithm Fourth.assets/Xnip2022-04-05_14-54-09.jpg)





这里我们取3x + 1进行实现:

```java
public void shellSort(int[] nums) {
  int arrayLength = nums.length;

  int h = 1;
  while (h < arrayLength / 3) {
    h = 3 * h + 1;
  }

  while (h >= 1) {
    for (int startIdx = h; startIdx < arrayLength; startIdx++) {
      for (int curIdx = startIdx; curIdx >= h && nums[curIdx] < nums[curIdx - h]; curIdx -= h) {
        swap(nums, curIdx, curIdx - h);
      }
    }

    h /= 3;
  }
}
```







复杂度:

- 最坏的情况下为O(N^3/2^)

![Xnip2022-04-05_14-57-03](Algorithm Fourth.assets/Xnip2022-04-05_14-57-03.jpg)







希尔排序的特点:

- 对小数组来说很有效
- 代码量少，可用于硬件中

<hr>













## 5) 洗牌(shuffle)

- 如何打乱一个数组，使得每个元素被均匀的打乱



- 遍历有序数组，保证左边的数字都是随机的
- 每次从左边随机获取一个元素与当前位置的数字进行交换

![Xnip2022-04-05_15-36-53](Algorithm Fourth.assets/Xnip2022-04-05_15-36-53.jpg)





Code:

```java
public void shuffle(int[] nums) {
  for (int i = 1; i < nums.length; i++) {
    int curRandomNum = StdRandom.uniform(0, i);
    ShellSort.swap(nums, curRandomNum, i);
  }
}
```

<hr>











## 6) 凸包Convex hull(排序的应用)

- 凸包(convex hull)代表将所有平面上的点都框在一个封闭图形里的最小面积



定义:

- 包含所有点的最小凸面
- 包含所有点的最小的封闭图形
- 顶点都是平面内的点

![Xnip2022-04-05_16-31-00](Algorithm Fourth.assets/Xnip2022-04-05_16-31-00.jpg)





- 输出：逆时针顺序输出顶点序列



具体应用:

- 对障碍物建模后获取了一个对应的convex hull凸包，可根据它获取绕过障碍物的最短路径

![Xnip2022-04-05_16-36-17](Algorithm Fourth.assets/Xnip2022-04-05_16-36-17.jpg)





- 求两个点之间的距离 
- ![Xnip2022-04-05_16-37-19](Algorithm Fourth.assets/Xnip2022-04-05_16-37-19.jpg)







- 计算的方式: 格雷厄姆扫描(Graham scan)
    - 选择一个最小的点p
    - 从p点开始排序
    - 判断每个点，只将按照逆时针转动的点作为顶点



![Xnip2022-04-05_16-39-48](Algorithm Fourth.assets/Xnip2022-04-05_16-39-48.jpg)



![Xnip2022-04-05_16-41-29](Algorithm Fourth.assets/Xnip2022-04-05_16-41-29.jpg)



- 难点：判断是否为逆时针(ccw: counterclockwise)

​	![Xnip2022-04-05_16-43-02](Algorithm Fourth.assets/Xnip2022-04-05_16-43-02.jpg)



- 实现：通过三个点之间的斜率

![Xnip2022-04-05_16-43-53](Algorithm Fourth.assets/Xnip2022-04-05_16-43-53.jpg)



- code implement:

![Xnip2022-04-05_16-44-36](Algorithm Fourth.assets/Xnip2022-04-05_16-44-36.jpg)

<hr>













# 四、归并排序(merge sort)



## 1. 归并排序

Principle:

- 将一个数组分为两半
- 递归排序每个部分
- 合并两个排序后的部分

![Xnip2022-04-06_14-36-39](Algorithm Fourth.assets/Xnip2022-04-06_14-36-39.jpg)





示例:

![Xnip2022-04-06_14-38-29](Algorithm Fourth.assets/Xnip2022-04-06_14-38-29.jpg)

确保从lo到mid和从mid + 1到hi是有序的，之后将两个数组进行merge合并即可





Java实现合并(merge):

![Xnip2022-04-06_14-41-01](Algorithm Fourth.assets/Xnip2022-04-06_14-41-01.jpg)

- 我们需要一个辅助数组(auxiliary)来表示排序后的数组
- 在示例中用到了断言(assert)来判断左右两边是否有序





断言解释:

![Xnip2022-04-06_14-44-35](Algorithm Fourth.assets/Xnip2022-04-06_14-44-35.jpg)





- Java实现排序

![Xnip2022-04-06_14-45-49](Algorithm Fourth.assets/Xnip2022-04-06_14-45-49.jpg)









- 递归分析:

![Xnip2022-04-06_14-46-20](Algorithm Fourth.assets/Xnip2022-04-06_14-46-20.jpg)







复杂度:

- 时间复杂度: nlog(n)
- 空间复杂度: nlog(n)



时间复杂度证明:

- 每次划分都是将数组除以2得到两个子数组，直到子数组长度为1

![Xnip2022-04-06_14-48-43](Algorithm Fourth.assets/Xnip2022-04-06_14-48-43.jpg)







空间复杂度证明:

- 归并排序并非是希尔排序、选择/插入排序那样的原地排序算法(in-place)，其需要额外空间

![Xnip2022-04-06_14-51-38](Algorithm Fourth.assets/Xnip2022-04-06_14-51-38.jpg)









归并排序的优化





- 优化一



如果数组较小，则使用之前的排序方式即可

![Xnip2022-04-06_14-55-04](Algorithm Fourth.assets/Xnip2022-04-06_14-55-04.jpg)







- 优化二

如果两个数组以及排好序了，且两个数组之间接触的元素也是有序的，则该数组已经有序了，不再需要排序

![Xnip2022-04-06_14-56-18](Algorithm Fourth.assets/Xnip2022-04-06_14-56-18.jpg)

<hr>








## 2. Comparable接口

- 很多时候，针对同一个数据集，我们需要根据不同的key进行排序，所以在需要排序的key上建立一个实现了Comparable的类或者lambda表达式即可

Eg:

![Xnip2022-04-07_10-30-04](Algorithm Fourth.assets/Xnip2022-04-07_10-30-04.jpg)





- 创建后只需要将该排序规则传入到Arrays.sort方法中的第二参数位置即可

Eg:

![Xnip2022-04-07_10-32-01](Algorithm Fourth.assets/Xnip2022-04-07_10-32-01.jpg)



![Xnip2022-04-07_10-32-32](Algorithm Fourth.assets/Xnip2022-04-07_10-32-32.jpg)

<hr>











## 3. 稳定性

迄今为止排序算法的稳定性：

- Insertion sort: 稳定
- Selection sort: 不稳定
- Shell sort: 不稳定
- Merge sort: 稳定(前提是两数相同时始终先添加左边那个数)



判断排序算法是否稳定的依据：

检查其是否有跨多个数字的交换

![Xnip2022-04-07_10-46-58](Algorithm Fourth.assets/Xnip2022-04-07_10-46-58.jpg)



![Xnip2022-04-07_10-46-19](Algorithm Fourth.assets/Xnip2022-04-07_10-46-19.jpg)



![Xnip2022-04-07_10-46-27](Algorithm Fourth.assets/Xnip2022-04-07_10-46-27.jpg)



![Xnip2022-04-07_10-47-04](Algorithm Fourth.assets/Xnip2022-04-07_10-47-04.jpg)

<hr>









## 4. 作业



### 1) Point

![Xnip2022-04-09_20-00-02](Algorithm Fourth.assets/Xnip2022-04-09_20-00-02.jpg)



要求:

创建一个Point类，其结构如图，需要你实现compareTo方法(重写Comparable里的方法)、slopTo方法，slopOrder方法，其余方法都有固定实现



- 其中compareTo方法当且仅当当前点的y值小于参数y值，或者当前y值=参数y值且当前x值小于参数x值时才返回负数(表示小于参数点)
- slopTo方法应该返回调用点与参数点连线的斜率，计算公式为: (y1 − y0) / (x1 − x0)，如果是一条水平线，则返回；如果是一条垂直的线，则返回正无穷；如果两点重合则返回负无穷
- slopOrder方法应该返回一个通过斜率比较两个参数点的比较器，其中斜率的计算通过slopTo方法实现





compareTo方法:

```java
@Override
public int compareTo(Point that) {
  return this.y == that.y ? this.x - that.x : this.y - that.y;
}
```



slopTo方法:

```java
public double slopeTo(Point that) {
  if (this.y == that.y && this.x == that.x) {
    return Double.NEGATIVE_INFINITY;
  }

  else if (this.y == that.y) {
    return 0;
  } else if (this.x == that.x) {
    return Double.POSITIVE_INFINITY;
  }

  return (that.y * 1.0 - this.y) / (that.x - this.x);
}
```



slopeOrder方法:

```java
public Comparator<Point> slopeOrder() {
  return new SlopeComparator(this);
}

private static class SlopeComparator implements Comparator<Point> {
  private final Point point;

  SlopeComparator(Point point) {
    this.point = point;
  }

  @Override
  public int compare(Point p1, Point p2) {
    double slope1 = p1.slopeTo(point);
    double slope2 = p2.slopeTo(point);
    return Double.compare(slope1, slope2);
  }
}
```



Full Code:

```java
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void draw() {
    StdDraw.point(this.x, this.y);
  }

  public void drawTo(Point that) {
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  @Override
  public int compareTo(Point that) {
    return this.y == that.y ? this.x - that.x : this.y - that.y;
  }

  public double slopeTo(Point that) {
    if (this.y == that.y && this.x == that.x) {
      return Double.NEGATIVE_INFINITY;
    }

    else if (this.y == that.y) {
      return 0;
    } else if (this.x == that.x) {
      return Double.POSITIVE_INFINITY;
    }

    return (that.y * 1.0 - this.y) / (that.x - this.x);
  }

  public Comparator<Point> slopeOrder() {
    return new SlopeComparator(this);
  }

  private static class SlopeComparator implements Comparator<Point> {
    private final Point point;

    SlopeComparator(Point point) {
      this.point = point;
    }

    @Override
    public int compare(Point p1, Point p2) {
      double slope1 = p1.slopeTo(point);
      double slope2 = p2.slopeTo(point);
      return Double.compare(slope1, slope2);
    }
  }

  /**
     * For test
     *
     * @param args
     */
  public static void main(String[] args) {

  }
}
```

<hr>









### 2) BruteCollinearPoints

给你一个类：LineSegment，其代表平面上的线，该类可作为工具类直接使用

![Xnip2022-04-09_20-25-29](Algorithm Fourth.assets/Xnip2022-04-09_20-25-29.jpg)



- 我们需要一个BruteCollinearPoints类检查4个点是否在同一条直线上，返回所有这样的直线
- 为了检查p, q, r, s四个点，需要检查它们彼此之间的斜率是否一致
- 你需要实现一个构造方法，其接收的参数为一堆点(上一道题写的类)
- 还需要实现一个numberOfSegements方法以返回输入到构造函数中在同一直线上的四个点的组合数(返回对应的直线数量)
- 最后还需要实现一个segments方法返回所有的直线实例



- 其中segments方法应该返回的数字是不重复的线段数(起始点位置交换算做同一直线)，同时也不应该包含子线段
- 如果构造方法的参数为null，或者该数组中存在元素为null，则抛出非法参数异常



- 构造方法:

首先我们需要检验参数，这里判断参数本身后再遍历参数数组中的元素即可

之后为了不重复计算，我们需要检查参数中是否有重复的点

for循环嵌套获取4个点，如果斜率相同则添加头尾point元素到集合中去

或完所有的组合后，将集合转换为数组

```java
public BruteCollinearPoints(Point[] points) {

  // Check null
  if (points == null) {
    throw new NullPointerException("The array is null.");
  }
  for (Point curPoint : points) {
    if (curPoint == null) {
      throw new NullPointerException("The current point is a null element.");
    }
  }

  Arrays.sort(points);

  // Check duplicate
  for (int i = 0; i < points.length - 1; i++) {
    if (points[i].compareTo(points[i + 1]) == 0) {
      throw new IllegalArgumentException();
    }
  }


  final int length = points.length;
  List<LineSegment> curLineSegment = new LinkedList<>();

  for (int a = 0; a < length - 3; a++) {
    Point ptA = points[a];

    for (int b = a + 1; b < length - 2; b++) {
      Point ptB = points[b];
      double slopeAB = ptA.slopeTo(ptB);

      for (int c = b + 1; c < length - 1; c++) {
        Point ptC = points[c];
        double slopeAC = ptA.slopeTo(ptC);

        if (slopeAB == slopeAC) {
          for (int d = c + 1; d < length; d++) {
            Point ptD = points[d];
            double slopeAD = ptA.slopeTo(ptD);

            if (slopeAB == slopeAD) {
              curLineSegment.add(new LineSegment(ptA, ptD));
            }
          }
        }
      }
    }
  }
  lineSegments = curLineSegment.toArray(new LineSegment[0]);
}
```







- numberOfSegements:

该方法直接返回数组的长度即可

```java
public int numberOfSegments() {
  return lineSegments.length;
}
```







- segments:

该方法直接返回数组即可

```java
public LineSegment[] segments() {
  return lineSegments;
}
```

<hr>









### 3) FastCollinearPoints

和之前一样的问题，但这次我们需要用更好的性能来解决

![Xnip2022-04-09_20-50-12](Algorithm Fourth.assets/Xnip2022-04-09_20-50-12.jpg)



大部分与上一题一样，不过我们需要在构造方法内对获取点的操作做一些改动

之前的操作为for循环暴力遍历，这里我们则只需要遍历一次原数组，获取每个元素对应的slopOrder规则并以此为基础对原数组排序

之后从不同的起始点开始遍历排序后的数组，如果斜率相同的继续，结束当前循环后，如果点的数量大于等于3，且排序数组中的起点位置在原数组当前点的后面，则将原数组当前点作为起点，最后一个遍历的排序数组中的点作为终点添加到结果集中



Code:

```java
public FastCollinearPoints(Point[] points) {
  // Check null
  if (points == null) {
    throw new NullPointerException("The array is null.");
  }
  for (Point curPoint : points) {
    if (curPoint == null) {
      throw new NullPointerException("The current point is a null element.");
    }
  }
  Arrays.sort(points);

  // Check duplicate
  for (int i = 0; i < points.length - 1; i++) {
    if (points[i].compareTo(points[i + 1]) == 0) {
      throw new IllegalArgumentException();
    }
  }


  final int length = points.length;
  final List<LineSegment> maxLineSegments = new LinkedList<>();

  for (Point p : points) {

    Point[] pointsBySlope = points.clone();
    Arrays.sort(pointsBySlope, p.slopeOrder());

    int idx = 1;
    while (idx < length) {

      LinkedList<Point> candidates = new LinkedList<>();
      final double CUR_SLOPE = p.slopeTo(pointsBySlope[idx]);
      do {
        candidates.add(pointsBySlope[idx]);
        idx++;
      } while (idx < length && p.slopeTo(pointsBySlope[idx]) == CUR_SLOPE);

      if (candidates.size() >= 3 && p.compareTo(candidates.getFirst()) < 0) {
        Point max = candidates.removeLast();
        maxLineSegments.add(new LineSegment(p, max));
      }
    }
  }
  lineSegments = maxLineSegments.toArray(new LineSegment[0]);
}
```

<hr>









# 五、快速排序(quick sort)



## 1. 快速排序



### 1) 理论(basic)

- 打乱数组(shuffle)
- 以一个随机数j进行分组(partition)
    - 保证j的左边没有比它大的数，右边没有比它小的数
    - a[j]的位置固定
- 排序(sort): 对每个分组进行递归排序

![Xnip2022-04-10_20-59-53](Algorithm Fourth.assets/Xnip2022-04-10_20-59-53.jpg)

图中选择了打乱后数组中的第一个字母







- 分组(partition)
    - 从左到右扫描i，直到等式a[i] < a[lo]不成立
    - 从右到左扫描j，直到等式a[j] > a[lo]不成立
    - 当等式不满足时交换a[i]和a[j]指向的数字

![Xnip2022-04-10_21-03-51](Algorithm Fourth.assets/Xnip2022-04-10_21-03-51.jpg)

<hr>













### 2) 实现

Partition的实现(针对Comparable实例数组):

![Xnip2022-04-10_21-06-37](Algorithm Fourth.assets/Xnip2022-04-10_21-06-37.jpg)



![Xnip2022-04-10_21-07-13](Algorithm Fourth.assets/Xnip2022-04-10_21-07-13.jpg)





- 将partition带入
- 通过递归调用partition即可通过不断的分组使得数组有序
- 注意这里引入了打乱操作使得排序的性能得以保证

![Xnip2022-04-10_21-08-39](Algorithm Fourth.assets/Xnip2022-04-10_21-08-39.jpg)



- 如果引入额外的数组可以使得分组更加简单且稳定，但会使得性能变差

<hr>









### 3) 分析

![Xnip2022-04-10_21-13-38](Algorithm Fourth.assets/Xnip2022-04-10_21-13-38.jpg)



- 在最理想的情况下，比较次数为NlgN
- 最坏的情况下，比较次数为 $$\frac{1}{2}$$ N^2^
- 平均情况为1.39NlgN

其比归并排序要多一些比较的操作，但移动操作更少





- 快速排序是一个原地的排序算法，但其递归调用的栈空间为对数logn
- 快速排序是一个不稳定的算法，因为其涉及到长距离的元素交换

<hr>





### 4) 优化

如果数组比较小，那么我们可以使用插入排序来替代它

![Xnip2022-04-10_21-24-06](Algorithm Fourth.assets/Xnip2022-04-10_21-24-06.jpg)

<hr>









## 2. 选择算法

在一个有序的数组中找出其中第k小的数字，可以用于top k等问题



- 将快速排序的分组思想应用在其中就能够解决选择问题:

![Xnip2022-04-11_10-23-56](Algorithm Fourth.assets/Xnip2022-04-11_10-23-56.jpg)



示例：

创建一个实现Comparable接口的学生类，在compareTo方法中指定排序为年龄降序

![Xnip2022-04-11_10-38-49](Algorithm Fourth.assets/Xnip2022-04-11_10-38-49.jpg)





![Xnip2022-04-11_10-40-38](Algorithm Fourth.assets/Xnip2022-04-11_10-40-38.jpg)



![Xnip2022-04-11_10-42-07](Algorithm Fourth.assets/Xnip2022-04-11_10-42-07.jpg)



- 同样的，我们可以在Arrays.sort方法中通过lambda表达式指定我们自己的排序方式:

![Xnip2022-04-11_10-44-08](Algorithm Fourth.assets/Xnip2022-04-11_10-44-08.jpg)

- 这里我们指定排序规则为年龄倒序



- 这种结合partition的选择算法称为快速选择(select sort)

其平均用时是线性的，最坏为$$\frac{1}{2}$$N^2^(同快速排序)

![Xnip2022-04-11_10-48-12](Algorithm Fourth.assets/Xnip2022-04-11_10-48-12.jpg)

<hr>







## 3. 重复的key

- 如果在待排序的数组中有重复的key，那么常规的快速排序就会得出错误的结果

![Xnip2022-04-11_11-05-09](Algorithm Fourth.assets/Xnip2022-04-11_11-05-09.jpg)

- 图中以城市名作为排序的依据，因为城市名重复，所以排序后的时间并不对







### 1. 比较

在对含有重复key数组进行排序的时候，不同排序算法的情况:

- 归并排序:

需要进行$$\frac{1}{2}$$NlgN到NlgN次比较



- 快速排序:

除非停止对重复的key分组，否则会进行N^2^次比较

![Xnip2022-04-11_11-10-05](Algorithm Fourth.assets/Xnip2022-04-11_11-10-05.jpg)

<hr>











### 2. 3-way partition

- 重写partition，将数组划分为三个部分以解决这个问题

![Xnip2022-04-11_11-13-33](Algorithm Fourth.assets/Xnip2022-04-11_11-13-33.jpg)



![Xnip2022-04-11_11-15-08](Algorithm Fourth.assets/Xnip2022-04-11_11-15-08.jpg)





实现:

![Xnip2022-04-11_11-16-14](Algorithm Fourth.assets/Xnip2022-04-11_11-16-14.jpg)

<hr>











### 3. 用时

- 当所有的key都重复时，用时为NlgN，如果之后常数级别的重复key，则为线性用时

![Xnip2022-04-11_11-30-51](Algorithm Fourth.assets/Xnip2022-04-11_11-30-51.jpg)



Eg:

![Xnip2022-04-11_11-30-19](Algorithm Fourth.assets/Xnip2022-04-11_11-30-19.jpg)

<hr>







## 4. 应用

![Xnip2022-04-11_14-54-19](Algorithm Fourth.assets/Xnip2022-04-11_14-54-19.jpg)

<hr>



### 1) Java实现的排序算法

Arrays.sort():

- 其对每种原始类型(primitive type)都有不同的重载方法
- 对于Comparator示例实现了一种方法
- 对于原始类型使用调整后的快速排序，对于引用类型(object)则使用调整后的归并排序

![Xnip2022-04-11_15-00-34](Algorithm Fourth.assets/Xnip2022-04-11_15-00-34.jpg)









为什么两种类型要使用不同的算法？

对于原始类型：比较在意性能，所以选择不会创建额外空间的快速排序

对于引用类型：稳定即可

<hr>







# 排序总结

|                         | inplace? | Stable? |       worst       |      average      |       best        |          descrbie          |
| :---------------------: | :------: | :-----: | :---------------: | :---------------: | :---------------: | :------------------------: |
|        selection        |   yes    |         | $$\frac{N^2}{2}$$ | $$\frac{N^2}{2}$$ | $$\frac{N^2}{2}$$ |          N次交换           |
|        insertion        |   yes    |   yes   | $$\frac{N^2}{2}$$ | $$\frac{N^2}{4}$$ |         N         | 用于小数组排列或者部分排列 |
|          shell          |   yes    |         |         ?         |         ?         |         N         |          代码量少          |
|          merge          |          |   yes   |       NlgN        |       NlgN        |       NlgN        |     能够保证NlgN，稳定     |
|          quick          |   yes    |         | $$\frac{N^2}{2}$$ |       2NlgN       |       NlgN        | 原地修改，比较快，但不稳定 |
| 3-way quick(快排改进版) |   yes    |   yes   |       NlgN        |       NlgN        |         N         |          圣杯级别          |

<hr>














# 六、优先队列(Priority Queue)



## 1. API/概念

与其他数据结构的对比:

![Xnip2022-04-12_13-56-53](Algorithm Fourth.assets/Xnip2022-04-12_13-56-53.jpg)

- 优先队列可以删除最值



- 最大优先队列中的方法和对应的泛型

![Xnip2022-04-12_13-58-16](Algorithm Fourth.assets/Xnip2022-04-12_13-58-16.jpg)





- 不同的实现和对应的消耗

![Xnip2022-04-12_13-59-54](Algorithm Fourth.assets/Xnip2022-04-12_13-59-54.jpg)

其中二叉堆的消耗最接近理想情况



- 优先队列可以分为有序和无序的实现方法

![Xnip2022-04-12_14-00-56](Algorithm Fourth.assets/Xnip2022-04-12_14-00-56.jpg)





- 无序最大优先队列的数组实现

![Xnip2022-04-12_14-02-09](Algorithm Fourth.assets/Xnip2022-04-12_14-02-09.jpg)



implements:

![Xnip2022-04-12_14-14-54](Algorithm Fourth.assets/Xnip2022-04-12_14-14-54.jpg)



![Xnip2022-04-12_14-15-05](Algorithm Fourth.assets/Xnip2022-04-12_14-15-05.jpg)







- 有序和无序优先队列的消耗区别

![Xnip2022-04-12_14-15-35](Algorithm Fourth.assets/Xnip2022-04-12_14-15-35.jpg)

<hr>









## 2. 二叉堆(Binary heap)

二叉堆的基础结构: 完全二叉树

![Xnip2022-04-12_14-57-29](Algorithm Fourth.assets/Xnip2022-04-12_14-57-29.jpg)



特性(property):

- 完全二叉树的高度 = lgN (N为节点的数量)
- 只有当节点数N增加到2的幂时，树的高度才会增加(重要性质)







二叉堆的表示(大根堆):

- 二叉堆: 用数组表示一个堆顺序的完全二叉树
- 堆顺序二叉树:
    - 节点上是值
    - 父节点都不会小于子节点
- 实现的数组:
    - 将节点通过层级顺序进行排列
    - 节点之间不需要显式的连接

![Xnip2022-04-12_15-05-02](Algorithm Fourth.assets/Xnip2022-04-12_15-05-02.jpg)







- 二叉堆的特性(大根堆):
    - 数组中第一个元素的值是最大的，其作为二叉树的根节点
    - 可通过数组的索引遍历整颗树:
        - 如果节点对应的索引为k，其父节点对应的索引为k / 2
        - 一个父节点对应的索引为k，其两个子节点的索引为2k和2k + 1

![Xnip2022-04-12_15-08-42](Algorithm Fourth.assets/Xnip2022-04-12_15-08-42.jpg)







- 大根堆上浮(swim)



如果子节点大于其父节点，那么需要进行上浮操作

为了消除错误的操作:

1. 交换子节点和父节点的值
2. 重复步骤1直到整个数组恢复堆顺序

swim实现:

```java
// The k represent the child node which value is greater then parent
private void swim(int k) {
  while (k > 1 && less(k / 2, k) {
    swap(k, k / 2);
    k /= 2;
  }
}
```



![Xnip2022-04-12_16-31-15](Algorithm Fourth.assets/Xnip2022-04-12_16-31-15.jpg)





- 插入操作:

将一个新的节点插入到数组末尾，并将其上浮

最多进行1 + lgN(树的高度)次比较



insert实现:

```java
public void insert(Key x) {
  pq[++N] = x;
  swim(N);
}
```



![Xnip2022-04-12_16-35-21](Algorithm Fourth.assets/Xnip2022-04-12_16-35-21.jpg)







- 堆的降级(与上浮相对)

如果一个父节点的值小于其一个子节点/或者两个，此时需要降级/下沉



为了消除错误的操作:

1. 交换父节点和较大的那个子节点的值
2. 重复步骤1直到数组恢复栈顺序



sink实现:

```java
// K represent the parent index which value is smaller then its child node
private void sink(int k) {
  while (2 * k <= N) {
    int j = 2 * K;
    
    // Get the bigger child
    if (j < N && less(j, j + 1)) {
      j++;
    }
    
    // Already restored
    if (!less(k, j)) {
      break;
    }
    
    swap(k, j);
    
    // Judge the new parent
    k = j;
  }
}
```



![Xnip2022-04-12_16-44-10](Algorithm Fourth.assets/Xnip2022-04-12_16-44-10.jpg)





- 删除最大值
    1. 将数组中的第一个元素与最后一个元素进行交换
    2. 将新的根节点进行下沉
    3. 将最后一个元素删除

最多进行2lgN次比较



delMax实现:

```java
public Key delMax() {
  Key max = pq[0];
  swap(0, N);
  
  sink(0);
  pq[N] = null;
  N--;
  
  return max;
}
```



![Xnip2022-04-12_16-49-12](Algorithm Fourth.assets/Xnip2022-04-12_16-49-12.jpg)





最大优先队列的实现(大根堆):

![Xnip2022-04-12_17-38-03](Algorithm Fourth.assets/Xnip2022-04-12_17-38-03.jpg)





- 不同实现的消耗分析

![Xnip2022-04-12_17-38-43](Algorithm Fourth.assets/Xnip2022-04-12_17-38-43.jpg)







- 其余一些细节
    - 对于下溢: 如果尝试对空的优先队列使用delete方法，则抛出异常
    - 对于溢出: 添加一个空的构造方法，并通过方法重写分配数组的大小
    - 改为最小优先队列(小根堆): 将less改为greater并实现即可



![Xnip2022-04-12_17-40-33](Algorithm Fourth.assets/Xnip2022-04-12_17-40-33.jpg)

<hr>





完整版:

```java
import part1.utils.SortAPI;

public class MaxPQ <Key extends Comparable<Key>>{
    private Key[] pq;
    private int idx;
    public Key[] quoteArray;

    public MaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity];
        quoteArray = pq;
    }

    public boolean isEmpty() {
        return idx == 0;
    }

    public void insert(Key key) {
        pq[idx] = key;

        swim(idx);
        idx++;
    }

    private void swim(int childIdx) {
        while (childIdx >= 0 && less(childIdx / 2, childIdx)) {
            SortAPI.swap(pq, childIdx / 2, childIdx);
            childIdx /= 2;
        }
    }

    public Key delMax() {
        Key max = pq[0];
        SortAPI.swap(pq, 0, idx);

        sink(0);
        pq[idx] = null;
        idx--;

        return max;
    }

    private void sink(int parentIdx) {
         while (2 * parentIdx <= idx) {
             int childIdx = 2 * parentIdx;

             if (childIdx < idx && less(childIdx, childIdx + 1)) {
                 childIdx++;
             }

             if (!less(parentIdx, childIdx)) {
                 break;
             }

             SortAPI.swap(pq, parentIdx, childIdx);
             parentIdx = childIdx;
         }
    }

    private boolean less(int firstIdx, int secondIdx) {
        return pq[firstIdx].compareTo(pq[secondIdx]) < 0;
    }
}
```















