package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("${path.to.avatar.folder}")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public void uploadAvatar(Long id, MultipartFile avatarFile) throws IOException {
        Student student = studentService.getById(id);

        Path filePath = Path.of(avatarsDir, student.getName() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(id);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long id) {
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
