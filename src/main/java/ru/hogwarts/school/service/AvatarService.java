package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.Collection;

public interface AvatarService {
    Avatar findAvatar(Long id);

    void uploadAvatar(Long id, MultipartFile avatar) throws IOException;

    Collection<Avatar> getAll(Integer pageNumber, Integer pageSize);
}
