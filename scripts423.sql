SELECT студент.name, студент.age, факультет.name
FROM студент
         FULL JOIN факультет ON студент.faculty_id = факультет.id;

SELECT студент.name AS имя_студента, студент.age AS возраст_студента
FROM аватар
         INNER JOIN студент ON аватар.student_id = студент.id;