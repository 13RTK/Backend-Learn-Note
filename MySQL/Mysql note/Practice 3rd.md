# Day61

## Tag: DENSE_RANK(), ORDER BY



![Xnip2021-07-19_16-17-15](MySQL Note.assets/Xnip2021-07-19_16-17-15.jpg)



![Xnip2021-07-19_16-16-31](MySQL Note.assets/Xnip2021-07-19_16-16-31.jpg)



![Xnip2021-07-19_16-33-12](MySQL Note.assets/Xnip2021-07-19_16-33-12.jpg)

题意:

给你一张员工信息表和一张部门表，请你查询出每个部门中薪资排名前三的员工信息和对应的部门(包含相同排名)







思路1:

- 既然要按照部门分类还要考虑排名，我们可以使用窗口函数DENSE_RANK()，在其中指定DepartmentId为PARTITION BY字段即可实现按照部门分组，再指定ORDER BY字段为Salary即可进行排名，最后再取上别名来记录排名并对应其他的数据，SQL如下:

SQL1:

```mysql
SELECT
	DENSE_RANK() OVER (
PARTITION BY DepartmentId
ORDER BY Salary DESC
	) AS 'ranking', DepartmentId, Name, Salary
FROM
	Employee
```







- 之后我们只需要取出该结果中ranking字段小于等于3的数据，并将DepartmentId字段与部门表进行连接对应即可，SQL如下

SQL2:

```mysql
SELECT
	t2.Name AS 'Department',
	t1.Name AS 'Employee',
	t1.Salary
FROM
	(
   SQL1
  ) AS t1
INNER JOIN Department AS t2 ON t1.DepartmentId = t2.Id
WHERE t1.ranking <= 3;
```









思路2:

- 当然也可以不使用窗口函数，将员工表自连接，并对重复的数据去重后，计算大于每个数据本身的数据个数即为其排名
- 最后判断排名小于3即可，SQL如下:

```mysql
SELECT
	t2.Name AS 'Department',
	t1.Name AS 'Employee',
	t1.Salary
FROM
	Employee AS t1
INNER JOIN Department AS t2 ON t1.DepartmentId = t2.Id
WHERE 3 > (
	SELECT
  	COUNT(DISTINCT t3.Salary)
 	FROM
  	Employee AS t3
 	WHERE t3.Salary > t1.Salary
  AND t1.DepartmentId = t3.DepartmentId
);
```

****























# Day62

## Tag: CASE, MOD, COUNT



![Xnip2021-07-20_11-21-07](MySQL Note.assets/Xnip2021-07-20_11-21-07.jpg)



![Xnip2021-07-20_11-16-53](MySQL Note.assets/Xnip2021-07-20_11-16-53.jpg)

题意:

给你一张座位表，请你查询出将相邻两个位置的人员进行交换后的结果(如果总人数为奇数，则不改变最后一个人员)









思路:

- 题意虽然是改变位置上的人员，但我们反过来想：改变每个人对应的id不就行了吗？
- 如果总人数为偶数，那么只需要将原来id为奇数的加一即可，原来为偶数的减一即可
- 如果总人数为奇数，那么我们只需要对最后一位进行判定就行了
- 总人数的计算可以使用COUNT()，查询后作为一个单独的数据进行判断就是了，SQL如下:

SQL1

```mysql
SELECT
		COUNT(id) AS 'counts'
	FROM
		seat
```





- 之后再根据其中的计数结果分情况讨论就是了，SQL如下

SQL:

```mysql
SELECT
	(CASE 
	WHEN id % 2 != 0 AND counts = id THEN
		id
	WHEN id % 2 != 0 AND counts != id THEN
	  id + 1
	ELSE
		id - 1
END) AS id,
	student
FROM
	seat,
	(
	SQL1
	) AS t1
ORDER BY id;
```

****

















# Day1

2021-07-24

## Tag: SUM() OVER



![Xnip2021-07-24_20-51-24](MySQL Note.assets/Xnip2021-07-24_20-51-24.jpg)



![Xnip2021-07-24_20-56-56](MySQL Note.assets/Xnip2021-07-24_20-56-56.jpg)



题意:

给你一张成绩统计表，其中统计了各个等级的总人数，请你根据这张表查询出每个等级的学生最差的排名



例子:

A | 3

B | 2

结果:

A | 3

B | 5

(等级为A的人排名至少为3，而等级为B的人排名至少为5)









思路:

- 这道题其实就是简单的排序累加，很巧的是，在SQL Day59中我们通过SUM() OVER这个窗口函数实现了这样的操作，所以我们将排序字段换一下就能解决这个问题，SQL如下:

```mysql
SELECT
	grade,
	SUM(number) OVER(
  ORDER BY grade
  ) AS 't_rank'
FROM
	class_grade;
```

****













# Day2

## Tag: SUM() OVER, SUM



![Xnip2021-07-25_18-35-45](MySQL Note.assets/Xnip2021-07-25_18-35-45.jpg)



![Xnip2021-07-25_18-32-08](MySQL Note.assets/Xnip2021-07-25_18-32-08.jpg)



题意:

给你一张等级表，请你找出所有排名为中位数的等级



思路:

- 中位数要么有一个，要么有两个，所以为了将两个都取到，我们需要从倒序和顺序两个方向取中位数
- 为了比较，我们需要从倒序和顺序两个方向获取各个等级的排名，这里就需要使用昨天的写法了，SQL如下:

SQL1:

```mysql
SELECT
		grade,
		(SELECT
			SUM(number)
		FROM
			class_grade
		) AS 'total',
		SUM(number) OVER (
		ORDER BY grade
		) AS 'a',
		SUM(number) OVER (
		ORDER BY grade DESC
		) AS 'd'
	FROM class_grade
```





- 之后我们将等级限定在这个范围内，并制定排名要大于等于总数的一半，SQL如下:

SQL:

```mysql
SELECT
	grade
FROM (
	SQL1) AS t1
WHERE a >= total / 2 AND d >= total / 2
ORDER BY grade;
	
```

****









# Day3

## Tag: SUM(), LIMIT, INNER JOIN



![Xnip2021-07-26_14-00-13](MySQL Note.assets/Xnip2021-07-26_14-00-13.jpg)



![Xnip2021-07-26_14-00-27](MySQL Note.assets/Xnip2021-07-26_14-00-27.jpg)

题意:

给你一张积分表，其中记录了每个用户id的加分情况；再给你一张用户表，其中记录了用户ID与用户名的对应关系

请你查出总分最高的用户名和对应的分数







思路:

- 由于我们要查询的是积分最高的用户，所以我们需要将每个用户的积分加起来比较每个用户的积分情况
- 所以需要使用SUM()来累加各个用户的积分，再使用GROUP BY来划分出各个用户即可
- 之后再将积分表和用户表连接起来就能将id变为用户名了
- 鉴于给出的表有可能是乱序的，所以我们需要按照积分总和进行倒序排列
- 之后再使用LIMTI取出一条数据即可，SQL如下

```mysql
SELECT 
    t2.name,
    SUM(t1.grade_num) AS 'grade_sum'
FROM
    grade_info AS t1
INNER JOIN user AS t2 ON t1.user_id = t2.id
GROUP BY t2.name
ORDER BY grade_sum DESC
LIMIT 1;
```

****













# Day4

## Tag: DENSE_RANK(), ORDER BY



![Xnip2021-07-27_09-21-33](MySQL Note.assets/Xnip2021-07-27_09-21-33.jpg)



![Xnip2021-07-27_09-23-18](MySQL Note.assets/Xnip2021-07-27_09-23-18.jpg)



题意:

给你一张分数表和一张id对应的语言表，请你找出其中每个语言得分前二名的id，语言和分数信息



思路:

- 首先我们需要获取每条数据的排名，且需要按照语言分类排序，这里可以想到使用DENSE_RANK()，在OVER()中指定PARTITION字段为language_id即可实现按照语言分组，再指定ORDER BY字段为score且为降序DESC即可，SQL如下:

SQL1:

```mysql
SELECT
	id,
	language_id,
	score,
	DENSE_RANK() OVER(
	PARTITION BY language_id
	ORDER BY score DESC
	) AS 'rank'
FROM
	grade;
```







- 之后我们再用language表连接这张临时表，将language_id转换为name，并限定排名字段rank小于等于2即可
- 在最后还需要满足题目要求的排序，只需要我们按照顺序写好就行了，SQL如下:

```mysql
SELECT
	t2.id,
	t1.name,
	t2.score
FROM
	`language` AS t1
INNER JOIN
(
SQL1
	) AS t2 ON t2.language_id = t1.id
WHERE t2.rank <= 2
ORDER BY t1.name ASC, t2.score DESC, t2.id ASC;
```







