package com.example.digitalassets.service;

import com.example.digitalassets.entity.Asset;
import com.example.digitalassets.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetService {

    private static final String STORAGE_DIR = "storage/assets";

    private final AssetRepository assetRepository;

    /* ============================
       CREATE (UPLOAD)
       ============================ */
    public Asset createWithUpload(
            MultipartFile file,
            String name,
            String description,
            String version,
            String authorUserId
    ) {
        try {
            Files.createDirectories(Path.of(STORAGE_DIR));

            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path filePath = Path.of(STORAGE_DIR, filename);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            Asset asset = new Asset();
            asset.setName(name);
            asset.setDescription(description);
            asset.setVersion(version);
            asset.setFilename(file.getOriginalFilename());
            asset.setStoragePath(filePath.toString());
            asset.setStorageBucket("local"); // ✅ IMPORTANT
            asset.setSizeBytes(file.getSize());
            asset.setContentType(file.getContentType());
            asset.setAuthorUserId(authorUserId);
            asset.setActive(true);
            asset.setCreatedAt(Instant.now());
            asset.setUpdatedAt(Instant.now());

            return assetRepository.save(asset);

        } catch (Exception e) {
            throw new RuntimeException("Failed to store file locally", e);
        }
    }

    /* ============================
       READ
       ============================ */
    public Asset getById(UUID id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));
    }

    public List<Asset> getAllActive() {
        return assetRepository.findByIsActiveTrue();
    }

    public List<Asset> getByAuthor(String authorId) {
        return assetRepository.findByAuthorUserId(authorId);
    }

    /* ============================
       UPDATE METADATA
       ============================ */
    public Asset update(UUID id, Asset updated) {
        Asset asset = getById(id);

        asset.setName(updated.getName());
        asset.setDescription(updated.getDescription());
        asset.setVersion(updated.getVersion());
        asset.setUpdatedAt(Instant.now());

        return assetRepository.save(asset);
    }

    /* ============================
       UPDATE FILE
       ============================ */
    public Asset updateFile(UUID id, MultipartFile file) {
        Asset asset = getById(id);

        try {
            Path path = Path.of(asset.getStoragePath());

            Files.copy(
                    file.getInputStream(),
                    path,
                    StandardCopyOption.REPLACE_EXISTING
            );

            asset.setFilename(file.getOriginalFilename());
            asset.setSizeBytes(file.getSize());
            asset.setContentType(file.getContentType());
            asset.setUpdatedAt(Instant.now());

            return assetRepository.save(asset);

        } catch (IOException e) {
            throw new RuntimeException("Failed to update file", e);
        }
    }

    /* ============================
       DOWNLOAD
       ============================ */
    public byte[] download(UUID id) {
        Asset asset = getById(id);

        try {
            return Files.readAllBytes(Path.of(asset.getStoragePath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }
    }

    /* ============================
       DELETE
       ============================ */
    public void deactivate(UUID id) {
        Asset asset = getById(id);
        asset.setActive(false);
        asset.setUpdatedAt(Instant.now());
        assetRepository.save(asset);
    }
    public Asset getActiveAsset(String assetId) {
        return assetRepository.findById(UUID.fromString(assetId))
                .filter(Asset::isActive)
                .orElseThrow(() -> new RuntimeException("Active asset not found"));
    }

}
