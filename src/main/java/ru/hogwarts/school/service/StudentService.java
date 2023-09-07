package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NullNameFieldException;
import ru.hogwarts.school.exception.StudentAgeException;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long counter = 0;

    //    create
    public Student createStudent(Student student) {
        studentNameValidator(student);
        studentAgeValidator(student);
        student.setId(++counter);
        referenceNameMaker(student);
        students.put(counter, student);
        return student;
    }

    //    read
    public Student getStudent(long id) {
        if (students.containsKey(id)) {
            return students.get(id);
        }
        return null;
    }

    //    update
    public Student updateStudent(Student student) {
        studentNameValidator(student);
        studentAgeValidator(student);
        referenceNameMaker(student);
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    //    delete
    public Student deleteStudent(long id) {
        if (students.containsKey(id)) {
            return students.remove(id);
        }
        return null;
    }

    public List<Student> getAllStudentsByAge(int age) {
        List<Student> studentsByAge = students.values()
                .stream()
                .filter(e -> e.getAge() == age)
                .sorted(Comparator.comparingLong(Student::getId))
                .collect(Collectors.toList());
        if (!studentsByAge.isEmpty()) {
            return studentsByAge;
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    private static void studentAgeValidator(Student student) {
        if (student.getAge() <= 0 || student.getAge() > 100) {
            throw new StudentAgeException("Некорректный возраст");
        }
    }

    private static void referenceNameMaker(Student student) {
        student.setName(capitalize(student.getName().toLowerCase()));
    }

    private static void studentNameValidator(Student student) {
        if (isBlank(student.getName())) {
            throw new NullNameFieldException("Вы не задали имя");
        }
    }
}
