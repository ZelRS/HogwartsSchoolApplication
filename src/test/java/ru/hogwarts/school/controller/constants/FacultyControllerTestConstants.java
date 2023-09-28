package ru.hogwarts.school.controller.constants;

import net.minidev.json.JSONObject;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

public class FacultyControllerTestConstants {
    public static final Long STUDENT_ID = 1L;
    public static final String STUDENT_NAME = "StudentName";
    public static final int STUDENT_AGE = 31;
    public static final Student STUDENT = new Student();
    public static final List<Student> STUDENTS = new ArrayList<>(List.of(STUDENT));
    public static final JSONObject FACULTY_OBJECT = new JSONObject();
    public static final Long FACULTY_ID = 1L;
    public static final String FACULTY_NAME = "FacultyName";

    public static final String FACULTY_OTHER_NAME = "FacultyName1";
    public static final String FACULTY_COLOR = "FacultyColor";
    public static final Faculty FACULTY = new Faculty();
    public static final List<Faculty> FACULTIES = new ArrayList<>(List.of(FACULTY));
}
