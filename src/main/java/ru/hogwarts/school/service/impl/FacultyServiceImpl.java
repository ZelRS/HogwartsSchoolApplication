package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.NullColorFieldException;
import ru.hogwarts.school.exception.NullNameFieldException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        facultyNameValidator(faculty);
        facultyColorValidator(faculty);
        referenceNameMaker(faculty);
        referenceColorMaker(faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty update(Faculty faculty) {
        facultyNameValidator(faculty);
        facultyColorValidator(faculty);
        referenceNameMaker(faculty);
        referenceColorMaker(faculty);
        Optional<Faculty> UpdatedFaculty = facultyRepository.findById(faculty.getId());
        if (UpdatedFaculty.isEmpty()) {
            throw new EntityNotFoundException("Факультет не найден");
        }
        return facultyRepository.save(faculty);
    }

    public void delete(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new EntityNotFoundException("Факультет не найден");
        }
        facultyRepository.deleteById(id);
    }

    public Faculty get(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new EntityNotFoundException("Факультет не найден");
        }
        return faculty.get();
    }

    public List<Faculty> getByColor(String color) {
        List<Faculty> faculties = facultyRepository.getByColor(color);
        if (faculties.isEmpty()) {
            throw new EntityNotFoundException("Факультеты не найдены");
        }
        return faculties;
    }

    public List<Faculty> getAll() {
        List<Faculty> faculties = facultyRepository.findAll();
        if (!faculties.isEmpty()) {
            return faculties;
        }
        throw new EntityNotFoundException("Факультеты не найдены");
    }

    private static void referenceNameMaker(Faculty faculty) {
        faculty.setName(capitalize(faculty.getName().toLowerCase()));
    }

    private static void referenceColorMaker(Faculty faculty) {
        faculty.setColor(faculty.getColor().toUpperCase());
    }

    private static void facultyNameValidator(Faculty faculty) {
        if (isBlank(faculty.getName())) {
            throw new NullNameFieldException("Вы не задали имя");
        }
    }

    private static void facultyColorValidator(Faculty faculty) {
        if (isBlank(faculty.getColor())) {
            throw new NullColorFieldException("Вы не задали цвет");
        }
    }
}
