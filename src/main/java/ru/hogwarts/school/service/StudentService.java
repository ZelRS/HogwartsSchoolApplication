package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student create(Student student);

    Student get(long id);

    Student update(Student student);

    void delete(long id);

    Collection<Student> getByAge(int age);

    Collection<Student> getAll();
}
