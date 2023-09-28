package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    void testCreate() throws Exception {
        Student student = new Student();
        student.setName("Roman");
        student.setAge(30);

        assertThat(this.testRestTemplate
                        .postForObject("http://localhost:" + port + "/student", student, Student.class))
                .isNotNull();
    }

    @Test
    void testGetById() throws Exception {
        assertThat(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/student/{id}", Student.class, 1L))
                .isNotNull();
    }

    @Test
    void testGetAll() throws Exception {
        assertThat(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/student/all", Collection.class))
                .isNotNull();
    }

    @Test
    void testGetAllByAgeBetween() throws Exception {
        assertThat(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/student/age?min=25&max=30", Collection.class))
                .isNotNull();
    }

    @Test
    void testGetFacultyByStudentId() throws Exception {
        assertThat(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/student/faculty/{id}", Faculty.class, 1L))
                .isNotNull();

    }

    @Test
    void testUpdate() throws Exception {
        assertThat(this.testRestTemplate
                .exchange("http://localhost:" + port + "/student",
                        HttpMethod.PUT, null, Student.class))
                .isNotNull();
    }

    @Test
    void testDeleteById() throws Exception{
        assertThat(this.testRestTemplate
                .exchange("http://localhost:" + port + "/student/100",
                        HttpMethod.DELETE, HttpEntity.EMPTY, Void.class))
                .isNotNull();
    }
}
