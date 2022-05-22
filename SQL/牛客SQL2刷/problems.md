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

<hr>









# 二、时间上倒数第三入职的员工

![Xnip2022-05-20_07-49-13](problems.assets/Xnip2022-05-20_07-49-13.jpg)



![Xnip2022-05-20_07-54-49](problems.assets/Xnip2022-05-20_07-54-49.jpg)

题意:
给你一张员工信息表，请查询出其中入职时间上倒数第三的员工



思路:

- 因为同一个入职时间可能存在多个员工，所以我们需要先获取倒数第三入职对应的时间，我们同样根据hire_date字段进行排序分页即可，SQL如下

SQL1:

```mysql
SELECT 
		hire_date 
FROM employees 
GROUP BY hire_date
ORDER BY hire_date DESC
LIMIT 1 OFFSET 2
```

- 最后只需要查询出所有匹配该日期的记录即可(注意结果去重)，最终SQL如下

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
WHERE hire_date = (
    SQL1
)
```





优化:

- 首先使用EXPLAIN查看执行计划，发现子查询和外部查询都使用全表扫描，没有用到索引
- 且子查询需要使用临时表和文件排序

![Xnip2022-05-20_08-01-02](problems.assets/Xnip2022-05-20_08-01-02.jpg)

- 再查看JSON格式，发现开销为3.20

![Xnip2022-05-20_08-00-37](problems.assets/Xnip2022-05-20_08-00-37.jpg)



- 根据执行计划，我们的想办法将文件排序去除，从SQL上看，排序针对的是hire_date字段，所以我们只需要在该字段上建立索引即可
- 此时再查看执行计划，发现文件排序和临时表都消失了

![Xnip2022-05-20_08-02-33](problems.assets/Xnip2022-05-20_08-02-33.jpg)

- 再查看开销，此时降为了1.20!


![Xnip2022-05-20_08-02-16](problems.assets/Xnip2022-05-20_08-02-16.jpg)

<hr>











# 三、各部门领导薪水

![Xnip2022-05-21_10-35-35](problems.assets/Xnip2022-05-21_10-35-35.jpg)



![Xnip2022-05-21_10-35-45](problems.assets/Xnip2022-05-21_10-35-45.jpg)

题意:

给你一张薪资表，一张部门领导信息表，请你查询出其中所有部门对应的部门领导信息和薪资



思路:

- 很明显，我们只需要根据emp_no字段，进行一次内连接即可，最后根据emp_no排序就是了
- 这条SQL的优化空间也很少，只需要在两张表的emp_no字段上建立索引即可(连接字段必须建立索引)，所以SQL如下

```mysql
SELECT
    t1.emp_no,
    t2.salary,
    t2.from_date,
    t2.to_date,
    t1.dept_no
FROM
    dept_manager AS t1
INNER JOIN salaries AS t2 ON t1.emp_no = t2.emp_no
ORDER BY t1.emp_no
```

<hr>









# 四、已分配员工信息

![Xnip2022-05-22_10-50-28](problems.assets/Xnip2022-05-22_10-50-28.jpg)



![Xnip2022-05-22_10-50-40](problems.assets/Xnip2022-05-22_10-50-40.jpg)

<hr>










# 五、所有员工信息

![Xnip2022-05-23_07-44-14](problems.assets/Xnip2022-05-23_07-44-14.jpg)



![Xnip2022-05-22_10-50-40](problems.assets/Xnip2022-05-22_10-50-40.jpg)

题意:

给你一张员工信息表，一张部门信息表，请你查询出其中所有员工的信息



思路:

- 昨天我们查询的是已经被分配部门的员工，而今天我们需要的是所有的员工信息，所以我们应该以员工表为准进行查询，在SQL上就是将员工表作为驱动表，所以使用外连接即可，SQL如下

```sql
SELECT
    t1.last_name,
    t1.first_name,
    t2.dept_no
FROM
    employees AS t1
LEFT JOIN dept_emp AS t2 ON t1.emp_no = t2.emp_no
```























