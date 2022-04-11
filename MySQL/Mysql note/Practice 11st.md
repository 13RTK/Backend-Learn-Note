# Day261

## Tag: EXISTS, LIMIT

![Xnip2022-04-11_06-54-57](MySQL Note.assets/Xnip2022-04-11_06-54-57.jpg)



![Xnip2022-04-11_06-55-14](MySQL Note.assets/Xnip2022-04-11_06-55-14.jpg)

<hr>





![Xnip2022-04-11_07-04-37](MySQL Note.assets/Xnip2022-04-11_07-04-37.jpg)



![Xnip2022-04-11_07-04-25](MySQL Note.assets/Xnip2022-04-11_07-04-25.jpg)

<hr>



![Xnip2022-04-11_07-14-55](MySQL Note.assets/Xnip2022-04-11_07-14-55.jpg)



![Xnip2022-04-11_07-15-00](MySQL Note.assets/Xnip2022-04-11_07-15-00.jpg)

题意:

给你一张成绩表，请你查询出其中每个学生的最高成绩和对应的科目，如果有多个科目的成绩相同，则取course_id小的那个



思路:

- 因为课程id可能会有多个，所以我们需要先查询出每个学生对应的最高分数，SQL如下

SQL1:

```mysql
SELECT
	student_id,
	MAX(grade) AS 'max_grade'
FROM
	Enrollments
GROUP BY student_id
```



- 之后通过id和分数进行匹配，并取最小的course_id即可，最终SQL如下

```mysql
SELECT
    student_id,
    MIN(course_id) AS 'course_id',
    grade
FROM
    Enrollments AS t1
WHERE (student_id, grade) IN (
    SQL1
)
GROUP BY student_id, grade
ORDER BY student_id
```

<hr>



![Xnip2022-04-11_07-33-41](MySQL Note.assets/Xnip2022-04-11_07-33-41.jpg)



![Xnip2022-04-11_07-33-27](MySQL Note.assets/Xnip2022-04-11_07-33-27.jpg)

















