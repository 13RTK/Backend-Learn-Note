# Day171

## Tag: TIME

![Xnip2022-01-11_11-00-53](MySQL Note.assets/Xnip2022-01-11_11-00-53.jpg)



![Xnip2022-01-11_11-01-06](MySQL Note.assets/Xnip2022-01-11_11-01-06.jpg)

题意:

给你一张课程信息表，一张上课情况记录表，请你查询出每个课程在开始时在线的人数







思路:

- 因为需要每个课程分开来看，所以需要分组，而其中每个课程的上课时间都是固定的，
- 上课时间都是19:00，所以我们只需要限定学员在直播间的时间中有19:00即可，这里使用TIME函数，所以SQL如下

```mysql
SELECT
    t1.course_id,
    t1.course_name,
    COUNT(t2.user_id) AS 'online_num'
FROM
    course_tb AS t1
INNER JOIN attend_tb AS t2 ON t1.course_id = t2.course_id
WHERE TIME(in_datetime) <= '19:00:00'
AND TIME(out_datetime) >= '19:00:00'
GROUP BY t1.course_id, t1.course_name
ORDER BY t1.course_id
```

<hr>













# Day172

## Tag: TIMESTAMPDIFF

![Xnip2022-01-12_13-53-02](MySQL Note.assets/Xnip2022-01-12_13-53-02.jpg)



![Xnip2022-01-12_13-54-03](MySQL Note.assets/Xnip2022-01-12_13-54-03.jpg)

题意:

给你一张视频互动信息表，一张视频信息表，请你查询有用户互动的近一个月内，每类视频的转发量和转发率





思路:

- 转发量很好计算，统计if_retweet字段即可
- 同样的，转发率只需要使用转发量除以播放量即可
- 但问题是，如何限制日期在最近的一个月内呢？
- 首先，我们需要找出最近的日期，这里使用MAX即可，SQL如下

SQL1:

```mysql
SELECT
	MAX(DATE(start_time)) AS 'last_date'
FROM
	tb_user_video_log
```





有了最近日期后，我们只需要加以限制即可，这里我使用TIMESTAMPDIFF，当然使用其他函数也未尝不可，最终SQL如下

```mysql
SELECT
    t2.tag,
    SUM(t1.if_retweet) AS 'retweet_cnt',
    ROUND(SUM(t1.if_retweet) / COUNT(uid), 3) AS 'retweet_rate'
FROM
    tb_user_video_log AS t1
INNER JOIN tb_video_info AS t2 ON t1.video_id = t2.video_id
WHERE TIMESTAMPDIFF(DAY, DATE(start_time), (
    SQL1
    )) < 30
GROUP BY t2.tag
ORDER BY retweet_rate DESC
```

<hr>











# Day173

## Tag: ROUND, GROUP BY

![Xnip2022-01-13_13-00-15](MySQL Note.assets/Xnip2022-01-13_13-00-15.jpg)



![Xnip2022-01-13_13-01-33](MySQL Note.assets/Xnip2022-01-13_13-01-33.jpg)

题意:

给你一张课程信息表，一张用户行为表，请你查询出其中每种科目转换率(报名人数/浏览人数)





思路:

- 报名人数只需要统计if_sign字段即可，浏览人数则统计if_vw字段即可，SQL如下

```mysql
SELECT
    t1.course_id,
    t1.course_name,
    ROUND(100 * SUM(t2.if_sign) / COUNT(t2.if_vw), 2) AS 'sign_rate'
FROM
    course_tb AS t1
INNER JOIN behavior_tb AS t2 ON t1.course_id = t2.course_id
GROUP BY t1.course_id, t1.course_name
ORDER BY course_id
```

<hr>











# Day174

## Tag: TIMESTAMPDIFF, AVG

![Xnip2022-01-14_14-58-36](MySQL Note.assets/Xnip2022-01-14_14-58-36.jpg)



![Xnip2022-01-14_14-58-51](MySQL Note.assets/Xnip2022-01-14_14-58-51.jpg)

题意:

给一张课程信息表，一张课程参与表，请你查询出其中每个课程的平均时长(分钟)





思路:

- 首先，每个用户的参与时长为出入直播间的时间之差，这里我使用TIMESTAMPDIFF，然后使用AVG计算平均值
- 剩余部分就比较常规了，所以SQL如下

```mysql
SELECT
    t1.course_name,
    ROUND(AVG(TIMESTAMPDIFF(MINUTE, t2.in_datetime, t2.out_datetime)), 2) AS 'avg_Len'
FROM
    course_tb AS t1
INNER JOIN attend_tb AS t2 ON t1.course_id = t2.course_id
GROUP BY t1.course_name
ORDER BY avg_Len DESC
```

<hr>

















# Day175

## Tag: IF

![Xnip2022-01-15_14-21-43](MySQL Note.assets/Xnip2022-01-15_14-21-43.jpg)



![Xnip2022-01-15_14-44-57](MySQL Note.assets/Xnip2022-01-15_14-44-57.jpg)

题意:
给你一张创作者信息表，一张回答信息表，请你查询出高质量回答中，不同等级的用户对应的数量





思路:

- 因为需要根据等级来分组，所以我们需要对作者等级author_level字段做简单的逻辑判断，可以使用CASE WHEN，也可以使用IF，个人比较懒，所以就用IF了
- 至于高质量回答，则限制char_len字段即可，最后注意分组和排序，SQL如下

```mysql
SELECT
    IF(t1.author_level <= 2, '1-2级', IF(t1.author_level >= 5, '5-6级', '3-4级')) AS 'level_cnt',
    COUNT(t2.author_id) AS 'num'
FROM
    author_tb AS t1
INNER JOIN answer_tb AS t2 ON t1.author_id = t2.author_id
WHERE char_len >= 100
GROUP BY level_cnt
ORDER BY num DESC
```







优化:

MySQL版本为5.7.10



优化前:

- 使用EXPLAIN查看执行计划，由于原表中没有创建任何索引，所以Extra字段中出现了"Using where"，即需要回表到server层中再做判断
- 又因为我们使用了ORDER BY进行排序，所以Extra字段中又出现了"Using filesort"，又因为排序时没有用到索引，所以需要使用临时表，因此Extra字段中出现了"Using temporary"
- 最后，因为两表需要连接且需要多次访问驱动表，为了减少I/O，MySQL默认使用了join buffer来一次性加载多条驱动表记录，方便与其他表匹配
- 再使用EXPLAIN FORMAT=JSON查看其查询开销，发现当前开销为18.46

![Xnip2022-01-15_14-47-09](MySQL Note.assets/Xnip2022-01-15_14-47-09.jpg)







分析:

- 从Extra列中的信息来看，我们可以尝试将回表操作变为走覆盖索引，其中两表连接时使用了字段author_id，而t2表又用到了char_len字段，所以我们可以尝试用这两个字段建立一个联合索引，这样t2表就能走覆盖索引了

- 此时查看执行计划，果然t2表中的"Using temporary"消失了，变为了"Using index"，说明执行计划使用了我们的联合索引，此时再查看开销，发现已经降为了16.81！



![Xnip2022-01-15_15-03-57](MySQL Note.assets/Xnip2022-01-15_15-03-57.jpg)

![Xnip2022-01-15_14-54-41](MySQL Note.assets/Xnip2022-01-15_14-54-41.jpg)

- t2表搞定了，那么t1表呢？首先肯定是对连接字段author_id创建索引
- 由于排序时没有使用索引，所以还是用到了临时表和文件排序
- 正常思路来看，一般都会想到在分组字段和排序字段上创建索引，但这里没这么简单
- 因为排序的字段是一个聚合函数表达式，不是表中的既存字段
- 那么就没办法了吗？有的朋友可能会想到函数索引，也就是借助虚拟列来创建函数索引，其中COUNT(t2.author_id)是没办法的，因为它需要分组，所以不考虑
- 但level_cnt可以考虑一下，因此我们可以在author_tb创建一个虚拟列，DDL语句如下

```mysql
ALTER TABLE author_tb ADD level_cnt varchar(10) AS (IF(t1.author_level <= 2, '1-2级', IF(t1.author_level >= 5, '5-6级', '3-4级')));
```

- 此时表结构如下

![Xnip2022-01-15_15-34-26](MySQL Note.assets/Xnip2022-01-15_15-34-26.jpg)

- 此时我们再对该虚拟列创建一个索引即可，这时再看查询计划和开销: 优化了个寂寞，查询计划只用到了我们创建的author_id索引，幸好查询开销降为了12.40！
- 那就这样吧，还是太菜了...

![Xnip2022-01-15_15-39-47](MySQL Note.assets/Xnip2022-01-15_15-39-47.jpg)



![Xnip2022-01-15_15-40-55](MySQL Note.assets/Xnip2022-01-15_15-40-55.jpg)

虚拟列的官方文档说明:

[MySQL :: MySQL 5.7 Reference Manual :: 13.1.18.7 CREATE TABLE and Generated Columns](https://dev.mysql.com/doc/refman/5.7/en/create-table-generated-columns.html)

<hr>

















# Day176

## Tag: HAVING

![Xnip2022-01-16_13-17-11](MySQL Note.assets/Xnip2022-01-16_13-17-11.jpg)



![Xnip2022-01-16_13-19-58](MySQL Note.assets/Xnip2022-01-16_13-19-58.jpg)

题意:

给你一张回答记录表，请你查询出其中单日回答数大于等于3的所有用户信息





思路:

- 

- 因为需要限制的是回答数是分组后的的信息，所以我们需要在分组后使用HAVING，SQL如下

```mysql
SELECT
    answer_date,
    author_id,
    COUNT(issue_id) AS 'answer_cnt'
FROM
    answer_tb
GROUP BY answer_date, author_id
HAVING answer_cnt >= 3
ORDER BY answer_date, author_id
```







优化:

MySQL版本为5.7.10，数据基于牛客网的示例



分析:

- 首先原表中没有任何索引，又因为我们需要分组和排序，所以用到了临时表和文件排序，因此Extra中有"Using temporary"和"Using filesort"
- 此时查询计划使用的是全表扫描，查询开销为23.80

![Xnip2022-01-16_13-24-01](MySQL Note.assets/Xnip2022-01-16_13-24-01.jpg)





思考:

- 既然是因分组和排序而起，那么在这两个字段上创建索引不就行了？
- 其实不然，因为分组和排序的字段是有先后顺序的，而如果两个字段的索引是独立的，那么在使用除第一个字段外的字段索引时是很低效的(会重复扫描)，此时索引会失效
- 因此，我们应该根据顺序创建一个联合索引，此时查看执行计划发现Extra字段变为了null，再查看查询开销发现降为了4.80!

![Xnip2022-01-16_13-25-38](MySQL Note.assets/Xnip2022-01-16_13-25-38.jpg)



![Xnip2022-01-16_13-40-29](MySQL Note.assets/Xnip2022-01-16_13-40-29.jpg)

<hr>



















# Day177

## Tag: Sub Query

![Xnip2022-01-17_12-02-24](MySQL Note.assets/Xnip2022-01-17_12-02-24.jpg)



题意:

给你一张题目信息表，一个回答情况表，请你查询出其中回答过教育类问题的用户中，回答过职场问题的用户数量



思路:

- 因为在统计前，我们需要对用户回答过的问题类型进行限制，而这个限制本身又有个条件(即在回答过教育类的用户中去查询)
- 因此我们首先应该为这个限制，获取对应的记录，因此我们需要先查询出所有回答过教育类问题的用户，SQL如下

SQL1:

```mysql
SELECT
	t2.author_id
FROM
	issue_tb AS t1
INNER JOIN answer_tb AS t2 ON t1.issue_id = t2.issue_id
WHERE t1.issue_type = 'Education'
```



- 将该查询结果作为条件之一，再统计用户数量即可，SQL如下

```mysql
SELECT
    COUNT(DISTINCT t2.author_id) AS 'num'
FROM
    issue_tb AS t1
INNER JOIN answer_tb AS t2 ON t1.issue_id = t2.issue_id
WHERE t2.author_id IN (
    SQL1
    )
AND t1.issue_type = 'Career';
```















优化:

- 按照惯例查看执行计划，因为没有索引，所以全为全表扫描
- 此时开销为21.38

![Xnip2022-01-17_14-19-43](MySQL Note.assets/Xnip2022-01-17_14-19-43.jpg)



![Xnip2022-01-17_14-19-25](MySQL Note.assets/Xnip2022-01-17_14-19-25.jpg)





分析:

- 对于没有任何索引的表(其实还是有隐藏列的)，我们可以利用《阿里巴巴Java开发手册》中对索引的约束，对两表连接的字段添加索引
- 所以我们对两表的issue_id添加索引，再为answer_tb中的author_id字段添加索引
- 为什么不为issue_type建立索引呢？首先，这个字段在表中的区分度并不高，就算加上，也作用不大，甚至会起反作用(各位可以去试试)
- 优化后，我们再次查看开销，降为了13.50

![Xnip2022-01-17_14-25-29](MySQL Note.assets/Xnip2022-01-17_14-25-29.jpg)



![Xnip2022-01-17_14-24-07](MySQL Note.assets/Xnip2022-01-17_14-24-07.jpg)

<hr>













# Day178

## Tag: CAST, SUBSTRING_INDEX

![Xnip2022-01-18_13-12-44](MySQL Note.assets/Xnip2022-01-18_13-12-44.jpg)



![Xnip2022-01-18_13-05-54](MySQL Note.assets/Xnip2022-01-18_13-05-54.jpg)

题意:

给你一张产品信息表，一张订单明细表，一张订单总表，请你查询出901店铺2021年10月份以来所有毛利率大于24.9%的商品信息和店铺的整体毛利率









思路:

- 既然需要商品和店铺两种，那么我们直接将问题分为两个部分不就行了？所以我们可以先行查询店铺的整体毛利率
- 因为店铺毛利率 = (1 - 总进价成本 / 总销售成本) * 100%，所以我们应该求出卖出的商品进价总和以及销售总和(两者对应的商品数量应该一致)，而最终结果的"%"符号则只需要使用CONCAT进行连接即可，所以SQL如下

SQL1

```mysql
SELECT
	'店铺汇总' AS 'product_id',
	CONCAT(ROUND(100 * (1 - (SUM(t1.in_price * t2.cnt) / SUM(t2.price * t2.cnt))), 1), '%') AS 'profit_rate'
FROM
	tb_product_info AS t1
INNER JOIN tb_order_detail AS t2 ON t1.product_id = t2.product_id
INNER JOIN tb_order_overall AS t3 ON t2.order_id = t3.order_id
WHERE t1.shop_id = 901
AND DATE(t3.event_time) >= '2021-10-01'
```



- 剩下的就是单个商品了，我们采用类似的方法即可，注意这里的毛利率需要基于单价计算
- 问题是，在获取901店铺每个商品的毛利率后，怎么筛选出毛利率大于24.9%的呢？此时我们的SQL为

SQL2

```mysql
SELECT
	t1.product_id,
	CONCAT(ROUND(100 * (1 - t1.in_price / AVG(t2.price)), 1), '%') AS 'profit_rate'
FROM
	tb_product_info AS t1
INNER JOIN tb_order_detail AS t2 ON t1.product_id = t2.product_id
INNER JOIN tb_order_overall AS t3 ON t2.order_id = t3.order_id
WHERE t1.shop_id = 901
AND DATE(t3.event_time) >= '2021-10-01'
GROUP BY t1.product_id
```



- 直接在HAVING中使用profit_rate吗？profit_rate是CONCAT连接后的结果，它是一个字符串呀，不能与数字直接比较，那咋办？类型转换？但它还带有一个"%"呀
- 不怕，我们可以先使用SUBSTRING_INDEX分割出数字部分，再使用CAST将其转换为DECIMAL类型即可，最终SQL如下

```mysql
SQL1
UNION ALL
SQL2
HAVING CAST(SUBSTRING_INDEX(profit_rate, '%', 1) AS DECIMAL(3, 1)) > 24.9
```





- 其他题解中用到了ROLLUP，在MySQL8.0确实没问题，但在MySQL5.7中ROLLUP和ORDER BY不能一起使用，因此我这里并未选择ROLLUP



官方文档:

![Xnip2022-01-18_13-42-21](MySQL Note.assets/Xnip2022-01-18_13-42-21.jpg)



![Xnip2022-01-18_13-43-08](MySQL Note.assets/Xnip2022-01-18_13-43-08.jpg)

<hr>

















# Day179

## Tag: COUNT() OVER, TIMESTAMPDIFF

![Xnip2022-01-19_10-14-28](MySQL Note.assets/Xnip2022-01-19_10-14-28.jpg)



![Xnip2022-01-19_10-13-37](MySQL Note.assets/Xnip2022-01-19_10-13-37.jpg)

题意:

给你一张产品信息表，一张订单明细表，一张订单总表，请你查询出近90天中，所有零食类商品中复购率最高的商品





思路:

- 首先，对日期的限制是在最近一天的基础上的，所以我们应该先查询出最近一天的日期值，SQL如下

SQL1

```mysql
SELECT
	MAX(event_time) AS 'last_date'
FROM
	tb_order_overall
```



- 有了日期基准后，我们再获取每个用户对应购买每种产品的次数即可
- 获取购买次数的时候可以使用窗口函数，也可以使用GROUP BY，SQL如下

SQL2

```mysql
SELECT
		DISTINCT t1.uid,
		t2.product_id,
		COUNT(t2.product_id) OVER(
				PARTITION BY t1.uid, t2.product_id
		) AS 'purchase_time'
FROM
		tb_order_overall AS t1
INNER JOIN tb_order_detail AS t2 ON t1.order_id = t2.order_id
INNER JOIN tb_product_info AS t3 ON t2.product_id = t3.product_id AND t3.tag = '零食'
WHERE TIMESTAMPDIFF(DAY, t1.event_time, (
		SQL1
		)) < 90
```



- 之后根据购买次数统计对应的次数即可，最终SQL如下

```mysql
SELECT
    product_id,
    ROUND(SUM(IF(purchase_time = 2, 1, 0)) / COUNT(DISTINCT uid), 3) AS 'repurchase_rate'
FROM (
	SQL2
    ) AS t1
GROUP BY product_id
ORDER BY repurchase_rate DESC, product_id
LIMIT 3
```

<hr>















# Day180

## Tag: HAVING

![Xnip2022-01-20_10-47-11](MySQL Note.assets/Xnip2022-01-20_10-47-11.jpg)



题意:

给你一张用户打车记录表，和一张打车记录表，请你查询出2021年国庆7天期间，在北京接单至少三次的司机平均的接单数和平均兼职收入







思路:

- 首先，我们需要查询出的是每个司机的平均接单数和平均的兼职收入，但每个司机的接单数和收入不是既存字段，所以我们需要先获取这两个数据才行
- 获取这两个数据的同时，我们也要限制订单的时间和接单地点，并在分组后筛选出接单数大于3的，SQL如下

SQL1

```mysql
SELECT
	t1.driver_id,
	COUNT(t1.order_id) AS 'order_num',
	SUM(t1.fare) AS 'income'
FROM
	tb_get_car_order AS t1
	INNER JOIN tb_get_car_record AS t2 ON t1.order_id = t2.order_id
WHERE DATE(finish_time) BETWEEN '2021-10-01' AND '2021-10-07'
AND t2.city = '北京'
GROUP BY driver_id
HAVING COUNT(t1.order_id) >= 3
```



- 有了这两个字段后，我们再统计平均接单数和收入即可，SQL如下

```mysql
SELECT
    '北京' AS 'city',
    ROUND(AVG(t1.order_num), 3) AS 'avg_order_num',
    ROUND(AVG(t1.income), 3) AS 'avg_income'
FROM (
    SQL1
    ) AS t1
```







优化:

- 查看执行计划的开销: 10.50
- 由于我们使用了子查询，而子查询的数据量较大，所以这里使用了物化表(将子查询结果放到临时表中，并建立哈希(较少)/B+索引(较多))，因此后面两个表的select_type为DERIVED，且PRIMARY表的表名为derived2
- 今天借着这单题整点花活: MySQL5.7的隐式排序(implicit sort)，即MySQL5.7中会在GROUP BY后面默认为分组字段加上一个排序(2019.4在MySQL8.0中已经移除)
- 因此，明明没有写ORDER BY，但我们的执行计划Extra中有一个"Using filesort"
- 其余的"Using where"、"Using temporary"和"Using join buffer"就比较常规了，即临时表分组，回表和存储驱动表记录的join buffer

![Xnip2022-01-20_10-42-13](MySQL Note.assets/Xnip2022-01-20_10-42-13.jpg)



![Xnip2022-01-20_10-46-20](MySQL Note.assets/Xnip2022-01-20_10-46-20.jpg)



分析

- 先从解决隐式排序开始，我们其实并不需要排序，所以这里我们使用ORDER BY NULL来避免排序，相应的再加上对应的索引，不过对应数据量少，开销并未减少

![Xnip2022-01-20_11-56-20](MySQL Note.assets/Xnip2022-01-20_11-56-20.jpg)



官方文档:

![Xnip2022-01-20_11-14-24](MySQL Note.assets/Xnip2022-01-20_11-14-24.jpg)



![Xnip2022-01-20_11-26-23](MySQL Note.assets/Xnip2022-01-20_11-26-23.jpg)

<hr>















# Day181

## Tag: WITH ROLLUP

<img src="MySQL Note.assets/Xnip2022-01-21_15-19-14.jpg" alt="Xnip2022-01-21_15-19-14" style="zoom:50%;" />



![Xnip2022-01-21_15-18-28](MySQL Note.assets/Xnip2022-01-21_15-18-28.jpg)

题意:

给你一张用户打车记录表，一张打车订单表，请你查询出2021年10月有过取消订单的司机中，所有已经完成的订单对应的平均评分和司机的整体评分









思路:

- 因为分为整体和各个司机两个部分，因此我们可以使用UNION ALL连接两个SQL来获取结果
- 但这里我们可以使用ROLLUP，ROLLUP会统计出整体的数据，但其分组字段为NULL，因此我们对分组字段(这里是driver_id)进行判断，就能分为单个司机和整体
- 但在这之前，我们需要获取对应的driver_id，SQL如下

SQL1

```mysql
SELECT
	driver_id
FROM
	tb_get_car_order
WHERE DATE(finish_time) BETWEEN '2021-10-01' AND '2021-10-31'
AND ISNULL(grade)
```



- 之后将该表作为物化表即可，并限制查询的记录为已经完成的订单(评分不为NULL)，最终SQL如下

```mysql
SELECT
	IFNULL(driver_id, '总体') AS 'driver_id',
	ROUND(AVG(grade), 1) AS 'avg_grade'
FROM
	tb_get_car_order
WHERE NOT ISNULL(grade)
AND driver_id IN (
	SQL1
)
GROUP BY driver_id
WITH ROLLUP
```

<hr>















# Day182

## Tag: DENSE_RANK

![Xnip2022-01-22_12-03-32](MySQL Note.assets/Xnip2022-01-22_12-03-32.jpg)



![Xnip2022-01-22_12-02-56](MySQL Note.assets/Xnip2022-01-22_12-02-56.jpg)

题意:

给你一张用户打车记录表，一张打车订单表，请你查询出每个城市中评分最高的司机的平均评分、日均接单量和日均里程数。当多个司机评分并列时，需一并查询出来









思路:

- 首先结果需要不同城市中的司机，所以我们需要按照城市来分类，又因为我们还需要每个司机的平均分，并需要根据这个平均分来排序
- 所以我们还需要根据城市和司机来分组，SQL如下

SQL1:

```mysql
SELECT
	t1.city,
	t2.driver_id,
	ROUND(AVG(t2.grade), 1) AS 'avg_grade'
FROM
	tb_get_car_record AS t1
INNER JOIN tb_get_car_order AS t2 ON t1.order_id = t2.order_id
GROUP BY t1.city, t2.driver_id
ORDER BY NULL
```



- 有了平均分后，我们再根据分数来获取每个城市中对应司机的排名，SQL如下

SQL2:

```mysql
SELECT
	city,
	driver_id,
	DENSE_RANK() OVER(
		PARTITION BY city
		ORDER BY avg_grade DESC
	) AS 'rank',
	avg_grade
FROM (
	SQL1
) AS t1
```



- 最后，我们再限制对应的排名为1，最后连接上述的临时表，再计算出日均接单数和历程即可，最终SQL如下

```mysql
SELECT
    t1.city,
    t2.driver_id,
    t3.avg_grade,
    ROUND(COUNT(t2.order_id) / COUNT(DISTINCT DATE(t2.order_time)), 1) AS 'avg_order_num',
    ROUND(SUM(t2.mileage) / COUNT(DISTINCT DATE(t2.order_time)), 3) AS 'avg_mileage'
FROM
    tb_get_car_record AS t1
INNER JOIN tb_get_car_order AS t2 ON t1.order_id = t2.order_id
INNER JOIN (
    SQL2
    ) AS t3 ON t1.city = t3.city AND t2.driver_id = t3.driver_id AND t3.rank = 1
GROUP BY t1.city, t2.driver_id
ORDER BY avg_order_num
```

<hr>

















# Day183

## Tag: Window Func, Frame

![Xnip2022-01-23_14-26-50](MySQL Note.assets/Xnip2022-01-23_14-26-50.jpg)



![Xnip2022-01-23_14-27-14](MySQL Note.assets/Xnip2022-01-23_14-27-14.jpg)

题意:

给你一张打车订单表，请你查询出2021年10月1号到3号这三天中，每天近7天的日均订单完成量和取消量





思路:

- 因为题目已经给了我们最终查询结果的依据了，那么我只需要在该表上进行查询即可，所以我们需要先查询出对应日期内每天的订单完成量和取消量，SQL如下

SQL1:

```mysql
SELECT
	DATE(finish_time) AS 'date',
	COUNT(start_time) AS 'finish_num',
	SUM(CASE WHEN ISNULL(start_time) THEN 1 ELSE 0 END ) AS 'cancel_num' 
FROM
	tb_get_car_order 
WHERE
	DATE(finish_time) BETWEEN '2021-09-25' AND '2021-10-03' 
GROUP BY DATE(finish_time)
```



- 之后再查询出每天的平均值即可，但需要注意的是，我们这里的平均值是在累加的基础上进行的，所以我们可以利用窗口函数指定ORDER BY字段为累加字段即可
- 这样就行了吗？注意我们需要的是每天近7日的订单记录，所以这三个日期需要限制对应的累加范围，这该咋办？
- 其实窗口函数还有一个可选参数即frame: 这里我们使用ROWS 6再使用PRECEDING即可指定累加范围为近7天，具体的解释可以去看这篇文章:

[新特性解读 | MySQL 8.0 窗口函数框架用法 (actionsky.com)](https://opensource.actionsky.com/20210125-mysql/)



- 最后我们再跳过不需要的那几天即可，因此最终SQL如下

```mysql
SELECT
	date,
	ROUND(AVG(finish_num) OVER(ORDER BY date ROWS 6 PRECEDING), 2) AS 'finish_num_7d',
	ROUND(AVG(cancel_num) OVER(ORDER BY date ROWS 6 PRECEDING), 2) AS 'cancel_num_7d'
FROM (
	SQL1
	) AS t1
LIMIT 3 OFFSET 6
```

<hr>













# Day184

## Tag: DATE_FORMAT

![Xnip2022-01-24_10-12-01](MySQL Note.assets/Xnip2022-01-24_10-12-01.jpg)



![Xnip2022-01-24_10-11-28](MySQL Note.assets/Xnip2022-01-24_10-11-28.jpg)

题意:

给你一张订单明细表和一张订单总表，请你查询出2021年10月，所有新用户的首单交易金额和平均获客成本





思路:

- 因为有日期限制，所以我们需要先获取10月份中对应新用户首单日期，SQL如下

SQL1:

```mysql
SELECT
		uid,
		MIN(DATE(event_time)) AS 'first_order_date'
FROM
		tb_order_overall
GROUP BY uid
HAVING DATE_FORMAT(first_order_date, '%Y-%m') = '2021-10'
```



- 有了对应的用户id和日期后，我们只需要根据这两个字段查询出对应订单的总金额和每单的优惠金额，SQL如下

SQL2:

```mysql
SELECT 
	t1.order_id,
	t2.total_amount,
	SUM(t1.price * t1.cnt) - total_amount AS 'discount_amount'
FROM
	tb_order_detail AS t1
LEFT JOIN tb_order_overall AS t2 ON t1.order_id = t2.order_id
WHERE (t2.uid, DATE(t2.event_time)) IN (
	SQL1
	)
GROUP BY t1.order_id
```



- 有了这些数据后我们再求平均值即可，最终SQL如下

```mysql
SELECT
	ROUND(AVG(total_amount), 1) AS 'avg_amount',
	ROUND(AVG(discount_amount), 1) AS 'avg_cost'
FROM (
	SQL2
	) AS temp
```





优化:



分析:

- 原表中只有主键列id，但id并未出现在我们的条件中，所以暂时无用，此时查询开销为11.50
- 因为我们使用了子查询的形式，所以id为3的SELECT中的select_type为"DEPENDENT SUBQUERY"即相关子查询，而t2和t1都以物化表的形式执行派生表查询，所以select_type为"DERIVED"

![Xnip2022-01-24_10-38-57](MySQL Note.assets/Xnip2022-01-24_10-38-57.jpg)



尝试:

- 从里到外，我们先尝试在最内层的查询中添加索引，又因为使用的是MySQL5.7，所以这里在所有的GROUP BY后面写一个ORDER BY NULL
- 因为我们最终需要按照uid来分组，所以这里我选择在uid上建立索引
- 此时再看发现除了t2表的Extra列外，其他的都变为null了，但因为表中数据太少，所以并未有什么开销上的收效

![Xnip2022-01-24_11-11-27](MySQL Note.assets/Xnip2022-01-24_11-11-27.jpg)

<hr>









# Day185

## Tag: DENSE_RANK

![Xnip2022-01-25_17-04-43](MySQL Note.assets/Xnip2022-01-25_17-04-43.jpg)



![Xnip2022-01-25_17-07-58](MySQL Note.assets/Xnip2022-01-25_17-07-58.jpg)

题意:

给你一张创作者信息表，一张回答信息表，请你查询出其中最大连续回答天数大于等于3天的用户对应的id、等级和最大的连续回答天数







思路:

- 很明显，这里最大的限制就是最大的连续回答天数，这里可以利用到一个函数即DENSE_RANK，因为它能够在保持连续相同的排名，可以利用它来获取每个用户的连续回答天数，SQL如下

SQL1:

```mysql
SELECT
	answer_date,
	author_id,
	DENSE_RANK() OVER(
		PARTITION BY author_id
		ORDER BY answer_date
	) AS 'consecutive_day_num'
FROM
	answer_tb
```



- 有了连续回答天数后，我们只需要获取每个用户的最大回答天数，并限制从3开始即可，这样再连接上用户的信息表，获取对应的用户等级即可，最终SQL如下

```mysql
SELECT
	t1.author_id,
	t2.author_level,
	MAX(t1.consecutive_day_num) AS 'day_cnt'
FROM (
	SQL1
	) AS t1
INNER JOIN author_tb AS t2 ON t1.author_id = t2.author_id
WHERE t1.consecutive_day_num >= 3
GROUP BY t1.author_id
ORDER BY author_id
```

<hr>



















# Day186

## Tag: TIMESTAMPDIFF, HAVING

![Xnip2022-01-26_10-43-05](MySQL Note.assets/Xnip2022-01-26_10-43-05.jpg)



![Xnip2022-01-26_10-43-42](MySQL Note.assets/Xnip2022-01-26_10-43-42.jpg)

题意:

给你一张课程信息表，一张用户行为表，一张上课情况表，请你查询出其中每个科目的出勤率(在线时长10分钟及以上)







思路:

- 首先，我们需要查询出有效出勤的用户信息和对应的课程id，SQL如下

SQL1:

```mysql
SELECT
		user_id,
		course_id,
		SUM(TIMESTAMPDIFF(MINUTE, in_datetime, out_datetime)) AS 'online_time'
FROM
		attend_tb
GROUP BY user_id, course_id
HAVING online_time >= 10
```



- 有了这个临时表后，我们再根据课程进行分组统计其中的客户id，这样就能统计出其中的出勤人数
- 再连接其余两表，统计行为表中的if_sign字段获取报名人数，这样我们就获取了出勤率了，最终SQL如下

```mysql
SELECT
	t1.course_id,
	t3.course_name,
	ROUND(100 * COUNT(t2.user_id) / SUM(t1.if_sign), 2) AS 'attend_rate'
FROM
	behavior_tb AS t1
LEFT JOIN (
	SQL1
	) AS t2 ON t1.user_id = t2.user_id AND t1.course_id = t2.course_id
LEFT JOIN course_tb AS t3 ON t1.course_id = t3.course_id
GROUP BY t1.course_id, t3.course_name;
```

<hr>



















# Day187

## Tag: UNION ALL, SUM() OVER()

![Xnip2022-01-27_14-18-23](MySQL Note.assets/Xnip2022-01-27_14-18-23.jpg)



![Xnip2022-01-27_14-19-06](MySQL Note.assets/Xnip2022-01-27_14-19-06.jpg)

题意:

给你一张课程信息表，一张上课情况表，请你查询出每个课程最大同时在线人数



思路:

- 首先，由上课情况表可知，一个用户可能某时刻进入直播或退出直播，所以我们应该计算进入的同时也计算退出直播，而此时的直播间人数应该以时间为序，依次添加或减少人数
- 但因为in_datetime和out_datetime字段在同一张表中，所以我们这里要将其放到同一列中，并按照in_datetime和out_datetime进行相反的标记，SQL如下

SQL1:

```mysql
SELECT
	course_id,
	in_datetime AS 'time',
	1 AS 'state'
FROM
	attend_tb
UNION ALL
SELECT
	course_id,
	out_datetime AS 'time',
	-1 AS 'state'
FROM
	attend_tb
```



- 此时我们再根据课程进行分组，以时间和state为序依次计算人数(应该先计算进入的人数，所以这里以state升序排列)，此时SQL如下

SQL2:

```mysql
SELECT
	course_id,
	SUM(state) OVER(
		PARTITION BY course_id
		ORDER BY time, state DESC
	) AS 'num'
FROM (
	SQL1
) AS t1
```



- 以该表为基础，再获取其中最大的num值即可，最终SQL如下

```mysql
SELECT
    t1.course_id,
    t1.course_name,
    MAX(t2.num) AS 'max_num'
FROM
    course_tb AS t1
INNER JOIN (
    SQL2
    ) AS t2 ON t1.course_id = t2.course_id
GROUP BY t1.course_id, t1.course_name
ORDER BY t1.course_id
```



















