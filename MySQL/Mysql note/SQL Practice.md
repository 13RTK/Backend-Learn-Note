# Day01

![Xnip2021-05-20_15-29-47](MySQL Note.assets/Xnip2021-05-20_15-29-47.jpg)



![Xnip2021-05-20_15-31-09](MySQL Note.assets/Xnip2021-05-20_15-31-09.jpg)



![Xnip2021-05-20_15-32-23](MySQL Note.assets/Xnip2021-05-20_15-32-23.jpg)

****





# Day02

![Xnip2021-05-21_11-14-34](MySQL Note.assets/Xnip2021-05-21_11-14-34.jpg)



![Xnip2021-05-21_12-16-26](MySQL Note.assets/Xnip2021-05-21_12-16-26.jpg)

****







# Day03

![Xnip2021-05-22_18-48-33](MySQL Note.assets/Xnip2021-05-22_18-48-33.jpg)



![Xnip2021-05-22_18-49-23](MySQL Note.assets/Xnip2021-05-22_18-49-23.jpg)

****











# Day04

![Xnip2021-05-23_16-04-10](MySQL Note.assets/Xnip2021-05-23_16-04-10.jpg)



![Xnip2021-05-23_16-04-41](MySQL Note.assets/Xnip2021-05-23_16-04-41.jpg)

****















# Day05

![Xnip2021-05-24_09-21-25](MySQL Note.assets/Xnip2021-05-24_09-21-25.jpg)



![Xnip2021-05-24_09-23-34](MySQL Note.assets/Xnip2021-05-24_09-23-34.jpg)

****









# Day06



![Xnip2021-05-25_22-42-59](MySQL Note.assets/Xnip2021-05-25_22-42-59.jpg)





![Xnip2021-05-25_22-44-17](MySQL Note.assets/Xnip2021-05-25_22-44-17.jpg)

****









# Day07

![Xnip2021-05-26_10-57-36](MySQL Note.assets/Xnip2021-05-26_10-57-36.jpg)



![Xnip2021-05-26_10-56-57](MySQL Note.assets/Xnip2021-05-26_10-56-57.jpg)

****







# Day08

![Xnip2021-05-27_18-55-26](MySQL Note.assets/Xnip2021-05-27_18-55-26.jpg)



左连接:

![Xnip2021-05-27_18-56-49](MySQL Note.assets/Xnip2021-05-27_18-56-49.jpg)







自然连接:

![Xnip2021-05-28_10-38-46](MySQL Note.assets/Xnip2021-05-28_10-38-46.jpg)

****















# Day09

![Xnip2021-05-28_19-25-56](MySQL Note.assets/Xnip2021-05-28_19-25-56.jpg)





![Xnip2021-05-28_19-26-28](MySQL Note.assets/Xnip2021-05-28_19-26-28.jpg)

****















# Day10

## Tag: Order, Limit



![Xnip2021-05-29_11-06-04](MySQL Note.assets/Xnip2021-05-29_11-06-04.jpg)





![Xnip2021-05-29_11-07-02](MySQL Note.assets/Xnip2021-05-29_11-07-02.jpg)

****











# Day11

## Tag: Order, Limit, OFFSET



![Xnip2021-05-30_16-12-51](MySQL Note.assets/Xnip2021-05-30_16-12-51.jpg)







![Xnip2021-05-30_16-20-37](MySQL Note.assets/Xnip2021-05-30_16-20-37.jpg)

****











# Day12

## Tag: DATEDIFF



![Xnip2021-05-31_13-17-39](MySQL Note.assets/Xnip2021-05-31_13-17-39.jpg)





![Xnip2021-05-31_13-23-12](MySQL Note.assets/Xnip2021-05-31_13-23-12.jpg)

****











# Day13

## Tag: Unite



![Xnip2021-06-01_17-26-16](MySQL Note.assets/Xnip2021-06-01_17-26-16.jpg)





![Xnip2021-06-01_17-27-09](MySQL Note.assets/Xnip2021-06-01_17-27-09.jpg)

****











# Day14

## Tag: Having



![Xnip2021-06-02_14-01-41](MySQL Note.assets/Xnip2021-06-02_14-01-41.jpg)



![Xnip2021-06-02_14-01-06](MySQL Note.assets/Xnip2021-06-02_14-01-06.jpg)

****











# Day15

## Tag: DISTINCT, ORDER BY



![MySQL func](MySQL Note.assets/MySQL func.jpg)



![Xnip2021-06-03_14-19-30](MySQL Note.assets/Xnip2021-06-03_14-19-30.jpg)

****







# Day16

## Tag: Sub Query



![Xnip2021-06-04_15-04-52](MySQL Note.assets/Xnip2021-06-04_15-04-52.jpg)





![Xnip2021-06-04_15-06-45](MySQL Note.assets/Xnip2021-06-04_15-06-45.jpg)

****













# Day17

## Tag: Bit Operate, ORDER



![Xnip2021-06-05_18-28-27](MySQL Note.assets/Xnip2021-06-05_18-28-27.jpg)



![Xnip2021-06-05_18-27-01](MySQL Note.assets/Xnip2021-06-05_18-27-01.jpg)

****









# Day18

## Tag: Sub Query, ORDER BY



![Xnip2021-06-06_11-40-23](MySQL Note.assets/Xnip2021-06-06_11-40-23.jpg)



![Xnip2021-06-06_11-42-53](MySQL Note.assets/Xnip2021-06-06_11-42-53.jpg)

****













# Day19

## Tag: LEFT JOIN



![Xnip2021-06-07_09-00-48](MySQL Note.assets/Xnip2021-06-07_09-00-48.jpg)



![Xnip2021-06-07_09-00-13](MySQL Note.assets/Xnip2021-06-07_09-00-13.jpg)

题意：

查询所有员工的last_name, first_name, dept_no信息，没有dept_no的也要显示(显示为null)



思路:

- 查询last_name和first_name很简单，SQL如下

SQL: SELECT last_name, first_name FROM employees;



- 由于employees表内一些数据在dept_emp表内没有对应的dept_no，所以要以employees的数据为准，这里想到用GROUP BY或者LEFT JOIN，但GROUP BY必须包含所有查询的字段，所以无法使用，只有使用LEFT JOIN

****















# Day20

## Tag: GROUP BY, ORDER, FUNC



![Xnip2021-06-08_12-56-17](MySQL Note.assets/Xnip2021-06-08_12-56-17.jpg)



![Xnip2021-06-08_12-57-13](MySQL Note.assets/Xnip2021-06-08_12-57-13.jpg)



![Xnip2021-06-08_12-59-10](MySQL Note.assets/Xnip2021-06-08_12-59-10.jpg)





题意:

计算两种员工的平均工资，并以平均值的升序排列，查询字段应该为title和avg(s.salary)





思路:

- 题目要求查询平均值，那么就需要用到聚合函数中的"AVG()"，而且需要按照title字段进行分组，所以得到如下SQL:

SQL1: SELECT t.title, AVG(s.salary) FROM titles AS t, salaries AS s WHERE t.emp_no=s.emp_no GROUP BY t.title;



- 之后再加上排序条件即可，SQL如下: 

SQL: SQL1 ORDER BY AVG(s.salary) ASC;





拓展:

对于分组查询，除了GROUP BY 之外，我们还可以使用JOIN，这里使用INNER/LEFT/RIGHT都是可以的，SQL: 

SQL: SELECT t.title, AVG(s.salary) AS 'avg(salary)' FROM titles AS t LEFT JOIN salaries AS s ON t.emp_no=s.emp_no GROUP BY t.title;

****











# Day21

## Tag: LEFT JOIN, Sub Query



![Xnip2021-06-09_08-49-15](MySQL Note.assets/Xnip2021-06-09_08-49-15.jpg)



![Xnip2021-06-09_08-48-41](MySQL Note.assets/Xnip2021-06-09_08-48-41.jpg)





题意：

从给定的三个表中，以emp_no为唯一对照，查询出employee表中每条数据对应的last_name, first_name和dept_name，没有对应dept_name的数据也要输出(输出为null)





思路:

- 首先观察三张表，我们需要的数据在employees和departments表中，但departments中没有emp_no字段，因此不能直接关联两张表



- departments中有dept_no字段，而dept_emp表中有dept_no和emp_no字段的对照关系，所以我们需要先关联departments和dept_emp表，获取一个emp_no和dept_name的对照关系表，SQL如下



SQL1: 

SELECT
dept.emp_no,
depa.dept_name
FROM
departments AS depa,
dept_emp AS dept
WHERE depa.dept_no=dept.dept_no





- 获取这张表后再将其作为临时表，用employees与其进行左连接即可(题目要求以employees的数据为准)，SQL如下

SQL:

SELECT
emp.last_name AS 'last_name',
emp.first_name AS 'first_name',
temp.dept_name AS 'dept_name'
FROM 
employees AS emp
LEFT JOIN (
SQL1) AS temp ON temp.emp_no=emp.emp_no;

****











# Day22

## Tag: Sub Query, MAX()



![Xnip2021-06-10_09-33-04](MySQL Note.assets/Xnip2021-06-10_09-33-04.jpg)



![Xnip2021-06-10_09-32-37](MySQL Note.assets/Xnip2021-06-10_09-32-37.jpg)



题意:

获取工资排名第二员工的emp_no, salary, last_name, first_name, 不得使用ORDER BY 进行排序







​	思路:

- 首先使用MAX()函数获取最大salary值，SQL如下


SQL1: 

SELECT

​	MAX(salary) 

FROM 

​	salaries;





- 之后再次使用MAX()函数，但指定salary字段不在SQL1的范围内，以获取排名第二的salary，SQL如下

SQL2: 

SELECT

​	MAX(salary)

FROM

​	salaries 

WHERE salary NOT IN (SQL1);





- 最后再以SQL2为子查询条件，直接获取相关字段即可，SQL如下

SQL3: 

SELECT

​	e.emp_no AS emp_no,

​	s.salary AS salary,

​	e.last_name AS last_name,

​	e.first_name AS first_name

FROM

​	employees AS e

INNER JOIN salaries AS s ON e.emp_no=s.emp_no

WHERE s.salary IN (SQL2);

****













# Day23

## Tag: Sub Query, COUNT/GROUNP BY



![Xnip2021-06-11_10-32-33](MySQL Note.assets/Xnip2021-06-11_10-32-33.jpg)



![Xnip2021-06-11_10-32-54](MySQL Note.assets/Xnip2021-06-11_10-32-54.jpg)







题意:

通过提供的三张表，获取各个部门的部门编号、部门名称和其所属员工在salaries表中被记录的次数(三个员工，对应两个部门，关系在dept_emp表中)





思路:

- 题目要求获取在salaries表中出现的次数，但salaries表中没有对应的dept_no，所以需要先通过dept_emp表获取salaries表中每条emp_no字段和dept_no的关系，SQL如下

SQL1:

```mysql
SELECT 
	s.emp_no,
	dept.dept_no
FROM 
	salaries AS s, 
	dept_emp AS dept 
WHERE s.emp_no = dept.emp_no;
```







- 再通过对该表使用COUNT()，获取各部门被记录的次数，由于是按照部门划为，所以需要使用GROUP BY，为了留下与其他表的对照字段，再取出该表的dept_no，SQL如下

SQL2:

```mysql
SELECT
	temp1.dept_no,
COUNT( temp1.dept_no ) AS 'sum' 
FROM
(SQL1) AS temp1 
	GROUP BY temp1.dept_no;
```





- 最后再通过departments表与其连接即可，SQL如下

SQL3:

```mysql
SELECT
	temp2.dept_no,
	depa.dept_name,
	temp2.sum 
FROM
	departments AS depa INNER JOIN (SQL2) AS temp2 ON depa.dept_no = temp2.dept_no;
```

**再加上ORDER BY 排序**



****













# Day24

## Tag: LEFT JOIN, NULL



![Xnip2021-06-12_15-26-00](MySQL Note.assets/Xnip2021-06-12_15-26-00.jpg)



![Xnip2021-06-12_15-25-32](MySQL Note.assets/Xnip2021-06-12_15-25-32.jpg)



题意:

- 查询未被分类的电影的id和名字(title)，必须使用JOIN查询





思路一:



- 如果不考虑JOIN的限制，可以先直接查出已经分类电影的id(SQL1)，再以此作为子查询条件，获取不在其中的电影信息，SQL如下


SQL1

```mysql
SELECT
	film_id
FROM
	film_category;
```



SQL2

```mysql
SELECT
	f.film_id,
	title
FROM
	film AS f
WHERE film_id NOT IN (SQL1);
```











思路二:

既然有电影没有被分类，那么进行连接的时候会出现NULL，所以我们可以连接两张表，并查找film_category字段为NULL的数据即可，SQL如下



SQL

```mysql
SELECT
	f.film_id,
	title
FROM
	film LEFT JOIN film_category AS fc
  ON f.film_id=fc.film_id
WHERE fc.category_id IS NULL;
```

****











# Day25

## Tag: CONCAT



![Xnip2021-06-13_15-11-36](MySQL Note.assets/Xnip2021-06-13_15-11-36.jpg)





题意:查询两个字段，并只返回为一列结果，两字段间还得加入一个空格



思路: 使用拼接的函数CONCAT()即可

```mysql
SELECT
	CONCAT(last_name, ' ', first_name);
FROM
	employees;
```









