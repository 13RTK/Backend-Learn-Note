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

****



















# Day123

## Tag: SUBSTRING_INDEX

![Xnip2021-11-24_07-27-51](MySQL Note.assets/Xnip2021-11-24_07-27-51.jpg)



![Xnip2021-11-24_07-32-00](MySQL Note.assets/Xnip2021-11-24_07-32-00.jpg)



![Xnip2021-11-24_07-37-29](MySQL Note.assets/Xnip2021-11-24_07-37-29.jpg)



![Xnip2021-11-24_07-44-46](MySQL Note.assets/Xnip2021-11-24_07-44-46.jpg)

题意:

给你一张试卷信息表，其中部分记录的试题类别tag、难度、时长被同时输入到了tag字段中，请你找出这些字段，并将它们正确拆分后列出





思路:

- 所谓的输入错误其实就是将多个字段值用","隔开后放到了同一个字段里，使用SUBSTRING_INDEX就能实现拆分
- 而确定这些字段可以根据difficulty(空字符长度为0)或者duration(为0)，SQL如下

```mysql
SELECT
    exam_id,
    SUBSTRING_INDEX(tag, ',', 1) AS 'tag',
    SUBSTRING_INDEX(SUBSTRING_INDEX(tag, ',', 2), ',', -1) AS 'difficulty',
    SUBSTRING_INDEX(tag, ',', -1) AS 'duration'
FROM
    examination_info
WHERE duration = 0;
```





优化:

- 由查询计划可知，最终的查询需要回表，且没有用到索引，查询消耗为1.80 
- 在WHERE子句中只有一个等值边界条件，所以很明显，在这个条件列上建立索引即可，此时消耗降为1.20
- 需要注意的是，MySQL5.7并不支持索引列以函数的形式出现，所以如果我们改为WHERE LENGTH(difficulty) = 0的话就不会用到索引了，除非通过虚拟列的方式，而MySQL8.0是支持函数索引的(也是通过虚拟列的方式)

****





















# Day124

## Tag: CHAR_LENGTH

![Xnip2021-11-25_07-39-51](MySQL Note.assets/Xnip2021-11-25_07-39-51.jpg)



![Xnip2021-11-25_07-42-26](MySQL Note.assets/Xnip2021-11-25_07-42-26.jpg)

题意:

给你一张用户信息表，请你将其中用户昵称长度大于10个字符的用户查询出来，并根据昵称长度做相应的处理







思路:

- 统计字符串长度的时候下意识会想到用LENGTH函数，但这个函数在统计时会受到编码的影响，所以并不准确
- 因此我们需要换用CHAR_LENGTH，在处理时只需要判断长度是否大于13即可，大于则使用LEFT进行截断，再使用CONCAT进行拼接即可



SQL:

```mysql
SELECT
    uid,
    IF(CHAR_LENGTH(nick_name) > 13, CONCAT(LEFT(nick_name, 10), '...'), nick_name) AS 'nick_name'
FROM
    user_info
WHERE CHAR_LENGTH(nick_name) > 10;
```

****





















# Day125

## Tag: UPPER, JOIN

![Xnip2021-11-26_07-59-30](MySQL Note.assets/Xnip2021-11-26_07-59-30.jpg)



![Xnip2021-11-26_07-59-07](MySQL Note.assets/Xnip2021-11-26_07-59-07.jpg)

题意:

给你一张试卷信息表，一张试卷作答表，请你查询出其中作答数小于3，且tag转换为大写后依然有数据的试卷信息和大写对应的作答数，tag转换为大写后无变化的则不需要查询出来







思路:

- 首先肯定是先找出作答数小于3的试卷了，而且还需要筛掉变为大写后无变化的数据，因此需要分组求和，大写转换则可以使用UPPER，SQL如下

SQL1

```mysql
SELECT
	t1.exam_id,
	t1.tag,
	COUNT( t2.start_time ) AS 'answer_cnt' 
FROM
	examination_info AS t1
INNER JOIN exam_record AS t2 ON t1.exam_id = t2.exam_id 
WHERE t1.tag != UPPER( t1.tag ) 
GROUP BY t1.exam_id, t1.tag
HAVING answer_cnt < 3 
```







- 之后需要寻找出其对应的大写数据，所以我们还需要查询出所有的作答记录，从中选取，SQL如下

SQL2

```mysql
SELECT
	t1.exam_id,
	t1.tag,
	COUNT( t2.start_time ) AS 'answer_cnt' 
FROM
	examination_info AS t1
INNER JOIN exam_record AS t2 ON t1.exam_id = t2.exam_id 
GROUP BY t1.exam_id, t1.tag
```









- 最后连接两张表，通过转换后的tag连接起来，并将对应的大写记录加起来，SQL如下


```mysql
SELECT
	t1.tag,
	SUM( t2.answer_cnt ) AS 'answer' 
FROM (
	SQL1
	) AS t1
INNER JOIN (
	SQL2
	) AS t2 ON UPPER( t1.tag ) = t2.tag 
GROUP BY t1.tag
```













