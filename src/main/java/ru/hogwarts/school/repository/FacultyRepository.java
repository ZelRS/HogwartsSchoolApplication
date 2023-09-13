package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByColorIgnoreCase(String color);

    Optional<Faculty> findFirstByNameOrColorIgnoreCase(String name, String color);

    Faculty findFacultyByStudentsId(long studentId);

}
