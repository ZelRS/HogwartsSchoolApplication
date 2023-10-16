package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        LOGGER.info("Was invoked method for create faculty");
        nameValidator(faculty);
        colorValidator(faculty);
        referenceNameMaker(faculty);
        referenceColorMaker(faculty);
        LOGGER.debug("Faculty was received successfully");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty update(Faculty faculty) {
        LOGGER.info("Was invoked method for update faculty");
        nameValidator(faculty);
        colorValidator(faculty);
        referenceNameMaker(faculty);
        referenceColorMaker(faculty);
        Optional<Faculty> UpdatedFaculty = facultyRepository.findById(faculty.getId());
        if (UpdatedFaculty.isEmpty()) {
            LOGGER.error("There is not student with ID = {}", faculty.getId());
            throw new EntityNotFoundException("Факультет не найден");
        }
        LOGGER.debug("Faculty was updated successfully");
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Was invoked method for delete faculty with ID = {}", id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            LOGGER.error("There is not student with ID = {}", id);
            throw new EntityNotFoundException("Факультет не найден");
        }
        facultyRepository.deleteById(id);
        LOGGER.debug("Faculty was removed successfully");
    }

    @Override
    public Faculty getById(Long id) {
        LOGGER.info("Was invoked method for get faculty with ID = {}", id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            LOGGER.error("There is not student with ID = {}", id);
            throw new EntityNotFoundException("Факультет не найден");
        }
        LOGGER.debug("Faculty was received successfully");
        return faculty.get();
    }

    @Override
    public Collection<Faculty> getAll() {
        LOGGER.info("Was invoked method for get all faculties");
        LOGGER.debug("List of faculties was received successfully");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> getAllByNameOrColor(String name, String color) {
        LOGGER.info("Was invoked method for get all faculties by name or color");
        LOGGER.debug("Faculty was received successfully");
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
            LOGGER.error("Name can't be empty");
            throw new NullNameFieldException("Вы не задали имя");
        }
    }

    private static void colorValidator(Faculty faculty) {
        if (isBlank(faculty.getColor())) {
            LOGGER.error("Color can't be empty");
            throw new NullColorFieldException("Вы не задали цвет");
        }
    }
}
