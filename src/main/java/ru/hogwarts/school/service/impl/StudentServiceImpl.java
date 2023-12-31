package ru.hogwarts.school.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.NullNameFieldException;
import ru.hogwarts.school.exception.StudentAgeException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // создаем студента
    @Override
    public Student create(Student student) {
        log.info("Was invoked method for create student");
        nameValidator(student);
        ageValidator(student.getAge());
        referenceNameMaker(student);
        log.debug("The student was created successfully");
        return studentRepository.save(student);
    }

    // обновляем существующего студента
    @Override
    public Student update(Student student) {
        log.info("Was invoked method for update student");
        nameValidator(student);
        ageValidator(student.getAge());
        referenceNameMaker(student);
        Optional<Student> UpdatedStudent = studentRepository.findById(student.getId());
        if (UpdatedStudent.isEmpty()) {
            log.error("There is not student with ID = {}", student.getId());
            throw new EntityNotFoundException("Студент не найден");
        }
        log.debug("The student was updated successfully");
        return studentRepository.save(student);
    }

    // удаляем студента по id
    @Override
    public void deleteById(Long id) {
        log.info("Was invoked method for delete student with ID = {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            log.error("There is not student with ID = {}", id);
            throw new EntityNotFoundException("Студент не найден");
        }
        studentRepository.deleteById(id);
        log.debug("The student was removed successfully");
    }

    // получаем студента по id
    @Override
    public Student getById(Long id) {
        log.info("Was invoked method for get student with ID = {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            log.error("There is not student with ID = {}", id);
            throw new EntityNotFoundException("Студент не найден");
        }
        log.debug("The student was received successfully");
        return student.get();
    }

    // получаем список всех студентов
    @Override
    public Collection<Student> getAll() {
        log.info("Was invoked method for get all students");
        log.debug("List of students was received successfully");
        return studentRepository.findAll();
    }

    // получаем стдентов с возрастом меньше max и больше min
    @Override
    public Collection<Student> getAllByAgeBetween(int min, int max) {
        log.info("Was invoked method for get all students with age between {} and {}", min, max);
        ageValidator(min);
        ageValidator(max);
        if (max < min) {
            log.error("Min age can't be less than max age");
            throw new StudentAgeException("Задан некорректный диапазон");
        }
        log.debug("List of students was received successfully");
        return studentRepository.findByAgeBetween(min, max);
    }

    // получаем количество студентов
    @Override
    public Integer getCountOfStudents() {
        log.info("Was invoked method for get count of students");
        log.debug("Count of students was received successfully");
        return studentRepository.getCountOfStudents();
    }

    // получаем средний возраст всех студентов
    @Override
    public Integer getAverageAgeOfStudents() {
        log.info("Was invoked method for get average age of students");
        log.debug("Average age of students was received successfully");
        return studentRepository.getAverageAgeOfStudents();
    }

    // получаем 5 последних студентов
    @Override
    public Collection<Student> getFiveLastStudents() {
        log.info("Was invoked method for get five last students");
        log.debug("List of five last students was received successfully");
        return studentRepository.getFiveLastStudents();
    }

    // получаем список имен, начинающихся на "A" в алфавитном порядке в верхнем регистре
    @Override
    public Collection<String> getSortedUpperCaseNamesStartsFromA() {
        log.info("Was invoked method for get sorted uppercase names starts from A");
        return studentRepository.findAll().stream()
                .map(student -> student.getName().toUpperCase())
                .filter(name -> name.startsWith("А"))
                .sorted()
                .collect(Collectors.toList());
    }

    // получаем средний возраст студентов, используя Stream API
    @Override
    public Double getAverageAgeOfStudentsWithStreamAPI() {
        log.info("Was invoked method for get average age of students with StreamAPI");
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0);
    }

    // получаем список студентов в консоль, используя дополнительные потоки
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

    // получаем список студентов в консоль, используя дополнительные СИНХРОНИЗИРОВАННЫЕ потоки
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

    // синхронизированный метод для вывода в консоль имен студентов в многопоточности
    private synchronized void printName(String name) {
        System.out.println(name);
    }

    // проверяем корректность переданного возраста
    private static void ageValidator(int age) {
        if (age <= 0 || age > 100) {
            log.error("Age can't be less than {} and more than {}", 0, 100);
            throw new StudentAgeException("Некорректный возраст");
        }
    }

    // проверяем, что имя передано
    private static void nameValidator(Student student) {
        if (isBlank(student.getName())) {
            log.error("Name can't be empty");
            throw new NullNameFieldException("Вы не задали имя");
        }
    }

    // формируем корректное предатвление имени
    private static void referenceNameMaker(Student student) {
        student.setName(capitalize(student.getName().toLowerCase()));
    }
}
