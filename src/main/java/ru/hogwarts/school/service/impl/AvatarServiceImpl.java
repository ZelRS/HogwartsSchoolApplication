package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("${path.to.avatar.folder}")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        LOGGER.info("Was invoked method for upload avatar to student with ID = {}", studentId);
        Student student = studentService.getById(studentId);

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

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generatorDataForDB(filePath));
        avatarRepository.save(avatar);
        LOGGER.debug("The avatar was uploaded successfully");
    }

    @Override
    public Avatar findAvatar(Long id) {
        LOGGER.info("Was invoked method for find avatar with ID = {}", id);
        LOGGER.debug("The avatar was received successfully");
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }

    @Override
    public Collection<Avatar> getAll(Integer pageNumber, Integer pageSize) {
        LOGGER.info("Was invoked method for find all avatars where page has number {} and has size {}",
                pageNumber, pageSize);
        LOGGER.debug("List of avatars was received successfully");
        return avatarRepository.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
    }

    private byte[] generatorDataForDB(Path filePath) throws IOException {
        LOGGER.info("Was invoked method for generate avatar from DB");
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage bi = ImageIO.read(bis);

            int height = bi.getHeight() / (bi.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, bi.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(bi, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            LOGGER.debug("The avatar was generated successfully");
            return baos.toByteArray();
        }
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
