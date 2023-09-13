package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculties")
@Tag(name = "API для работы с факультетами")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("faculty")
    @Operation(summary = "Создание факультета")
    public ResponseEntity<Faculty> create(@RequestBody Faculty facultyRs) {
        Faculty faculty = facultyService.create(facultyRs);
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("faculty")
    @Operation(summary = "Изменение факультета")
    public ResponseEntity<Faculty> edit(@RequestBody Faculty facultyRs) {
        Faculty faculty = facultyService.update(facultyRs);
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("faculty/{id}")
    @Operation(summary = "Удаление факультета")
    public ResponseEntity<Faculty> deleteById(@PathVariable("id") long id) {
        facultyService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("faculty/{id}")
    @Operation(summary = "Получить факультет по id")
    public ResponseEntity<Faculty> getById(@PathVariable("id") long id) {
        Faculty faculty = facultyService.getById(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    @Operation(summary = "Получить все факультеты по указанному цвету. Если цвет не указан - получить все факультеты")
    public ResponseEntity<Collection<Faculty>> getAllOrAllByColor(@RequestParam(required = false) String color) {
        if (color != null) {
            return ResponseEntity.ok(facultyService.getAllByColor(color));
        }
        return ResponseEntity.ok(facultyService.getAll());
    }

    @GetMapping("faculty/nameOrColor")
    @Operation(summary = "Получить факультет по названию или цвету")
    public ResponseEntity<Faculty> getByNameOrColor(@RequestParam(required = false) String name,
                                                    @RequestParam(required = false) String color) {
        Faculty faculty = facultyService.getByNameOrColor(name, color);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("faculty/student{studentId}")
    @Operation(summary = "Получить факультет по id студента")
    public ResponseEntity<Faculty> getByStudentId(@PathVariable("studentId") long studentId) {
        Faculty faculty = facultyService.getByStudent(studentId);
        return ResponseEntity.ok(faculty);

    }
}
