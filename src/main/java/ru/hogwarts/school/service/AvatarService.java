package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.Collection;

public interface AvatarService {
    Avatar findAvatar(Long id); //найти аватар студента по id

    void uploadAvatar(Long id, MultipartFile avatar) throws IOException; // загрузить аватар студента

    Collection<Avatar> getAll(Integer pageNumber, Integer pageSize); //получить все аватары
}
