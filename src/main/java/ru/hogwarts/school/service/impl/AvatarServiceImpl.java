package ru.hogwarts.school.service.impl;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AvatarServiceImpl implements AvatarService {
    @Value("${path.to.avatar.folder}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;

    private final StudentService studentService;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    // загружаем аватар в БД и в локальное хранилище с присваиванием его студенту
    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        log.info("Was invoked method for upload avatar to student with ID = {}", studentId);
        Student student = studentService.getById(studentId);

        Path filePath = Path.of(avatarsDir, student.getName() + "." + getExtensions(avatarFile.getOriginalFilename()));

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        // загрузка аватара в локальное хранилище
        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        // загрузка аватара в БД
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generatorDataForDB(filePath));
        avatarRepository.save(avatar);

        log.debug("The avatar was uploaded successfully");
    }

    // находим аватар в БД и, если его нет, создаем новый аватар
    @Override
    public Avatar findAvatar(Long id) {
        log.info("Was invoked method for find avatar with ID = {}", id);
        log.debug("The avatar was received successfully");
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }

    // получаем все аватары из БД с ипользованием пагинации
    @Override
    public Collection<Avatar> getAll(Integer pageNumber, Integer pageSize) {
        log.info("Was invoked method for find all avatars where page has number {} and has size {}",
                pageNumber, pageSize);
        log.debug("List of avatars was received successfully");
        return avatarRepository.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
    }

    // данный метод генерирует сжимает получаемое изображение для последующей передачи его в БД
    private byte[] generatorDataForDB(Path filePath) throws IOException {
        log.info("Was invoked method for generate avatar from DB");
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
            log.debug("The avatar was generated successfully");
            return baos.toByteArray();
        }
    }

    // вытаскиваем расширение файла
    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
