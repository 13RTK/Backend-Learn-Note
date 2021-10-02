# Day61

## Tag: DISTINCT

![Xnip2021-09-23_07-50-49](MySQL Note.assets/Xnip2021-09-23_07-50-49.jpg)



![Xnip2021-09-23_07-50-58](MySQL Note.assets/Xnip2021-09-23_07-50-58.jpg)

题意:

给你一张论坛信息表，其中parent_id为null的表示帖子，其余为parent_id对应的评论，请你查询出每个帖子对应的评论数





思路:

- 首先选择帖子需要筛选出parent_id为null的数据，且因为数据中有重复的部分，所以需要去重
- 之后计算评论也需要去重，且需要通过parent_id与表的sub_id进行自连接匹配，SQL如下

```mysql
select 
    DISTINCT t1.sub_id AS 'post_id',
    (
        select 
            COUNT(distinct t2.sub_id)
        FROM 
            Submissions AS t2
        WHERE t2.parent_id = t1.sub_id) AS 'number_of_comments'
FROM 
    Submissions AS t1
WHERE t1.parent_id is null
ORDER BY t1.sub_id
```

****















# Day62

## Tag: LEFT JOIN

![Xnip2021-09-24_09-02-45](MySQL Note.assets/Xnip2021-09-24_09-02-45.jpg)



![Xnip2021-09-24_09-02-57](MySQL Note.assets/Xnip2021-09-24_09-02-57.jpg)

题意:

给你一张项目人员对应表，一张员工信息表，请你查询出每个项目对应的平均人员工作年限





思路:

- 求平均年限即对employee表中的experience_year字段求平均值，一个AVG就能搞定
- 但项目信息在另一张表中，所以需要我们连接两张表，SQL如下

```mysql
SELECT
    t1.project_id,
    ROUND(AVG(t2.experience_years), 2) AS 'average_years'
FROM
    Project AS t1
LEFT JOIN Employee AS t2 ON t1.employee_id = t2.employee_id
GROUP BY t1.project_id;
```

****











# Day63

## Tag: GROUP BY, HAVING

![MySQL mac ver.](MySQL Note.assets/MySQL mac ver..jpg)



![Xnip2021-09-25_08-06-04](MySQL Note.assets/Xnip2021-09-25_08-06-04.jpg)

题意:

给你一张项目表，请你查询出其中员工数最多的项目





思路:

- 需要注意的是，员工数最多的项目很可能不知一个，所以我们需要先查询出其中最大的员工数量，SQL如下

SQL1:

```mysql
SELECT
	COUNT(employee_id) AS 'emp_num'
FROM
	Project
GROUP BY project_id
ORDER BY emp_num DESC
LIMIT 1;
```







- 之后再根据这个数据来匹配即可，SQL如下

```mysql
SELECT
    project_id
FROM
    Project
GROUP BY project_id
HAVING COUNT(employee_id) = SQL1;
```

****















# Day64

## Tag: DISTINCT, BETWEEN AND

![Xnip2021-09-26_07-36-10](MySQL Note.assets/Xnip2021-09-26_07-36-10.jpg)



![Xnip2021-09-26_07-35-47](MySQL Note.assets/Xnip2021-09-26_07-35-47.jpg)

题意:

给你一张用户活动表，请你计算出截止2019-07-27(包含)30天内平均每个用户的通话数





思路:

- 题目虽然强调了一个有效通话，但其实没什么用，这里唯一的限制就是日期，我们使用DATE_DIFF或者BETWEEN AND都行
- 这里由于数据中存在很多重复字段，所以只能通过COUNT再DISTINCT去重的方式来计算
- 且如果没有匹配数据，则需要将null变为0，所以需要使用IFNULL，SQL如下

```mysql
SELECT
    IFNULL(ROUND(COUNT(DISTINCT session_id) / COUNT(DISTINCT user_id), 2), 0) AS 'average_sessions_per_user'
FROM
    Activity
WHERE activity_date BETWEEN '2019-06-28' AND '2019-07-27'
```

****



















# Day65

## Tag: DISTINCT, COUNT

![Xnip2021-09-27_07-28-11](MySQL Note.assets/Xnip2021-09-27_07-28-11.jpg)



![Xnip2021-09-27_07-29-26](MySQL Note.assets/Xnip2021-09-27_07-29-26.jpg)

题意:

给你一张活动表，一张移除表。请你计算出所有被举报的帖子中，平均每天被移除的比例





思路:

- 查询被举报的表也就是限制字段extra为'spam'，而被移除的帖子id在移除表中，也就是分子
- 而对应的分母在活动表中，两者都用COUNT即可计算，不用直接使用AVG是因为表中id可能重复，所以我们还需要使用DISTINCT去重
- 在计算平均每天的删除比例前，我们还需要计算出每天的删除比例，SQL如下

SQL1:

```mysql
SELECT
    t1.action_date,
    COUNT(DISTINCT t2.post_id) / COUNT(DISTINCT t1.post_id) AS 'remove_percent'
FROM
    Actions AS t1
LEFT JOIN Removals AS t2 ON t1.post_id = t2.post_id
WHERE t1.extra = 'spam'
GROUP BY t1.action_date
```





- 之后再对每天的删除比例求平均值，SQL如下

```mysql
SELECT
    ROUND(AVG(remove_percent * 100), 2) AS 'average_daily_percent'
FROM (
SQL1
) AS t1
```

****













# Day66

## Tag: UNION ALL

![Xnip2021-09-28_07-28-06](MySQL Note.assets/Xnip2021-09-28_07-28-06.jpg)



![Xnip2021-09-28_07-28-16](MySQL Note.assets/Xnip2021-09-28_07-28-16.jpg)

题意:

给你一张好友申请通过表，请你计算出其中好友最多的用户id和其好友数



思路:

- 一个好友申请通过后，双方应该互为好友，但我们计算的时候必须要将一列作为id进行分组，才能计算另一列
- 所以为了将两列都计算在内，我们需要将表中的申请和通过ID交换后，与原表上下连接，SQL如下

SQL1:

```mysql
SELECT 
	requester_id AS 'id',
	accepter_id AS 'friend'
FROM
	request_accepted
UNION all
SELECT 
	accepter_id AS 'id',
	requester_id AS 'friend'
FROM
	request_accepted;
```





- 之后再进行常规的分组和排序即可，SQL如下

```mysql
SELECT
    id,
    COUNT(friend) AS 'num'
FROM (
    SQL1
) AS t1
GROUP BY id
ORDER BY num DESC
LIMIT 1;
```

****

















# Day67

## Tag: ORDER BY, LEFT JOIN

![Xnip2021-09-29_07-29-58](MySQL Note.assets/Xnip2021-09-29_07-29-58.jpg)



![Xnip2021-09-29_07-30-09](MySQL Note.assets/Xnip2021-09-29_07-30-09.jpg)

题意:

给你一张学生信息表，一张部门表，请你查询出其中每个部门的学生数量(没有学生的部门也要显示)，结果按照学生数量降序排列，如果数量相同则按照部门名称的字典序排列



思路:

- 因为没有学生的部门也需要显示，所以我们需要以部门表最主，需要使用左/右连接，最后的排序无非就是两个条件而已，SQL如下

```mysql
SELECT
    t1.dept_name,
    COUNT(t2.student_id) AS 'student_number'
FROM
    department AS t1
LEFT JOIN student AS t2 ON t1.dept_id = t2.dept_id
GROUP BY t1.dept_name
ORDER BY student_number DESC, t1.dept_name;
```

****













# Day68

## Tag: LEFT JOIN, IFNULL

![Xnip2021-09-30_07-41-09](MySQL Note.assets/Xnip2021-09-30_07-41-09.jpg)



![Xnip2021-09-30_07-40-49](MySQL Note.assets/Xnip2021-09-30_07-40-49.jpg)

题意:

给你一张产品信息表，请你查询出其中所有产品在2019-08-16时的价格，如果表中没有2019-08-16及之前的价格，则认为之前的价格为10





思路:

- 首先不去操心没有价格时的判断，先获取16号有价格数据的产品信息
- 将日期进行限制，取出最接近目标日期的，就是我们需要的数据，SQL如下

SQL1:

```mysql
SELECT
	product_id,
	new_price
FROM
	Products
WHERE (product_id, change_date) IN (
	SELECT
		product_id,
		MAX(change_date)
	FROM
		Products
	WHERE change_date <= '2019-08-16'
	GROUP BY product_id
		)
```







- 之后再用原表的id进行连接，对于不符合的数据连接后会出现null值，也就是在目标日期前没有数据的产品
- 使用IFNULL对于null值进行讨论，将其变为10即可，SQL如下

```mysql
SELECT
	t1.product_id,
	IFNULL(t2.new_price, 10) AS 'price'
FROM (
	SELECT
		DISTINCT product_id
	FROM
		Products
	) AS t1
LEFT JOIN (
	SQL1
	) AS t2 ON t1.product_id = t2.product_id
```

****



















# Day69

## Tag: ROUND

![Xnip2021-10-01_11-41-43](MySQL Note.assets/Xnip2021-10-01_11-41-43.jpg)



![Xnip2021-10-01_11-41-25](MySQL Note.assets/Xnip2021-10-01_11-41-25.jpg)

题意:

给你一张配送表，请你计算出其中即时订单的百分比(即订单日期与用户期望日期相同)



思路:

- 因为只有一张表，而需求的数据有一个条件限制，所以需要自连接
- 首先查询出所有即时订单的数量，SQL如下

SQL1:

```mysql
SELECT 
	COUNT(delivery_id) 
FROM
	Delivery
WHERE order_date = customer_pref_delivery_date
```





- 之后再查询出所有订单的数量，SQL如下

SQL2:

```mysql
SELECT 
	COUNT(delivery_id)
FROM
	Delivery
```



- 最后再将结果相除并用ROUND指定小数位数即可，SQL如下

```mysql
SELECT
    ROUND( (SQL1) / (SQL2) * 100 , 2) AS 'immediate_percentage'
```

****















# Day70

## Tag: BETWEEN AND

![Xnip2021-10-02_21-28-34](MySQL Note.assets/Xnip2021-10-02_21-28-34.jpg)



![Xnip2021-10-02_21-28-14](MySQL Note.assets/Xnip2021-10-02_21-28-14.jpg)

题意:

给你一张产品表，一张销售记录表，请你查询出每种产品的平均售价(注意售价会随日期变动)



思路:

- 由于价格会变动，所以我们使用价格乘以数量时还需要限定时间段，使用BETWEEN AND即可
- 之后常规的分组，并计算数量即可，SQL如下

```mysql
SELECT
    t1.product_id,
    ROUND(SUM(t2.units * t1.price) / SUM(t2.units), 2) AS 'average_price'
FROM
    Prices AS t1
INNER JOIN UnitsSold AS t2 ON t1.product_id = t2.product_id
WHERE t2.purchase_date BETWEEN t1.start_date AND t1.end_date
GROUP BY t1.product_id;
```





