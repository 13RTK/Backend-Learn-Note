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









