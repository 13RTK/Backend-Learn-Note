# Day261

## Tag: EXISTS, LIMIT

![Xnip2022-04-11_06-54-57](MySQL Note.assets/Xnip2022-04-11_06-54-57.jpg)



![Xnip2022-04-11_06-55-14](MySQL Note.assets/Xnip2022-04-11_06-55-14.jpg)

<hr>





![Xnip2022-04-11_07-04-37](MySQL Note.assets/Xnip2022-04-11_07-04-37.jpg)



![Xnip2022-04-11_07-04-25](MySQL Note.assets/Xnip2022-04-11_07-04-25.jpg)

<hr>



![Xnip2022-04-11_07-14-55](MySQL Note.assets/Xnip2022-04-11_07-14-55.jpg)



![Xnip2022-04-11_07-15-00](MySQL Note.assets/Xnip2022-04-11_07-15-00.jpg)

题意:

给你一张成绩表，请你查询出其中每个学生的最高成绩和对应的科目，如果有多个科目的成绩相同，则取course_id小的那个



思路:

- 因为课程id可能会有多个，所以我们需要先查询出每个学生对应的最高分数，SQL如下

SQL1:

```mysql
SELECT
	student_id,
	MAX(grade) AS 'max_grade'
FROM
	Enrollments
GROUP BY student_id
```



- 之后通过id和分数进行匹配，并取最小的course_id即可，最终SQL如下

```mysql
SELECT
    student_id,
    MIN(course_id) AS 'course_id',
    grade
FROM
    Enrollments AS t1
WHERE (student_id, grade) IN (
    SQL1
)
GROUP BY student_id, grade
ORDER BY student_id
```

<hr>



![Xnip2022-04-11_07-33-41](MySQL Note.assets/Xnip2022-04-11_07-33-41.jpg)



![Xnip2022-04-11_07-33-27](MySQL Note.assets/Xnip2022-04-11_07-33-27.jpg)

<hr>













# Day262

## Tag: CASE

![Xnip2022-04-12_07-21-47](MySQL Note.assets/Xnip2022-04-12_07-21-47.jpg)



![Xnip2022-04-12_07-22-47](MySQL Note.assets/Xnip2022-04-12_07-22-47.jpg)

题意:

给你一张变量表，一张表达式表，请你查询出其中所有表达式的结果





思路:

- 很明显，我们需要将变量的值对应在表达式中去，但两个变量结合三个符号有很多种情况，所以直接列出所有情况是不太好的
- 其实我们只需要根据三种符号列出对应的结果即可，且为了获取变量值的时候不受到表达式表中同行不同变量的影响，我们应该将变量表连接两次以分别获取两个值，最终SQL如下

```mysql
SELECT
    t1.left_operand,
    t1.operator,
    t1.right_operand,
    CASE WHEN (operator = '>' AND t2.value > t3.value)
    OR (operator = '=' AND t2.value = t3.value)
    OR (operator = '<' AND t2.value < t3.value)
    THEN 'true'
    ELSE 'false' END AS 'value'
FROM
    Expressions AS t1
INNER JOIN Variables AS t2 ON t1.left_operand = t2.name
INNER JOIN Variables AS t3 ON t1.right_operand = t3.name
```

<hr>



![Xnip2022-04-12_07-31-56](MySQL Note.assets/Xnip2022-04-12_07-31-56.jpg)



![Xnip2022-04-12_07-31-36](MySQL Note.assets/Xnip2022-04-12_07-31-36.jpg)

<hr>



![Xnip2022-04-12_07-35-19](MySQL Note.assets/Xnip2022-04-12_07-35-19.jpg)



![Xnip2022-04-12_07-35-23](MySQL Note.assets/Xnip2022-04-12_07-35-23.jpg)

<hr>



![Xnip2022-04-12_07-38-56](MySQL Note.assets/Xnip2022-04-12_07-38-56.jpg)



![Xnip2022-04-12_07-38-44](MySQL Note.assets/Xnip2022-04-12_07-38-44.jpg)

<hr>















# Day263

## Tag: CROSS JOIN

![Xnip2022-04-13_07-28-55](MySQL Note.assets/Xnip2022-04-13_07-28-55.jpg)



![Xnip2022-04-13_07-30-05](MySQL Note.assets/Xnip2022-04-13_07-30-05.jpg)

题意:

给你一张学生信息表，一张学科信息表，一张考试记录表，请你查询出其中每个学生参加每个科目的次数





思路:

- 这里需要注意的是，不管该学生是否参与了所有的科目，我们都要查询出每个学生对应的每个科目的次数
- 所以这里需要一个由学生id和学科组成的嵌套表侧栏，我们通过交叉连接生成一个笛卡尔积即可
- 最后将生成的笛卡尔积作为驱动表连接到考试记录表即可，最终SQL如下(COUNT(字段)不会统计NULL)

```mysql
SELECT
    t1.student_id,
    t1.student_name,
    t2.subject_name,
    COUNT(t3.subject_name) AS 'attended_exams'
FROM
    Students AS t1
CROSS JOIN Subjects AS t2
LEFT JOIN Examinations AS t3 ON t1.student_id = t3.student_id
AND t2.subject_name = t3.subject_name
GROUP BY t1.student_id, t1.student_name, t2.subject_name
ORDER BY t1.student_id, t2.subject_name
```

<hr>





![Xnip2022-04-13_07-59-26](MySQL Note.assets/Xnip2022-04-13_07-59-26.jpg)



![Xnip2022-04-13_07-59-43](MySQL Note.assets/Xnip2022-04-13_07-59-43.jpg)

<hr>



![Xnip2022-04-13_08-00-21](MySQL Note.assets/Xnip2022-04-13_08-00-21.jpg)



![Xnip2022-04-13_08-00-26](MySQL Note.assets/Xnip2022-04-13_08-00-26.jpg)

<hr>


![Xnip2022-04-13_08-01-34](MySQL Note.assets/Xnip2022-04-13_08-01-34.jpg)

![Xnip2022-04-13_08-05-56](MySQL Note.assets/Xnip2022-04-13_08-05-56.jpg)

<hr>











# Day264

## Tag: CASE

![Xnip2022-04-14_07-07-47](MySQL Note.assets/Xnip2022-04-14_07-07-47.jpg)



![Xnip2022-04-14_07-09-38](MySQL Note.assets/Xnip2022-04-14_07-09-38.jpg)

题意:

给你一张国家信息表，一张天气信息表，请你根据每个国家2019年11月的平均天气状态值查询出每个国家对应的天气类型





思路:

- 很明显，我们需要根据状态值的平均值查询进行分支判断
- 获取平均值则需要分组，而分支判断则使用CASE即可，最后用WHERE子句限制日期即可，最终SQL如下

```mysql
SELECT
    t1.country_name,
    CASE WHEN AVG(t2.weather_state) <= 15 THEN 'Cold'
    WHEN AVG(t2.weather_state) >= 25 THEN 'Hot'
    ELSE 'Warm' END AS 'weather_type'
FROM
    Countries AS t1
INNER JOIN Weather AS t2 ON t1.country_id = t2.country_id
WHERE YEAR(t2.day) = 2019
AND MONTH(t2.day) = 11
GROUP BY t1.country_name
```

<hr>



![Xnip2022-04-14_07-19-28](MySQL Note.assets/Xnip2022-04-14_07-19-28.jpg)



![Xnip2022-04-14_07-19-47](MySQL Note.assets/Xnip2022-04-14_07-19-47.jpg)

题意:

给你一张座位信息表，请你将其中相邻座位的学生进行交换，如果最后一个学生的id是奇数则他/她不交换，请你查询出交换后的信息





思路:

- 这里一看到交换可能就想到要更新表，或者通过判断id的奇偶来交换姓名，但这样做其实很麻烦
- 最简单的方式还是替换id:
- 如果id为奇数，当前id等于总数，那么说明其是最后一个学生，则不对其进行任何处理；如果只是奇数，则id + 1；是偶数则id - 1，就这样解决了
- 最终SQL如下

```mysql
SELECT
    CASE WHEN id % 2 != 0 AND id = (SELECT COUNT(*) FROM Seat) THEN id
    WHEN id % 2 != 0 THEN id + 1
    WHEN id % 2 = 0 THEN id - 1
    ELSE NULL END AS 'id',
    student
FROM
    Seat
ORDER BY id
```

<hr>



![Xnip2022-04-14_07-32-07](MySQL Note.assets/Xnip2022-04-14_07-32-07.jpg)



![Xnip2022-04-14_07-32-11](MySQL Note.assets/Xnip2022-04-14_07-32-11.jpg)

<hr>





![Xnip2022-04-14_07-42-32](MySQL Note.assets/Xnip2022-04-14_07-42-32.jpg)



![Xnip2022-04-14_07-42-37](MySQL Note.assets/Xnip2022-04-14_07-42-37.jpg)

<hr>













# Day265

## Tag: 连续空余座位

![Xnip2022-04-15_07-19-29](MySQL Note.assets/Xnip2022-04-15_07-19-29.jpg)



![Xnip2022-04-15_07-26-43](MySQL Note.assets/Xnip2022-04-15_07-26-43.jpg)

题意:

给你一张电影院的座位信息，请你查询出其中连续的空缺座位号



思路:

- 连续其实意味着id相差为1，而空缺则说明free字段值为1，所以我们通过自连接即可，SQL如下

```mysql
SELECT
    t1.seat_id
FROM
    Cinema AS t1
INNER JOIN Cinema AS t2 ON ABS(t2.seat_id - t1.seat_id) = 1
WHERE (t1.free && t2.free) = 1
GROUP BY t1.seat_id
ORDER BY t1.seat_id
```

<hr>



![Xnip2022-04-15_07-45-36](MySQL Note.assets/Xnip2022-04-15_07-45-36.jpg)



![Xnip2022-04-15_07-45-41](MySQL Note.assets/Xnip2022-04-15_07-45-41.jpg)

<hr>



![Xnip2022-04-15_07-49-30](MySQL Note.assets/Xnip2022-04-15_07-49-30.jpg)



![Xnip2022-04-15_07-49-34](MySQL Note.assets/Xnip2022-04-15_07-49-34.jpg)

<hr>





![Xnip2022-04-15_07-54-27](MySQL Note.assets/Xnip2022-04-15_07-54-27.jpg)



![Xnip2022-04-15_07-54-32](MySQL Note.assets/Xnip2022-04-15_07-54-32.jpg)

题意:

给你一张用户日志表，请你查询出其中在同一时刻通过不同ip地址登陆的用户



思路:

- 其实所谓的某一时刻，就是其他的登陆记录时间在其他登陆记录的区间内
- 所以我们通过BETWEEN AND就能进行限制，SQL如下

```mysql
SELECT
    t1.account_id
FROM
    LogInfo AS t1
INNER JOIN LogInfo AS t2 ON t2.login BETWEEN t1.login AND t1.logout
AND t1.account_id = t2.account_id
AND t1.ip_address != t2.ip_address
GROUP BY t1.account_id
```

<hr>









# Day266

## Tag: INNER JOIN

![Xnip2022-04-16_08-50-41](MySQL Note.assets/Xnip2022-04-16_08-50-41.jpg)



![Xnip2022-04-16_08-50-51](MySQL Note.assets/Xnip2022-04-16_08-50-51.jpg)

 题意:

给你一张点的坐标表，请你查询出其中任意两点可以形成的矩形的面积(需要大于0)





思路:

- 题目说是任意两点，但其实有一些限制在其中:
- 首先不能和自身组合，其次不能向两边延伸，否则会出现重复计算，所以我们只能使用不等式连接
- 其次，面积不得为0，面积其实就是两点横纵坐标之差的绝对值的乘积
- 最简单的方法是使用`HAVING`子句，因为面积是查询之后得到的字段信息，通过`HAVING`才能对其进行限制，所以我们使用`HAVING > 0`就能解决
- 但为什么我用了`WHERE`子句呢？这样需要写两行，明显没有`HAVING`来得优雅
- 其实`WHERE`的写法性能会更好一些，因为`HAVING`是针对所有计算出来的结果进行限制，而`WHERE`则是在计算之前进行限制，所以节省了部分开销，所以我选择使用看起来更麻烦的`WHERE` 
- SQL如下

```mysql
SELECT
    t1.id AS 'p1',
    t2.id AS 'p2',
    ABS(t2.x_value - t1.x_value) * ABS(t2.y_value - t1.y_value) AS 'area'
FROM
    Points AS t1
INNER JOIN Points AS t2 ON t1.id < t2.id
WHERE (t2.x_value - t1.x_value) != 0
AND (t2.y_value - t1.y_value) != 0
ORDER BY area DESC, t1.id, t2.id
```

<hr>





![Xnip2022-04-16_09-13-13](MySQL Note.assets/Xnip2022-04-16_09-13-13.jpg)



![Xnip2022-04-16_09-13-20](MySQL Note.assets/Xnip2022-04-16_09-13-20.jpg)

<hr>



![Xnip2022-04-16_09-22-19](MySQL Note.assets/Xnip2022-04-16_09-22-19.jpg)



![Xnip2022-04-16_09-22-24](MySQL Note.assets/Xnip2022-04-16_09-22-24.jpg)

<hr>











# Day267

## Tag: EXISTS, HAVING

![Xnip2022-04-17_08-18-06](MySQL Note.assets/Xnip2022-04-17_08-18-06.jpg)



![Xnip2022-04-17_08-18-18](MySQL Note.assets/Xnip2022-04-17_08-18-18.jpg)

题意:

给你一张商品信息表，一张订单信息表，请你查询出其中每类商品最近一天的订单信息



思路:

- 最近一天即日期值最大，所以我们需要先分组获取每种商品的最大日期值才行
- 获取后，我们可以直接使用IN来匹配product_id和order_date字段，但对于子查询参数使用IN显然不太好，所以我选择改写为EXISTS，最终SQL如下

```mysql
SELECT
    t1.product_name,
    t1.product_id,
    t2.order_id,
    t2.order_date
FROM
    Products AS t1
INNER JOIN Orders AS t2 ON t1.product_id = t2.product_id
WHERE EXISTS (
    SELECT
        t3.product_id,
        MAX(t3.order_date) AS 'order_date'
    FROM
        Orders AS t3
    WHERE t1.product_id = t3.product_id
    GROUP BY product_id
    HAVING order_date = t2.order_date
)
ORDER BY t1.product_name, t1.product_id, t2.order_id
```

<hr>



![Xnip2022-04-17_09-04-21](MySQL Note.assets/Xnip2022-04-17_09-04-21.jpg)



![Xnip2022-04-17_09-05-30](MySQL Note.assets/Xnip2022-04-17_09-05-30.jpg)

<hr>



![Xnip2022-04-17_09-12-35](MySQL Note.assets/Xnip2022-04-17_09-12-35.jpg)



![Xnip2022-04-17_09-12-40](MySQL Note.assets/Xnip2022-04-17_09-12-40.jpg)

题意:

给你一张用户信息表，一张产品信息表，请你查询出其中购买了所有产品的用户id



思路:

- 如果将所有的用户进行分组，可以发现我们需要的用户与其他用户的不同点: 其购买产品的种类 = 产品表中的产品种类
- 所以我们只需要利用目标集合的特点即可，针对集合进行筛选需要使用HAVING，所以SQL如下

```mysql
SELECT
    t1.customer_id
FROM
    Customer AS t1
INNER JOIN Product AS t2 ON t1.product_key = t2.product_key
GROUP BY t1.customer_id
HAVING COUNT(DISTINCT t2.product_key) = (SELECT COUNT(*) FROM Product)
```

<hr>








# Day268

## Tag: UNION ALL, GROUP BY, LIMIT

![](MySQL Note.assets/Xnip2022-04-18_08-03-54.jpg)



![Xnip2022-04-18_08-12-36](MySQL Note.assets/Xnip2022-04-18_08-12-36.jpg)

题意:

给你一张电影信息表，一张用户信息表，一张评分记录表，请你查询出其中评论次数最多的用户对应的用户名，以及2020年2月中平均评分最高的电影名称



思路:

- 很明显，我们需要查询两个不同的结果并对两个结果做加法
- 评论次数只需要分组后直接在`ORDER BY`中对`COUNT(*)`排序即可
- 之后电影评分也是如此，只不过多了对日期的限制，并换为按照`AVG`排序即可
- 最后要将两个查询视作两个集合，对它们做并集操作，因为两个查询的结果截然不同，所以我们最好使用`UNION ALL`而不是简单的`UNION`
- 因为`UNION`在去重的同时会进行排序，加上`ALL`选项就能避免这个操作，从而节省开销，实现SQL优化

<hr>





![Xnip2022-04-18_08-25-29](MySQL Note.assets/Xnip2022-04-18_08-25-29.jpg)



![Xnip2022-04-18_08-37-14](MySQL Note.assets/Xnip2022-04-18_08-37-14.jpg)

<hr>









# Day269

## Tag: Sub Query

![Xnip2022-04-19_07-06-03](MySQL Note.assets/Xnip2022-04-19_07-06-03.jpg)



![Xnip2022-04-19_07-06-11](MySQL Note.assets/Xnip2022-04-19_07-06-11.jpg)

题意:

给你一张用户活跃记录表，请你查询出首次登陆后第二天再次登陆的玩家比率





思路:

- 从题目上看，我们需要统计首次登陆后第二天的人数，以及总人数
- 但在统计首次登陆后第二天的人数之前，我们需要知道每个用户第一次登陆的时间才行，SQL如下

SQL1:

```mysql
SELECT
	player_id,
	MIN(event_date) AS 'first_day'
FROM
	Activity
GROUP BY player_id
```



- 有了首次登陆的记录后，我们只需要根据日期和id进行外连接即可
- 这样一来，根据这张临时表可以获取总人数，而与其连接的表中不为NULL的字段数量就是第二天的登陆人数了，SQL如下

SQL2:

```mysql
SELECT
    ROUND(COUNT(t2.player_id) / COUNT(*), 2) AS 'fraction'
FROM (
    SQL1
) AS t1
LEFT JOIN Activity AS t2 ON DATEDIFF(t2.event_date, t1.first_day) = 1
AND t1.player_id = t2.player_id
```

<hr>







![Xnip2022-04-19_07-32-55](MySQL Note.assets/Xnip2022-04-19_07-32-55.jpg)



![Xnip2022-04-19_07-33-01](MySQL Note.assets/Xnip2022-04-19_07-33-01.jpg)

<hr>











# Day270

## Tag: COUNT() OVER

![Xnip2022-04-20_07-58-39](MySQL Note.assets/Xnip2022-04-20_07-58-39.jpg)



SQL:

```mysql
SELECT
    employee_id,
    COUNT(*) OVER(
        PARTITION BY team_id
    ) AS 'team_size'
FROM
    Employee
```

<hr>





![Xnip2022-04-20_08-08-00](MySQL Note.assets/Xnip2022-04-20_08-08-00.jpg)



![Xnip2022-04-20_08-21-10](MySQL Note.assets/Xnip2022-04-20_08-21-10.jpg)

题意:

给你一张分数记录表，请你查询出其中每种性别在每一天的累积分数



思路:

- 题目本身其实很简单，因为是求不同性别的分数，所以需要根据性别分组，之后需要根据日期值来累加
- 如果熟悉窗口函数的话，其实很容易就会想到使用SUM(col) OVER()，此时SQL如下

```mysql
SELECT
    gender,
    day,
    SUM(score_points) OVER(
        PARTITION BY gender
        ORDER BY day
    ) AS 'total'
FROM
    Scores
```





- 那么问题来了，MySQL目前只有8.0才实现了窗口函数，如果是之前那些不支持的版本呢？
- 此时我们就要考虑标准SQL的解法了，其实累加计算就是在根据性别分组的前提下，生成每个日期对应的递归子集，每个子集的和值就是对应日期范围内的累计值
- 而获取递归子集的方法就是非等值自连接，用连接的方式写如下:

```mysql
SELECT
    t1.gender,
    t1.day,
    SUM(t2.score_points) AS 'total'
FROM
    Scores AS t1
INNER JOIN Scores AS t2 ON t1.day >= t2.day
AND t1.gender = t2.gender
GROUP BY t1.gender, t1.day
ORDER BY t1.gender, t1.day
```



- 其实这个连接的部分也可以放在SELECT列表里，此时SQL如下

```mysql
SELECT
    t1.gender,
    t1.day,
    (
        SELECT
            SUM(t2.score_points)
        FROM
            Scores AS t2
        WHERE t1.day >= t2.day
        AND t1.gender = t2.gender
    ) AS 'total'
FROM
    Scores AS t1
ORDER BY gender, day
```

<hr>





![Xnip2022-04-20_08-31-17](MySQL Note.assets/Xnip2022-04-20_08-31-17.jpg)



![Xnip2022-04-20_08-31-01](MySQL Note.assets/Xnip2022-04-20_08-31-01.jpg)

<hr>









# Day271

## Tag: DENSE_RANK

![Xnip2022-04-21_07-38-37](MySQL Note.assets/Xnip2022-04-21_07-38-37.jpg)



![Xnip2022-04-21_07-43-54](MySQL Note.assets/Xnip2022-04-21_07-43-54.jpg)

<hr>



![Xnip2022-04-21_07-53-59](MySQL Note.assets/Xnip2022-04-21_07-53-59.jpg)



![Xnip2022-04-21_07-54-10](MySQL Note.assets/Xnip2022-04-21_07-54-10.jpg)

题意:

给你一张产品信息表，一张订单信息表，请你查询出其中每个商品对应的最近一次订单





思路:

- 首先，最近一次其实就是指日期值最大，所以我们首先要求的是每件商品对应的最近的日期值，SQL如下

SQL1:

```mysql
SELECT
	product_id,
	MAX(order_date) AS 'last_date'
FROM
	Orders
GROUP BY product_id
```



- 有了这张临时表/内联视图后，我们只需要查询出每件商品中日期值与其对应的即可，最终SQL如下

```mysql
SELECT
    t1.product_name,
    t1.product_id,
    t2.order_id,
    t2.order_date
FROM
    Products AS t1
INNER JOIN Orders AS t2 ON t1.product_id = t2.product_id
INNER JOIN (
    SQL1
) AS t3 ON t2.product_id = t3.product_id AND t2.order_date = t3.last_date
ORDER BY t1.product_name, t1.product_id, t2.order_id
```

<hr>



![Xnip2022-04-21_08-16-36](MySQL Note.assets/Xnip2022-04-21_08-16-36.jpg)



![Xnip2022-04-21_08-16-58](MySQL Note.assets/Xnip2022-04-21_08-16-58.jpg)

<hr>



![Xnip2022-04-21_08-28-25](MySQL Note.assets/Xnip2022-04-21_08-28-25.jpg)



![Xnip2022-04-21_08-28-33](MySQL Note.assets/Xnip2022-04-21_08-28-33.jpg)

<hr>









# Day272

## Tag: CTE, LEAD

![Xnip2022-04-22_07-57-09](MySQL Note.assets/Xnip2022-04-22_07-57-09.jpg)



![Xnip2022-04-22_07-57-16](MySQL Note.assets/Xnip2022-04-22_07-57-16.jpg)

题意:

给你一张用户关系表，请查询出其中所有共同关注数量最多的用户对





思路:

- 首先我们自然需要求出最大的共同关注数，这里用自连接即可，SQL如下

SQL1:

```mysql
SELECT
		t1.user_id AS 'user1_id',
		t2.user_id AS 'user2_id',
		COUNT(*) AS 'cnt'
FROM
		Relations AS t1
INNER JOIN Relations AS t2 ON t1.follower_id = t2.follower_id
WHERE t1.user_id < t2.user_id
GROUP BY user1_id, user2_id
ORDER BY cnt DESC
```



- 有了该表后，我们通过取其第一条记录的cnt列值即可获取对应的最大关注数
- 此时再查询出所有匹配该数的记录即可，最终SQL如下

```mysql
WITH summary AS (
    SQL1
)
SELECT
    user1_id,
    user2_id
FROM
    summary
WHERE cnt = (SELECT cnt FROM summary LIMIT 1)
```

<hr>



![Xnip2022-04-22_08-12-08](MySQL Note.assets/Xnip2022-04-22_08-12-08.jpg)



![Xnip2022-04-22_08-11-55](MySQL Note.assets/Xnip2022-04-22_08-11-55.jpg)



![Xnip2022-04-22_08-33-19](MySQL Note.assets/Xnip2022-04-22_08-33-19.jpg)

<hr>













# Day273

## Tag: CTE

![Xnip2022-04-23_10-18-26](MySQL Note.assets/Xnip2022-04-23_10-18-26.jpg)



![Xnip2022-04-23_10-18-57](MySQL Note.assets/Xnip2022-04-23_10-18-57.jpg)

题意:

给你一张友谊情况表，请你查询出其中所有的共同好友大于3个的用户id



思路:

- 因为原题中只给出了好友关系，所以我们不能直接查询，而是要将数据从行转换为列才行，这样才能获取每个用户的信息
- 所以我们首先进行转换，SQL如下

SQL1:

```mysql
SELECT
		user1_id,
		user2_id
FROM
		Friendship
UNION ALL
SELECT
		user2_id,
		user1_id
FROM
		Friendship
```



- 之后，使用原表的两个列与该临时表的第一列进行连接(连接两次)
- 分别连接其两个列后，就能够获取所有的用户将情况了
- 再限制两个临时表的第二列对应的id相同(即共同好友)，最后分组并使用HAVING进行筛选即可，最终SQL如下

```mysql
WITH all_friendship AS (
    SQL1
)

SELECT
    t1.user1_id,
    t1.user2_id,
    COUNT(*) AS 'common_friend'
FROM
    Friendship AS t1
INNER JOIN all_friendship AS t2 ON t1.user1_id = t2.user1_id
INNER JOIN all_friendship AS t3 ON t1.user2_id = t3.user1_id
WHERE t2.user2_id = t3.user2_id
GROUP BY t1.user1_id, t1.user2_id
HAVING COUNT(*) >= 3
```

<hr>



![Xnip2022-04-23_10-38-43](MySQL Note.assets/Xnip2022-04-23_10-38-43.jpg)



![Xnip2022-04-23_10-38-55](MySQL Note.assets/Xnip2022-04-23_10-38-55.jpg)

<hr>







![Xnip2022-04-23_10-37-48](MySQL Note.assets/Xnip2022-04-23_10-37-48.jpg)



![Xnip2022-04-23_10-37-37](MySQL Note.assets/Xnip2022-04-23_10-37-37.jpg)

<hr>





![Xnip2022-04-23_10-42-15](MySQL Note.assets/Xnip2022-04-23_10-42-15.jpg)





![Xnip2022-04-23_10-42-04](MySQL Note.assets/Xnip2022-04-23_10-42-04.jpg)

<hr>













# Day274

## Tag: CTE, EXISTS

![Xnip2022-04-24_08-31-33](MySQL Note.assets/Xnip2022-04-24_08-31-33.jpg)



![Xnip2022-04-24_08-31-42](MySQL Note.assets/Xnip2022-04-24_08-31-42.jpg)

题意:

给你一张顾客信息表，请你查询出其中遗失的用户id



思路:

- 所谓的遗失其实就是处在1到MAX的序号之间，但不在表中罢了
- 如果我们有一个具有全部连续编号的表，将其与现有表中的id进行差集运算，得到的不就是答案了吗？
- 所以我们首先需要构建所有的连续编号，这里我们使用递归的CTE(仅限MySQL，标准SQL需要用0-9的数字进行自连接组合)，SQL如下

SQL1:

```mysql
WITH recursive seq AS (
    SELECT
        1 AS 'sequence'
    UNION ALL
    SELECT
        sequence + 1
    FROM
        seq
    WHERE sequence < (SELECT MAX(customer_id) FROM Customers)
)
```



- 有了它之后就简单多了，我们只需要进行差集运算即可，但MySQL并未实现EXCEPT，所以我们可以用NOT EXISTS/NOT IN，连接来代替它，鉴于性能，这里我选择NOT EXISTS，最终SQL如下

```mysql
SQL1

SELECT
    t1.sequence AS 'ids'
FROM
    seq AS t1
WHERE NOT EXISTS (
    SELECT
        customer_id
    FROM
        Customers AS t2
    WHERE t1.sequence = t2.customer_id
)
ORDER BY ids
```

<hr>


![Xnip2022-04-24_14-20-02](MySQL Note.assets/Xnip2022-04-24_14-20-02.jpg)



![Xnip2022-04-24_14-20-11](MySQL Note.assets/Xnip2022-04-24_14-20-11.jpg)

<hr>



![Xnip2022-04-24_14-21-32](MySQL Note.assets/Xnip2022-04-24_14-21-32.jpg)



![Xnip2022-04-24_14-21-37](MySQL Note.assets/Xnip2022-04-24_14-21-37.jpg)

<hr>











# Day275

## Tag: EXISTS, GROUP BY

![Xnip2022-04-25_08-12-17](MySQL Note.assets/Xnip2022-04-25_08-12-17.jpg)



![Xnip2022-04-25_08-12-46](MySQL Note.assets/Xnip2022-04-25_08-12-46.jpg)

题意:

给你一张学生信息表，一张考试信息表，请你查询出其中成绩位于中游的学生



思路:

- 基础的思路就是查询出每次考试中的最值分数对应的学生id
- 最值分数其实就是同一场考试中，没人比他高或者没人比他低，所以我们可以通过自连接获取对应的id，SQL如下

SQL1:

```mysql
SELECT
		e1.student_id 
FROM
		exam AS e1
INNER JOIN exam AS t2 ON e1.exam_id = t2.exam_id 
GROUP BY e1.exam_id, e1.student_id
HAVING (SUM(e1.score < t2.score) = 0 OR SUM(e1.score > t2.score) = 0)
```



- 有了该临时表后，我们使用NOT IN, NOT EXISTS或者自连接都是可以的，最终SQL如下

```mysql
SELECT
    t1.student_id,
    t2.student_name
FROM
    Exam AS t1    
INNER JOIN Student AS t2 ON t1.student_id = t2.student_id
WHERE NOT EXISTS (
		SQL1
		AND t1.student_id = e1.student_id
)
GROUP BY t1.student_id, t2.student_name
ORDER BY t1.student_id
```

<hr>













# Day276

## Tag: 前三工资

![Xnip2022-04-26_07-27-37](MySQL Note.assets/Xnip2022-04-26_07-27-37.jpg)



![Xnip2022-04-26_07-27-00](MySQL Note.assets/Xnip2022-04-26_07-27-00.jpg)

题意:

给你一张员工信息表，一张部门信息表，请你查询出其中每个部门工资前三位的员工信息





思路:

- 

- 最简单的思路就是求出每个部门中，每个员工对应的工资排行，然后筛选出前3位即可
- 这里我们用最简单的窗口函数写法，SQL如下

SQL1:

```mysql
SELECT
		t2.name AS 'Department',
		t1.name AS 'Employee',
		t1.salary AS 'Salary',
		DENSE_RANK() OVER(
				PARTITION BY t1.departmentId
				ORDER BY t1.salary DESC
		) AS 'rn'
FROM
		Employee AS t1
INNER JOIN Department AS t2 ON t1.departmentId = t2.id
```



- 最后限制排名，并查询出相应的字段即可，最终SQL如下

```mysql
SELECT
    Department,
    Employee,
    Salary
FROM (
    SQL1
) AS temp
WHERE rn <= 3
```

<hr>



![Xnip2022-04-26_07-48-25](MySQL Note.assets/Xnip2022-04-26_07-48-25.jpg)



![Xnip2022-04-26_07-48-37](MySQL Note.assets/Xnip2022-04-26_07-48-37.jpg)

<hr>







# Day277

## Tag: 按年列出销售额

![Xnip2022-04-27_09-13-25](MySQL Note.assets/Xnip2022-04-27_09-13-25.jpg)



![Xnip2022-04-27_09-21-02](MySQL Note.assets/Xnip2022-04-27_09-21-02.jpg)

题意:

给你一张产品信息表，一张销售记录表，请你查询出其中2018年到2020年中每件产品每年的销售总额





思路:

- 该题目的难点在于，我们只知道销售的起止日期，而我们需要的是具体的天数
- 首先，我们先获取所有的销售天数差，这里需要从0开始直到最大值，所以使用CTE递归是最简单的写法:

SQL1:

```mysql
WITH RECURSIVE day_diff(day_period) AS (
    SELECT 
        0 AS 'day_period'
    UNION ALL
    SELECT
        day_period + 1
    FROM 
        day_diff
    WHERE day_period < (SELECT MAX(DATEDIFF(period_end, period_start)) FROM Sales)
)
```



- 之后我们的思路是这样的：在原表上加入我们得到的天数差，这样一来得到的就是每天的日期，再取这些日期的年份不就可以按照年分组了吗？这里对应SQL: 

```mysql
DATE_FORMAT(DATE_ADD(t1.period_start, INTERVAL t2.day_period DAY), '%Y')
...
INNER JOIN day_diff AS t2 ON DATEDIFF(t1.period_end, t1.period_start) >= t2.day_period
```



- 又因为我们连接了日期差这张表，所以连接后的表对应的行数其实就是天数，所以我们此时直接统计每件商品对应的所有的平均日金额就能得到总金额了
- 最后只需要限制一下日期，写一下分组即可，最终SQL如下

```mysql
SQL1

SELECT
    t1.product_id,
    t3.product_name,
    DATE_FORMAT(DATE_ADD(t1.period_start, INTERVAL t2.day_period DAY), '%Y') AS 'report_year',
    SUM(t1.average_daily_sales) AS 'total_amount'
FROM
    Sales AS t1
INNER JOIN day_diff AS t2 ON DATEDIFF(t1.period_end, t1.period_start) >= t2.day_period
INNER JOIN Product AS t3 ON t1.product_id = t3.product_id
GROUP BY t1.product_id, t3.product_name, report_year
HAVING report_year BETWEEN 2018 AND 2020
ORDER BY t1.product_id, report_year
```

<hr>



![Xnip2022-04-27_14-15-08](MySQL Note.assets/Xnip2022-04-27_14-15-08.jpg)



SQL:

```mysql
SELECT
    Employee.id,
    company,
    salary
FROM
    Employee
INNER JOIN (
    SELECT
        t1.id
    FROM
        Employee AS t1
    INNER JOIN Employee AS t2 ON t1.company = t2.company
    GROUP BY t1.id
    HAVING SUM(CASE WHEN t1.salary <= t2.salary THEN 1 ELSE 0 END) >= COUNT(*) / 2
    AND SUM(CASE WHEN t1.salary >= t2.salary THEN 1 ELSE 0 END) >= COUNT(*) / 2
) AS temp
WHERE Employee.id = temp.id
GROUP BY company, salary
```

<hr>







# Day278

## Tag: SUM() OVER()

![Xnip2022-04-28_08-08-31](MySQL Note.assets/Xnip2022-04-28_08-08-31.jpg)



![Xnip2022-04-28_08-08-18](MySQL Note.assets/Xnip2022-04-28_08-08-18.jpg)

题意:

给你一张数字频率表，请你查询出其中出现次数中位数对应的数字





思路:

- 因为中位数是根据频率来的，根据中位数的定义，不管是从开头还是结尾位置算，中位数的位次都应该是一样的
- 根据这个特性，我们可以构造两个集合：一个从头开始累积，一个从尾部开始，且规定两个集合的累计值都要大于总的频率和值的一半
- 这样一来，满足两种情况的值就必然是中位数了
- 所以我们现在需要先获取两种顺序下的频率和值，累加值是SUM() OVER的专长，所以SQL如下

```mysql
SELECT
		num,
		SUM(frequency) OVER(
				ORDER BY num
		) AS 'asc_sum',
		SUM(frequency) OVER(
				ORDER BY num DESC
		) AS 'desc_sum',
		total_sum
FROM
		Numbers,
(SELECT SUM(frequency) AS 'total_sum' FROM Numbers) AS sum_temp
```



- 最后只需要加上我们之前规定的限制即可(因为中位数可能有两个，所以要用AVG取均值)

```mysql
SELECT
    ROUND(AVG(num), 1) AS 'median'
FROM (
    SQL1
    ) AS temp
WHERE asc_sum >= total_sum / 2 AND desc_sum >= total_sum / 2 
```

<hr>









# Day279

## Tag: 活跃用户

![Xnip2022-04-29_08-05-53](MySQL Note.assets/Xnip2022-04-29_08-05-53.jpg)



![Xnip2022-04-29_08-06-38](MySQL Note.assets/Xnip2022-04-29_08-06-38.jpg)

题意:

给你一张账户信息表，一张登陆信息表，请你查询出至少连续登陆5天的用户





思路:

- 从题目来看，我们需要的其实只是用户的id，所以要在Logins表上筛选
- 因为需要判断同一个用户连续登陆的天数是否大于等于5，因为日期都在一列中，所以我们需要使用自连接，通过限制两张表中日期值的差值为0到4
- 如果有符合条件的用户，那么其在该区间内对应的日期数一定为5，所以我们只需要连接后用HAVING判断一下分组后的集合属性即可
- 在分组的时候也要注意，不能简单的按照用户id和姓名分组，还要按照起始日期分组，因为连续日期区间可能会有多个，我们要把不同起始日期的连续区间视作不同的集合，最终SQL如下

```mysql
SELECT
    DISTINCT t1.id,
    t1.name
FROM
    Accounts AS t1
INNER JOIN Logins AS t2 ON t1.id = t2.id
INNER JOIN Logins AS t3 ON t2.id = t3.id
WHERE DATEDIFF(t3.login_date, t2.login_date) BETWEEN 0 AND 4
GROUP BY t1.id, t1.name, t2.login_date
HAVING COUNT(DISTINCT t3.login_date) = 5
```











