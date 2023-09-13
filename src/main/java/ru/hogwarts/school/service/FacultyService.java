package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty create(Faculty faculty);

    Faculty get(long id);

    Faculty update(Faculty faculty);

    void delete(long id);

    Collection<Faculty> getByColor(String color);

    Collection<Faculty> getAll();
}
