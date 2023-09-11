package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculty")
@Tag(name= "API для работы с факультетами")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    @Operation(summary = "Создание факультета")
    public ResponseEntity<Faculty> create(@RequestBody Faculty facultyRs) {
        Faculty faculty = facultyService.create(facultyRs);
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    @Operation(summary = "Изменение факультета")
    public ResponseEntity<Faculty> edit(@RequestBody Faculty facultyRs) {
        Faculty faculty = facultyService.update(facultyRs);
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета")
    public ResponseEntity<Faculty> delete(@PathVariable("id") long id) {
        Faculty faculty = facultyService.delete(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получить факультет по id")
    public ResponseEntity<Faculty> get(@PathVariable("id") long id) {
        Faculty faculty = facultyService.get(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("color")
    @Operation(summary = "Получить все факультеты по указанному цвету")
    public ResponseEntity<List<Faculty>> getByColor(@RequestParam String color) {
        List<Faculty> faculties = facultyService.getByColor(color);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("all")
    @Operation(summary = "Получить все факультеты")
    public ResponseEntity<List<Faculty>> getAll() {
        List<Faculty> faculties = facultyService.getAll();
        return ResponseEntity.ok(faculties);
    }
}
