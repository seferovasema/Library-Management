
package com.sema.librarymanagment.service.impl;

import com.sema.librarymanagment.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    @Value("${file.upload.max-size-bytes}")
    private long maxFileSize;

    @Value("${file.upload.allowed-extensions}")
    private String allowedExtensions;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException(
                    "File size must not exceed 5 MB"
            );
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || !originalFileName.contains(".")) {
            throw new IllegalArgumentException("File must have an extension");
        }

        String extension = originalFileName
                .substring(originalFileName.lastIndexOf(".") + 1)
                .toLowerCase();

        List<String> allowed = Arrays.stream(allowedExtensions.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        if (!allowed.contains(extension)) {
            throw new IllegalArgumentException(
                    "File type is not allowed"
            );
        }

        Path uploadPath = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();

        Files.createDirectories(uploadPath);

        String fileName = UUID.randomUUID() + "." + extension;

        Path targetPath = uploadPath
                .resolve(fileName)
                .normalize();

        if (!targetPath.startsWith(uploadPath)) {
            throw new IllegalArgumentException(
                    "Invalid file path"
            );
        }

        Files.copy(
                file.getInputStream(),
                targetPath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }

    @Override
    public byte[] downloadFile(String fileName) throws IOException {

        Path uploadPath = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();

        Path filePath = uploadPath
                .resolve(fileName)
                .normalize();

        if (!filePath.startsWith(uploadPath)) {
            throw new IllegalArgumentException(
                    "Invalid file path"
            );
        }

        if (!Files.exists(filePath)) {
            throw new IOException("File not found");
        }

        return Files.readAllBytes(filePath);
    }
}

