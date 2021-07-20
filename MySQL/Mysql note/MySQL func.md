# 1. DATEDIFF(expr1, expr2)

SQL Practice Day 12



- 返回expr1-expr2的值，用来判断两个日期的先后关系

**注意：日期值需要加上' '**

![Xnip2021-05-31_13-47-08](MySQL Note.assets/Xnip2021-05-31_13-47-08.jpg)







exam:

![Xnip2021-05-31_13-36-34](MySQL Note.assets/Xnip2021-05-31_13-36-34.jpg)

****











# 2.REPLACE(column, oldVal, newVal)

SQL Practice Day 29



将指定字段中的制定数据，进行修改，**可用于更新字段**

![Xnip2021-06-17_12-50-02](MySQL Note.assets/Xnip2021-06-17_12-50-02.jpg)





eg:

![Xnip2021-06-17_12-52-57](MySQL Note.assets/Xnip2021-06-17_12-52-57.jpg)















# 3. Window Func

- 仅在MySQL8.0及其之后的版本才开始支持窗口函数





## 1) DENSE_RANK()

- 根据分组或整体进行排名，排名值连续(相同排名后不会跳过)
- 该函数应该和ORDER BY一起使用，不然字段就是对等关系
- 使用PARTITION BY可以指定分组的字段，该函数则对不同的分组进行排名(不写则对所有数据进行排序)
- 使用ORDER BY指定排序字段及规则

Syntax:

```mysql
DENSE_RANK() OVER (
	PARTITION BY column_name
  ORDER BY column_name DESC/ASC
) AS 'alias'
```





eg:

![Xnip2021-07-16_13-12-51](MySQL Note.assets/Xnip2021-07-16_13-12-51.jpg)

![Xnip2021-07-16_13-13-36](MySQL Note.assets/Xnip2021-07-16_13-13-36.jpg)























