package com.example.digitalassets.service;

import com.example.digitalassets.entity.Asset;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.UUID;

@Service
public class StorageService {

    private static final String BUCKET = "assets";

    private final WebClient supabaseWebClient;

    public StorageService(
            @Qualifier("supabaseWebClient") WebClient supabaseWebClient
    ) {
        this.supabaseWebClient = supabaseWebClient;
    }

    /* ============================
       DOWNLOAD
       ============================ */
    public byte[] download(Asset asset) {

        String path = String.format(
                "/storage/v1/object/%s/%s",
                asset.getStorageBucket(),
                asset.getStoragePath()
        );

        return supabaseWebClient.get()
                .uri(path)
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + System.getenv("SUPABASE_SERVICE_ROLE_KEY")
                )
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }

    /* ============================
       UPLOAD
       ============================ */
    public StorageResult upload(MultipartFile file) {

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";

            String filename = UUID.randomUUID() + extension;
            String path = "uploads/" + filename;

            supabaseWebClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/storage/v1/object/{bucket}/{path}")
                            .build(BUCKET, path)
                    )
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            "Bearer " + System.getenv("SUPABASE_SERVICE_ROLE_KEY")
                    )
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .bodyValue(file.getBytes())
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            return new StorageResult(
                    BUCKET,
                    path,
                    originalFilename,
                    file.getSize()
            );

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to storage", e);
        }
    }
}
