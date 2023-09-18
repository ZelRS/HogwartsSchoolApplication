package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.NullColorFieldException;
import ru.hogwarts.school.exception.NullNameFieldException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        nameValidator(faculty);
        colorValidator(faculty);
        referenceNameMaker(faculty);
        referenceColorMaker(faculty);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty update(Faculty faculty) {
        nameValidator(faculty);
        colorValidator(faculty);
        referenceNameMaker(faculty);
        referenceColorMaker(faculty);
        Optional<Faculty> UpdatedFaculty = facultyRepository.findById(faculty.getId());
        if (UpdatedFaculty.isEmpty()) {
            throw new EntityNotFoundException("Факультет не найден");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteById(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new EntityNotFoundException("Факультет не найден");
        }
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty getById(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new EntityNotFoundException("Факультет не найден");
        }
        return faculty.get();
    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getAllByNameOrColor(String name, String color) {
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    private static void referenceNameMaker(Faculty faculty) {
        faculty.setName(capitalize(faculty.getName().toLowerCase()));
    }

    private static void referenceColorMaker(Faculty faculty) {
        faculty.setColor(faculty.getColor().toUpperCase());
    }

    private static void nameValidator(Faculty faculty) {
        if (isBlank(faculty.getName())) {
            throw new NullNameFieldException("Вы не задали имя");
        }
    }

    private static void colorValidator(Faculty faculty) {
        if (isBlank(faculty.getColor())) {
            throw new NullColorFieldException("Вы не задали цвет");
        }
    }
}
