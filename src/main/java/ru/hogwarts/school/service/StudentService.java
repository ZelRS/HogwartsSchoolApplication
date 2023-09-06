package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long counter = 0;

    //    create
    public Student createStudent(Student student) {
        student.setId(++counter);
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
}
