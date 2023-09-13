package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty create(Faculty faculty);

    Faculty getById(long id);

    Faculty update(Faculty faculty);

    void deleteById(long id);

    Collection<Faculty> getAllByColor(String color);

    Collection<Faculty> getAll();

    Faculty getByNameOrColor(String name, String color);

    Faculty getByStudent(long studentId);
}
