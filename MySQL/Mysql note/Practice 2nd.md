# Day31

## Tag: ORDER BY, Sub Query, LIMIT



![Xnip2021-06-19_12-08-10](MySQL Note.assets/Xnip2021-06-19_12-08-10.jpg)



![Xnip2021-06-19_12-08-27](MySQL Note.assets/Xnip2021-06-19_12-08-27.jpg)



题意:

根据给出的表，查询出工资排名第二员工的编号和工资数目





思路:

- 首先考虑获取对应的salary值，因为不是获取最大值且涉及到排序，那么很明显就应该用ORDER BY，并且指定为DESC降序，再使用LIMIT获取第二位即可，SQL如下



SQL1:

```mysql
SELECT
	salary
FROM
	salaries
ORDER BY salary LIMIT 1, 1;
```

- LIMIT 1, 1: 

参数一: 从索引为1的位置开始

参数二: 一共获取1条数据



- 这里还可以使用OFFSET进行搭配:

最后一行改为: ORDER BY salary LIMIT 1 OFFSET 1(即一共取一条数据，但跳过第一条数据)



同: SQL Day11







- 最后再将其作为子查询条件即可，SQL如下

SQL2

```mysql
SELECT
	emp_no,
	salary
FROM
	salaries
WHERE salary = (SQL2);
```

- 因为子查询结果只有一条数据，所以这里可以用"="

****











# Day32

## Tag: DELETE, Sub Query



![Xnip2021-06-20_11-42-31](MySQL Note.assets/Xnip2021-06-20_11-42-31.jpg)



![Xnip2021-06-20_11-41-52](MySQL Note.assets/Xnip2021-06-20_11-41-52.jpg)





题意:

删除重复的员工号重复的数据(emp_no)，保留每个员工号中id值最小的数据





思路:

- 首先通过聚合函数查询出最小的员工id，并用GROUP BY限制在每个emp_no内，SQL如下：

SQL1

```mysql
SELECT
	MIN(id)
FROM
	titles_test
GROUP BY emp_no
```





- 之后再将其作为子查询结果，注意因为我们用到了删除操作，这里不能直接作为条件使用(不能一边用条件，一边删除条件中的数据)，我们可以在SQL1的基础上在套一层，对SQL1进行一次查询，将该结果表作为条件进行删除即可，SQL如下：



SQL2

```mysql
DELETE 
FROM
	titles_test
WHERE id NOT IN(
	SELECT * FROM (
  	SQL1
  ) AS t1
);
```

****













# Day33

## Tag: UPDATE, JOIN



![Xnip2021-06-21_08-52-57](MySQL Note.assets/Xnip2021-06-21_08-52-57.jpg)



![Xnip2021-06-21_08-52-06](MySQL Note.assets/Xnip2021-06-21_08-52-06.jpg)



![Xnip2021-06-21_08-57-44](MySQL Note.assets/Xnip2021-06-21_08-57-44.jpg)





题意:

将奖金表emp_bonus中的员工在工资表salaries中的salary增加10%













思路:

- 查出奖金表中的员工号，SQL如下

SQL1

```mysql
SELECT
	emp_no
FROM
	emp_bonus;
```





- 将其作为子查询条件，进行查询，注意要更新的是员工当前的薪水(to_date为'9999-01-01')，SQL如下

SQL2

```mysql
UPDATE salaries
SET salary = salary * 0.1 + salary
	WHERE emp_no IN (SQL1)
	AND to_date = '9999-01-01'
```















