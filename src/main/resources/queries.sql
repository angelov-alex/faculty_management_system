// QUERY1: Student report with sum of credits
SELECT s.NAME AS "STUDENT NAME", SUM(cr.CREDIT_HOURS)
FROM SAP.STUDENT s
         JOIN SAP.STUDENT_COURSE j
              ON s.UUID = j.STUDENT_UUID
         JOIN SAP.COURSE c
              ON j.COURSE_UUID = c.UUID
         JOIN SAP.CREDIT cr
              ON cr.UUID = c.CREDIT_UUID
GROUP BY s.NAME;

//QUERY2: COURSES With more than 1 Student sorted from top to bottom
SELECT c.NAME AS "COURSE NAME", COUNT(j.STUDENT_UUID) AS COUNT_STUDENTS
FROM SAP.STUDENT s
         JOIN SAP.STUDENT_COURSE j
              ON s.UUID = j.STUDENT_UUID
         JOIN SAP.COURSE c
              ON j.COURSE_UUID = c.UUID
GROUP BY c.NAME
HAVING COUNT(j.STUDENT_UUID) > 1
ORDER BY COUNT(j.STUDENT_UUID) DESC;

//QUERY3: TEACHERS with more than 2 courses with at least 5 enrolled students
SELECT TEACHER_NAME
FROM (SELECT t.NAME AS TEACHER_NAME
      FROM SAP.TEACHER t
               JOIN SAP.COURSE c
                    ON t.UUID = c.TEACHER_UUID
      GROUP BY t.NAME
      HAVING COUNT(c.UUID) > 1) q1 -- minimum number of courses leaded by the teacher
         JOIN (SELECT t.NAME, COUNT(j.STUDENT_UUID) AS COUNT_STUDENTS
               FROM SAP.TEACHER t
                        JOIN SAP.COURSE c
                             ON t.UUID = c.TEACHER_UUID
                        JOIN SAP.STUDENT_COURSE j
                             ON c.UUID = j.COURSE_UUID
               GROUP BY t.NAME
               HAVING COUNT(j.STUDENT_UUID) > 4 -- minimum total number of students of teacher
               ORDER BY COUNT(j.STUDENT_UUID) DESC) q2
              ON (q1.TEACHER_NAME = q2.NAME);