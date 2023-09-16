package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.capitalize;

@Entity
@JsonIgnoreProperties(value = {"students"})
public class Faculty {
    @Id
    @SequenceGenerator(name = "facultyIdSeq", sequenceName = "faculty_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facultyIdSeq")
    private long id;

    private String name;

    private String color;

    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference
    private Collection<Student> students;

    public Faculty() {
    }

    public Faculty(long id, String name, String color) {
        this.id = id;
        this.name = capitalize(name.toLowerCase());
        this.color = color.toUpperCase();
    }

    public long getId() {
        return id;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id && Objects.equals(name, faculty.name) && Objects.equals(color, faculty.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
