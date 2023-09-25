package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty create(Faculty faculty);

    Faculty getById(Long id);

    Faculty update(Faculty faculty);

    void deleteById(Long id);

    Collection<Faculty> getAll();

    Collection<Faculty> getAllByNameOrColor(String name, String color);
}
