# 十、单表访问方法

- MySQL Server对一条SQL语句进行语法解析后(语法无误)，**会将其交给优化器进行优化**
- 优化的结果便是**执行计划**: 指定使用的索引、连接的顺序等等
- 最后按照执行计划中的步骤调用存储引擎接口来真正的查询，最后返回结果集



Eg Table:

![Xnip2021-10-21_10-29-23](MySQL Note.assets/Xnip2021-10-21_10-29-23.jpg)



![Xnip2021-10-21_10-29-32](MySQL Note.assets/Xnip2021-10-21_10-29-32.jpg)





## 10.1 访问方法

- MySQL官方将MySQL**执行查询语句的方式**，称为访问方法
- 同一条查询语句**可以使用多种访问方法**来执行







## 10.2 const

- **通过主键/唯一二级索引列**与**常数进行等值比较**，来**定位一条记录**的访问方法定义为const
- 如果**索引有多个列(联合索引)**，则只有索引中**每个列都与常数进行等值比较时**，const访问方法才有效

Eg SQL:

```mysql
SELECT * FROM single_table WHERE id = 1438;
SELECT * FROM single_table WHERE key2 = 3841;
```





**注意：**对于唯一二级索引列，查询列为NULL时，比较特殊

```mysql
SELECT * FROM single_table WHERE key2 IS NULL;
```

- 因为唯一二级索引中为**null值的记录数量没有限制**，所以**查询结果可能有多条记录**，**因此也不能用const**













## 10.3 ref

- 搜索条件为**二级索引列与常数进行等值比较**，**形成的扫描区间为单点扫描区间**，**采用二级索引执行查询**的访问方法为ref
- 需要回表

Eg SQL:

```mysql
SELECT * FROM single_table WHERE key1 = 'abc';
```

- 每获取一条二级索引记录，**就会立马对其执行回表操作**





过程:

![Xnip2021-10-21_10-41-50](MySQL Note.assets/Xnip2021-10-21_10-41-50.jpg)

- 由于通过普通二级索引列进行等值比较后，可能会匹配到多条记录，所以该ref访问方法比const要查一点



**注意：**

1. 二级索引允许存储null，其不限制null的数量，所以**在对二级索引列执行IS NULL条件查询时，只能使用ref**
2. 对于联合二级索引来说，只要前面的连续索引列是与常数进行等值比较，就能使用ref方法

Eg SQL:

```mysql
SELECT * FROM single_table WHERE key_part1 = 'god like' AND key_part2 = 'legendary'
```

- 联合索引idx_key_part，key_part1和key_part2是按照顺序的，所以能够使用ref





- 如果左边的连续列不全是等值的话，就不能用ref方法:

```mysql
SELECT * FROM single_table WHERE key_part1 = 'god like' AND key_part2 > 'legendary'
```

















## 10.4 ref_or_null

- 在ref的基础上，还需要将列中值为null的记录也找出，该种执行方法即为ref_or_null
- 只是比ref访问方法多扫描了一些值为null的二级索引记录
- 值为null的记录会被放在索引的最左边

Eg SQL:

```mysql
SELECT * FROM single_table WHERE key1 = 'abc' OR Key1 IS NULL;
```





执行过程:

![Xnip2021-10-21_10-57-08](MySQL Note.assets/Xnip2021-10-21_10-57-08.jpg)















## 10.5 range

- 使用索引进行查询时，**对应的扫描区间为若干单点扫描区间或者范围扫描区间的访问方法为range**

Eg SQL:

```mysql
SELECT * FROM single_table WHERE key2 IN (1438, 6328) OR key2 >= 38 AND key2 <= 79;
```



**注意：**

- 仅包含一个单点扫描区间或者区间为(-∞, +∞)的访问方法不能称为range













## 10.6 index

- **扫描全部二级索引记录的访问方法为index**

Eg SQL

```mysql
SELECT key_part1, key_part2, key_part3 FROM single_table WHERE key_part2 = 'abc';
```



































