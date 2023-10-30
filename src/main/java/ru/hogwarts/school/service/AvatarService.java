package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.Collection;

public interface AvatarService {
    // находим аватар студента по id
    Avatar findAvatar(Long id);

    // загружаем аватар студента
    void uploadAvatar(Long id, MultipartFile avatar) throws IOException;

    // получаем все аватары с пагинацией
    Collection<Avatar> getAll(Integer pageNumber, Integer pageSize);
}
