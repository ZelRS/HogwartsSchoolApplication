package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
@Tag(name= "API для работы со студентами")
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
    @Operation(summary = "Удаление студента")
    public ResponseEntity<Student> delete(@PathVariable("id") long id) {
        Student student = studentService.delete(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получить студента по id")
    public ResponseEntity<Student> getAll(@PathVariable("id") long id) {
        Student student = studentService.get(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("age")
    @Operation(summary = "Получить всех студентов по указанному возрасту")
    public ResponseEntity<List<Student>> getStudentsByAge(@RequestParam int age) {
        List<Student> students = studentService.getByAge(age);
        return ResponseEntity.ok(students);
    }

    @GetMapping("all")
    @Operation(summary = "Получить всех студентов")
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }
}
