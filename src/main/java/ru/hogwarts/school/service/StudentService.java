package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student create(Student student);

    Student get(long id);

    Student update(Student student);

    void delete(long id);

    List<Student> getByAge(int age);

    List<Student> getAll();
}
