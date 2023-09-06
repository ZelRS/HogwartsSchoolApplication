package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.hogwarts.school.constants.StudentServiceTestConstants.*;

class StudentServiceTest {
    private final StudentService studentService = new StudentService();

    @BeforeEach
    public void setUp() {
        studentService.createStudent(STUDENT);
    }

    @Test
    void shouldCreateStudent() {
        int beforeCount = studentService.getAllStudents().size();
        assertThat(studentService.createStudent(CREATED_STUDENT))
                .isEqualTo(CREATED_STUDENT)
                .isIn(studentService.getAllStudents());

        assertThat(studentService.getAllStudents())
                .hasSize(beforeCount + 1);
    }

    @Test
    void ShouldGetStudent() {
        assertThat(studentService.getStudent(STUDENT.getId()))
                .isEqualTo(STUDENT)
                .isIn(studentService.getAllStudents());
    }

    @Test
    void ShouldReturnNullInCreateStudentIfIdIsNotExist() {
        assertThat(studentService.getStudent(NOT_EXISTING_ID))
                .isEqualTo(null);
    }

    @Test
    void shouldUpdateFaculty() {
        studentService.createStudent(STUDENT);
        assertThat(studentService.updateStudent(CREATED_STUDENT))
                .isEqualTo(CREATED_STUDENT)
                .isIn(studentService.getAllStudents());
    }

    @Test
    void shouldDeleteStudent() {
        assertThat(studentService.deleteStudent(STUDENT.getId()))
                .isEqualTo(STUDENT)
                .isNotIn(studentService.getAllStudents());

        assertThat(studentService.getAllStudents())
                .isEmpty();
    }

    @Test
    void ShouldReturnNullInDeleteStudentIfIdIsNotExist() {
        assertThat(studentService.deleteStudent(NOT_EXISTING_ID))
                .isEqualTo(null);
    }

    @Test
    void ShouldGetAllStudentsByAge() {
        studentService.createStudent(CREATED_STUDENT);
        assertThat(studentService.getAllStudentsByAge(31))
                .isNotEqualTo(STUDENTS_LIST);

        assertThat(studentService.getAllStudentsByAge(31))
                .hasSize(1);
    }

    @Test
    void getAllStudents() {
        studentService.createStudent(CREATED_STUDENT);
        assertThat(studentService.getAllStudents())
                .isEqualTo(STUDENTS_LIST);

        assertThat(studentService.getAllStudents())
                .hasSize(2);
    }
}