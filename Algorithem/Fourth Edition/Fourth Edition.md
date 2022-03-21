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

| algorithm  | initialize | union | find/connected |
| :--------: | :--------: | :---: | :------------: |
| quick-find |     N      |   N   |       N        |









