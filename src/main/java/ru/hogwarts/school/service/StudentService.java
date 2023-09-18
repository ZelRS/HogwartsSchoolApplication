package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student create(Student student);

    Student getById(long id);

    Student update(Student student);

    void deleteById(long id);

    Collection<Student> getAll();

    Collection<Student> getAllByAgeBetween(int max, int min);
}
