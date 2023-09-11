package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty create(Faculty faculty);

    Faculty get(long id);

    Faculty update(Faculty faculty);

    void delete(long id);

    List<Faculty> getByColor(String color);

    List<Faculty> getAll();
}
