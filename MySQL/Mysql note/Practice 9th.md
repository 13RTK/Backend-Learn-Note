# Day201

## Tag: CASE, CROSS JOIN

![Xnip2022-02-10_18-39-13](MySQL Note.assets/Xnip2022-02-10_18-39-13.jpg)



![Xnip2022-02-10_18-40-39](MySQL Note.assets/Xnip2022-02-10_18-40-39.jpg)

题意:

给你一张运动员信息表，一张冠军记录表，请你查询出每个球员目前的冠军数量





思路:

- 我们需要的是记录下运动员夺冠的次数，但这里的问题是: 每年有不同的比赛，所以不止一个列
- 那么我们在连接匹配的时候就需要多次匹配才行，这里我们使用多个CASE相加即可，SQL如下

```mysql
SELECT
    t1.player_id,
    t1.player_name,
    SUM(
        CASE WHEN t1.player_id = t2.Wimbledon THEN 1 ELSE 0 END
        +
        CASE WHEN t1.player_id = t2.Fr_open THEN 1 ELSE 0 END
        +
        CASE WHEN t1.player_id = t2.US_open THEN 1 ELSE 0 END
        +
        CASE WHEN t1.player_id = t2.Au_open THEN 1 ELSE 0 END
    ) AS 'grand_slams_count'
FROM
    Players AS t1
INNER JOIN Championships AS t2
GROUP BY t1.player_id, t1.player_name
HAVING grand_slams_count > 0
```

<hr>

















# Day202

## Tag: BETWEEN AND, INNER JOIN

![Xnip2022-02-11_15-21-12](MySQL Note.assets/Xnip2022-02-11_15-21-12.jpg)



![Xnip2022-02-11_15-23-50](MySQL Note.assets/Xnip2022-02-11_15-23-50.jpg)

题意:

给你一张登陆记录表，请你查询出其中同一时间在不同的ip地址登陆的账号





思路:

- 分析一下题意: 同一时间、不同ip地址
- 因为只有一张表，所以我们需要自连接，这里按照题目按照用户id连接后，再限制ip地址不同
- 而同一时间则让其中一张表的login字段处在另一张表的login和logout时间之间即可，最后注意去重，最终SQL如下

```mysql
SELECT
    DISTINCT t1.account_id
FROM
    LogInfo AS t1
INNER JOIN LogInfo AS t2 ON t1.account_id = t2.account_id
AND t1.ip_address != t2.ip_address
AND t1.login BETWEEN t2.login AND t2.logout
```

<hr>









# Day203

## Tag: HAVING, DENSE_RANK

![Xnip2022-02-12_13-08-30](MySQL Note.assets/Xnip2022-02-12_13-08-30.jpg)



![Xnip2022-02-12_13-08-42](MySQL Note.assets/Xnip2022-02-12_13-08-42.jpg)

题意:

给你一张员工信息表，请你将相同工资人数大于1的员工分为一组，并获取每组的排序





思路:

- 因为查询的数据必需为相同人数大于1，所以我们需要先查询出相同人数大于1的工资金额，这里用自连接后再分组统计人数后使用HAVING限制即可，SQL如下

SQL1:

```mysql
SELECT
    t1.salary,
    DENSE_RANK() OVER(
        ORDER BY t1.salary
    ) AS 'team_id'
FROM
    Employees AS t1
INNER JOIN Employees AS t2 ON t1.employee_id = t2.employee_id
GROUP BY t1.salary
HAVING COUNT(t1.employee_id) > 1
```



- 有了该临时表后，我们再用对应的金额进行连接即可，最终SQL如下

```mysql
SELECT
    t1.employee_id,
    t1.name,
    t1.salary,
    t2.team_id
FROM
    Employees AS t1
INNER JOIN (
	SQL1
) AS t2 ON t1.salary = t2.salary
ORDER BY t2.team_id, t1.employee_id
```

<hr>











# Day204

## Tag: EXISTS, IN, HAVING

![Xnip2022-02-13_12-50-35](MySQL Note.assets/Xnip2022-02-13_12-50-35.jpg)



![Xnip2022-02-13_12-58-30](MySQL Note.assets/Xnip2022-02-13_12-58-30.jpg)



![Xnip2022-02-13_12-59-51](MySQL Note.assets/Xnip2022-02-13_12-59-51.jpg)

题意:

(根据《阿里巴巴Java开发手册》表名不能出现大写字母，且表名不能为复数)

给你一张消费者信息表，一张订单表，一张产品信息表，请你查询出其中每个产品的最新一天的订单信息





思路:

- 

- 因为需要的是每个产品的最新一天的信息，所以这里我们需要先获取每个产品对应的最新一天，SQL如下

SQL1:

```mysql
SELECT
	product_id,
	MAX(order_date) AS 'order_date'
FROM
	Orders
GROUP BY product_id
```



- 获取后以该临时表为WHERE子句的参数，进行限制即可，最后注意连接顺序，最终SQL如下

```mysql
SELECT
    t1.product_name,
    t2.product_id,
    t2.order_id,
    t2.order_date
FROM
    Products AS t1
INNER JOIN Orders AS t2 ON t1.product_id = t2.product_id
WHERE (t2.product_id, t2.order_date) IN (
    SQL1
)
ORDER BY t1.product_name, t2.product_id, t2.order_id
```







拓展:

- 如果了解过EXISTS的朋友可能知道：EXISTS的性能往往要比IN要好，如果还恰巧读过《阿里巴巴Java开发手册》的话，可能会记得其中SQL语句部分有这样一句话:
- 【推荐】in 操作能避免则避免，若实在避免不了，需要仔细评估 in 后边的集合元素数量，控制在 1000 个之内。
- 那么我们就把IN改写为EXISTS，SQL如下

```mysql
SELECT
    t1.product_name,
    t2.product_id,
    t2.order_id,
    t2.order_date
FROM
    Products AS t1
INNER JOIN Orders AS t2 ON t1.product_id = t2.product_id
WHERE EXISTS (
    SELECT
        product_id,
        MAX(order_date) AS 'last_order_date'
    FROM
        Orders AS t3
    GROUP BY product_id
    HAVING t1.product_id = t3.product_id AND t2.order_date = last_order_date
)
ORDER BY t1.product_name, t2.product_id, t2.order_id
```



- 但此时力扣的提交记录上却显示改为EXISTS后用时几乎翻倍了？！这是什么情况？难道是那些写书的在乱写？其实不然
- 在用IN的版本中，我们的子查询为“不相关子查询”，不相关子查询的执行顺序如图

![Xnip2022-02-13_13-17-12](MySQL Note.assets/Xnip2022-02-13_13-17-12.jpg)

![Xnip2022-02-13_13-20-57](MySQL Note.assets/Xnip2022-02-13_13-20-57.jpg)



- 而在EXISTS的版本中，我们使用的是相关子查询，其执行顺序如图:

![Xnip2022-02-13_13-22-15](MySQL Note.assets/Xnip2022-02-13_13-22-15.jpg)

- 很明显，相关子查询的步骤较多，自然用时就长

// 笔记来自《MySQL是怎样运行的》



优化:

- 因为IN的版本为不相关子查询，所以我们以IN的版本为基础来优化
- 由查询计划可知，因为Orders表中的product_id上没有任何索引，所以子查询表中的Extra列为"Using temporary"和"Using filesort"，此时开销为10.81

![Xnip2022-02-13_13-37-25](MySQL Note.assets/Xnip2022-02-13_13-37-25.jpg)



![Xnip2022-02-13_13-36-48](MySQL Note.assets/Xnip2022-02-13_13-36-48.jpg)





尝试:

- 那么很明显需要为Orders表中的"product_id"列建立索引才行，又因为我这里使用的MySQL5.7.10，所以存在隐式排序，所以还需要添加一行"ORDER BY NULL"
- 但在表t2中，我们同时使用了t2.product_id和t2.order_date两个列来和临时表进行匹配
- 所以为了不让t2回表(Using where)，我们应该为这两个列创建联合索引(也叫组合索引)，只要保证product_id是第一个字段，那么该联合索引同样可以用到子查询中
- 最后t1表该怎么优化呢？因为排序字段中有t1.product_name，那么我们在product_name上建立索引？
- 并不，在该字段上建立索引后，也不过是将ALL变为了Index而已，提升微乎其微，更别说对于varchar字段建立索引还需要根据文本区分度决定具体的索引长度





结果:

- 此时再查看执行计划，t2和子查询都分别由ALL变为了ref和range，查询开销降为了8.48!

![Xnip2022-02-13_13-59-58](MySQL Note.assets/Xnip2022-02-13_13-59-58.jpg)



![Xnip2022-02-13_13-55-47](MySQL Note.assets/Xnip2022-02-13_13-55-47.jpg)

<hr>













# Day205

## Tag: Window Func, Frame

![Xnip2022-02-14_12-33-43](MySQL Note.assets/Xnip2022-02-14_12-33-43.jpg)



![Xnip2022-02-14_12-34-07](MySQL Note.assets/Xnip2022-02-14_12-34-07.jpg)

题意:

给你一张消费记录表，请你查询出最近几天中各自6天内的总营业额和日均营业额







思路:

- 该题目其实类似Day183，只不过这道题目的日期需要我们自己获取
- 首先因为表中的记录是分为单个订单的，所以我们要将其整理为每天的营业额，SQL如下

SQL1:

```mysql
SELECT
    visited_on,
    SUM(amount) AS 'single_day_sum'
FROM
    Customer
GROUP BY visited_on
```



- 之后我们直接获取每天近6日的营业额总和，这里使用了窗口函数的Frame选项，SQL如下

SQL2

```mysql
SELECT
    visited_on,
    SUM(single_day_sum) OVER(
        ORDER BY visited_on
        ROWS 6 PRECEDING
    ) AS 'amount_sum'
FROM (
		SQL1
    ) AS t1
```



- 最后我们再对日期进行限制即可，这里只需要保证日期与表中最小日期的差值≥6即可，SQL如下

```mysql
SELECT
    visited_on,
    amount_sum AS 'amount',
    ROUND(amount_sum / 7, 2) AS 'average_amount'
FROM (
		SQL2
) AS temp
WHERE TIMESTAMPDIFF(DAY, (SELECT MIN(visited_on) FROM Customer), visited_on) >= 6
ORDER BY visited_on
```

<hr>















# Day206

## Tag: LEFT JOIN, ISNULL

![Xnip2022-02-15_13-00-10](MySQL Note.assets/Xnip2022-02-15_13-00-10.jpg)



![Xnip2022-02-15_13-22-34](MySQL Note.assets/Xnip2022-02-15_13-22-34.jpg)

题意:

给你一张订单信息表，请你根据题目对应的条件查询出订单信息





思路:

- 最容易的想到方法的就是查询出有类型为0的订单对应的客户id，再用IN或者EXISTS结合UNION来做查询，这种写法的SQL如下

```mysql
SELECT
    t1.order_id,
    t1.customer_id,
    t1.order_type
FROM
    Orders AS t1
WHERE NOT EXISTS (
    SELECT
        t2.customer_id
    FROM
        Orders AS t2
    WHERE t2.order_type = 0
    GROUP BY t2.customer_id
    HAVING t1.customer_id = t2.customer_id
)
UNION
SELECT
    t1.order_id,
    t1.customer_id,
    t1.order_type
FROM
    Orders AS t1
WHERE order_type = 0
AND EXISTS (
    SELECT
        t2.customer_id
    FROM
        Orders AS t2
    WHERE t2.order_type = 0
    GROUP BY t2.customer_id
    HAVING t1.customer_id = t2.customer_id
)
```



- 但其实可以通过自连接得到一种简单的写法:
- 自连接时指定两表的"order_type"字段不同(该字段只有两个值)，且连接使用外连接
- 此时查询出来的结果有两种情况: 要么该客户只有一种类型的订单(只有0或者只有1)，此时他的order_type字段就为NULL；要么该客户有两种类型的订单，此时其order_type自度就不为NULL
- 此时如果该用户只有一种类型的订单，那么直接输出其记录即可。但如果有两种，那么不能输出类型为1的订单，这在SQL中如何体现呢？
- 这就是这种写法最巧妙也是最费解的地方了，我们输出的字段数据是来自表t1的，而WHERE子句中限制的是t2表的字段，此时WHERE子句的内容为:

```mysql
WHERE ISNULL(t2.order_type) OR t2.order_type = 1;
```



- 前面判断NULL很容易理解，那后面是啥？这里可以这样理解:
- 这里我们使用的是逻辑OR，如果该字段不为NULL，那么此时就会判断其值是否为1
- 如果其值为1，说明t1.order_type = 0(两表连接时的条件为"order_type"字段不同)，此时直接输入就能保证t1.order_type全为0了！
- 最终SQL如下

```mysql
SELECT
    DISTINCT t1.order_id,
    t1.customer_id,
    t1.order_type
FROM
    Orders AS t1
LEFT JOIN Orders AS t2 ON t1.customer_id = t2.customer_id AND t1.order_type != t2.order_type
WHERE ISNULL(t2.order_type) OR t2.order_type = 1
```

<hr>







# Day207

## Tag: HAVING, ABS

![Xnip2022-02-16_13-41-40](MySQL Note.assets/Xnip2022-02-16_13-41-40.jpg)



![Xnip2022-02-16_13-43-40](MySQL Note.assets/Xnip2022-02-16_13-43-40.jpg)

题意:

给你一张点的坐标表，请你查询出其中任意两个点可以形成的面积不为0的矩形面积





思路:

- 从题目的示例来看，矩形的计算方法就是横纵坐标之差的乘积的绝对值
- 而确保任意两点的方法就是自连接后让两表的id列值不同，但简单的使用id不相等还不够，这里ing更改使用<以保证不会重复计算
- 最后只需要使用HAVING排除掉面积为0的组合并按规则排序即可，最终SQL如下

```mysql
SELECT
    t1.id AS 'p1',
    t2.id AS 'p2',
    ABS((t1.x_value - t2.x_value) * (t1.y_value - t2.y_value)) AS 'area'
FROM
    Points AS t1
INNER JOIN Points AS t2 ON t1.id < t2.id
HAVING area > 0
ORDER BY area DESC, p1, p2
```

<hr>















# Day208

## Tag: CASE, OR, Boolean

![Xnip2022-02-17_14-01-39](MySQL Note.assets/Xnip2022-02-17_14-01-39.jpg)



![Xnip2022-02-17_14-01-47](MySQL Note.assets/Xnip2022-02-17_14-01-47.jpg)

题意:

给你一张变量取值表，一张表达式表，请你查询出表达式表中每条数据对应的boolean结果







思路:

- 因为表中只有两个变量，而符号有三种，所以一共有六种情况
- 当然，我们可以将6种情况都一一列出再用CASE来做分支，但其实不用这么麻烦
- 我们只需要将为true或者为false的所有情况都列出来并用OR连接即可，剩下的情况就是false了
- 需要注意的是，在比较值的时候，我们不能比较同一张variables表中的字段值，所以这里需要分别用left_operand和right_operand连接variables表两次，所以最终SQL如下

```mysql
SELECT
    t1.left_operand,
    t1.operator,
    t1.right_operand,
    CASE WHEN (t1.operator = '>' AND t2.value > t3.value) 
    OR 
    (t1.operator = '<' AND t2.value < t3.value)
    OR
    (t1.operator = '=' AND t2.value = t3.value)
    THEN 'true'
    ELSE 'false' END AS 'value'
FROM
    Expressions AS t1
INNER JOIN Variables AS t2 ON t1.left_operand = t2.name
INNER JOIN Variables AS t3 ON t1.right_operand = t3.name
```

<hr>













# Day209

## Tag: CTE

![Xnip2022-02-18_09-40-09](MySQL Note.assets/Xnip2022-02-18_09-40-09.jpg)



![Xnip2022-02-18_09-40-35](MySQL Note.assets/Xnip2022-02-18_09-40-35.jpg)

题意:

给你一张飞机起降记录表，请你查询出其中承载最多飞机的机场id







思路:

- 由表可知: 每条记录中记录的是离港的机场id、降落的机场id和架次
- 因为离港的和降落的机场id都要统计，所以这里我们首先需要将两张表连接起来，SQL如下

SQL1:

```mysql
SELECT
    departure_airport AS 'airport_id',
    flights_count
FROM
    Flights
UNION ALL
SELECT
    arrival_airport AS 'airport_id',
    flights_count
FROM
    Flights
```



- 之后再统计每个机场对应的架次，且为了之后好找出最大承载量，按照架次进行排序，SQL如下

SQL2:

```mysql
SELECT
    airport_id,
    SUM(flights_count) AS 'num'
FROM (
		SQL1
) AS t1
GROUP BY airport_id
ORDER BY num DESC
```



- 最后我们再通过取第一条记录的num字段，就能获取最大的承载量了，再用这个承载量去匹配对应的机场id即可
- 这里需要将SQL2生成的表使用两次，如果是MySQL5.7的话写两次确实很难看，所以这里我们可以运用MySQL8.0的特性: CTE，这样就只需要写一次了，最终SQL如下

```mysql
WITH temp AS (
		SQL2
)

SELECT
    airport_id
FROM 
    temp
WHERE num = (SELECT num FROM temp LIMIT 1)
```

<hr>















# Day210

## Tag: EXISTS, IN, JOIN

![Xnip2022-02-19_10-08-00](MySQL Note.assets/Xnip2022-02-19_10-08-00.jpg)



![Xnip2022-02-19_10-08-36](MySQL Note.assets/Xnip2022-02-19_10-08-36.jpg)



![Xnip2022-02-19_10-09-50](MySQL Note.assets/Xnip2022-02-19_10-09-50.jpg)

题意:

给你一张订阅信息表，一张流媒体访问记录表，请你查询出2021年有订阅但2021没有访问流媒体的用户数量



思路:

- 这里存在两种限制关系，这里我们需要的是订阅表中的用户信息，所以我们需要先查询出2021年没有访问流媒体的用户id，SQL如下

SQL1:

```mysql
SELECT
    t2.account_id
FROM
    Streams AS t2
WHERE YEAR(t2.stream_date) = 2021
```



- 之后再以此作为WHERE子句的参数，使用NOT IN即可，最终SQL如下

```mysql
SELECT
    COUNT(*) AS 'accounts_count'
FROM
    Subscriptions AS t1
WHERE (YEAR(t1.start_date) = 2021 OR YEAR(t1.end_date) = 2021)
AND account_id NOT IN (
		SQL1
)
```





拓展:

- 从解题的角度来说，这样其实就完结了，但从实用的角度呢？
- 这里我们使用到了IN，在实际生产/业务中，如果IN中的数据个数一旦超过了1000个，那么就会出问题，在《阿里巴巴Java开发手册》中也有类似的建议
- 那么为什么不建议使用IN，且用的时候需要限制参数个数在1000个以内呢？
- 我们先来查看一个MySQL系统变量: max_allowed_packet，它代表了任何生成值/中间值/参数的最大大小，其默认值为4MB，官方文档如图:

![Xnip2022-02-19_10-30-45](MySQL Note.assets/Xnip2022-02-19_10-30-45.jpg)

- 再发挥一下想象力，MySQL中单个字符最多占用4个字节(utf8mb4)，那么1000个参数*4不就是4000bytes了吗？刚好4MB！



- 所以在实际业务中，如果没有在配置文件中修改max_allowed_packet这个变量的话，最好不要在SQL中使用IN查询
- 因此这里我们将其改为使用EXISTS

```mysql
SELECT
    COUNT(*) AS 'accounts_count'
FROM
    Subscriptions AS t1
WHERE (YEAR(t1.start_date) = 2021 OR YEAR(t1.end_date) = 2021)
AND NOT EXISTS (
    SELECT
        t2.account_id
    FROM
        Streams AS t2
    WHERE t1.account_id = t2.account_id
    AND YEAR(t2.stream_date) = 2021
)
```



最后推荐各位阅读一篇运维老哥写的文章，相信能对IN的使用有更深的理解:

[技术分享｜mysql in溢出bug和排查经历 - 力扣（LeetCode） (leetcode-cn.com)](https://leetcode-cn.com/circle/discuss/c40Pde/)

<hr>





















# Day211

## Tag: EXISTS

![Xnip2022-02-20_15-41-41](MySQL Note.assets/Xnip2022-02-20_15-41-41.jpg)



![Xnip2022-02-20_15-45-28](MySQL Note.assets/Xnip2022-02-20_15-45-28.jpg)

题意:

给你一张员工信息表，请你查询出直接或间接向老板汇报的员工id





思路:

- 如果是有多个中间层的话，其实这道题目很难，但题目中指明最多只有3层间接关系
- 所以最简单的方法就是三次IN子查询或者连接三张表，但其中IN有参数个数限制，连接时驱动表对应的结果集也可能会很大，所以两种方式都不太好
- 这里我们可以改为使用EXISTS，依次查询每层即可，SQL如下

```mysql
SELECT
	t1.employee_id
FROM
	Employees AS t1
WHERE EXISTS (
	SELECT
		t2.employee_id
	FROM
		Employees AS t2
	WHERE EXISTS (
    SELECT
      t3.employee_id
    FROM
      Employees AS t3
    WHERE t3.manager_id = 1
    AND t2.manager_id = t3.employee_id
		)
	AND t1.manager_id = t2.employee_id
	)
AND t1.employee_id != 1
```

<hr>

















# Day212

## Tag: CASE

![Xnip2022-02-21_08-33-52](MySQL Note.assets/Xnip2022-02-21_08-33-52.jpg)



![Xnip2022-02-21_08-34-21](MySQL Note.assets/Xnip2022-02-21_08-34-21.jpg)

题意:

给你一张股票交易记录表，请你计算出每支股票对应的盈亏





思路:

- 因为需要的是每支股票的情况，所以很明显需要分组，而判断买卖只需要使用CASE判断operation字段即可，最终SQL如下

```mysql
SELECT
    stock_name,
    SUM(
    CASE WHEN operation = 'Buy'
    THEN -price
    WHEN operation = 'Sell'
    THEN price
    ELSE NULL END
    ) AS 'capital_gain_loss'
FROM
    Stocks
GROUP BY stock_name
```

<hr>















# Day213

## Tag: Self Join, CTE

![Xnip2022-02-22_09-36-34](MySQL Note.assets/Xnip2022-02-22_09-36-34.jpg)



![Xnip2022-02-22_09-37-01](MySQL Note.assets/Xnip2022-02-22_09-37-01.jpg)

题意:

给你一张用户信息表，一张交易记录表，请你查询出其中的可疑用户id(连续两个月的总收入超过其最大收入)





思路:

- 因为交易表中的记录是分散的，而我们需要的是判断每个月对应的总收入，所以需要按照用户id和月份先分组，SQL如下

SQL1:

```mysql
SELECT
    account_id,
    DATE_FORMAT(day, '%Y%m') AS 'month',
    SUM(CASE WHEN type = 'Creditor' THEN amount ELSE 0 END) AS 'sum'
FROM
    Transactions
GROUP BY account_id, month
```



- 之后再通过连接，查询出其中超过最大收入的用户id和对应的月份，SQL如下

SQL2:

```mysql
SELECT
    t2.account_id,
    t2.month
FROM
    Accounts AS t1
INNER JOIN (
    SQL1
) AS t2 ON t1.account_id = t2.account_id
WHERE t1.max_income < t2.sum
```





- 最后我们只需要对该表进行自连接，查询出有连续月份的id即可
- 为了不重复写，这里用到了MySQL8.0的新特性: CTE，最终SQL如下

```mysql
WITH temp AS (
	SQL2
)

SELECT
	DISTINCT t1.account_id
FROM
	temp AS t1
INNER JOIN temp AS t2 ON t1.account_id = t2.account_id
WHERE t1.month = t2.month + 1
```

<hr>













# Day214

## Tag: SUM() OVER

![Xnip2022-02-23_09-28-06](MySQL Note.assets/Xnip2022-02-23_09-28-06.jpg)



![Xnip2022-02-23_09-28-16](MySQL Note.assets/Xnip2022-02-23_09-28-16.jpg)

题意:

给你一张交易记录表，请你查询出每个用户每个交易日对应的账户余额





思路:

- 因为是按照用户划分的，所有需要分组，但余额是随日期变化的，简单的使用SUM和GROUP BY不能达到这样的效果
- 我们需要实现分组累加的效果，但这恰恰是SUM() OVER()这个窗口函数擅长的，所以如果熟悉它的话，就能轻松秒杀，最终SQL如下

```mysql
SELECT
    account_id,
    day,
    SUM(
        CASE WHEN type = 'Deposit' 
        THEN amount
        WHEN type = 'withdraw'
        THEN -amount
        ELSE NULL END
    ) OVER (
        PARTITION BY account_id
        ORDER BY day
    ) AS 'balance'
FROM
    Transactions
```

<hr>

















# Day215

## Tag: CTE, SQL recursive

![Xnip2022-02-24_07-58-43](MySQL Note.assets/Xnip2022-02-24_07-58-43.jpg)



![Xnip2022-02-24_07-59-06](MySQL Note.assets/Xnip2022-02-24_07-59-06.jpg)

题意:

给你一张顾客信息表，请你查询出其中遗失的顾客id





思路:

- 按照题目的说法，我们需要的客户id在1到100之间，且不能出现在顾客表中，且不大于最大的客户id
- 首先我们需要生成1到100，这里我们可以使用CTE来简单的递归构造，SQL如下

SQL1:

```mysql
WITH recursive temp AS (
    SELECT
        1 AS 'n'
    UNION ALL
    SELECT
        n + 1
    FROM
        temp
    WHERE n < 100
)
```



- 之后我们只需要查询出原表中的最大Id和所有出现的id，再代入条件即可，最终SQL如下

```mysql
SQL1

SELECT
    n AS 'ids'
FROM
    temp
WHERE NOT EXISTS (
    SELECT
        customer_id
    FROM
        Customers
    WHERE n = customer_id
)
AND n < (
    SELECT
        MAX(customer_id)
    FROM
        Customers
)
```

<hr>

















# Day216

## Tag: Define Func

![Xnip2022-02-25_07-27-54](MySQL Note.assets/Xnip2022-02-25_07-27-54.jpg)



![Xnip2022-02-25_07-28-09](MySQL Note.assets/Xnip2022-02-25_07-28-09.jpg)

题意:

给你一张员工薪资表，请你创建一个函数，使它能查询出第N高的薪水





思路:

- 该题目考察的是MySQL自建函数的过程，只要遵循语法即可，语法见图
- 这里的思路是对薪水进行排序，再分页即可，这里我们只需要跳过前面N-1个即可
- 所以刚开始的时候我们就需要获取N - 1，这里可以对参数N进行修改: N := N - 1;
- 之后写出SQL再代入N即可，最终SQL如下

```mysql
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
  SET N := N - 1;
  RETURN (
    SELECT
      salary
    FROM
      Employee
    GROUP BY salary
    ORDER BY salary DESC
    LIMIT 1 OFFSET N
  );
END
```

<hr>



















# Day217

## Tag: 中位数

![Xnip2022-02-26_10-29-14](MySQL Note.assets/Xnip2022-02-26_10-29-14.jpg)



![Xnip2022-02-26_10-29-29](MySQL Note.assets/Xnip2022-02-26_10-29-29.jpg)

题意:

给你一张员工信息表，请你查询出每个公司中薪资处于中位数的员工信息





思路:

- 很巧的是，对于求中位数，在《SQL进阶教程》第一章的HAVING部分刚好有提到
- 书中的解法是自连接后计算出两个子集，且让两个子集都包含中间部分(即SQL中的 >= COUNT(*) / 2和COUNT(*))
- 最后只需要取出它们的重合部分即可，具体看图

![Xnip2022-02-26_10-38-19](MySQL Note.assets/Xnip2022-02-26_10-38-19.jpg)



![Xnip2022-02-26_10-38-24](MySQL Note.assets/Xnip2022-02-26_10-38-24.jpg)



![Xnip2022-02-26_10-38-28](MySQL Note.assets/Xnip2022-02-26_10-38-28.jpg)



- 沿着大佬的思路，我们再看这道题目，与书中示例不同的是，这里我们在统计时需要根据公司分组
- 如果题目要求仅仅是求每个公司对应的中位数薪资的话，直接照搬书上的SQL就能做到，但这里我们需要的是中位数对于的员工信息，所以没这么简单
- 这里我们需要先获取每个公司中薪资等于中位数的员工id，这里我们只需要模仿书上的写法即可，SQL如下

SQL1:

```mysql
SELECT
		t1.id
FROM
		Employee AS t1
INNER JOIN Employee AS t2 ON t1.company = t2.company
GROUP BY t1.id, t1.salary
HAVING SUM(CASE WHEN t1.salary <= t2.salary THEN 1 ELSE 0 END) >= COUNT(*) / 2
AND SUM(CASE WHEN t1.salary >= t2.salary THEN 1 ELSE 0 END) >= COUNT(*) / 2
```





- 最后我们需要以这些员工id为基础，查询出需要的即可，但简单的使用IN还不行
- 因为我们查询出来的是薪资等于中位数的员工，但其中不是每个员工都处于中间位置
- 这里我们需要在最外层查询中根据公司和薪资分组，这样相同的薪资中就只会有一条记录得到匹配，重复的就被删除了，最终SQL如下

```mysql
SELECT
    id,
    company,
    salary
FROM
    Employee
WHERE id IN (
    SQL1
)
GROUP BY company, salary
```

<hr>















# Day218

## Tag: Recursive, CTE

![Xnip2022-02-27_10-01-02](MySQL Note.assets/Xnip2022-02-27_10-01-02.jpg)



![Xnip2022-02-27_10-01-27](MySQL Note.assets/Xnip2022-02-27_10-01-27.jpg)

题意:

看起来很多，但其实还好

给你三张表，一张司机信息表，一张订单表，一张确认的订单表(订单表中部分订单可能取消)

请你查询出从2020年1月到10月中，每个月中近3个月的平均订单距离和订单时长







思路:

- 从结果来看，我们只需要订单表和确认订单表，但麻烦的是，从1月到10月这10行并未出现在表中，要么我们使用CASE WHEN构建，要么写一个从1到12月的表
- 这里我们可以选择通过CTE递归的方法生成1到12，方法和SQL Day215生成从1到100是一样的，SQL如下

SQL1:

```mysql
WITH recursive month(cur_month) AS (
    SELECT
        1
    UNION ALL
    SELECT
        cur_month + 1
    FROM
        month
    WHERE cur_month < 12
)
```



- 之后我们连接订单和确认订单表，限制订单的年份，再用我们生成的月份表连接限制订单的月份(注意这里是一个区间，所以用到了BETWEEN AND)
- 最后按照月份分组，并排序，再限制前10个月即可，最终SQL如下

```mysql
SQL1

SELECT
    t3.cur_month AS 'month',
    IFNULL(ROUND(SUM(t1.ride_distance) / 3, 2), 0) AS 'average_ride_distance',
    IFNULL(ROUND(SUM(t1.ride_duration) / 3, 2), 0) AS 'average_ride_duration'
FROM
    AcceptedRides AS t1
INNER JOIN Rides AS t2 ON t1.ride_id = t2.ride_id AND YEAR(t2.requested_at) = 2020
RIGHT JOIN month AS t3 ON MONTH(t2.requested_at) BETWEEN t3.cur_month AND t3.cur_month + 2
GROUP BY t3.cur_month
ORDER BY t3.cur_month
LIMIT 10
```

<hr>

















# Day219

## Tag: 连续区间的起始和结束数字

![Xnip2022-02-28_07-58-16](MySQL Note.assets/Xnip2022-02-28_07-58-16.jpg)



![Xnip2022-02-28_07-59-30](MySQL Note.assets/Xnip2022-02-28_07-59-30.jpg)

题意:

给你一张日志表，其中有若干数字，请你查询出其中所有连续区间对应的起始和结束数字







思路:

- 该题目可以分为两个部分求解: 即获取边界数字和对应每个区间
- 边界数字很好理解，对于左边界: 在其基础上 -1的数不存在；对于右边界: 在其基础上 +1的数不存在，所以我们利用这两个条件就能分别查询出边界数字，SQL如下

SQL1:

```mysql
SELECT 
	log_id
FROM 
	Logs
WHERE NOT EXISTS (
  SELECT
  	log_id
  FROM
  	Logs AS temp
  WHERE temp.log_id = Logs.log_id - 1
)
```



SQL2:

```mysql
SELECT 
	log_id
FROM 
	Logs
WHERE NOT EXISTS (
  SELECT
  	log_id
  FROM
  	Logs AS temp
  WHERE temp.log_id = Logs.log_id + 1
)
```

- 之后只需要对应左右边界即可，但该怎么对应？这里我们拿题目的示例来分析:
- 按照Logs表中(1, 2, 3, 7, 8, 10)来查询的话，前两个SQL的查询结果分别为:
- (1, 7, 10)和(3, 8, 10)，如果之间连接的话会形成笛卡尔积，所以需要一个限制条件
- 又因为左边界必然小于等于右边界，所以我们可以使用非等值连接，即将连接条件设置为: t1.log_id <= t2.log_id
- 但这样明显还是不行，每个左边界只需要一个右边界，这时候分组就派上用场了
- 此时连接后，t1中第一条的对应关系为: (1) -> (3, 8, 10)，我们只需要根据t1.log_id分组，再取t2.log_id中的最小值即可！最终SQL如下

```mysql
SELECT
    t1.log_id AS 'start_id',
    MIN(t2.log_id) AS 'end_id'
FROM (
		SQL1
    ) AS t1
INNER JOIN (
    SQL2
    ) AS t2 ON t1.log_id <= t2.log_id
GROUP BY t1.log_id
```

<hr>















# Day220

## Tag: UNION ALL, CASE WHEN

![Xnip2022-03-01_07-58-24](MySQL Note.assets/Xnip2022-03-01_07-58-24.jpg)



![Xnip2022-03-01_07-57-56](MySQL Note.assets/Xnip2022-03-01_07-57-56.jpg)

题意:

给你一张用户信息表，一张交易记录表，请你查询出所有用户的信息和当前的账户余额，最后再判断其是否透支





思路:

- 因为交易表中减少和增加的用户id在同一行，所以我们需要把行转换为列，这就需要UNION ALL了，我们将用户表中的credit和交易表中的paid_by和paid_to都视作user_id
- 这样我们就将两张表的记录转换为了一张只有id和正负金额的交易表，SQL如下

SQL1:

```mysql
SELECT
	user_id,
	credit AS 'amount'
FROM
	Users
UNION ALL
SELECT
	paid_by AS 'user_id',
	-amount AS 'amount'
FROM
	Transactions
UNION ALL
SELECT
	paid_to AS 'user_id',
	amount AS 'amount'
FROM
	Transactions
```



- 之后根据用户id分组，将金额统计起来即可，SQL如下

SQL2:

```mysql
SELECT
	user_id,
	SUM(amount) AS 'credit'
FROM (
	SQL1
		) AS temp
GROUP BY user_id
```



- 最后再连接用户表查询出用户信息，再根据当前余额判断是否透支即可，最终SQL如下

```mysql
SELECT
	t2.user_id,
	t2.user_name,
	t1.credit,
	CASE WHEN t1.credit >= 0 THEN 'No'
	ELSE 'Yes' END AS 'credit_limit_breached'
FROM (
	SQL2
	) AS t1
INNER JOIN Users AS t2 ON t1.user_id = t2.user_id
```

<hr>













# Day221

## Tag: CTE, RECURSIVE

![Xnip2022-03-02_08-09-25](MySQL Note.assets/Xnip2022-03-02_08-09-25.jpg)



![Xnip2022-03-02_08-11-49](MySQL Note.assets/Xnip2022-03-02_08-11-49.jpg)

题意:

给你一张任务对应表，一张已执行任务表，请你查询出其中所有没被执行的任务对





思路:

- 因为任务对应表中是id对应子任务id，所以我们可以根据该表获取每个任务id对应的所有子任务id
- 将这个问题抽象一下：给你几组数字，请你为每组数字从给定的上限开始生成出所有的连续id
- 是不是很熟悉？没错，又是CTE的递归用法，语法可参照图中的官方文档，我们首先生成出每组任务id对应的所有子任务id，SQL如下

SQL1:

```mysql
WITH RECURSIVE temp(task_id, subtask_id) AS (
    SELECT
        task_id,
        subtasks_count AS 'subtask_id'
    FROM
        Tasks
    UNION ALL
    SELECT
        task_id,
        subtask_id - 1
    FROM
        temp
    WHERE subtask_id - 1 >= 1

```



- 之后就简单了，只要排除掉Executed表中的子任务id即可，最终SQL如下

```mysql
SQL1

SELECT
    task_id,
    subtask_id
FROM
    temp
WHERE NOT EXISTS (
    SELECT
        task_id,
        subtask_id
    FROM
        Executed
    WHERE Executed.task_id = temp.task_id 
    AND Executed.subtask_id = temp.subtask_id
    );
```

<hr>











# Day222

## Tag: CTE

![Xnip2022-03-03_07-20-42](MySQL Note.assets/Xnip2022-03-03_07-20-42.jpg)



![Xnip2022-03-03_07-21-35](MySQL Note.assets/Xnip2022-03-03_07-21-35.jpg)

题意:

给你一张用户关系表，请你查询出共同关注者数量最多的用户对





思路:

- 既然是需要最多的共同关注者，那么follower_id应该相同，所以这里我们需要使用自连接，连接后根据id分组统计得到的行数就是每对关注者的共同关注人数
- 为了去重，这里最好再限制一下两个id的大小关系，为了之后获取最大共同关注人数，这里按照人数进行了排序，SQL如下

SQL1:

```mysql
SELECT
    t1.user_id AS 'user1_id',
    t2.user_id AS 'user2_id',
    COUNT(*) AS 'num'
FROM
    Relations AS t1
INNER JOIN Relations AS t2 ON t1.follower_id = t2.follower_id
WHERE t1.user_id < t2.user_id
GROUP BY t1.user_id, t2.user_id
ORDER BY num DESC
```



- 根据这张临时表，我们可以直接通过LIMIT获取最大的共同关注人数，再根据这个数字去匹配对应的用户对即可，最终SQL如下

```mysql
WITH temp AS (
		SQL1
)

SELECT
    user1_id,
    user2_id
FROM
    temp
WHERE num = (SELECT num FROM temp LIMIT 1)
```

<hr>













# Day223

## Tag: CASE

![Xnip2022-03-04_07-54-34](MySQL Note.assets/Xnip2022-03-04_07-54-34.jpg)



![Xnip2022-03-04_07-54-52](MySQL Note.assets/Xnip2022-03-04_07-54-52.jpg)

题意:

给你一张会员信息表，一张访问记录表，一张购买记录表，请你根据转换率对每个用户进行分级(转换率 = 100 * 购买次数 / 访问次数)，大于等于80为钻石，50到80为黄金，小于50为白银，没有访问记录的为青铜



思路:

- 很明显这道题目需要我们先获取每个用户的转换率
- 因为有可能存在没有购买记录的用户，所以这里我们需要以用户表为驱动表进行外连接
- 需要注意的是，部分用户可能只有访问记录而没有购买记录，有的用户干脆没有访问记录，为了区分这两类用户，我们需要保存外连接得到的NULL
- 这样转换率为NULL的就是青铜了，SQL如下

SQL1:

```mysql
SELECT
    t1.member_id,
    t1.name,
    100 * COUNT(t3.charged_amount) / COUNT(t2.visit_id) AS 'conversion_rate'
FROM
    Members AS t1
LEFT JOIN Visits AS t2 ON t1.member_id = t2.member_id
LEFT JOIN Purchases AS t3 ON t2.visit_id = t3.visit_id
GROUP BY t1.member_id, t1.name
```





- 之后只需要根据该表的转换率进行分支判断即可，这里我选择CASE，最终SQL如下

```mysql
SELECT
    member_id,
    name,
    CASE WHEN ISNULL(conversion_rate) THEN 'Bronze'
    WHEN conversion_rate >= 80 THEN 'Diamond'
    WHEN conversion_rate >= 50 AND conversion_rate < 80 THEN 'Gold'
    WHEN conversion_rate < 50 THEN 'Silver'
    ELSE NULL END AS 'category'
FROM (
		SQL1
) AS temp
```

<hr>









# Day224

## Tag: HAVING

![Xnip2022-03-05_08-36-08](MySQL Note.assets/Xnip2022-03-05_08-36-08.jpg)



![Xnip2022-03-05_08-36-39](MySQL Note.assets/Xnip2022-03-05_08-36-39.jpg)

题意:

给你一张候选人信息表，一张面试信息表，请你查询出工作经验大于等于2，面试总分大于15的候选人id



思路:

- 今天的SQL索然无味，感觉这是道简单题
- 因为要求总分，所以自然需要根据id分组，而工作年限在连接的时候就可以进行限制了
- 因为是分组后进行限制，所以这里使用HAVING即可，最终SQL如下

```mysql
SELECT
    t1.candidate_id
FROM
    Candidates AS t1
INNER JOIN Rounds AS t2 ON t1.interview_id = t2.interview_id
WHERE t1.years_of_exp >= 2
GROUP BY t1.candidate_id
HAVING SUM(t2.score) > 15
```

<hr>











# Day225

## Tag: GROUP BY, OUTTER JOIN

![Xnip2022-03-06_10-56-23](MySQL Note.assets/Xnip2022-03-06_10-56-23.jpg)



![Xnip2022-03-06_11-02-49](MySQL Note.assets/Xnip2022-03-06_11-02-49.jpg)

题意:

给你一张公交车到站时间表，一张乘客到站时间表，请你查询出每辆公交车上搭载的乘客(公交车只能搭载其到达时间小于乘客到达时间的乘客)





思路:

- 

- 该题目的难点在于，如何在计算乘客数的时候排除之前的公交车，也就是让乘客搭乘最近到达的一辆车
- 其实最近到达就是该乘客能够搭乘的车辆中，最早到达的车辆，我们可以先查询出这样的对应关系，SQL如下

SQL1:

```mysql
SELECT
    t1.passenger_id,
    MIN(t2.arrival_time) AS 'min_bus_time'
FROM
    Passengers AS t1
INNER JOIN Buses AS t2 ON t1.arrival_time <= t2.arrival_time
GROUP BY t1.passenger_id
```



- 有了这张表后，我们只需要根据时间连接公交车表，再分组统计乘客数量即可
- 为了统计所有车辆上乘客的数量，这里我们使用外连接，将公交车表作为驱动表
- 为了排除NULL，这里使用COUNT(列)即可，最终SQL如下

```mysql
SELECT
    t1.bus_id,
    COUNT(t2.passenger_id) AS 'passengers_cnt'
FROM
    Buses AS t1
LEFT JOIN (
		SQL1
) AS t2 ON t1.arrival_time = t2.min_bus_time
GROUP BY t1.bus_id
ORDER BY t1.bus_id
```

<hr>











# Day226

## Tag: CAST, RANK

![Xnip2022-03-07_08-11-18](MySQL Note.assets/Xnip2022-03-07_08-11-18.jpg)



![Xnip2022-03-07_08-12-49](MySQL Note.assets/Xnip2022-03-07_08-12-49.jpg)

题意:

给你一张队伍积分表，一张积分变动表，请你查询出每个队伍在积分变动后排名的变动



思路:

- 很明显，这里需要我们查询出每个队伍变动前后的排名
- 需要注意的是，变动后如果出现同分，则按照队伍名称的字典序排序
- 最简单的方式就是使用窗口函数直接排序，直接获取变动前后的排名，SQL如下

SQL1:

```mysql
SELECT
	t1.team_id,
	name,
	RANK() OVER(
		ORDER BY points DESC, name
		) AS 'rank_1',
	RANK() OVER(
		ORDER BY points + points_change DESC, NAME
		) AS 'rank_2'
FROM
	TeamPoints AS t1
INNER JOIN PointsChange t2 ON t1.team_id = t2.team_id
```



- 最后只需要获取每个队伍变动前后的名称之差即可
- 需要注意的是，窗口函数返回的是一个UNSIGNED类型，所以为了得到负数，我们需要转换一下类型，最终SQL如下

```mysql
SELECT
	team_id,
	`name`,
	CAST(rank_1 AS SIGNED) - CAST(rank_2 AS SIGNED) AS 'rank_diff'
FROM (
	SQL1
	) AS temp
```

<hr>













# Day227

## Tag: ROUND, INNER JOIN

![Xnip2022-03-08_07-39-45](MySQL Note.assets/Xnip2022-03-08_07-39-45.jpg)



![Xnip2022-03-08_07-40-00](MySQL Note.assets/Xnip2022-03-08_07-40-00.jpg)

题意:

给你一张配送表，请你查询出所有用户的第一份订单中是即时订单的百分比(即时订单是指order_date = customer_pref_delivery的订单)





思路:

- 首先，为了限定统计的范围，我们需要先查询出所有用户的第一份订单信息，SQL如下

SQL1:

```mysql
SELECT
    customer_id,
    MIN(order_date) AS 'first_order'
FROM
    Delivery
GROUP BY customer_id
```



- 之后我们只需要通过连接后，计算出即时订单的数量和所有第一份订单的总数后得到百分比即可，最终SQL如下

```mysql
SELECT
    ROUND(100 * SUM(CASE WHEN t1.order_date = t1.customer_pref_delivery_date THEN 1 ELSE 0 END) / COUNT(t2.customer_id), 2) AS 'immediate_percentage'
FROM
    Delivery AS t1
INNER JOIN (
		SQL1
) AS t2 ON t1.customer_id = t2.customer_id AND t1.order_date = t2.first_order
```

<hr>









# Day228

## Tag: EXISTS

![Xnip2022-03-09_10-49-59](MySQL Note.assets/Xnip2022-03-09_10-49-59.jpg)



![Xnip2022-03-09_10-50-51](MySQL Note.assets/Xnip2022-03-09_10-50-51.jpg)

题意:

给你一张保险投保信息表，请你查询出2016年成功投资的金额(2015年有与其金额相同的其他投保记录，且该投保人的位置信息是唯一的)



思路:

- 抽象一下，题目的要求有两个:
- 一是2015有其他金额与其相同的记录，在SQL中体现为t1.TIV_2015 = t2.TIV_2015 AND t1.PID != t2.PID
- 二是不同的经纬度信息，在SQL中体现为t1.LAT = t3.LAT AND t1.LON = t3.LON AND t1.PID != t3.PID
- 将两个规则用AND连接起来即可，最终SQL如下

```mysql
SELECT
	ROUND(SUM(TIV_2016), 2) AS 'TIV_2016'
FROM
	insurance AS t1
WHERE EXISTS (
	SELECT 
		t2.PID
	FROM 
		insurance AS t2 
	WHERE t1.TIV_2015 = t2.TIV_2015 
	AND t1.PID != t2.PID
	) 
AND NOT EXISTS (
	SELECT
		t3.PID
	FROM
		insurance AS t3
	WHERE t1.LAT = t3.LAT 
	AND t1.LON = t3.LON 
	AND t1.PID != t3.PID
	)
```

<hr>















# Day229

## Tag: ROW_NUMBER

![Xnip2022-03-10_07-27-53](MySQL Note.assets/Xnip2022-03-10_07-27-53.jpg)



![Xnip2022-03-10_07-28-46](MySQL Note.assets/Xnip2022-03-10_07-28-46.jpg)

题意:

给你一张记录表，请你将该表进行重构，使得first_col列的值是递增的，而second_col的值是递减的







思路:

- 这里很明显需要将两列分开处理，所以这两列不可能在同一张表中
- 这里我们首先需要获取出每列值在结果所需排列方式下对应的排名，SQL如下

SQL1:

```mysql
SELECT
    first_col,
    ROW_NUMBER() OVER(
        ORDER BY first_col
    ) AS 'first_rank'
FROM
    Data
```



SQL2:

```mysql
SELECT
    second_col,
    ROW_NUMBER() OVER(
        ORDER BY second_col DESC
    ) AS 'second_rank'
FROM
    Data
```



- 注意这里不要使用DENSE_RANK，因为相同值的排名会被跳过，排名是不连续的
- 最后连接两表，且让两列的列值对应的rank相同即可，最终SQL如下

```mysql
SELECT
    t1.first_col,
    t2.second_col
FROM (
		SQL1
) AS t1
INNER JOIN (
		SQL2
) AS t2 ON t1.first_rank = t2.second_rank
```

<hr>













# Day230

## Tag: CTE, CASE, UNION ALL

![Xnip2022-03-11_07-42-36](MySQL Note.assets/Xnip2022-03-11_07-42-36.jpg)



![Xnip2022-03-11_07-43-14](MySQL Note.assets/Xnip2022-03-11_07-43-14.jpg)

题意:

给你一张候选人信息表，请你查询出根据条件能够雇佣最多的工程师的id，要求先雇佣尽可能多的高级工程师



思路:

- 题目乍一看也很迷，我们先考虑算出两类工程师的佣金
- 这里我们需要得出按照金额排序累加后，雇佣的工程师所需的累积费用
- 累积则需要累加，所以这里我们使用SUM() OVER窗口函数，通过按照工程师类型分组，金额升序即可，SQL如下

SQL1:

```mysql
SELECT
    employee_id,
    experience,
    SUM(salary) OVER(
        PARTITION BY experience
        ORDER BY salary
    ) AS 'salary'
FROM
    Candidates
```



- 有了该表后，我们只需要限定experience字段和佣金总额，即可查询出我们能雇佣的最多的高级工程师id，SQL如下

SQL2:

```mysql
SELECT
    employee_id,
    salary
FROM
    SQL1
WHERE experience = 'Senior'
AND salary <= 70000
```



- 最后，我们只需要分别查询出两张表，只不过在查询初级工程师的时候，我们需要先行判断是否雇佣了高级工程师
- 如果有的话，则需要从预算中减去雇佣高级工程师的金额，SQL中体现为:

```mysql
CASE WHEN (SELECT COUNT(*) FROM t2) = 0 
    THEN 0 
    ELSE (SELECT MAX(salary) FROM t2) END + salary <= 70000
```



- 最终SQL如下

```mysql
WITH t1 AS (
    SQL1
),
t2 AS (
    SQL2
)
SELECT
    employee_id
FROM
    t2
UNION ALL
SELECT
    employee_id
FROM
    t1
WHERE experience = 'Junior'
AND CASE WHEN (SELECT COUNT(*) FROM t2) = 0 
    THEN 0 
    ELSE (SELECT MAX(salary) FROM t2) END + salary <= 70000
```











