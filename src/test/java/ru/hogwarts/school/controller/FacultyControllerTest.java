package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

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

    }

    @Test
    void testUpdate() throws Exception {

    }

    @Test
    void testDeleteById() throws Exception {

    }

    @Test
    void testGetById() throws Exception {

    }

    @Test
    void testGetAllByNameOrColor() throws Exception {

    }

    @Test
    void testGetStudentsByFacultyId() throws Exception {

    }

    @Test
    void testGetAll() throws Exception {

    }
}
