package ru.hogwarts.school.controller.webmvctest.constants;

import net.minidev.json.JSONObject;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentControllerWebMvcTestConstants {
    public static final Long STUDENT_ID = 1L;
    public static final String STUDENT_NAME = "Roman";

    public static final String STUDENT_OTHER_NAME = "Ivan";
    public static final int STUDENT_AGE = 31;
    public static final Student STUDENT = new Student();
    public static final List<Student> STUDENTS = new ArrayList<>(List.of(STUDENT));
    public static final JSONObject STUDENT_OBJECT = new JSONObject();
    public static final Long FACULTY_ID = 1L;
    public static final String FACULTY_NAME = "Qqqq";
    public static final String FACULTY_COLOR = "QQQQ";
    public static final Faculty FACULTY = new Faculty();
}
