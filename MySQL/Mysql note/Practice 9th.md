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











