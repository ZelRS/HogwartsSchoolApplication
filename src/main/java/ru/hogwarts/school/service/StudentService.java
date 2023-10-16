package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student create(Student student); //создать студента

    Student getById(Long id); // получить студента по id

    Student update(Student student); //обновить существующего студента

    void deleteById(Long id); //удалить студента по id

    Collection<Student> getAll(); //получить список всех студентов

    Collection<Student> getAllByAgeBetween(int max, int min); //получить стдентов с возрастом меньше max и больше min

    Integer getCountOfStudents(); //получить количество студентов

    Integer getAverageAgeOfStudents(); //получить средний возраст студентов

    Collection<Student> getFiveLastStudents(); //получить 5 последних студентов

    Collection<String> getSortedUpperCaseNamesStartsFromA(); //получить список имен, начинающихся на "A" в алфавитном
                                                             // порядке в верхнем регистре

    Double getAverageAgeOfStudentsWithStreamAPI(); //получить средний возраст студентов, используя Stream API
}
