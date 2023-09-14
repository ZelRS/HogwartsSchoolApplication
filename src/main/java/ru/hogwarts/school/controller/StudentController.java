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
@RequestMapping("students")
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
    public ResponseEntity<Student> deleteById(@PathVariable("id") long id) {
        studentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Получить студента по id")
    public ResponseEntity<Student> getById(@PathVariable("id") long id) {
        Student student = studentService.getById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    @Operation(summary = "Получить всех студентов по возрасту. Если возраст не указан, получить всех студентов")
    public ResponseEntity<Collection<Student>> getAllOrAllByAge(@RequestParam(required = false) Integer age) {
        if (age != null) {
            return ResponseEntity.ok(studentService.getAllByAge(age));
        }
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("age_range")
    @Operation(summary = "Получить всех студентом по возрасту от и до указанного значения")
    public ResponseEntity<Collection<Student>> getAllByAgeBetween(@RequestParam int min, @RequestParam int max) {
        Collection<Student> students = studentService.getAllByAgeBetween(min, max);
        return ResponseEntity.ok(students);
    }

    @GetMapping("{id}/faculty")
    @Operation(summary = "Получить факультет по id студента")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable("id") long studentId) {
        Faculty faculty = studentService.getFacultyByStudentId(studentId);
        return ResponseEntity.ok(faculty);
    }

}
