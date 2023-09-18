package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

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
    private long id;

    private String name;

    private String color;

    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference
    private Collection<Student> students;

    public Faculty(long id, String name, String color) {
        this.id = id;
        this.name = capitalize(name.toLowerCase());
        this.color = color.toUpperCase();
    }
}
