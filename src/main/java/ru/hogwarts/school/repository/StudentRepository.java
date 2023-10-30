package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // находим всех студентов с возрастом в указанном диапазоне
    Collection<Student> findByAgeBetween(int min, int max);

    // получаем количество всех студентов
    @Query(value = "SELECT count(*) FROM студент", nativeQuery = true)
    Integer getCountOfStudents();

    // получаем средний возраст всех студентов
    @Query(value = "SELECT avg(age) FROM студент", nativeQuery = true)
    Integer getAverageAgeOfStudents();

    // получаем пять последних студентов
    @Query(value = "SELECT * FROM студент ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getFiveLastStudents();
}
