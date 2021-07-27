







# 1.REPLACE(column, oldVal, newVal)

SQL Practice Day 29



将指定字段中的制定数据，进行修改，**可用于更新字段**

![Xnip2021-06-17_12-50-02](MySQL Note.assets/Xnip2021-06-17_12-50-02.jpg)





eg:

![Xnip2021-06-17_12-52-57](MySQL Note.assets/Xnip2021-06-17_12-52-57.jpg)















# 2.Window Func

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







## 2) SUM() OVER











****





# 3.Date Func



## 1) DATE

- 获取日期数据字段中的日期部分


Syntax:

```mysql
DATE(column_name);
```



Eg:

![EC31DC07-F408-4F75-BBBD-4F6C862B2315](MySQL Note.assets/EC31DC07-F408-4F75-BBBD-4F6C862B2315.png)









## 2) TIME

- 获取日期数据类型中的时间部分

Syntax:

```mysql
TIME(column_name)
```



Eg:

![Xnip2021-07-26_17-39-00](MySQL Note.assets/Xnip2021-07-26_17-39-00.jpg)













## 3) DATEDIFF

SQL Practice Day 12

- 返回expr1-expr2的值，用来判断两个日期的先后关系

Syntax:

```mysql
DATEDIFF(expr1, expr2);
```

 



**注意：日期值需要加上' '**

![Xnip2021-05-31_13-47-08](MySQL Note.assets/Xnip2021-05-31_13-47-08.jpg)







exam:

![Xnip2021-05-31_13-36-34](MySQL Note.assets/Xnip2021-05-31_13-36-34.jpg)











## 4) ADDDATE

- 将指定的日期值添加上指定的天数



Syntax:

```mysql
ADDDATE(expr, day);
```

![Xnip2021-07-26_20-39-29](MySQL Note.assets/Xnip2021-07-26_20-39-29.jpg)



## 5) ADDTIME













# 4.String



## 1) LEFT/RIGHT

- 用于获取字符串左边或者右边制定位置的字符串(可用来按照特定位置的字符进行比较)



Syntax:

```mysql
LEFT(str, len);
RIGHT(str, len);
```



Eg:

![Xnip2021-07-26_17-44-55](MySQL Note.assets/Xnip2021-07-26_17-44-55.jpg)









## 2) LENGTH()

- 获取字符串的长度



Syntax:

```mysql
LENGTH(str);
```



![Xnip2021-07-26_17-46-32](MySQL Note.assets/Xnip2021-07-26_17-46-32.jpg)











## 3) LOCATE()



Syntax:

```mysql
LOCATE(subStr, Str)

LOCATE(subStr, Str, Pos)
```

- 找出字符串中子串第一次出现的开始位置
- 如果未找到匹配的子串，则返回0





Eg:

![Xnip2021-07-26_17-51-35](MySQL Note.assets/Xnip2021-07-26_17-51-35.jpg)











## 4) LOWER/UPPER

- 将制定的字段转换为全小写/大写

Syntax:

```mysql
LOWER(str);
UPPER(str);
```



Eg:

![Xnip2021-07-26_20-02-25](MySQL Note.assets/Xnip2021-07-26_20-02-25.jpg)









## 5) LTRIM/RTRIM/TRIM

- 去除字段数据左边/右边的空格/左右两边的空格



Syntax:

```mysql
LTRIM(str);
RTRIM(str);
TRIM(str);
```



Eg:

![Xnip2021-07-26_20-16-23](MySQL Note.assets/Xnip2021-07-26_20-16-23.jpg)



![Xnip2021-07-26_20-17-11](MySQL Note.assets/Xnip2021-07-26_20-17-11.jpg)



![Xnip2021-07-26_20-17-37](MySQL Note.assets/Xnip2021-07-26_20-17-37.jpg)





## 6) SUBSTR()

- 从原字符串中，获取指定范围内的子串

Syntax:

```mysql
SUBSTR(str, pos, len);
```



Eg:

![Xnip2021-07-26_20-22-51](MySQL Note.assets/Xnip2021-07-26_20-22-51.jpg)











