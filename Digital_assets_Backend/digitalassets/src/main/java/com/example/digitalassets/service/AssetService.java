package com.example.digitalassets.service;

import com.example.digitalassets.entity.Asset;
import com.example.digitalassets.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository repository;
    private final StorageService storageService;

    /* ============================
       CREATE WITH FILE UPLOAD
       ============================ */
    public Asset createWithUpload(
            MultipartFile file,
            String name,
            String description,
            String version,
            String authorUserId
    ) {
        StorageResult stored = storageService.upload(file);

        Asset asset = Asset.builder()
                .name(name)
                .description(description)
                .version(version)
                .filename(stored.filename())
                .storageBucket(stored.bucket())
                .storagePath(stored.path())
                .sizeBytes(stored.size())
                .contentType(file.getContentType())
                .authorUserId(authorUserId)
                .isActive(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return repository.save(asset);
    }

    /* ============================
       READ
       ============================ */
    public Asset getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));
    }

    public List<Asset> getAllActive() {
        return repository.findByIsActiveTrue();
    }

    public List<Asset> getByAuthor(String authorId) {
        return repository.findByAuthorUserIdAndIsActiveTrue(authorId);
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

        return repository.save(asset);
    }

    /* ============================
       UPDATE FILE
       ============================ */
    public Asset updateFile(UUID id, MultipartFile file) {

        Asset asset = getById(id);
        StorageResult stored = storageService.upload(file);

        asset.setFilename(stored.filename());
        asset.setStorageBucket(stored.bucket());
        asset.setStoragePath(stored.path());
        asset.setSizeBytes(stored.size());
        asset.setContentType(file.getContentType());
        asset.setUpdatedAt(Instant.now());

        return repository.save(asset);
    }

    /* ============================
       SOFT DELETE
       ============================ */
    public void deactivate(UUID id) {
        Asset asset = getById(id);
        asset.setActive(false);
        asset.setUpdatedAt(Instant.now());
        repository.save(asset);
    }
    public Asset getActiveAsset(String assetId) {

        UUID id;
        try {
            id = UUID.fromString(assetId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid assetId format");
        }

        Asset asset = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        if (!asset.isActive()) {
            throw new RuntimeException("Asset is inactive");
        }

        return asset;
    }

}
