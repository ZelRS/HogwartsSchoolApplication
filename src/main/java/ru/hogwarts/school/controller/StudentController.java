package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
@Tag(name = "API для работы со студентами")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @Operation(summary = "Создание студента")
    public ResponseEntity<Student> create(@RequestBody Student studentRs) {
        Student student = studentService.create(studentRs);
        return ResponseEntity.ok(student);
    }

    @PutMapping
    @Operation(summary = "Изменение студента")
    public ResponseEntity<Student> update(@RequestBody Student studentRs) {
        Student student = studentService.update(studentRs);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента по id")
    public ResponseEntity<Student> deleteById(@PathVariable("id") Long id) {
        studentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Получить студента по id")
    public ResponseEntity<Student> getById(@PathVariable("id") Long id) {
        Student student = studentService.getById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("age")
    @Operation(summary = "Получить всех студентом по возрасту от и до указанного значения")
    public ResponseEntity<Collection<Student>> getAllByAgeBetween(@RequestParam int min, @RequestParam int max) {
        Collection<Student> students = studentService.getAllByAgeBetween(min, max);
        return ResponseEntity.ok(students);
    }

    @GetMapping("faculty/{id}")
    @Operation(summary = "Получить факультет по id студента")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable("id") Long studentId) {
        Faculty faculty = studentService.getById(studentId).getFaculty();
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("all")
    @Operation(summary = "Получить всех студентов")
    public ResponseEntity<Collection<Student>> getAll() {
        Collection<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("count")
    @Operation(summary = "Получить количество студентов в школе")
    public ResponseEntity<Integer> getCountOfStudents() {
        return ResponseEntity.ok(studentService.getCountOfStudents());
    }

    @GetMapping("age/average")
    @Operation(summary = "Получить средний возраст студентов")
    public ResponseEntity<Integer> getAverageAgeOfStudents() {
        return ResponseEntity.ok(studentService.getAverageAgeOfStudents());
    }

    @GetMapping("last-five")
    @Operation(summary = "Получить 5 последних студентов")
    public ResponseEntity<Collection<Student>> getFiveLastStudents() {
        return ResponseEntity.ok(studentService.getFiveLastStudents());
    }

}
