package com.sema.librarymanagment.service.impl;

import com.sema.librarymanagment.exception.ResourceNotFoundException;
import com.sema.librarymanagment.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Value("${file.upload.dir:uploads}")
    private String uploadDir;

    @Value("${file.upload.max-size-bytes:5242880}")
    private long maxFileSize;

    @Value("${file.upload.allowed-extensions:jpg,jpeg,png,webp}")
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

        String detectedType = detectImageType(readHeader(file));
        if (detectedType == null || !matchesExtension(detectedType, extension)) {
            throw new IllegalArgumentException(
                    "File content does not match its extension (possible spoofed file type)"
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
            throw new ResourceNotFoundException("File not found: " + fileName);
        }

        return Files.readAllBytes(filePath);
    }

    private byte[] readHeader(MultipartFile file) throws IOException {
        try (InputStream in = file.getInputStream()) {
            return in.readNBytes(12);
        }
    }

    private String detectImageType(byte[] header) {
        if (header.length >= 3
                && (header[0] & 0xFF) == 0xFF
                && (header[1] & 0xFF) == 0xD8
                && (header[2] & 0xFF) == 0xFF) {
            return "jpg";
        }
        if (header.length >= 8
                && (header[0] & 0xFF) == 0x89 && header[1] == 0x50
                && header[2] == 0x4E && header[3] == 0x47
                && header[4] == 0x0D && header[5] == 0x0A
                && header[6] == 0x1A && header[7] == 0x0A) {
            return "png";
        }
        if (header.length >= 12
                && header[0] == 'R' && header[1] == 'I' && header[2] == 'F' && header[3] == 'F'
                && header[8] == 'W' && header[9] == 'E' && header[10] == 'B' && header[11] == 'P') {
            return "webp";
        }
        return null;
    }

    private boolean matchesExtension(String detectedType, String extension) {
        if ("jpg".equals(detectedType)) {
            return "jpg".equals(extension) || "jpeg".equals(extension);
        }
        return detectedType.equals(extension);
    }
}