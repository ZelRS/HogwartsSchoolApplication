package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.controller.constants.StudentControllerWebMvcTestConstants.*;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentServiceImpl studentServiceImpl;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    public void setUp() {
        FACULTY.setId(FACULTY_ID);
        FACULTY.setName(FACULTY_NAME);
        FACULTY.setColor(FACULTY_COLOR);

        STUDENT.setId(STUDENT_ID);
        STUDENT.setName(STUDENT_NAME);
        STUDENT.setAge(STUDENT_AGE);
        STUDENT.setFaculty(FACULTY);

        STUDENT_OBJECT.put("id", STUDENT_ID);
        STUDENT_OBJECT.put("name", STUDENT_NAME);
        STUDENT_OBJECT.put("age", STUDENT_AGE);
        STUDENT_OBJECT.put("faculty", FACULTY);
    }

    @Test
    public void createTest() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(STUDENT);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(STUDENT_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(STUDENT_ID))
                .andExpect(jsonPath("$.name").value(STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(STUDENT_AGE));
    }

    @Test
    public void getByIdTest() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/{id}", STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(STUDENT_ID))
                .andExpect(jsonPath("$.name").value(STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(STUDENT_AGE));
    }

    @Test
    public void getAllTest() throws Exception {
        when(studentRepository.findAll()).thenReturn(STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all")
                        .content(STUDENT_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(STUDENT_ID))
                .andExpect(jsonPath("$[0].name").value(STUDENT_NAME))
                .andExpect(jsonPath("$[0].age").value(STUDENT_AGE));
    }

    @Test
    public void getAllByAgeBetweenTest() throws Exception {
        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?min=31&max=31")
                        .content(STUDENT_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(STUDENT_ID))
                .andExpect(jsonPath("$[0].name").value(STUDENT_NAME))
                .andExpect(jsonPath("$[0].age").value(STUDENT_AGE));
    }

    @Test
    public void getFacultyByStudentIdTest() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty/{id}", STUDENT_ID)
                        .content(STUDENT_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FACULTY_ID))
                .andExpect(jsonPath("$.name").value(FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR));
    }

    @Test
    public void deleteByIdTest() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/{id}", STUDENT_ID)
                        .content(STUDENT_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTest() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(STUDENT));

        STUDENT.setName(STUDENT_OTHER_NAME);

        when(studentRepository.save(any(Student.class))).thenReturn(STUDENT);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(STUDENT_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(STUDENT_ID))
                .andExpect(jsonPath("$.name").value(STUDENT_OTHER_NAME))
                .andExpect(jsonPath("$.age").value(STUDENT_AGE));
    }
}
