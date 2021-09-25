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



















