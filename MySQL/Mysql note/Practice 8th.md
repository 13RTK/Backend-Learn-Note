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







