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

****













# Day71

## Tag: CASE WHEN

![Xnip2021-10-03_11-39-21](MySQL Note.assets/Xnip2021-10-03_11-39-21.jpg)



![Xnip2021-10-03_11-39-32](MySQL Note.assets/Xnip2021-10-03_11-39-32.jpg)

题意:

给你一张国家信息表，一张天气表，请你查询出每个国家在11月的天气(取决于weather_state的平均值)



思路:

- 平均值只需分组后用AVG即可，但对应的天气有3个选项，所以我们需要分情况才行，可以使用IF嵌套，还是使用CASE WHEN更简洁一些
- 对于日期的限定，如果是年/月，个人建议之间使用YAER()或者MONTH()，这样比较直观，也不需要自己去写具体的左右边界日期，SQL如下

```mysql
SELECT
    t1.country_name,
    CASE 
    WHEN AVG(t2.weather_state) <= 15 THEN 'Cold'
    WHEN AVG(t2.weather_state) >= 25 THEN 'Hot'
    ELSE 'Warm'
    END AS 'weather_type'
FROM
    Countries AS t1
INNER JOIN Weather AS t2 ON t1.country_id = t2.country_id
WHERE MONTH(t2.day) = 11
GROUP BY t1.country_name;
```

****

















# Day72

## Tag: DISTINCT, ROUND

![Xnip2021-10-04_10-48-50](MySQL Note.assets/Xnip2021-10-04_10-48-50.jpg)



![Xnip2021-10-04_10-51-43](MySQL Note.assets/Xnip2021-10-04_10-51-43.jpg)

题意:

给你一张用户信息表，一张注册记录表，请你查询出所有赛事的用户注册率





思路:

- 分子为每个赛事的报名人数，在注册记录表中，使用COUNT计算即可
- 分子则为用户表中用户的数量，同样使用COUNT计算即可，SQL如下:

```mysql
SELECT
    contest_id,
    ROUND(COUNT(user_id) / (SELECT COUNT(user_id) FROM Users) * 100, 2) AS 'percentage'
FROM
    Register
GROUP BY contest_id
ORDER BY percentage DESC, contest_id;
```

****



















# Day73

## Tag: COUNT OVER()

![Xnip2021-10-05_10-55-06](MySQL Note.assets/Xnip2021-10-05_10-55-06.jpg)



![Xnip2021-10-05_10-55-28](MySQL Note.assets/Xnip2021-10-05_10-55-28.jpg)



![Xnip2021-10-05_10-56-41](MySQL Note.assets/Xnip2021-10-05_10-56-41.jpg)

题意:

给你一张员工信息表，请查询出每个员工的id和其对应组内的员工总数







思路:

- 要求对应组内的员工数，常规思路就是通过team_id分组，使用COUNT进行计算，但这样写的话就需要创建一张临时表了，可行但不够优雅
- 最简单的方式则是直接使用窗口函数CONUT OVER()，SQL如下

```mysql
SELECT
    employee_id,
    COUNT(employee_id) OVER(
        PARTITION BY team_id
    ) AS 'team_size'
FROM
    Employee;
```

- 这样在一个字段中就解决了分组和求和的操作，不需要连接表






- 但这样的操作仅限于MySQL8.0或者MariaDB等支持窗口函数的高版本才行，如果和我一样在用5.7呢？
- 那么我们借鉴这样的思路，同样通过一个字段自连接来解决问题，SQL如下

```mysql
SELECT
    t2.employee_id,
    (
        SELECT
            COUNT(employee_id)
        FROM
            Employee AS t1
        WHERE t1.team_id = t2.team_id
    ) AS 'team_size'
FROM
    Employee AS t2;
```

****













# Day74

## Tag: REGEXP, *

![Xnip2021-10-06_13-18-27](MySQL Note.assets/Xnip2021-10-06_13-18-27.jpg)



![Xnip2021-10-06_13-18-37](MySQL Note.assets/Xnip2021-10-06_13-18-37.jpg)



![Xnip2021-10-06_13-17-48](MySQL Note.assets/Xnip2021-10-06_13-17-48.jpg)

题意:

给你一张用户信息表，请你查询出邮箱地址符合条件的用户的所有信息，邮箱地址条件:

以字母开头，邮箱前缀为字母，数字，"-"，"_"，"."，"/"，后缀为"@leetcode.com"





思路:

- 该题目明显考察的是正则表达式，我们一点一点的处理
- 首先是开头，正则表达式中使用"^"来表示开头，题目要求开头为字母(大小写)，所以我们使用[]包裹可以表示匹配其中的一个
- 在[]中使用-表示匹配这个范围内的，所以开头如下:

```mysql
^[a-zA-Z]
```



- 除了开头，前缀剩余的部分还应该匹配数字，和其他特殊符号，由于这些特殊符号在正则表达式中会被解释，所以我们要在它们前面使用\来转义，此时正则为:

```
^[a-zA-Z][a-zA-Z0-9\_\-\.]
```



- 这样写只是匹配了前两个位置而已，前缀剩余部分的规则与第二个位置相同，此时我们使用*即可将前一个任意字符匹配任意次，正则如下

```
^[a-zA-Z][a-zA-Z0-9\_\-\.]*
```



- 最后我们匹配一下邮箱后缀，正则表达式用$来表示结尾，其中邮箱的"."也需要转义，所以最终正则为:

```
^[a-zA-Z][a-zA-Z0-9\_\.\-]*@leetcode\.com$
```





- 将该正则结合到MySQL的WHERE字句中，最终SQL如下:

```mysql
SELECT
    *
FROM
    Users
WHERE mail REGEXP '^[a-zA-Z][a-zA-Z0-9\_\.\-]*@leetcode\.com$'
```









