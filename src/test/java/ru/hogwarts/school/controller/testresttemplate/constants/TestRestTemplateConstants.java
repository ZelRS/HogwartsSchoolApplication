package ru.hogwarts.school.controller.testresttemplate.constants;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

public class TestRestTemplateConstants {
    public static final Long STUDENT_ID = 1L;
    public static final String STUDENT_NAME = "Studentname";
    public static final String STUDENT_OTHER_NAME = "Otherstudentname";
    public static final int STUDENT_AGE = 30;
    public static final Long FACULTY_ID = 1L;
    public static final String FACULTY_NAME = "Facultyname";
    public static final String FACULTY_OTHER_NAME = "Otherfacultyname";
    public static final String FACULTY_COLOR = "FACULTYCOLOR";
    public static final Student STUDENT = new Student(
            STUDENT_ID,
            STUDENT_NAME,
            STUDENT_AGE
    );
    public static final Faculty FACULTY = new Faculty(
            FACULTY_ID,
            FACULTY_NAME,
            FACULTY_COLOR
    );
}
