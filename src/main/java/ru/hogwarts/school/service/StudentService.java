package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    // создаем студента
    Student create(Student student);

    // получаем студента по id
    Student getById(Long id);

    // обновляем существующего студента
    Student update(Student student);

    // удаляем студента по id
    void deleteById(Long id);

    // получаем список всех студентов
    Collection<Student> getAll();

    // получаем стдентов с возрастом меньше max и больше min
    Collection<Student> getAllByAgeBetween(int max, int min);

    // получаем количество студентов
    Integer getCountOfStudents();

    // получаем средний возраст всех студентов
    Integer getAverageAgeOfStudents();

    // получаем 5 последних студентов
    Collection<Student> getFiveLastStudents();

    // получаем список имен, начинающихся на "A" в алфавитном порядке в верхнем регистре
    Collection<String> getSortedUpperCaseNamesStartsFromA();

    // получаем средний возраст студентов, используя Stream API
    Double getAverageAgeOfStudentsWithStreamAPI();

    // получаем список студентов в консоль, используя дополнительные потоки
    void getStudentsWithThreadsInConsole();

    // получаем список студентов в консоль, используя дополнительные СИНХРОНИЗИРОВАННЫЕ потоки
    void getStudentsWithSynchronizedThreadsInConsole();
}
