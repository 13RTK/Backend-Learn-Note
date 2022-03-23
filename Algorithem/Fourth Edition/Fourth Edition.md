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



























