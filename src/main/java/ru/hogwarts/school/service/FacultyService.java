package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty create(Faculty faculty);

    Faculty getById(long id);

    Faculty update(Faculty faculty);

    void deleteById(long id);

    Collection<Faculty> getAll();

    Collection<Faculty> getAllByNameOrColor(String name, String color);
}
