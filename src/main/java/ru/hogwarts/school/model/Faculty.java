package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.capitalize;

@Entity(name = "Факультет")
@JsonIgnoreProperties(value = {"students"})
@Getter
@Setter
@NoArgsConstructor
public class Faculty {
    @Id
    @SequenceGenerator(name = "facultyIdSeq", sequenceName = "faculty_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facultyIdSeq")
    private Long id;

    private String name;

    private String color;

    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference
    private Collection<Student> students;

    public Faculty(Long id, String name, String color, Collection<Student> students) {
        this.id = id;
        this.name = capitalize(name);
        this.color = color.toUpperCase();
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(id, faculty.id) && Objects.equals(name, faculty.name) && Objects.equals(color, faculty.color) && Objects.equals(students, faculty.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, students);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", students=" + students +
                '}';
    }
}
