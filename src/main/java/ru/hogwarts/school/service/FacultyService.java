package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NullColorFieldException;
import ru.hogwarts.school.exception.NullNameFieldException;
import ru.hogwarts.school.model.Faculty;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long counter = 0;

    //    create
    public Faculty createFaculty(Faculty faculty) {
        facultyNameValidator(faculty);
        facultyColorValidator(faculty);
        referenceNameMaker(faculty);
        referenceColorMaker(faculty);
        faculty.setId(++counter);
        faculties.put(counter, faculty);
        return faculty;
    }

    //    read
    public Faculty getFaculty(long id) {
        if (faculties.containsKey(id)) {
            return faculties.get(id);
        }
        return null;
    }

    //    update
    public Faculty updateFaculty(Faculty faculty) {
        facultyNameValidator(faculty);
        facultyColorValidator(faculty);
        referenceNameMaker(faculty);
        referenceColorMaker(faculty);
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    //    delete
    public Faculty deleteFaculty(long id) {
        if (faculties.containsKey(id)) {
            return faculties.remove(id);
        }
        return null;
    }

    public List<Faculty> getAllFacultiesByColor(String color) {
        List<Faculty> studentsByAge = faculties.values()
                .stream()
                .filter(e -> e.getColor().equals(color))
                .sorted(Comparator.comparingLong(Faculty::getId))
                .collect(Collectors.toList());
        if (!studentsByAge.isEmpty()) {
            return studentsByAge;
        }
        return null;
    }

    public List<Faculty> getAllFaculties() {
        return new ArrayList<>(faculties.values());
    }

    private static void referenceNameMaker(Faculty faculty) {
        faculty.setName(capitalize(faculty.getName().toLowerCase()));
    }

    private static void referenceColorMaker(Faculty faculty) {
        faculty.setColor(faculty.getColor().toUpperCase());
    }

    private static void facultyNameValidator(Faculty faculty) {
        if (isBlank(faculty.getName())) {
            throw new NullNameFieldException("Вы не задали имя");
        }
    }

    private static void facultyColorValidator(Faculty faculty) {
        if (isBlank(faculty.getColor())) {
            throw new NullColorFieldException("Вы не задали цвет");
        }
    }
}
