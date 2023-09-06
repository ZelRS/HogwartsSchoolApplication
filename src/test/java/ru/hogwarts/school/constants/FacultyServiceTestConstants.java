package ru.hogwarts.school.constants;

import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.List;

public class FacultyServiceTestConstants {
    public static final Faculty FACULTY = new Faculty(1, "Гриффиндор", "красный");
    public static final Faculty CREATED_FACULTY = new Faculty(1, "Слизерин", "зеленый");
    public static final long NOT_EXISTING_ID = 2;
    public static final List<Faculty> FACULTIES_LIST = new ArrayList<>(List.of(FACULTY, CREATED_FACULTY));
}
