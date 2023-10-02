package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.NullNameFieldException;
import ru.hogwarts.school.exception.StudentAgeException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        nameValidator(student);
        ageValidator(student.getAge());
        referenceNameMaker(student);
        return studentRepository.save(student);
    }

    @Override
    public Student update(Student student) {
        nameValidator(student);
        ageValidator(student.getAge());
        referenceNameMaker(student);
        Optional<Student> UpdatedStudent = studentRepository.findById(student.getId());
        if (UpdatedStudent.isEmpty()) {
            throw new EntityNotFoundException("Студент не найден");
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new EntityNotFoundException("Студент не найден");
        }
        studentRepository.deleteById(id);
    }

    @Override
    public Student getById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new EntityNotFoundException("Студент не найден");
        }
        return student.get();
    }

    @Override
    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getAllByAgeBetween(int min, int max) {
        ageValidator(min);
        ageValidator(max);
        if (max < min) {
            throw new StudentAgeException("Задан некорректный диапазон");
        }
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Integer getCountOfStudents() {
        return studentRepository.getCountOfStudents();
    }

    @Override
    public Integer getAverageAgeOfStudents() {
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public Collection<Student> getFiveLastStudents() {
        return studentRepository.getFiveLastStudents();
    }

    private static void ageValidator(int age) {
        if (age <= 0 || age > 100) {
            throw new StudentAgeException("Некорректный возраст");
        }
    }

    private static void referenceNameMaker(Student student) {
        student.setName(capitalize(student.getName().toLowerCase()));
    }

    private static void nameValidator(Student student) {
        if (isBlank(student.getName())) {
            throw new NullNameFieldException("Вы не задали имя");
        }
    }
}
