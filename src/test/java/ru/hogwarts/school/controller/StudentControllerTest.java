package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private Long id;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testCreate() throws Exception {

    }

    @Test
    void testUpdate() throws Exception {

    }

    @Test
    void testDeleteById() throws Exception {

    }

    @Test
    void testGetById() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/1", Student.class))
                .isNotNull();
    }

    @Test
    void testGetAllByAgeBetween() throws Exception {

    }

    @Test
    void testGetFacultyByStudentId() throws Exception {

    }

    @Test
    void testGetAll() throws Exception {

    }
}
