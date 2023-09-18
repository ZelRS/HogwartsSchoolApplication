package ru.hogwarts.school.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "аватар")
@Getter
@Setter
@NoArgsConstructor
public class Avatar {
    @Id
    @GeneratedValue
    private  Long id;

    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;

    @OneToOne
    private Student student;

    public Avatar(Long id, String filePath, long fileSize, String mediaType, byte[] data, Student student) {
        this.id = id;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.data = data;
        this.student = student;
    }
}
