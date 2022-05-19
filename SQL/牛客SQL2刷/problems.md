# 一、最晚入职员工

![Xnip2022-05-19_08-11-13](problems.assets/Xnip2022-05-19_08-11-13.jpg)



![Xnip2022-05-19_08-11-35](problems.assets/Xnip2022-05-19_08-11-35.jpg)

题意:

给你一张员工信息表，请你查询出其中最晚入职的员工信息



思路:

- 所谓最晚入职其实就是hire_day字段最大，所以我们可以先查询出hire_day字段中的最大值，然后用来匹配即可
- 但这样写的话需要进行子查询或者连接查询，其实我们只需要对表中的数据按照hire_day字段进行倒序排序即可，然后利用LIMIT分页取第一条记录即可(tuple)
- 面试中千万不要在SELECT列表中使用*

SQL如下

```mysql
SELECT
    emp_no,
    birth_date,
    first_name,
    last_name,
    gender,
    hire_date
FROM
    employees
WHERE hire_date = (SELECT MAX(hire_date) FROM employees)

SELECT * FROM employees
ORDER BY hire_date DESC 
LIMIT 1
```























