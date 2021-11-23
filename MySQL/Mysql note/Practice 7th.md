# Day121

## Tag: HAVING

![Xnip2021-11-22_07-22-19](MySQL Note.assets/Xnip2021-11-22_07-22-19.jpg)



![Xnip2021-11-22_07-25-05](MySQL Note.assets/Xnip2021-11-22_07-25-05.jpg)



![Xnip2021-11-22_07-34-59](MySQL Note.assets/Xnip2021-11-22_07-34-59.jpg)



![Xnip2021-11-22_07-36-09](MySQL Note.assets/Xnip2021-11-22_07-36-09.jpg)



![Xnip2021-11-22_07-36-25](MySQL Note.assets/Xnip2021-11-22_07-36-25.jpg)

题意:

给你一张试卷作答记录表，请你计算出其中未完成试卷的次数和未完成率





思路:

- 计算未完成数其实就是计算score或者submit_time为null的值的数量，所以我们只需要判断一下NULL再用SUM计数即可
- 而未完成率则需要在获取未完成数量的基础上，还需要除以所有的作答数，该数值不能简单地用COUNT，因为COUNT不计算NULL值，所以需要我们将NULL转换一下才行，最后记得用ROUND保留三位小数
- 在获取值之后进行分组，但最后需要注意，题目并不需要我们将没有未完成记录的试卷也查询出来，所以需要我们在分组后对未完成数进行限定，只取出该值≥1的数据，SQL如下

```mysql
SELECT
    exam_id,
    SUM(IF(submit_time IS NULL, 1, 0)) AS 'incomplete_cnt',
    ROUND(
		SUM(IF(submit_time IS NULL, 1, 0)) 
		/ 
		COUNT(IF(submit_time IS NULL, 0, 0)), 3
		) AS 'incomplete_rate'
FROM
    exam_record
GROUP BY exam_id
HAVING incomplete_cnt >= 1;
```









优化:

- 根据执行计划，最终需要使用临时表和文件排序，明显是因为最后的分组字段
- 所以在该字段上建立索引即可，可见文件排序和临时表都消失了，查询消耗直接降为1.60

****



























# Day122

## Tag: IFNULL, TIMESTAMPDIFF

![Xnip2021-11-23_07-19-34](MySQL Note.assets/Xnip2021-11-23_07-19-34.jpg)



![Xnip2021-11-23_07-22-42](MySQL Note.assets/Xnip2021-11-23_07-22-42.jpg)



![Xnip2021-11-23_07-31-03](MySQL Note.assets/Xnip2021-11-23_07-31-03.jpg)



![Xnip2021-11-23_07-44-29](MySQL Note.assets/Xnip2021-11-23_07-44-29.jpg)



![Xnip2021-11-23_07-44-10](MySQL Note.assets/Xnip2021-11-23_07-44-10.jpg)

题意:

给你一张用户信息表，一张试卷信息表，一张作答记录表，请你查询出每个0级用户所有高难度题目的平均用时和平均分数，如果试卷未完成则按照默认时间和0分处理









思路:

- 求分数本身其实就是求出总分再除以总人数，但我们统计总分的时候需要考虑未完成的情况(分数为null)，所以要用IF来转换NULL值为0
- 统计总人数也是如此，使用IFNULL处理即可，给的值随意，只要不为null就都能被COUNT计算到，最外层记得用ROUND保留整数
- 在计算平均用时的时候也需要处理NULL值，正常计算用时则需要TIMESTAMPDIFF并制定单位为MINUTE，为null则用IFNULL设置为duration，记得最外层使用ROUND保留一位小数
- 最后连接三张表，写上剩余的限制条件即可，SQL如下

```mysql
SELECT
    t1.uid,
    ROUND(
			SUM(IF(t1.score IS NULL, 0, t1.score)) 
			/
			COUNT(IFNULL(t1.score IS NULL, 0)), 0
		) AS 'avg_score',
    ROUND(
			AVG(IFNULL(TIMESTAMPDIFF(MINUTE, t1.start_time, t1.submit_time), t2.duration)), 1
		) AS 'avg_time_took'
FROM
    exam_record AS t1
INNER JOIN examination_info AS t2 ON t1.exam_id = t2.exam_id
INNER JOIN user_info AS t3 ON t1.uid = t3.uid
WHERE t3.level = 0
AND t2.difficulty = 'hard'
GROUP BY t1.uid
```







优化:

- 由查询计划可知，查询消耗为5.33，最终驱动表为t3且只有t1使用了索引
- 在t3的Extra列中有Using where，说明最终需要回表，涉及到t3的列只有uid和level，其中uid上已经有了一个唯一键，所以我们在level上建立一个索引即可
- 查看查询计划，消耗降为了5.13，同样，我们也可以在examination_info上的difficulty列上建立索引，但效果差不多
- 但t1就比较难优化了，因为涉及到的列很多，想要强行走索引的话需要创建多个索引或者一个很大的联合索引

































