# Day231

## Tag: ROW_NUMBER

![Xnip2022-03-12_10-06-13](MySQL Note.assets/Xnip2022-03-12_10-06-13.jpg)



![Xnip2022-03-12_10-09-19](MySQL Note.assets/Xnip2022-03-12_10-09-19.jpg)

题意:

给你一张学生的来历表，请你根据地域，按照学生的姓名排序生成一张透视表



思路:

- 如果是简单的按照地域生成透视表的话，使用CASE WHEN就可以，但这里偏偏要按照学生姓名排序，这样就比较头痛了
- 为了让结果按照姓名排序，我们需要先行获取按照地区分组后的姓名排名，这里使用窗口函数即可，SQL如下

SQL1:

```mysql
SELECT 
    name,
    continent,
    ROW_NUMBER() OVER(
        PARTITION BY continent
        ORDER BY name
    ) AS 'rank'
FROM
    student
```



- 之后只需要从该表中获取对应地区的字典序最靠前的姓名即可
- 因为临时表中以及排好了序，所以我们直接使用即可，最终SQL如下

```mysql
SELECT
    MAX(CASE WHEN continent = 'America' THEN name END) as America,
    MAX(CASE WHEN continent = 'Asia' THEN name END) as Asia,
    MAX(CASE WHEN continent = 'Europe' THEN name END) as Europe
FROM (
		SQL1
) AS temp
GROUP BY `rank`
```

<hr>













# Day232

## Tag: ROW_NUMBER, COUNT() OVER

![Xnip2022-03-13_08-04-20](MySQL Note.assets/Xnip2022-03-13_08-04-20.jpg)



![Xnip2022-03-13_08-03-43](MySQL Note.assets/Xnip2022-03-13_08-03-43.jpg)

题意:

给你一张用户活动记录表，请你查询出用户最近的第二次活动，如果只有一个活动则直接查询出来即可



思路:

- 因为需要的是最近的第二次活动，所以我们需要将活动根据时间进行排序，且需要根据用户分组
- 且因为部分用户的活动次数可能只有1次，所以为了区分这两种用户，我们还需要根据用户分组后，统计其对应的活动次数，综合考虑下来，窗口函数是最适合的，SQL如下

SQL1:

```mysql
SELECT
    username,
    activity,
    startDate,
    endDate,
    ROW_NUMBER() OVER(
        PARTITION BY username
        ORDER BY endDate DESC
    ) AS 'rank',
    COUNT(*) OVER(
        PARTITION BY username
    ) AS 'cnt'
FROM
    UserActivity
```



- 获取该临时表后，我们只需要查询出排名为2，或者活动数为1的即可，最终SQL如下

```mysql
WITH temp AS (
    SQL1
)

SELECT
    username,
    activity,
    startDate,
    endDate
FROM
    temp
WHERE `rank` = 2
OR `cnt` = 1
```

<hr>









# Day233

## Tag: ROW_NUMBER, GROUP BY, LEFT JOIN

![Xnip2022-03-14_07-23-40](MySQL Note.assets/Xnip2022-03-14_07-23-40.jpg)



![Xnip2022-03-14_07-21-58](MySQL Note.assets/Xnip2022-03-14_07-21-58.jpg)

题意:

给你一张运动员的比赛记录表，请你查询出每个运动员的最长连胜次数





思路:

- 该题目可以使用窗口函数的frame解决，但说实话，我也记不住语法，这里就学习一下别人的解法:
- 首先，我们获取每个运动员对应比赛的时间次序，SQL如下

SQL1:

```mysql
SELECT
    player_id,
    match_day,
    result,
    ROW_NUMBER() OVER(
        PARTITION BY player_id
        ORDER BY match_day
    ) AS 'match_rank'
FROM
    Matches
```



- 之后重点来了，我们再查询出每个胜场的次序，并用比赛次序减去胜场次序
- 你问我为啥这么做？待会儿就知道了，此时SQL如下

SQL2:

```mysql
SELECT
    player_id,
    match_rank - ROW_NUMBER() OVER(
        PARTITION BY player_id
        ORDER BY match_rank
    ) AS 'diff'
FROM (
		SQL1
	) AS temp
WHERE result = 'Win'
)
```



- 将该表作为CTE临时表，根据player_id和diff分组，统计列数，SQL如下

SQL3:

```mysql
WITH rank_diff AS (
		SQL2
)

SELECT
    player_id,
    diff,
    COUNT(*) AS 'num'
FROM
    rank_diff
GROUP BY player_id, diff
```



- 到这里，就该解释思路了：
- 到这一步的时候，SQL的结果如图，可以看到，我们得出的结果中: 同一个运动员中diff不同的列对应的num不同
- 其实这不同的num对应的就是不同时间区间内，该运动员连胜的场次，为什么？
- 之前我们根据比赛时间获取了次序，之后又用它减去了胜场的次序，各位想想：如果处于同一连胜区间内，那么这些记录对应的差是不是都该相同呢？
- 所以我们只需要获取其中记录最多的区间对应的场次，不就是运动员的最长连胜次数了吗？语言表达有限，建议各位自己试一试，此时SQL如下

SQL4:

```mysql
WITH rank_diff AS (
		SQL2
)

SELECT
    player_id,
    MAX(num) AS 'longest_streak'
FROM (
		SQL3
) AS diff_num
GROUP BY player_id
```





- 到了这一步，我们是否成功了呢？其实还没有，在计算diff差的时候，如果某个倒霉蛋一次也没赢过，那么他在CTE临时表中是没有记录的
- 所以为了查询出所有的运动员记录，我们还必须根据原表中的运动员id来查询，这里使用外连接即可，最终SQL如下

```mysql
WITH rank_diff AS (
		SQL2
)

SELECT
    t1.player_id,
    IFNULL(t2.longest_streak, 0) AS 'longest_streak'
FROM (
    SELECT
        DISTINCT player_id
    FROM
        Matches
) AS t1
LEFT JOIN (
		SQL4
) AS t2 ON t1.player_id = t2.player_id
```

<hr>









# Day234

## Tag: GROUP_CONCAT, CONCAT

![Xnip2022-03-15_07-22-23](MySQL Note.assets/Xnip2022-03-15_07-22-23.jpg)



![Xnip2022-03-15_07-37-24](MySQL Note.assets/Xnip2022-03-15_07-37-24.jpg)



![Xnip2022-03-15_07-22-43](MySQL Note.assets/Xnip2022-03-15_07-22-43.jpg)

题意:

给你一张Terms表，其中一列数据为次数，另一列为系数，请你根据次数的升序构建一个一元多次等式，左边是表中的所有项，右边为0，正负号在等式中要显式表示，次数大于等于1的，则要用<符号><系数>X^<次数>的方式表示出来，如果次数等于0，则只表示系数即可





思路:

- 看结果有些复杂，但我们可以将等式拆成两边，左边就是我们要构建的
- 其中对应表中的每行数据来说，数据无非分为这几个部分: <符号>，<系数>，X，<次数>
- 因此，我们可以先构造出每行对应的部分，首先符号很简单，因为题目指明了，系数列不为0，所以我们只需要根据系数即可获取符号
- 后面三个部分就有些特殊了，因为需要分三种情况：如果次数为0，则只保留系数本身，如果次数为1，则构建为系数 + X；其余情况下才构建<系数>X<次数>
- 最后将这三个部分拼接起来即可，这里使用CONCAT即可，需要注意的是，为了之后构建出来的等式是按照次数降序的，这里我们还需要将次数一并查询出来，SQL如下

SQL1

```mysql
SELECT
    power,
    CONCAT(
        CASE WHEN factor < 0 
        THEN '-' ELSE '+' END,
        CASE WHEN power = 0 
        THEN ABS(factor)
        WHEN power = 1
        THEN CONCAT(ABS(factor), 'X')
        ELSE CONCAT(ABS(factor), 'X', '^', power) END
    ) AS 'LHS'
FROM
    Terms
```



- 之后我们只需要将这些行连在一起即可，这里需要使用GROUP_CONCAT
- 从官方文档可见，其可以在拼接时排序，且因为其默认的分隔符为","，所以我们还需要将默认分隔符改为空字符，最后只需要再右边拼接上"=0"即可，最终SQL如下

```mysql
SELECT
    CONCAT(GROUP_CONCAT(LHS ORDER BY power DESC SEPARATOR ''), '=0') AS 'equation'
FROM (
		SQL1
FROM
    Terms
) AS `left`
```

<hr>











# Day235

## Tag: DATE_FORMAT, LEFT JOIN, TIMESTAMPDIFF

![Xnip2022-03-16_07-38-21](MySQL Note.assets/Xnip2022-03-16_07-38-21.jpg)



![Xnip2022-03-16_07-39-35](MySQL Note.assets/Xnip2022-03-16_07-39-35.jpg)

题意:

给你一张用户日志表，请你查询出其中每天新用户的次日留存率



思路:

- 次日留存的计算无非就是次日再次登陆的用户人数 / 当天的新用户人数
- 我们将分子和分母拆开来看，因为要求每天的新用户，所以我们需要根据用户分组并获取每个用户的注册日期，SQL如下

SQL1

```mysql
SELECT
		uid,
		MIN(DATE(in_time)) AS 'first_time'
FROM
		tb_user_log
GROUP BY uid
```



- 接下来，我们只需要按照该临时表的数据，连接上原表后获取每个用户的次日记录即可，这里我们需要使用外连接
- 等等，这样真的可以吗？题目中有一点需要注意: 如果in_time和out_time跨天了，则也算做两天活跃，所以一张表中的in_time和out_time都需要与注册日期进行比较
- 这里我们需要将这两个同行的数据转换为同列才行，所以这里我们还需要对原表进行一次UNION查询，SQL如下

SQL2:

```mysql
SELECT
		uid,
		DATE(in_time) AS 'dt'
FROM
		tb_user_log
UNION
SELECT
		uid,
		DATE(out_time) AS 'dt'
FROM
		tb_user_log
```



- 一切就绪，接下来就很常规了，我们只需要将SQL1作为驱动表，对两表使用外连接，并限制两表的日期关系(注意这里要用AND，外连接的AND和WHERE分别指连接时判断和连接前判断)
- 最后再限制一下日期为2021-10，对日期分组排序即可，最终SQL如下

```mysql
SELECT
	t1.first_time AS 'dt',
	ROUND(COUNT(t2.uid) / COUNT(t1.uid), 2) AS 'uv_left_rate'
FROM (
	SQL1
LEFT JOIN (
  SQL2
) AS t2 ON t1.uid = t2.uid
AND TIMESTAMPDIFF(DAY, t1.first_time, t2.dt) = 1
WHERE DATE_FORMAT(t1.first_time, '%Y%m') = '202111'
GROUP BY t1.first_time
```

<hr>













# Day236

## Tag: GROUP BY, 日活, 新用户占比

![Xnip2022-03-17_07-35-21](MySQL Note.assets/Xnip2022-03-17_07-35-21.jpg)



![Xnip2022-03-17_07-36-47](MySQL Note.assets/Xnip2022-03-17_07-36-47.jpg)

题意:

给你一张用户活动日志表，请你查询出每天的日活用户数和新用户的占比



思路:

- 首先自然需要查询出所有新用户对应的首次使用日期，之后才好进行对比，SQL如下

SQL1:

```mysql
SELECT
		uid,
		MIN(DATE(in_time)) AS 'first_time'
FROM
		tb_user_log
GROUP BY uid
```



- 有了该表后，按理说我们只需要再连接上原表后，统计每天的总用户数和新用户数即可
- 但题目中又提到了：如果in_time和out_time跨天了，则两天都算做活跃
- 是不是有点眼熟，对，昨天有这个要求，所以我们参照昨天的做法，将行转换为列即可，SQL如下

SQL2

```mysql
SELECT
		uid,
		DATE(in_time) AS 'dt'
FROM
		tb_user_log
UNION
SELECT
		uid,
		DATE(out_time) AS 'dt'
FROM
		tb_user_log
```



- 最后，我们才应该连接两表，注意应该以原表数据为主，因为有些日期中可能没有新用户，所以要用到外连接，最终SQL如下

```mysql
SELECT
    DATE(t2.dt) AS 'dt',
    COUNT(t2.uid) AS 'dau',
    ROUND(IFNULL(COUNT(t1.uid), 0) / COUNT(t2.uid), 2) AS 'uv_new_ratio'
FROM (
    SQL1
    ) AS t1
RIGHT JOIN (
    SQL2
) AS t2 ON t1.uid = t2.uid AND t1.first_time = t2.dt
GROUP BY dt
ORDER BY dt
```

<hr>











# Day237

## Tag: DENSE_RANK

![Xnip2022-03-18_07-50-34](MySQL Note.assets/Xnip2022-03-18_07-50-34.jpg)



![Xnip2022-03-18_07-53-23](MySQL Note.assets/Xnip2022-03-18_07-53-23.jpg)





题意:

给你一张店铺销售记录表，请查询出11月中连续2天以上购物的用户对应的购物天数





思路:

- 对于连续的购物天数来说，如果对其进行排序的话，其实对于的rank应该是连续的
- 所以我们只需要获取每个用户按照日期排序的记录，最后留下≥2的即可，首先获取每个日期对应的次序，SQL如下

SQL1:

```mysql
SELECT
		user_id,
		sales_date,
		DENSE_RANK() OVER(
			PARTITION BY user_id
			ORDER BY sales_date
		) AS 'rn'
FROM sales_tb
```



- 之后只需要限制月份和对应的次序大小即可，最终SQL如下

```mysql
SELECT
    user_id,
    MAX(rn) AS 'days_count'
FROM (
    SQL1
    ) AS temp
WHERE rn >= 2
AND MONTH(sales_date) = 11
GROUP BY user_id
ORDER BY user_id
```



拓展:

- 是不是感觉哪里不对？
- 要是一个用户间隔1天及以上后又有一次购买记录呢？这种算法不还是把他算进去了吗？
- 没错，一旦表中有间隔1天以上的用户记录的话，这种算法就是错的了
- 这其实是牛客网的数据不严谨造成的，各位可以去试试，在有间隔一天及以上的用户记录存在的情况下
- 随便拿一个题解的答案去运行，会发现这条本不连续的记录还是被查询出来了

<hr>













# Day238

## Tag: CURRENT_TIME

![Xnip2022-03-19_09-31-46](MySQL Note.assets/Xnip2022-03-19_09-31-46.jpg)



![Xnip2022-03-19_09-31-54](MySQL Note.assets/Xnip2022-03-19_09-31-54.jpg)

题意:

给你一张提交记录表，一张题目信息表，请你查询出今天每个题目对应的提交数







思路:

- 这道题目可能说得不是很清楚，所以这位按照我们给的题意来理解就是了
- 首先，该题目要求按照不同的题目统计，所以需要分组，并且需要我们限制日期为当前日期
- 获取当前日期的函数可以是CURRENT_DATE()/CURRENT_DATE/CURDATE()
- 所以题目就很明显了，分组再统计就是了
- 但需要注意的是，我们在排序的时候用到了subject_id字段，所以在分组的时候需要加上该字段才行
- 最后在牛客网上做这道题目的时候不要按照我的格式写，否则会报错的，建议先把SELECT列表放在一行中运行通过了再改回来(已经给维护者反馈了)

<hr>












# Day239

## Tag: GMV, 动销率

![Xnip2022-03-20_07-50-28](MySQL Note.assets/Xnip2022-03-20_07-50-28.jpg)



![Xnip2022-03-20_07-58-38](MySQL Note.assets/Xnip2022-03-20_07-58-38.jpg)

题意:

给你一张产品信息表，一张销售记录表，请你计算每款产品的动销率和售罄率





思路:

- 该题目不是难在SQL的编写上，而是理解题目中动销率和售罄率这两个概念上
- 动销率其实可以理解为: 售出的数量 / (总数 - 售出的数量)
- 而售罄率 = GMV / 备货值，其中GMV = 销售总额，备货值 = 吊牌价 * 备货数
- 这么一看好想不难对吧？其实这里有一个坑
- 按照常规的思路，直接根据style_id分组，连接另外一个表后直接就能统计各个数据，这样子写出来的SQL如下

```mysql
SELECT
    t1.style_id,
    ROUND(100 * SUM(t2.sales_num) / (SUM(t1.inventory) - SUM(t2.sales_num)), 2) AS 'pin_rate',
    ROUND(100 * SUM(t2.sales_price) / SUM(t1.tag_price * t1.inventory), 2) AS 'sell-through_rate'
FROM
    product_tb AS t1
INNER JOIN sales_tb AS t2 ON t1.item_id = t2.item_id
GROUP BY t1.style_id
ORDER BY t1.style_id
```



- 但提交时就会发现是错误的，为啥呀？明明是按照定义来的呀？
- 其实在连接的时候，如果同一个item_id的商品在销售表中出现多次的话，连接时就会将其对应的数据查询多次，这样一来对应的数值都会减少
- 部分题解可能直接就在对应的字段或者乘积上去重就过了，但其实是不严谨的:
- 如果两个item_id不同的商品，其对应的tag_price * inventory恰巧相同呢？
- 所以这种方式不可行





- 为了解决重复计算的问题，这里我们可以先按照item_id分组查询出对应的sku和gmv，SQL如下

SQL1

```mysql
SELECT
		item_id,
		SUM(sales_num) AS 'sku',
		SUM(sales_price) AS 'gmv'
FROM
		sales_tb
WHERE MONTH(sales_date) = 11 AND YEAR(sales_date) = 2021
GROUP BY item_id
```



- 最后我们再连接计算记录，最终SQL如下

```mysql
SELECT
    t1.style_id,
    ROUND(100 * SUM(t2.sku) / SUM(t1.inventory - t2.sku), 2) AS 'pin_rate',
    ROUND(100 * SUM(t2.gmv) / SUM(t1.tag_price * t1.inventory), 2) AS 'sell-through_rate'
FROM
    product_tb AS t1
INNER JOIN (
    SQL1
    ) AS t2 ON t1.item_id = t2.item_id
GROUP BY t1.style_id
ORDER BY t1.style_id
```

<hr>













# Day240

## Tag: ROW_NUMBER

![Xnip2022-03-21_08-28-30](MySQL Note.assets/Xnip2022-03-21_08-28-30.jpg)



![Xnip2022-03-21_08-28-03](MySQL Note.assets/Xnip2022-03-21_08-28-03.jpg)

题意:

给你一张体育馆的人流量表，请你查询出其中人数大于等于100且id连续的三行或以上的记录



思路:

- 该思路借鉴自: [row_number方式解决连续性问题 - 体育馆的人流量 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/problems/human-traffic-of-stadium/solution/row_numberfang-shi-jie-jue-lian-xu-xing-42uhh/)
- 按照题意，id连续其实就是日期连续，因此该题目的最简单的解法就是两个自连接，限制彼此的日期关系即可

- 官方题解就是这个思路，但说实话，这种做法其实开销巨大
- 这里我们可以将问题转换一下: 在筛选去所有人数大于100的记录后，如果记录对应的行号减去id对应的差值相同的话，就说明这几个日期是连续的
- 因此我们可以先查询出每条记录对应的id与行号的差值，SQL如下

SQL1:

```mysql
SELECT
		t2.id - ROW_NUMBER() OVER( 
				ORDER BY t2.id
		) AS 'step',
		t2.id,
		t2.visit_date,
		t2.people 
FROM
		Stadium AS t2
WHERE t2.people >= 100 
```



- 又因为题目要求连续3天的，所以我们只需要根据差值分组(因此差值相同的才是连续的)，统计记录数即可获取每个连续时段内对应的日期数，SQL如下

SQL2:

```mysql
SELECT
		COUNT(*) OVER(
            PARTITION BY t1.step
        ) AS 'count',
		t1.id,
		t1.visit_date,
		t1.people 
FROM (
		SQL1
		) AS t1 
) AS t2
```



- 最后只需要限制count字段，进行排序即可，最终SQL如下

```mysql
SELECT
	t2.id,
	t2.visit_date,
	t2.people
FROM (
	SQL2
	) AS t2
WHERE t2.count >= 3
ORDER BY t2.id
```

<hr>









# Day241

## Tag: DENSE_RANK, LEFT JOIN

![Xnip2022-03-22_08-44-05](MySQL Note.assets/Xnip2022-03-22_08-44-05.jpg)



![Xnip2022-03-22_08-47-18](MySQL Note.assets/Xnip2022-03-22_08-47-18.jpg)

题意:

给你一张用户信息表，一张订单表，一张物品信息表，请你查询出每个用户按日期顺序卖出的第二件商品的品牌是否为其喜爱的品牌，如果用户没有卖出记录或者没有卖出两件则默认为no





思路:

- 因为涉及到按照日期排序，所以我们应该先获取每个用户按照日期排序后卖出的第二件商品，SQL如下

SQL1:

```mysql
SELECT
    t1.seller_id,
    t2.item_brand,
    DENSE_RANK() OVER(
        PARTITION BY seller_id
        ORDER BY order_date
    ) AS 'date_rank'
FROM
    Orders AS t1
INNER JOIN Items AS t2 ON t1.item_id = t2.item_id
```



- 之后再限定date_rank为2来获取用户卖出的第二件商品，SQL如下

SQL2:

```mysql
SELECT
		seller_id,
		item_brand
	FROM (
		SQL1
		) AS temp
	WHERE date_rank = 2
```



- 最后只需要再连接Users表，比较商品品牌即可，最终SQL如下

```mysql
SELECT
	t1.user_id AS 'seller_id',
	CASE WHEN t1.favorite_brand = t2.item_brand THEN 'yes'
	ELSE 'no' END AS '2nd_item_fav_brand'
FROM
	Users AS t1
LEFT JOIN (
	SQL2
	) AS t2 ON t1.user_id = t2.seller_id
```





提醒:

- 注意：部分老铁可能会这样想：SQL2不是脱裤子放屁吗？我直接以Users表为驱动表，将三张表进行外连接不就行了吗?
- 想法不错，但这里有个问题:
- 刚开始使用Users表和获取rank的Orders表进行外连接后，确实查询出了每个用户对于的交易记录，且没有交易记录的用户也为NULL
- 但之后与Items表进行外连接的时候会出现问题:
- 如果用户没有任何交易记录，则其对应的item_id字段为NULL，这样在与Items表进行连接的时候就是Items.item_id = NULL，这个表达式的结果一定不为真
- 对应的没有交易记录的用户则不会出现在结果集中
- 为了避免这样的结果，我不得已才先将后面两张表进行了连接
- 类似的问题各位可以参考《SQL进阶教程》中第一章第五节中外连接的内容

<hr>















# Day242

## Tag: RANGE, SUM OVER

![Xnip2022-03-23_08-06-56](MySQL Note.assets/Xnip2022-03-23_08-06-56.jpg)



![Xnip2022-03-23_08-06-34](MySQL Note.assets/Xnip2022-03-23_08-06-34.jpg)



![Xnip2022-03-23_08-21-38](MySQL Note.assets/Xnip2022-03-23_08-21-38.jpg)

题意:

给你一张员工薪资表，请你查询出其中每个员工除了最近一个月外，每个月近三个月的累积薪资



思路:

- 首先因为不能统计最近一个月的数据，所以我们需要先获取每个员工对应的最近一个月用于之后的排除，SQL如下

SQL1

```mysql
SELECT
		Id,
		MAX(Month) AS 'max_month'
FROM
		Employee
GROUP BY Id
```



- 对于累加，我说过这是SUM OVER擅长的事，但这里不是简单的累加这么简单，而是需要累加近两个月的值
- 这就涉及到窗口函数的frame参数了，窗口函数在OVER中一般只用到了两个参数: PARTITION分组和ORDER BY排序，但其实第三个参数frame也非常有用
- frame可以用来限制窗口的范围，在这道题目中，我们可以使用“RANGE 2 PRECODING”这样的表达式，其代表每个值前面的两个值
- 和它类似的有ROW，但其统计的是前面的两行，所以这里并不适用，最终将SQL1通过EXISTS改写如下

```mysql
SELECT
    Id,
    Month,
    SUM(Salary) OVER(
        PARTITION BY Id
        ORDER BY Month RANGE 2 PRECEDING
    ) AS 'Salary'
FROM 
    Employee AS t1
WHERE NOT EXISTS (
    SELECT
        Id,
        MAX(Month) AS 'max_month'
    FROM
        Employee AS t2
    GROUP BY Id
    HAVING t2.Id = t1.Id AND max_month = t1.Month
)
ORDER BY Id, Month DESC
```

<hr>













# Day243

## Tag: LEFT JOIN

![Xnip2022-03-24_08-48-50](MySQL Note.assets/Xnip2022-03-24_08-48-50.jpg)



![Xnip2022-03-24_08-49-55](MySQL Note.assets/Xnip2022-03-24_08-49-55.jpg)

题意:

给你一张消费记录表，请你查询出每天只使用手机、桌面端和两者都使用的用户人数和消费金额





思路:

- 从结果示例可知，不管有没有我们需要查询出每一天对应三种情况的数据，所以这里就需要外连接制作嵌套式表侧栏了
- 从结果可知：表侧栏就是日期和平台情况的笛卡尔积，SQL如下

SQL:

```mysql
SELECT
		DISTINCT spend_date,
		t2.platform
FROM
		Spending,
		(
		SELECT
			DISTINCT platform
		FROM
			Spending
		UNION ALL
		SELECT
			'both'
		) AS t2
```



- 之后我们需要根据原表查询出每天每个人对应的平台和累加的金额，SQL如下

SQL2:

```mysql
SELECT 
		user_id,
		spend_date,
		CASE WHEN COUNT(platform)=2 THEN 'both' ELSE platform END AS 'platform',
		SUM(amount) AS 'amount'
FROM 
		Spending 
GROUP BY user_id, spend_date
```



- 最后以表侧栏为驱动表，进行外连接即可，最终SQL如下

```mysql
SELECT
	t1.spend_date,
	t1.platform,
	IFNULL(SUM(t2.amount), 0) AS 'total_amount',
	COUNT(t2.user_id) AS 'total_users'
FROM (
	SQL1
	) AS t1
LEFT JOIN (
	SQL2
	) AS t2 ON t1.spend_date = t2.spend_date AND t1.platform = t2.platform
GROUP BY t1.spend_date, t1.platform
```

<hr>











# Day244

## Tag: DISTINCT, TIME

![Xnip2022-03-25_07-32-31](MySQL Note.assets/Xnip2022-03-25_07-32-31.jpg)



![Xnip2022-03-25_07-32-42](MySQL Note.assets/Xnip2022-03-25_07-32-42.jpg)

题意:

给你一张购买记录表，请你在自定义函数getUserIDs中编写一个SQL以返回规定日期内，消费金额大于等于最小金额的用户数量





思路:

- 看起来自定义函数很麻烦，其实只需要我们用上函数提供的三个参数就行了
- 需要注意的是，统计的字段应该为去重后的user_id，这里不需要判断为NULL的情况，因为COUNT(字段)是不会将NULL计算在内的，最终内部SQL如下

```mysql
SELECT
		COUNT(DISTINCT user_id) AS 'user_cnt'
FROM
		Purchases
WHERE time_stamp BETWEEN startDate AND endDate
AND amount >= minAmount
```

<hr>









# Day245

## Tag: HAVING

![Xnip2022-03-26_11-25-35](MySQL Note.assets/Xnip2022-03-26_11-25-35.jpg)



![Xnip2022-03-26_11-24-57](MySQL Note.assets/Xnip2022-03-26_11-24-57.jpg)

题意:

给你一张学生信息表，一张考试成绩表，请你查询出其中所有至少参加了一次测试，且成绩处于中游的学生信息



思路:

- 最直接的方式就是查询出每次考试中的最高分和最低分最远的学生id，之后用NOT IN排除即可
- 这里有一个技巧: 对成绩表进行自连接，通过SUM(t1.score < t2.score)和SUM(t1.score > t2.score)即可获取最高分和最低分，所以SQL如下

SQL1:

```mysql
SELECT
	t1.student_id 
FROM
	exam AS t1
INNER JOIN exam AS t2 ON t1.exam_id = t2.exam_id 
GROUP BY t1.exam_id, t1.student_id
HAVING SUM(t1.score < t2.score) = 0 OR SUM(t1.score > t2.score) = 0
```



- 有了需要排除的学生id后就很简单了，使用NOT IN排除即可，这里能不能改为NOT EXISTS呢？当然可以，在HAVING后面接一个就行，就是开销有点大，最终EXISTS的版本如下

```mysql
SELECT
    DISTINCT t1.student_id,
    t2.student_name
FROM
    Exam AS t1    
INNER JOIN Student AS t2 ON t1.student_id = t2.student_id
WHERE NOT EXISTS (
	SELECT
		e1.student_id 
	FROM
		exam AS e1
	INNER JOIN exam AS t2 ON e1.exam_id = t2.exam_id 
	GROUP BY e1.exam_id, e1.student_id 
	HAVING (SUM(e1.score < t2.score) = 0 OR SUM(e1.score > t2.score) = 0)
    AND t1.student_id = e1.student_id
)
ORDER BY t1.student_id
```

<hr>











# Day246

## Tag: AVG() OVER, CASE

![Xnip2022-03-27_10-52-18](MySQL Note.assets/Xnip2022-03-27_10-52-18.jpg)



![Xnip2022-03-27_10-52-01](MySQL Note.assets/Xnip2022-03-27_10-52-01.jpg)

题意:

给你一张员工工资表，一张员工信息表，请你查询出每个月每个部门的平均工资和公司平均工资之间的关系





思路:

- 

- 该问题其实可以分解为求出每个月公司的平均工资和每个月每个部门的平均工资
- 能不能一起查询出来呢？这里用传统的GROUP BY肯定不行，所以需要借助窗口函数，我们只需要修改一下分组条件就能获取这两个数据了，SQL如下

SQL1:

```mysql
SELECT
		DISTINCT LEFT(t1.pay_date, 7) AS 'pay_month',
		t2.department_id,
		AVG(amount) OVER(
				PARTITION BY MONTH(t1.pay_date), t2.department_id
		) AS 'depart_avg',
		AVG(amount) OVER(
				PARTITION BY MONTH(t1.pay_date)
		) AS 'company_avg'
FROM
		Salary AS t1
INNER JOIN employee AS t2 ON t1.employee_id = t2.employee_id
```



- 有了该数据后，我们只需要进行分支判断即可，最终SQL如下

```mysql
SELECT
    pay_month,
    department_id,
    CASE WHEN depart_avg > company_avg THEN 'higher'
    WHEN depart_avg < company_avg THEN 'lower'
    WHEN depart_avg = company_avg THEN 'same'
    ELSE NULL END AS 'comparison'
FROM (
    SQL1
) AS temp
```

<hr>











# Day247

## Tag: SUM() OVER

![Xnip2022-03-28_08-08-34](MySQL Note.assets/Xnip2022-03-28_08-08-34.jpg)



![Xnip2022-03-28_08-08-50](MySQL Note.assets/Xnip2022-03-28_08-08-50.jpg)

题意:

给你一张数字频率表，请你查询出其中的中位数





思路:

- 求中位数我们早在SQL Day217就做过了，当时我们的做法是：使用HAVING分别计算上下部分员工的工资，使得上下部分有一个交集: >= COUNT(*)
- 那么今天这里还能不能使用这种方法呢？显然不能，因为数据被压缩了，我们无法通过COUNT(*)来计算数据总数，但思想其实是一样的
- 我们同样只需要找出上下两个部分的交集即可，所以我们需要分别按照正序和倒序来累加frequency字段，并获取frequency字段的和，SQL如下

SQL1

```mysql
SELECT
		num,
		SUM(frequency) OVER(
				ORDER BY num
		) AS 'asc_sum',
		SUM(frequency) OVER(
				ORDER BY num DESC
		) AS 'desc_sum',
   	SUM(frequency) OVER() AS 'total_sum'
FROM
		Numbers
```



- 最后我们只需要参照之前的做法，获取其中的交集数据即可，最终SQL如下

```mysql
SELECT
    ROUND(AVG(num), 1) AS 'median'
FROM (
    SQL1
    ) AS temp
WHERE asc_sum >= total_sum / 2 AND desc_sum >= total_sum / 2 
```

<hr>













# Day248

## Tag: ROW_NUMBER() OVER, DATE_SUB

![Xnip2022-03-29_07-23-40](MySQL Note.assets/Xnip2022-03-29_07-23-40.jpg)



![Xnip2022-03-29_07-23-54](MySQL Note.assets/Xnip2022-03-29_07-23-54.jpg)

题意:

给你一张任务失败记录表，一张任务成功记录表，请你查询出其中2019年内任务连续同状态的起止日期





思路:

- 题目有些费解，可以简单理解为：每天都会运行一个任务，其可能成功，可能失败
- 你需要做的就是查询出连续成功或者失败区间的起止日期
- 就这种求连续区间的题目而言，难点在于如何判断日期是在同一个连续的区间内的
- 其实这种题目在SQL Day240中出现过一次，当时我们的做法是id - 日期的次序，差值相同的说明在同一个日期区间内
- 相应的，在这道题目里，我们可以通过日期 - 日期次序生成的日期值作为判断是否同组的依据，所以这里我们一并查询出成功和失败日期对应的差值，SQL如下

```mysql
SELECT
		state,
		cur_date,
		DATE_SUB(cur_date, INTERVAL ROW_NUMBER() OVER(PARTITION BY state ORDER BY cur_date) DAY) AS 'diff'
FROM (
		SELECT
				'failed' AS 'state',
				fail_date AS 'cur_date'
		FROM
				Failed
		WHERE YEAR(fail_date) = 2019
		UNION ALL
		SELECT
				'succeeded' AS 'state',
				success_date
		FROM
				Succeeded
		WHERE YEAR(success_date) = 2019
) AS temp
```





- 有了该表后，我们只需要根据状态和差值分组，求出每组的最值日期值即可，最终SQL如下

```mysql
SELECT
    state AS 'period_state',
    MIN(cur_date) AS 'start_date',
    MAX(cur_date) AS 'end_date'
FROM (
    SQL1
) AS t1
GROUP BY state, diff
ORDER BY start_date
```



- 注意: 该条语句中，SELECT列表里的字段和GROUP BY并不对应，在MySQL里想要成功运行的话需要修改sql_mode

<hr>















# Day249

## Tag: DENSK_RANK, GROUP BY

![Xnip2022-03-30_08-45-29](MySQL Note.assets/Xnip2022-03-30_08-45-29.jpg)



![Xnip2022-03-30_08-45-52](MySQL Note.assets/Xnip2022-03-30_08-45-52.jpg)

题意:

给你一张选手信息表，一张比赛记录表，请你查询出每组中分数最高的选手，如果分数相同则返回id较小的那个人





思路:

- 首先自然是计算每个选手的总分了，因为这里first_player和second_player在同一行，所以我们需要联合查询进行转换，SQL如下

SQL1:

```mysql
SELECT
		first_player AS 'player_id',
		first_score AS 'score'
FROM
		Matches
UNION ALL
SELECT
		second_player AS 'player_id',
		second_score AS 'score'
FROM
		Matches
```



- 之后直接进行分组求和即可，SQL如下

SQL2:

```mysql
SELECT
		player_id,
		SUM(score) AS 'score_sum'
FROM (
		SQL1
		) AS union_tb
GROUP BY player_id
```



- 有了分数后我们就能根据分数和id进行排序了，这里我选择窗口函数，SQL如下

SQL3:

```mysql
SELECT
		t1.group_id,
		t2.player_id,
		DENSE_RANK() OVER(
				PARTITION BY t1.group_id
				ORDER BY t2.score_sum DESC, t2.player_id
		) AS 'player_rank'
FROM
		Players AS t1
INNER JOIN (
		SQL2
) AS t2 ON t1.player_id = t2.player_id
```



- 有了排名后我们只需要筛选出其中排名为1的即可，最终SQL如下

```mysql
SELECT
    group_id,
    player_id
FROM (
    SQL2
WHERE player_rank = 1
```

<hr>















# Day250

## Tag: LEFT JOIN ... ON 1

![Xnip2022-03-31_08-53-48](MySQL Note.assets/Xnip2022-03-31_08-53-48.jpg)



![Xnip2022-03-31_08-53-06](MySQL Note.assets/Xnip2022-03-31_08-53-06.jpg)

题意:

给你一张用户行为日志表，请你按照活跃间隔查询出不同等级的用户





思路:

- 这里参照大佬的思路：https://blog.nowcoder.net/n/10050f751c994c358957f6744b44d01d
- 首先，我们可以查询出每一个用户对应最值日期，SQL如下

SQL1:

```mysql
SELECT 
	uid, 
	MIN(DATE(in_time)) AS first_dt,
	MAX(DATE(out_time)) AS last_dt
FROM 
	tb_user_log
GROUP BY uid
```



- 之后我们还需要最近一天的日期和用户数以备之后使用，SQL如下

SQL2:

```mysql
SELECT 
	MAX(DATE(out_time)) AS cur_dt,
	COUNT(DISTINCT uid) AS user_cnt
FROM 
	tb_user_log
```





- 此时我们只需要连接两表，计算出每个用户的活跃记录与当前日期的差值即可，SQL如下

SQL3:

```mysql
SELECT 
	uid,
	user_cnt,
	TIMESTAMPDIFF(DAY, first_dt, cur_dt) AS first_dt_diff,
	TIMESTAMPDIFF(DAY, last_dt, cur_dt) AS last_dt_diff
FROM (
	SQL1
) AS user_boundary_date
LEFT JOIN (
	SQL1
) AS t_overall_info ON 1
```



- 有了差值，剩下的就很简单了：
- 只需要根据差值做判断，对每个用户id进行分类即可，SQL如下

SQL4:

```mysql
SELECT 
	uid,
	user_cnt,
	CASE WHEN last_dt_diff >= 30 THEN "流失用户"
	WHEN last_dt_diff >= 7 THEN "沉睡用户"
	WHEN first_dt_diff < 7 THEN "新晋用户"
	ELSE "忠实用户" END AS 'user_grade'
FROM (
	SQL3
) AS tb_user_info
```



- 最后只需要按照用户分组，统计用户id和用户数量并相除即可，最终SQL如下

```mysql
SELECT 
	user_grade, 
	ROUND(COUNT(uid) / MAX(user_cnt), 2) AS 'ratio'
FROM (
	SQL4
) AS tb_user_grade
GROUP BY user_grade
ORDER BY ratio DESC;
```

<hr>











# Day251

## Tag: CASE WHEN, JOIN DELETE

![Xnip2022-04-01_08-01-06](MySQL Note.assets/Xnip2022-04-01_08-01-06.jpg)



![Xnip2022-04-01_08-01-18](MySQL Note.assets/Xnip2022-04-01_08-01-18.jpg)

题目1:

给你一张员工薪资表，请你查询出每个人的奖金，如果该员工的名字不是以M开头且id为奇数，则奖金为其薪资，否则为0



思路:

- 这里其实需要一个分支判断，自然想到使用IF或者CASE WHEN，为了可移植性，这里选择用CASE WHEN较好
- 当然，用两条SELECT再用UNION ALL联合也是可以的，但没有CASE WHEN优雅

<hr>



![Xnip2022-04-01_08-07-00](MySQL Note.assets/Xnip2022-04-01_08-07-00.jpg)



![Xnip2022-04-01_08-08-11](MySQL Note.assets/Xnip2022-04-01_08-08-11.jpg)

题目2:

给你一张薪资细节表，请你将其中所有员工的性别进行替换



思路:

- 很多题解都用到了ASCII这个函数，这种做法当然很妙，但需要依赖具体的函数
- 其实这里也可以使用CASE WHEN，该用法在《SQL进阶教程》第一章第一节就提到过

<hr>





![Xnip2022-04-01_08-13-22](MySQL Note.assets/Xnip2022-04-01_08-13-22.jpg)



![Xnip2022-04-01_08-13-31](MySQL Note.assets/Xnip2022-04-01_08-13-31.jpg)

题意3:

请你删除个人信息表中邮箱重复的信息，且保留id小的那个



思路:

- 我们先不考虑题意，先尝试查询出需要删除的数据:

```mysql
SELECT
	t1.email
FROM
	Person AS t1
INNER JOIN Person AS t2 ON t1.email = t2.email
WHERE t1.id > t2.id
```



- 写出这个SQL之后其实就得到答案了，我们只需要替换一下:

```mysql
DELETE
	t1
FROM
	Person AS t1
INNER JOIN Person AS t2 ON t1.email = t2.email
WHERE t1.id > t2.id
```

<hr>









# Day252

## Tag: SUBSTRING, LOWER, UPPER, REGEXP, GROUP_CONCAT

![Xnip2022-04-02_07-18-59](MySQL Note.assets/Xnip2022-04-02_07-18-59.jpg)



![Xnip2022-04-02_07-20-12](MySQL Note.assets/Xnip2022-04-02_07-20-12.jpg)

 题意:

给你一张用户信息表，请你将其中的姓名按照规定的格式查询出来





思路:

- 题目需要我们将name字段分两部分处理：取开头题目很简单，使用LEFT即可，再用UPPER包裹即可变为大写
- 之后的部分只需要使用SUBSTRINg即可，再用LOWER包裹，最后使用CONCAT将两部分组合在一起

<hr>









![Xnip2022-04-02_07-31-12](MySQL Note.assets/Xnip2022-04-02_07-31-12.jpg)



![Xnip2022-04-02_07-31-26](MySQL Note.assets/Xnip2022-04-02_07-31-26.jpg)

题意:

给你一张销售记录表，请你查询出其中每天的销售的商品数量和对应的商品名称





思路:

- 因为是按照日期来，所以需要根据日期分组，至于商品数量则需要使用COUNT计算字段数，注意这里要去重
- 第三个字段，如果没学过GROUP_CONCAT的话可能就会很懵，其实这是一个聚合分组的函数，其中也需要去重和排序

<hr>



![Xnip2022-04-02_07-43-10](MySQL Note.assets/Xnip2022-04-02_07-43-10.jpg)



![Xnip2022-04-02_07-43-27](MySQL Note.assets/Xnip2022-04-02_07-43-27.jpg)

题意:

给你一张病人的信息表，请你查询出其中患有1类糖尿病的患者





思路:

- 该题目需要我们匹配conditions字段中出现DIAB1的记录
- 但conditions中有两种出现正确的DIAB1字符的情况：要么在开头，要么作为第2个或之后的词组出现
- 所以我们需要同时考虑两种情况中的一种，在开头很简单，用正则^DIAB1即可，但第二种情况呢？
- 其实只需要匹配' DIAB1'即可，也就是在前面加一个空格，在正则中表示或需要使用｜，所以最终正则为(^DIAB1| DIAB1)

<hr>













# Day253

## Tag: UNION ALL

![Xnip2022-04-03_07-36-59](MySQL Note.assets/Xnip2022-04-03_07-36-59.jpg)



![Xnip2022-04-03_07-36-30](MySQL Note.assets/Xnip2022-04-03_07-36-30.jpg)

题意:

给你一张雇员信息表，一张薪资表，请你查询出其中所有信息缺失的员工(缺失姓名或者薪资)





思路:

- 因为在MySQL中不支持全外连接，所以这里我们需要使用两次外连接，这两次连接中name或salary为NULL的其实就是我们需要的结果，之后用UNION ALL获取并集即可
- 如果使用的是支持全外连接的DBMS的话(比如PostgreSQL)，则可以这样写:

```postgresql
WITH tb_temp AS (
	SELECT
		t1.employee_id AS "first_id",
		t2.employee_id AS "second_id"
	FROM
		"Employees" AS t1
	FULL JOIN "Salaries" AS t2 ON t1.employee_id = t2.employee_id
	WHERE t1.name IS NULL
	OR t2.salary IS NULL
)

SELECT
	first_id AS "employee_id"
FROM
	"tb_temp"
WHERE first_id IS NOT NULL
UNION ALL
SELECT
	second_id
FROM
	"tb_temp"
WHERE second_id IS NOT NULL
ORDER BY employee_id
	
```

- 这样一来就只需要一次连接操作了

<hr>



![Xnip2022-04-03_08-05-12](MySQL Note.assets/Xnip2022-04-03_08-05-12.jpg)



![Xnip2022-04-03_08-05-41](MySQL Note.assets/Xnip2022-04-03_08-05-41.jpg)

<hr>







![Xnip2022-04-03_08-04-00](MySQL Note.assets/Xnip2022-04-03_08-04-00.jpg)



![Xnip2022-04-03_08-06-10](MySQL Note.assets/Xnip2022-04-03_08-06-10.jpg)

<hr>





![Xnip2022-04-03_08-09-40](MySQL Note.assets/Xnip2022-04-03_08-09-40.jpg)



![Xnip2022-04-03_08-09-44](MySQL Note.assets/Xnip2022-04-03_08-09-44.jpg)

<hr>













# Day254

## Tag: LEFT JOIN

![Xnip2022-04-04_08-48-00](MySQL Note.assets/Xnip2022-04-04_08-48-00.jpg)



![Xnip2022-04-04_08-50-14](MySQL Note.assets/Xnip2022-04-04_08-50-14.jpg)

题意:

给你一张个人信息表，一张地理位置表，请你查询出其中所有人的姓名和地址



思路:

- 题意里说得花里胡哨的，其实就是以个人信息表为驱动表查询出每个人的信息即可，所以这里一个外连接就能搞定

<hr>



![Xnip2022-04-04_08-57-09](MySQL Note.assets/Xnip2022-04-04_08-57-09.jpg)



![Xnip2022-04-04_08-57-16](MySQL Note.assets/Xnip2022-04-04_08-57-16.jpg)

<hr>



![Xnip2022-04-04_08-58-24](MySQL Note.assets/Xnip2022-04-04_08-58-24.jpg)



![Xnip2022-04-04_09-00-55](MySQL Note.assets/Xnip2022-04-04_09-00-55.jpg)

<hr>













# Day255

## Tag: DATEDIFF, EXISTS

![Xnip2022-04-05_10-10-53](MySQL Note.assets/Xnip2022-04-05_10-10-53.jpg)



![Xnip2022-04-05_10-12-07](MySQL Note.assets/Xnip2022-04-05_10-12-07.jpg)

题意:

给你一张每天的气温记录表，请你查询出相比前一天气温升高的记录id



思路:

- 

- 因为需要不同行对比，所以我们需要使用内连接，连接的关系即为日期差值为1和温度的高低关系，所以SQL如下

```mysql
SELECT
    t1.id
FROM
    Weather AS t1
INNER JOIN Weather AS t2 ON DATEDIFF(t1.recordDate, t2.recordDate) = 1
AND t1.Temperature > t2.Temperature
```

<hr>





![Xnip2022-04-05_10-24-04](MySQL Note.assets/Xnip2022-04-05_10-24-04.jpg)



![Xnip2022-04-05_10-24-12](MySQL Note.assets/Xnip2022-04-05_10-24-12.jpg)

<hr>









# Day256

## Tag: GROUP BY, DATEDIFF

![Xnip2022-04-06_07-13-35](MySQL Note.assets/Xnip2022-04-06_07-13-35.jpg)



![Xnip2022-04-06_07-14-10](MySQL Note.assets/Xnip2022-04-06_07-14-10.jpg)



题意:

给你一张用户活跃记录表，请你查询出2019-07-27近30天中每天的活跃用户数量





思路:

- 因为需要统计每天的数据，所以需要按照日期分组
- 在统计用户数的时候需要去重，最后再使用日期函数限制日期值即可，最终SQL如下

```mysql
SELECT
    activity_date AS 'day',
    COUNT(DISTINCT user_id) AS 'active_users'
FROM
    Activity
WHERE DATEDIFF('2019-07-27', activity_date) < 30
GROUP BY day
```

<hr>



![Xnip2022-04-06_07-24-02](MySQL Note.assets/Xnip2022-04-06_07-24-02.jpg)



![Xnip2022-04-06_07-24-29](MySQL Note.assets/Xnip2022-04-06_07-24-29.jpg)

<hr>



![Xnip2022-04-06_07-26-50](MySQL Note.assets/Xnip2022-04-06_07-26-50.jpg)



![Xnip2022-04-06_07-27-47](MySQL Note.assets/Xnip2022-04-06_07-27-47.jpg)

<hr>








# Day257

## Tag: LIMIT

![Xnip2022-04-07_07-03-53](MySQL Note.assets/Xnip2022-04-07_07-03-53.jpg)



![Xnip2022-04-07_07-06-37](MySQL Note.assets/Xnip2022-04-07_07-06-37.jpg)

题意:

给你一张订单信息表，请你查询出其中下了最多订单的客户number





思路:

- 这里很明显需要分组，而且很特别的是，分组的和我们需要的是同一个字段
- 又因为需要的是最多的数据，所以这里使用LIMIT 1进行分表即可，SQL如下

```mysql
SELECT
    customer_number
FROM
    Orders
GROUP BY customer_number
ORDER BY COUNT(*) DESC
LIMIT 1
```



- 题目中有一个进阶要求即：如果有多个订单数最多并列的用户呢？
- 其实只需要查询出最值再进行匹配即可，这里只需要使用HAVING子句，SQL如下

```mysql
SELECT
    customer_number
FROM
    Orders
GROUP BY customer_number
HAVING COUNT(*) = (
    SELECT 
        COUNT(customer_number) AS 'cnt' 
    FROM 
        Orders 
    GROUP BY customer_number 
    ORDER BY cnt DESC  
    LIMIT 1
    )
```

<hr>



![Xnip2022-04-07_07-14-25](MySQL Note.assets/Xnip2022-04-07_07-14-25.jpg)



![Xnip2022-04-07_07-14-39](MySQL Note.assets/Xnip2022-04-07_07-14-39.jpg)

<hr>









![Xnip2022-04-07_07-17-21](MySQL Note.assets/Xnip2022-04-07_07-17-21.jpg)



![Xnip2022-04-07_07-17-48](MySQL Note.assets/Xnip2022-04-07_07-17-48.jpg)

<hr>



![Xnip2022-04-07_07-19-58](MySQL Note.assets/Xnip2022-04-07_07-19-58.jpg)



![Xnip2022-04-07_07-20-02](MySQL Note.assets/Xnip2022-04-07_07-20-02.jpg)

<hr>











# Day258

## Tag: CASE

![Xnip2022-04-08_07-07-35](MySQL Note.assets/Xnip2022-04-08_07-07-35.jpg)



![Xnip2022-04-08_07-10-52](MySQL Note.assets/Xnip2022-04-08_07-10-52.jpg)

题意:

给你一张股票交易记录表，请你查询出每只股票对应的盈亏



思路:

- 判断盈亏其实就是根据operation字段，这里我们用CASE做条件分支即可，然后累加结果
- 当然要根据股票分组才行，最终SQL如下

```mysql
SELECT
    stock_name,
    SUM(
        CASE WHEN operation = 'Buy' THEN -price 
        ELSE price END
    ) AS 'capital_gain_loss'
FROM
    Stocks
GROUP BY stock_name
```

<hr>



![Xnip2022-04-08_07-16-48](MySQL Note.assets/Xnip2022-04-08_07-16-48.jpg)



![Xnip2022-04-08_07-16-53](MySQL Note.assets/Xnip2022-04-08_07-16-53.jpg)

<hr>



![Xnip2022-04-08_07-25-25](MySQL Note.assets/Xnip2022-04-08_07-25-25.jpg)



![Xnip2022-04-08_07-25-49](MySQL Note.assets/Xnip2022-04-08_07-25-49.jpg)

<hr>











# Day259

## Tag: HAVING

![Xnip2022-04-09_09-47-58](MySQL Note.assets/Xnip2022-04-09_09-47-58.jpg)



![Xnip2022-04-09_09-48-26](MySQL Note.assets/Xnip2022-04-09_09-48-26.jpg)

题意:

给你一张信息表，请你查询出其中重复的邮箱



思路:

- 重复的邮箱其实等价于出现次数>1的邮箱
- 所以我们只需要统计每个邮箱的出现次数，再筛选出其中出现超过一次的即可
- 计算出现次数需要分组，所以我们对分组后的数据进行筛选，这里需要用到HAVING，SQL如下

```mysql
SELECT
    Email
FROM
    Person
GROUP BY Email
HAVING COUNT(*) > 1
```

<hr>



![Xnip2022-04-09_10-00-04](MySQL Note.assets/Xnip2022-04-09_10-00-04.jpg)



![Xnip2022-04-09_10-00-08](MySQL Note.assets/Xnip2022-04-09_10-00-08.jpg)

<hr>



![Xnip2022-04-09_10-05-15](MySQL Note.assets/Xnip2022-04-09_10-05-15.jpg)



![Xnip2022-04-09_10-05-23](MySQL Note.assets/Xnip2022-04-09_10-05-23.jpg)

<hr>





![Xnip2022-04-09_10-13-51](MySQL Note.assets/Xnip2022-04-09_10-13-51.jpg)



![Xnip2022-04-09_10-13-57](MySQL Note.assets/Xnip2022-04-09_10-13-57.jpg)

<hr>









# Day260

## Tag: CASE

![Xnip2022-04-10_07-58-41](MySQL Note.assets/Xnip2022-04-10_07-58-41.jpg)



![Xnip2022-04-10_07-58-21](MySQL Note.assets/Xnip2022-04-10_07-58-21.jpg)

题意:

给你一张交易记录表，请你查询出每个月每个国家对应的交易总数、允许的交易记录数、交易总额、允许的交易总额





思路:

- 我们交易总数很简单，统计行数就行
- 而允许的交易总数即为state列为"approved"的列，所以这里我们需要条件分支，这里用CASE即可
- 交易总额同样只需要用SUM总计amount即可
- 而允许的交易总额则同样使用CASE做分支判断即可，最终SQL如下

```mysql
SELECT
    LEFT(trans_date, 7) AS 'month',
    country,
    COUNT(*) AS 'trans_count',
    SUM(CASE WHEN state = 'approved' THEN 1 ELSE 0 END) AS 'approved_count',
    SUM(amount) AS 'trans_total_amount',
    SUM(CASE WHEN state = 'approved' THEN amount ELSE 0 END) AS 'approved_total_amount'
FROM
    Transactions
GROUP BY month, country
```

<hr>



![Xnip2022-04-10_08-08-12](MySQL Note.assets/Xnip2022-04-10_08-08-12.jpg)



![Xnip2022-04-10_08-08-21](MySQL Note.assets/Xnip2022-04-10_08-08-21.jpg)

<hr>





![Xnip2022-04-10_08-11-38](MySQL Note.assets/Xnip2022-04-10_08-11-38.jpg)



![Xnip2022-04-10_08-11-48](MySQL Note.assets/Xnip2022-04-10_08-11-48.jpg)

<hr>



![Xnip2022-04-10_08-21-06](MySQL Note.assets/Xnip2022-04-10_08-21-06.jpg)



![Xnip2022-04-10_08-21-16](MySQL Note.assets/Xnip2022-04-10_08-21-16.jpg)















