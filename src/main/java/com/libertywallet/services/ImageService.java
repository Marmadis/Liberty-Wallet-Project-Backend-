package com.libertywallet.services;

import com.libertywallet.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    public String saveImage(MultipartFile file) {
        try {
            // Генерируем уникальное имя файла
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filepath = Paths.get(imageUploadDir, filename);

            // Сохраняем файл прямо в корневую директорию
            Files.write(filepath, file.getBytes());

            // Возвращаем только имя файла (или можно полный URL, если нужно)
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }

    public ResponseEntity<byte[]> getImage(String filename) {
        try {
            Path file = Paths.get(imageUploadDir, filename);

            if (!Files.exists(file)) {
                throw new NotFoundException("Image not found");
            }

            byte[] image = Files.readAllBytes(file);
            HttpHeaders headers = new HttpHeaders();

            // Определяем ContentType по имени файла
            String contentType = Files.probeContentType(file);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(image.length);

            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image", e);
        }
    }

    public void deleteImage(String filename) {
        try {
            Path file = Paths.get(imageUploadDir, filename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image", e);
        }
    }
}
