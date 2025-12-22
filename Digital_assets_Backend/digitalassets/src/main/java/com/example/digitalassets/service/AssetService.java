package com.example.digitalassets.service;

import com.example.digitalassets.entity.Asset;
import com.example.digitalassets.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssetService {

    private final AssetRepository repository;

    public AssetService(AssetRepository repository) {
        this.repository = repository;
    }

    public Asset create(Asset asset) {
        asset.setActive(true);
        return repository.save(asset);
    }

    public Asset getById(UUID assetId) {
        return repository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));
    }

    public Asset getActiveAsset(String assetId) {
        return repository.findById(UUID.fromString(assetId))
                .filter(Asset::isActive)
                .orElseThrow(() -> new RuntimeException("Asset not found or inactive"));
    }

    public List<Asset> getAllActive() {
        return repository.findByIsActiveTrue();
    }

    public List<Asset> getByAuthor(String authorUserId) {
        return repository.findByAuthorUserIdAndIsActiveTrue(authorUserId);
    }

    public Asset update(UUID assetId, Asset updated) {
        Asset existing = getById(assetId);

        if (!existing.isActive()) {
            throw new RuntimeException("Cannot update inactive asset");
        }

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setVersion(updated.getVersion());
        existing.setFilename(updated.getFilename());
        existing.setStoragePath(updated.getStoragePath());
        existing.setStorageBucket(updated.getStorageBucket());
        existing.setSizeBytes(updated.getSizeBytes());
        existing.setContentType(updated.getContentType());

        return repository.save(existing);
    }

    public void deactivate(UUID assetId) {
        Asset asset = getById(assetId);
        asset.setActive(false);
        repository.save(asset);
    }
}
