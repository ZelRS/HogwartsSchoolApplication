package ru.hogwarts.school.controller.webmvctest;

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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.controller.webmvctest.constants.FacultyControllerWebMvcTestConstants.*;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyServiceImpl;

    @InjectMocks
    private FacultyController facultyController;

    @BeforeEach
    public void setUp() {
        STUDENT.setId(STUDENT_ID);
        STUDENT.setName(STUDENT_NAME);
        STUDENT.setAge(STUDENT_AGE);

        FACULTY.setId(FACULTY_ID);
        FACULTY.setName(FACULTY_NAME);
        FACULTY.setColor(FACULTY_COLOR);
        FACULTY.setStudents(STUDENTS);

        FACULTY_OBJECT.put("id", FACULTY_ID);
        FACULTY_OBJECT.put("name", FACULTY_NAME);
        FACULTY_OBJECT.put("color", FACULTY_COLOR);
        FACULTY_OBJECT.put("students", STUDENTS);
    }

    @Test
    public void createTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(FACULTY);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(FACULTY_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FACULTY_ID))
                .andExpect(jsonPath("$.name").value(FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR));
    }

    @Test
    public void getByIdTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FACULTY_ID))
                .andExpect(jsonPath("$.name").value(FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR));
    }

    @Test
    public void getAllTest() throws Exception {
        when(facultyRepository.findAll()).thenReturn(FACULTIES);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .content(FACULTY_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(FACULTY_ID))
                .andExpect(jsonPath("$[0].name").value(FACULTY_NAME))
                .andExpect(jsonPath("$[0].color").value(FACULTY_COLOR));
    }

    @Test
    public void getAllByNameOrColorTest() throws Exception {
        when(facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(any(String.class), any(String.class)))
                .thenReturn(FACULTIES);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?nameOrColor=QQQQ")
                        .content(FACULTY_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(FACULTY_ID))
                .andExpect(jsonPath("$[0].name").value(FACULTY_NAME))
                .andExpect(jsonPath("$[0].color").value(FACULTY_COLOR));
    }

    @Test
    public void getStudentsByFacultyIdTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/students/{id}", FACULTY_ID)
                        .content(FACULTY_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(STUDENT_ID))
                .andExpect(jsonPath("$[0].name").value(STUDENT_NAME))
                .andExpect(jsonPath("$[0].age").value(STUDENT_AGE));
    }

    @Test
    public void deleteByIdTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", FACULTY_ID)
                        .content(FACULTY_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(FACULTY));

        FACULTY.setName(FACULTY_OTHER_NAME);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(FACULTY);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(FACULTY_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FACULTY_ID))
                .andExpect(jsonPath("$.name").value(FACULTY_OTHER_NAME))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR));
    }
}
