package ru.hogwarts.school.constants;

import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceTestConstants {
    public static final Student STUDENT = new Student(1, "Roman", 31);
    public static final Student CREATED_STUDENT = new Student(1, "Ivan", 24);
    public static final long NOT_EXISTING_ID = 2;
    public static final List<Student> STUDENTS_LIST = new ArrayList<>(List.of(STUDENT, CREATED_STUDENT));
}
