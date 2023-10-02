package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.hogwarts.school.controller.TestConstants.*;

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
        ResponseEntity<Faculty> postResponseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/faculty", FACULTY, Faculty.class);

        assertThat((postResponseEntity.getStatusCode())).isEqualTo(HttpStatus.OK);

        Faculty postRsFaculty = postResponseEntity.getBody();

        assertThat(postRsFaculty.getName()).isEqualTo(FACULTY.getName());
        assertThat(postRsFaculty.getColor()).isEqualTo(FACULTY.getColor());

    }

    @Test
    void testGetById() throws Exception {
        ResponseEntity<Faculty> postResponseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/faculty", FACULTY, Faculty.class);

        Faculty postRsFaculty = postResponseEntity.getBody();

        ResponseEntity<Faculty> getResponseEntity = testRestTemplate
                .getForEntity("http://localhost:" + port + "/faculty/" + postRsFaculty.getId(), Faculty.class);

        Faculty getRsFaculty = getResponseEntity.getBody();

        assertThat(getRsFaculty.getId()).isEqualTo(postRsFaculty.getId());
        assertThat(getRsFaculty.getName()).isEqualTo(postRsFaculty.getName());
        assertThat(getRsFaculty.getColor()).isEqualTo(postRsFaculty.getColor());
    }

    @Test
    void testGetAllByNameOrColor() throws Exception {
        assertThat(testRestTemplate
                .getForEntity("http://localhost:" + port + "/faculty?nameOrColor=AnyName", String.class))
                .isNotNull();
    }

    @Test
    void testGetStudentsByFacultyId() throws Exception {
        assertThat(testRestTemplate
                .getForEntity("http://localhost:" + port + "/faculty/student/" + FACULTY_ID, String.class))
                .isNotNull();
    }

    @Test
    void testGetAll() throws Exception {
        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/all", String.class))
                .isNotNull();
    }

    @Test
    void testUpdate() throws Exception {
        ResponseEntity<Faculty> postResponseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/faculty", FACULTY, Faculty.class);
        Faculty postRsFaculty = postResponseEntity.getBody();

        postRsFaculty.setName(FACULTY_OTHER_NAME);
        testRestTemplate.put("http://localhost:" + port + "/faculty/", postRsFaculty, Faculty.class);

        ResponseEntity<Faculty> getResponseEntity = testRestTemplate
                .getForEntity("http://localhost:" + port + "/faculty/" + postRsFaculty.getId(), Faculty.class);
        Faculty getRsFaculty = getResponseEntity.getBody();

        assertThat(getRsFaculty.getName()).isEqualTo(postRsFaculty.getName());
    }

    @Test
    void testDeleteById() throws Exception {
        ResponseEntity<Faculty> postResponseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/faculty", FACULTY, Faculty.class);
        Faculty postRsFaculty = postResponseEntity.getBody();

        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + postRsFaculty.getId(), Faculty.class);

        ResponseEntity<Faculty> getResponseEntity = testRestTemplate
                .getForEntity("http://localhost:" + port + "/faculty/" + postRsFaculty.getId(), Faculty.class);

        Faculty getRsFaculty = getResponseEntity.getBody();

        assertThat(getRsFaculty.getName()).isNull();
    }
}
