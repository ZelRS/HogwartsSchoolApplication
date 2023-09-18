package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static org.apache.commons.lang3.StringUtils.capitalize;

@Entity(name = "Студент")
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    @SequenceGenerator(name = "studentIdSeq", sequenceName = "student_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentIdSeq")
    private long id;

    private String name;

    private int age;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @JsonBackReference
    private Faculty faculty;

    public Student(long id, String name, int age) {
        this.id = id;
        this.name = capitalize(name.toLowerCase());
        this.age = age;
    }
}
