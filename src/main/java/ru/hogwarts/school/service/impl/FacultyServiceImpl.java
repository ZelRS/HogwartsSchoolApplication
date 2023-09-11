package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.NullColorFieldException;
import ru.hogwarts.school.exception.NullNameFieldException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private static long counter = 0;

    public Faculty create(Faculty faculty) {
        facultyNameValidator(faculty);
        facultyColorValidator(faculty);
        referenceNameMaker(faculty);
        referenceColorMaker(faculty);
        faculty.setId(++counter);
        faculties.put(counter, faculty);
        return faculty;
    }

    public Faculty update(Faculty faculty) {
        facultyNameValidator(faculty);
        facultyColorValidator(faculty);
        referenceNameMaker(faculty);
        referenceColorMaker(faculty);
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        throw new EntityNotFoundException("Факультет не найден");
    }

    public Faculty delete(long id) {
        if (faculties.containsKey(id)) {
            return faculties.remove(id);
        }
        throw new EntityNotFoundException("Факультет не найден");
    }

    public Faculty get(long id) {
        if (faculties.containsKey(id)) {
            return faculties.get(id);
        }
        throw new EntityNotFoundException("Факультет не найден");
    }

    public List<Faculty> getByColor(String color) {
        List<Faculty> faculties = this.faculties.values()
                .stream()
                .filter(e -> e.getColor().equals(color.toUpperCase()))
                .sorted(Comparator.comparingLong(Faculty::getId))
                .collect(Collectors.toList());
        if (!faculties.isEmpty()) {
            return faculties;
        }
        throw new EntityNotFoundException("Факультеты не найдены");
    }

    public List<Faculty> getAll() {
        List<Faculty> faculties = new ArrayList<>(this.faculties.values());
        if (!faculties.isEmpty()) {
            return faculties;
        }
        throw new EntityNotFoundException("Факультеты не найдены");
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
