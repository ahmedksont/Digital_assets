package com.example.digitalassets.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class LocalStorageService {

    private static final Path ROOT = Paths.get("uploads");

    public LocalStorageService() throws IOException {
        Files.createDirectories(ROOT);
    }

    public StoredFile store(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String filename = UUID.randomUUID() + extension;
            Path destination = ROOT.resolve(filename);

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return new StoredFile(
                    filename,
                    destination.toString(),
                    file.getSize(),
                    file.getContentType()
            );

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file locally", e);
        }
    }
}
