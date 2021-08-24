# Day31

## Tag: Sub Query, IN, SUM, IF

![Xnip2021-08-23_15-59-59](MySQL Note.assets/Xnip2021-08-23_15-59-59.jpg)



![Xnip2021-08-23_15-59-33](MySQL Note.assets/Xnip2021-08-23_15-59-33.jpg)

题意:

给你一张用户表和一张行程表，请你查询2013-10-01到2013-10-03每天正常用户订单的取消率(正常用户是指客户和司机都没有被ban)，结果保留两位小数





思路:

- 从简单的开始，限制日期我们可以使用BETWEEN AND
- 想要指定正常用户，我们可先查询出被ban的用户，再使用NOT IN即可进行排除
- 难点在于如果计算取消的订单数，平时计算数量会想到使用COUNT
- 但这里一旦使用COUNT则必须对CONUT内的字段进行限定，而之后计算总数时COUNT中使用的字段会收到影响
- 如果一定要使用COUNT，则需要将Trip表重复查询两次，以确保前后两次COUNT字段不会相互干扰
- 这样其实很麻烦，我们完全可以利用IF将COUNT变为SUM，如果Status为completed则为0，否则为1
- 这样我们只将Trip表查询了一次，还没有影响到之后的分母，SQL如下

```mysql
SELECT
	Request_at AS 'Day',
	ROUND(SUM(IF(Status = 'completed', 0, 1)) / COUNT(Status), 2) AS 'Cancellation Rate'
FROM
	Trips
WHERE Client_Id NOT IN (SELECT Users_Id FROM Users WHERE Banned = 'Yes')
AND Driver_Id NOT IN (SELECT Users_Id FROM Users WHERE Banned = 'Yes')
AND Request_at BETWEEN '2013-10-01' AND '2013-10-03'
GROUP BY Day;
```

****









# Day32

## Tag: OR, IS NULL

![Xnip2021-08-24_22-47-24](MySQL Note.assets/Xnip2021-08-24_22-47-24.jpg)



![Xnip2021-08-24_22-52-55](MySQL Note.assets/Xnip2021-08-24_22-52-55.jpg)







题意:

给你一张客户表，请你查询出所有推荐人id不为2的客户名称







思路:

- 这道题目看起来一个referee_id != 2就能解决，但这样其实只能查询出所有非null的数据，所以我们还需要将所有为null的数据也包含其中，SQL如下

```mysql
SELECT
	name
FROM
	customer
WHERE referee_id != 2 OR referee_id IS NULL
```



