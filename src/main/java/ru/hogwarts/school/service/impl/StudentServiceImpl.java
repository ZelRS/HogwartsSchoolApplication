package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.NullNameFieldException;
import ru.hogwarts.school.exception.StudentAgeException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class StudentServiceImpl implements StudentService {
   private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        studentNameValidator(student);
        studentAgeValidator(student);
        referenceNameMaker(student);
        return studentRepository.save(student);
    }

    public Student update(Student student) {
        studentNameValidator(student);
        studentAgeValidator(student);
        referenceNameMaker(student);
        Optional<Student> UpdatedStudent = studentRepository.findById(student.getId());
        if (UpdatedStudent.isEmpty()) {
            throw new EntityNotFoundException("Студент не найден");
        }
        return studentRepository.save(student);
    }

    public void delete(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new EntityNotFoundException("Студент не найден");
        }
        studentRepository.deleteById(id);
    }

    public Student get(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new EntityNotFoundException("Студент не найден");
        }
        return student.get();
    }

    public List<Student> getByAge(int age) {
        List<Student> students = studentRepository.getByAge(age);
        if (students.isEmpty()) {
            throw new EntityNotFoundException("Студенты не найдены");
        }
        return students;
    }

    public List<Student> getAll() {
        List<Student> students = studentRepository.findAll();
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
