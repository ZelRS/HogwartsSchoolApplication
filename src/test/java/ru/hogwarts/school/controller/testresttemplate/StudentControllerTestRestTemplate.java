package ru.hogwarts.school.controller.testresttemplate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.hogwarts.school.controller.testresttemplate.constants.TestRestTemplateConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplate {
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
        ResponseEntity<Student> postResponseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/student", STUDENT, Student.class);

        assertThat((postResponseEntity.getStatusCode())).isEqualTo(HttpStatus.OK);

        Student postRsStudent = postResponseEntity.getBody();

        assertThat(postRsStudent.getName()).isEqualTo(STUDENT.getName());
        assertThat(postRsStudent.getAge()).isEqualTo(STUDENT.getAge());
    }

    @Test
    void testGetById() throws Exception {
        ResponseEntity<Student> postResponseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/student", STUDENT, Student.class);
        Student postRsStudent = postResponseEntity.getBody();

        ResponseEntity<Student> getResponseEntity = testRestTemplate
                .getForEntity("http://localhost:" + port + "/student/" + postRsStudent.getId(), Student.class);
        Student getRsStudent = getResponseEntity.getBody();

        assertThat(getRsStudent.getId()).isEqualTo(postRsStudent.getId());
        assertThat(getRsStudent.getName()).isEqualTo(postRsStudent.getName());
        assertThat(getRsStudent.getAge()).isEqualTo(postRsStudent.getAge());
    }

    @Test
    void testGetAll() throws Exception {
        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/student/all", String.class))
                .isNotNull();
    }

    @Test
    void testGetAllByAgeBetween() throws Exception {
        assertThat(testRestTemplate
                .getForEntity("http://localhost:" + port + "/student/age?min=29&max=31", String.class))
                .isNotNull();
    }

    @Test
    void testGetFacultyByStudentId() throws Exception {
        STUDENT.setFaculty(FACULTY);

        ResponseEntity<Student> postResponseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/student", STUDENT, Student.class);
        Student postRsStudent = postResponseEntity.getBody();

        ResponseEntity<Faculty> getResponseEntity = testRestTemplate
                .getForEntity("http://localhost:" + port + "/student/faculty/" + postRsStudent.getId(), Faculty.class);
        Faculty getRsFaculty = getResponseEntity.getBody();
        System.out.println(getRsFaculty);

        assertThat(postRsStudent.getFaculty()).isEqualTo(getRsFaculty);
    }

    @Test
    void testUpdate() throws Exception {
        ResponseEntity<Student> postResponseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/student", STUDENT, Student.class);
        Student postRsStudent = postResponseEntity.getBody();

        postRsStudent.setName(STUDENT_OTHER_NAME);
        testRestTemplate.put("http://localhost:" + port + "/student/", postRsStudent, Student.class);

        ResponseEntity<Student> getResponseEntity = testRestTemplate
                .getForEntity("http://localhost:" + port + "/student/" + postRsStudent.getId(), Student.class);
        Student getRsStudent = getResponseEntity.getBody();

        assertThat(getRsStudent.getName()).isEqualTo(postRsStudent.getName());
    }

    @Test
    void testDeleteById() throws Exception {
        ResponseEntity<Student> postResponseEntity = testRestTemplate
                .postForEntity("http://localhost:" + port + "/student", STUDENT, Student.class);
        Student postRsStudent = postResponseEntity.getBody();

        testRestTemplate.delete("http://localhost:" + port + "/student/" + postRsStudent.getId(), Student.class);

        ResponseEntity<Student> getResponseEntity = testRestTemplate
                .getForEntity("http://localhost:" + port + "/student/" + postRsStudent.getId(), Student.class);
        Student getRsStudent = getResponseEntity.getBody();

        assertThat(getRsStudent.getName()).isNull();
    }
}
