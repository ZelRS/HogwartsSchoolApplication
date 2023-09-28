package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void testCreate() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Qqqq");
        faculty.setColor("QQQQ");

        assertThat(this.testRestTemplate
                .postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class))
                .isNotNull();
    }

    @Test
    void testGetById() throws Exception {
        assertThat(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/faculty/{id}", Faculty.class, 1L))
                .isNotNull();
    }

    @Test
    void testGetAllByNameOrColor() throws Exception {
        assertThat(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/faculty?nameOrColor=Qqqq", Collection.class))
                .isNotNull();
    }

    @Test
    void testGetStudentsByFacultyId() throws Exception {
        assertThat(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/faculty/students/{id}", Collection.class, 1L))
                .isNotEmpty();
    }

    @Test
    void testGetAll() throws Exception {
        assertThat(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/faculty/all", Collection.class))
                .isNotNull();
    }

    @Test
    void testUpdate() throws Exception {
        assertThat(this.testRestTemplate
                .exchange("http://localhost:" + port + "/faculty",
                        HttpMethod.PUT, null, Faculty.class))
                .isNotNull();

    }

    @Test
    void testDeleteById() throws Exception {
        assertThat(this.testRestTemplate
                .exchange("http://localhost:" + port + "/faculty/100",
                        HttpMethod.DELETE, HttpEntity.EMPTY, Void.class))
                .isNotNull();
    }
}
