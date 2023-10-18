package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.NullNameFieldException;
import ru.hogwarts.school.exception.StudentAgeException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        LOGGER.info("Was invoked method for create student");
        nameValidator(student);
        ageValidator(student.getAge());
        referenceNameMaker(student);
        LOGGER.debug("The student was created successfully");
        return studentRepository.save(student);
    }

    @Override
    public Student update(Student student) {
        LOGGER.info("Was invoked method for update student");
        nameValidator(student);
        ageValidator(student.getAge());
        referenceNameMaker(student);
        Optional<Student> UpdatedStudent = studentRepository.findById(student.getId());
        if (UpdatedStudent.isEmpty()) {
            LOGGER.error("There is not student with ID = {}", student.getId());
            throw new EntityNotFoundException("Студент не найден");
        }
        LOGGER.debug("The student was updated successfully");
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Was invoked method for delete student with ID = {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            LOGGER.error("There is not student with ID = {}", id);
            throw new EntityNotFoundException("Студент не найден");
        }
        studentRepository.deleteById(id);
        LOGGER.debug("The student was removed successfully");
    }

    @Override
    public Student getById(Long id) {
        LOGGER.info("Was invoked method for get student with ID = {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            LOGGER.error("There is not student with ID = {}", id);
            throw new EntityNotFoundException("Студент не найден");
        }
        LOGGER.debug("The student was received successfully");
        return student.get();
    }

    @Override
    public Collection<Student> getAll() {
        LOGGER.info("Was invoked method for get all students");
        LOGGER.debug("List of students was received successfully");
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getAllByAgeBetween(int min, int max) {
        LOGGER.info("Was invoked method for get all students with age between {} and {}", min, max);
        ageValidator(min);
        ageValidator(max);
        if (max < min) {
            LOGGER.error("Min age can't be less than max age");
            throw new StudentAgeException("Задан некорректный диапазон");
        }
        LOGGER.debug("List of students was received successfully");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Integer getCountOfStudents() {
        LOGGER.info("Was invoked method for get count of students");
        LOGGER.debug("Count of students was received successfully");
        return studentRepository.getCountOfStudents();
    }

    @Override
    public Integer getAverageAgeOfStudents() {
        LOGGER.info("Was invoked method for get average age of students");
        LOGGER.debug("Average age of students was received successfully");
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public Collection<Student> getFiveLastStudents() {
        LOGGER.info("Was invoked method for get five last students");
        LOGGER.debug("List of five last students was received successfully");
        return studentRepository.getFiveLastStudents();
    }

    @Override
    public Collection<String> getSortedUpperCaseNamesStartsFromA() {
        LOGGER.info("Was invoked method for get sorted uppercase names starts from A");
        return studentRepository.findAll().stream()
                .map(student -> student.getName().toUpperCase())
                .filter(name -> name.startsWith("А"))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeOfStudentsWithStreamAPI() {
        LOGGER.info("Was invoked method for get average age of students with StreamAPI");
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0);
    }

    @Override
    public void getStudentsWithThreadsInConsole() {
        List<String> studentsNames = studentRepository.findAll().stream()
                .map(Student::getName)
                .collect(Collectors.toList());

        System.out.println(studentsNames.get(0));
        System.out.println(studentsNames.get(1));

        new Thread(() -> {
            System.out.println(studentsNames.get(2));
            System.out.println(studentsNames.get(3));
        }).start();

        new Thread(() -> {
            System.out.println(studentsNames.get(4));
            System.out.println(studentsNames.get(5));
        }).start();
    }

    @Override
    public void getStudentsWithSynchronizedThreadsInConsole() {
        List<String> studentsNames = studentRepository.findAll().stream()
                .map(Student::getName)
                .collect(Collectors.toList());

        printName(studentsNames.get(0));
        printName(studentsNames.get(1));

        new Thread(() -> {
            printName(studentsNames.get(2));
            printName(studentsNames.get(3));
        }).start();

        new Thread(() -> {
            printName(studentsNames.get(4));
            printName(studentsNames.get(5));
        }).start();
    }

    private synchronized void printName(String name) {
        System.out.println(name);
    }

    private static void ageValidator(int age) {
        if (age <= 0 || age > 100) {
            LOGGER.error("Age can't be less than {} and more than {}", 0, 100);
            throw new StudentAgeException("Некорректный возраст");
        }
    }

    private static void referenceNameMaker(Student student) {
        student.setName(capitalize(student.getName().toLowerCase()));
    }

    private static void nameValidator(Student student) {
        if (isBlank(student.getName())) {
            LOGGER.error("Name can't be empty");
            throw new NullNameFieldException("Вы не задали имя");
        }
    }
}
