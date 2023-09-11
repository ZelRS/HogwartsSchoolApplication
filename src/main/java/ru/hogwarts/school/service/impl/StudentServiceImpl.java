package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.NullNameFieldException;
import ru.hogwarts.school.exception.StudentAgeException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private static long counter = 0;

    public Student create(Student student) {
        studentNameValidator(student);
        studentAgeValidator(student);
        student.setId(++counter);
        referenceNameMaker(student);
        students.put(counter, student);
        return student;
    }

    public Student update(Student student) {
        studentNameValidator(student);
        studentAgeValidator(student);
        referenceNameMaker(student);
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        throw new EntityNotFoundException("Студент не найден");
    }

    public Student delete(long id) {
        if (students.containsKey(id)) {
            return students.remove(id);
        }
        throw new EntityNotFoundException("Студент не найден");
    }

    public Student get(long id) {
        if (students.containsKey(id)) {
            return students.get(id);
        }
        throw new EntityNotFoundException("Студент не найден");
    }

    public List<Student> getByAge(int age) {
        List<Student> students = this.students.values()
                .stream()
                .filter(e -> e.getAge() == age)
                .sorted(Comparator.comparingLong(Student::getId))
                .collect(Collectors.toList());
        if (!students.isEmpty()) {
            return students;
        }
        throw new EntityNotFoundException("Студенты не найдены");
    }

    public List<Student> getAll() {
        List<Student> students = new ArrayList<>(this.students.values());
        if (!students.isEmpty()) {
            return students;
        }
        throw new EntityNotFoundException("Студенты не найдены");
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
