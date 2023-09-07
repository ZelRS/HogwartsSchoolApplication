package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.hogwarts.school.constants.FacultyServiceTestConstants.*;

class FacultyServiceTest {
    private final FacultyService facultyService = new FacultyService();

    @BeforeEach
    public void setUp() {
        facultyService.createFaculty(FACULTY);
    }

    @Test
    void shouldCreateFaculty() {
        int beforeCount = facultyService.getAllFaculties().size();
        assertThat(facultyService.createFaculty(CREATED_FACULTY))
                .isEqualTo(CREATED_FACULTY)
                .isIn(facultyService.getAllFaculties());

        assertThat(facultyService.getAllFaculties())
                .hasSize(beforeCount + 1);
    }

    @Test
    void ShouldGetFaculty() {
        assertThat(facultyService.getFaculty(FACULTY.getId()))
                .isEqualTo(FACULTY)
                .isIn(facultyService.getAllFaculties());
    }

    @Test
    void ShouldReturnNullInCreateStudentIfIdIsNotExist() {
        assertThat(facultyService.getFaculty(NOT_EXISTING_ID))
                .isEqualTo(null);
    }

    @Test
    void shouldUpdateFaculty() {
        facultyService.createFaculty(FACULTY);
        assertThat(facultyService.updateFaculty(CREATED_FACULTY))
                .isEqualTo(CREATED_FACULTY)
                .isIn(facultyService.getAllFaculties());
    }

    @Test
    void shouldDeleteFaculty() {
        assertThat(facultyService.deleteFaculty(FACULTY.getId()))
                .isEqualTo(FACULTY)
                .isNotIn(facultyService.getAllFaculties());

        assertThat(facultyService.getAllFaculties())
                .isEmpty();
    }

    @Test
    void ShouldReturnNullInDeleteStudentIfIdIsNotExist() {
        assertThat(facultyService.deleteFaculty(NOT_EXISTING_ID))
                .isEqualTo(null);
    }

    @Test
    void ShouldGetAllStudentsByAge() {
        facultyService.createFaculty(CREATED_FACULTY);
        assertThat(facultyService.getAllFacultiesByColor("КРАСНЫЙ"))
                .isNotEqualTo(FACULTIES_LIST);

        assertThat(facultyService.getAllFacultiesByColor("КРАСНЫЙ"))
                .hasSize(1);
    }

    @Test
    void getAllStudents() {
        facultyService.createFaculty(CREATED_FACULTY);
        assertThat(facultyService.getAllFaculties())
                .isEqualTo(FACULTIES_LIST);

        assertThat(facultyService.getAllFaculties())
                .hasSize(2);
    }
}