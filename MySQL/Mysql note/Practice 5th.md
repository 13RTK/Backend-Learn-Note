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







