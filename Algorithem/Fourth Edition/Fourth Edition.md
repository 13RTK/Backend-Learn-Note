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







