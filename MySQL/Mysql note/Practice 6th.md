# Day91

## Tag: TIMESTAMPDIFF

![Xnip2021-10-22_10-59-40](MySQL Note.assets/Xnip2021-10-22_10-59-40.jpg)



![Xnip2021-10-22_10-58-49](MySQL Note.assets/Xnip2021-10-22_10-58-49.jpg)



![Xnip2021-10-22_11-00-28](MySQL Note.assets/Xnip2021-10-22_11-00-28.jpg)

题意:

给你一张用户请求记录表，请你查询出其中所有在24小时内发出至少两次请求的用户id







思路:

- 至少两次也就是说有两条记录在24内就行了，我们只需要判断两条记录的时间差在24小时内即可
- 同一张表中无法就一个字段进行比较，所以我们这里需要对这张表进行内连接，判断另一张表中时间晚于另一张表的记录中，是否存在时间差小于24小时的
- 限制两个datatimel类型可使用TIMESTAMPDIFF函数，将单位设置为SECOND即可，SQL如下

```mysql
SELECT
	DISTINCT t1.user_id
FROM
	Confirmations AS t1
INNER JOIN Confirmations AS t2 ON t1.user_id = t2.user_id
AND t1.time_stamp < t2.time_stamp
AND TIMESTAMPDIFF(SECOND, t1.time_stamp, t2.time_stamp) <= 24 * 60 * 60;
```



























# Day92

## Tag: UNION

![Xnip2021-10-22_11-37-40](MySQL Note.assets/Xnip2021-10-22_11-37-40.jpg)



![Xnip2021-10-22_11-37-24](MySQL Note.assets/Xnip2021-10-22_11-37-24.jpg)

题意:

给你一张体验信息表，请你查询出三种平台上三种体验的尝试次数





思路:

- 该题目就是分组的问题，但在计算次数之前，需要我们重组三个平台和三种体验
- 所以需要我们手动写出三个平台的名称和三种体验的名称，并构成笛卡尔积，最后再连接上次数
- 其中左边的笛卡尔积为驱动表，对应未在被驱动表中匹配的记录，也需要统计为0，所以需要外连接，SQL如下

```mysql
SELECT
    t1.platform,
    t2.experiment_name,
    COUNT(t3.platform) AS 'num_experiments'
FROM (
SELECT
    'Android' AS 'platform'
UNION
SELECT
    'IOS' AS 'platform'
UNION
SELECT
    'Web' AS 'platform'
) AS t1
INNER JOIN (
    SELECT
        'Reading' AS 'experiment_name'
    UNION
    SELECT
        'Sports' AS 'experiment_name'
    UNION
    SELECT
        'Programming' AS 'experiment_name'
) AS t2
LEFT JOIN Experiments AS t3 USING(platform, experiment_name)
GROUP BY t1.platform, t2.experiment_name;
```

****



















# Day93

## Tag: IFNULL, HAVING

![Xnip2021-10-25_08-24-53](MySQL Note.assets/Xnip2021-10-25_08-24-53.jpg)



![Xnip2021-10-25_08-25-36](MySQL Note.assets/Xnip2021-10-25_08-25-36.jpg)

题意:

给你一张图书信息表，再给你一张订单表，请你查询出过去一年中订单数不足10的书籍信息(不考虑上架不满1个月的书籍)，今天的日期为2019-06-23





思路:

- 根据示例来看，结果需要以图书表为驱动表，其中在被驱动表(订单表)中未被匹配的记录则需要从null变为0
- 因为计算订单数时只考虑一年以内的，所以在连接时就可以限制被驱动表中的订单时间
- 在总体数据中再限制上架时间大于等于一个月
- 最后在分组后，使用HAVING统计订单数并限制即可，SQL如下

```mysql
SELECT
    t2.book_id,
    t2.name
FROM
    Orders AS t1
RIGHT JOIN Books AS t2 ON t1.book_id = t2.book_id
AND t1.dispatch_date >= '2018-06-23'
WHERE t2.available_from >= 30
GROUP BY t1.book_id
HAVING SUM(IFNULL(t1.quantity, 0)) < 10;
```

****























# Day94

## Tag: HAVING

![Xnip2021-10-26_07-13-07](MySQL Note.assets/Xnip2021-10-26_07-13-07.jpg)



![Xnip2021-10-26_07-12-46](MySQL Note.assets/Xnip2021-10-26_07-12-46.jpg)



![Xnip2021-10-26_07-15-11](MySQL Note.assets/Xnip2021-10-26_07-15-11.jpg)

题意:

给你一张员工信息表，请你查询出其中有至少5名下属经理的名字







思路1:

- 首先我们可以直接获取经理的Id，并通过计算经理id出现的次数筛选出其中下属数大于5的经理，SQL如下

SQL1

```mysql
SELECT
	ManagerId
FROM
	Employee
GROUP BY ManagerId
HAVING (COUNT(ManagerId)) >= 5;
```



- 之后再将其作为临时表，与原表进行内连接即可，SQL如下

```mysql
SELECT
	t2.Name
FROM (
SQL1
) AS t1
INNER JOIN Employee AS t2 ON t1.ManagerId = t2.Id;
```









思路2:

- 其实我们可以直接将两张表进行内连接，我们需要查询的无非是MangerId对应Id的名字
- 所以将原表进行内连接，对应Id后以经理Id进行分组，并计算并限制分组后ManagerId的数量即可，SQL如下

```mysql
SELECT
	t1.Name
FROM
	Employee AS t1
INNER JOIN Employee AS t2 ON t1.Id = t2.ManagerId
GROUP BY t1.Id
HAVING (COUNT(t2.ManagerId)) >= 5;
```

****













# Day95

## Tag: LIMIT, INNER JOIN

![Xnip2021-10-27_13-26-25](MySQL Note.assets/Xnip2021-10-27_13-26-25.jpg)



![Xnip2021-10-27_13-25-58](MySQL Note.assets/Xnip2021-10-27_13-25-58.jpg)

题意:

给你一张候选人信息表，一张投票记录表，请你查询出最终当选者的信息





思路:

- 当选者即为票数最多的候选人，由于候选人的信息在Candidate表中，而需要统计的票数在Vote表中，所以需要连接两张表才行
- 首先查询出票数最多的候选人ID，使用COUNT进行分组，将查询结果按照票数进行排序，最后第一条数据就是当选人的信息，使用LIMIT只查询第一条即可，SQL如下

SQL1

```mysql
SELECT
	CandidateId,
	COUNT(CandidateId) AS 'number'
FROM
	Vote
GROUP BY CandidateId
ORDER BY number DESC
LIMIT 1;
```



- 最后再将该临时表与候选人信息表连接起来即可，SQL如下

```mysql
SELECT
	t1.Name
FROM
	Candidate AS t1
INNER JOIN (
SQL1
) AS t2 ON t1.id = t2.CandidateId;
```

****













# Day96

## Tag: LIMIT

![Xnip2021-10-28_07-14-36](MySQL Note.assets/Xnip2021-10-28_07-14-36.jpg)



![Xnip2021-10-28_07-13-38](MySQL Note.assets/Xnip2021-10-28_07-13-38.jpg)

题意:

给你一张调查日志表，请你计算出其中回答率最高的问题id





思路:

- 观察表的结构可以看到，所有回答的记录中，action列的值都为answer，而回答率最高其实也就是回答数量最多
- 所以我们只需要分组计算每个问题的回答率，并取出其中的最大值即可
- 一次分组后只能求出所有回答id对应的回答次数，但我们可以对结果进行倒序排序，之后第一条数据就是我们想要的，使用LIMIT取第一条即可，SQL如下

```mysql
SELECT
    question_id AS 'survey_log'
FROM
    SurveyLog
WHERE action = 'answer'
GROUP BY question_id
ORDER BY COUNT(action) DESC
LIMIT 1;
```

****

















# Day97

## Tag: HAVING

![Xnip2021-10-29_07-12-38](MySQL Note.assets/Xnip2021-10-29_07-12-38.jpg)



![Xnip2021-10-29_07-11-15](MySQL Note.assets/Xnip2021-10-29_07-11-15.jpg)

题意:

给你一张商品信息表，一张交易记录表，请你计算出购买个数大于20，而单个商品质量小于50的所有商品信息







思路:

- 因为查询列表中需要我们统计购买个数，所以需要使用SUM，并分组
- 而商品的信息和交易记录在两张不同的表中，所以还需要连接两张表才行
- 连接时我们可以先筛选出质量小于50的商品
- 之后我们需要在分组的基础上限制商品的数量，此时使用HAVING即可，最后再根据要求排序，SQL如下

```mysql
SELECT
    t1.id,
    t1.name,
    t1.weight,
    SUM(t2.count) AS 'total'
FROM
    goods AS t1
INNER JOIN trans AS t2 ON t1.id = t2.goods_id
WHERE t1.weight < 50
GROUP BY t1.id
HAVING total > 20
ORDER BY t1.id;
```

****

















# Day98

## Tag: IF

![Xnip2021-10-30_13-05-51](MySQL Note.assets/Xnip2021-10-30_13-05-51.jpg)



![Xnip2021-10-30_13-05-08](MySQL Note.assets/Xnip2021-10-30_13-05-08.jpg)

题意:

给你一张销售表，请你查询出每天苹果和橘子的销售数量差





思路:

- 因为苹果和橘子的记录可以通过字段fruit来判断，所以我们使用IF来区别是该加还是减数量即可，SQL如下

```mysql
SELECT
    sale_date,
    SUM(IF(fruit = 'apples', sold_num, -sold_num)) AS 'diff'
FROM
    Sales
GROUP BY sale_date
ORDER BY sale_date
```

****

















# Day99

## Tag: UNION, Temperary Table

![Xnip2021-10-31_10-36-24](MySQL Note.assets/Xnip2021-10-31_10-36-24.jpg)



![Xnip2021-10-31_10-33-42](MySQL Note.assets/Xnip2021-10-31_10-33-42.jpg)

题意:

给你一张电影信息表，一张用户信息表，一张电影评分记录表，请你查询出其中评论电影最多的用户名(如果有评论数相同的则按照用户名字典序取靠前的)，并查询出2020年2月中平均评分最高的电影(如有同分则按照电影名字典序取靠前的)





思路:

- 题目中要求我们查询出两种数据，最终还要在一个结果集中，所以很明显需要写两个SQL再用UNION连接起来
- 先写第一个，题目要找评论数最多的用户名，然而用户名在用户表中，而评论的信息在评分表中，所以需要内连接两张表
- 为了找到最值，我们需要根据用户来分组计算对应的评论数并排序，其中排序除了按照评论数倒序排列外，还需要按照用户名升序排列(字典序)，最后排列出的第一条数据就是我们想要的用户名，用LIMIT取第一个即可，SQL如下

SQL1:

```mysql
SELECT
    t1.name AS 'results'
FROM
    Users AS t1
INNER JOIN MovieRating AS t2 ON t1.user_id = t2.user_id
GROUP BY t1.name
ORDER BY COUNT(t2.rating) DESC, t1.name
LIMIT 1
```





- 搞定了第一条，接下来需要搞定平均评分最高的电影，其中求平均评分使用AVG即可，限制日期则使用YEAR和MONTH函数即可
- 注意电影名和评分信息不在同一张表中，所以仍旧需要内连接，最终排列也需要加上对电影名进行升序排列(字典序)
- 最后用LIMIT取第一条记录即可，SQL如下

SQL2:

```mysql
SELECT
	t1.title AS 'results'
FROM
	Movies AS t1
INNER JOIN MovieRating AS t2 ON t1.movie_id = t2.movie_id
WHERE YEAR(t2.created_at) = 2020 AND MONTH(t2.created_at) = 2
GROUP BY t1.title
ORDER BY AVG(t2.rating) DESC, t1.title
LIMIT 1
```





- 最后的最后，我们需要将这两张表上下连接起来，注意这两张表都是查询出的临时表，所以查询列表中的字段别名需要一致，且两张表都需要添加括号以表示一张表，SQL如下


```mysql
(SQL1)
UNION
(SQL2)
```

****

















# Day100

## Tag: SQRT, MIN

![Xnip2021-11-01_07-18-18](MySQL Note.assets/Xnip2021-11-01_07-18-18.jpg)



![Xnip2021-11-01_07-23-41](MySQL Note.assets/Xnip2021-11-01_07-23-41.jpg)

题意:

给你一张二维坐标表，请你查询出其中最短的两点间距离







思路:

- 单纯地求两点间距离很简单，获取x、y的距离平方和再开方即可，开方可以使用SQRT函数，平方可以使用POW函数
- 然而所有点都在同一张表中的同一字段，所以需要对该表两次查询，进行自连接，注意自连接条件只需要其中一个坐标不相等即可
- 最后可以按照距离进行升序排列，然后取第一条记录
- 但我们其实可以在计算距离时就使用MIN来获取最短距离，这样就不需要排序和取第一条记录了，SQL如下

```mysql
SELECT
    ROUND(SQRT(MIN(POW((t1.x - t2.x), 2) + POW(t1.y - t2.y, 2))), 2) AS 'shortest'
FROM
    point2D AS t1
INNER JOIN point2D AS t2 ON t1.x != t2.x OR t1.y != t2.y
```









