package com.example.digitalassets.service;

import com.example.digitalassets.entity.Upsell;
import com.example.digitalassets.repository.UpsellRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UpsellService {

    private final UpsellRepository repository;

    public UpsellService(UpsellRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public Upsell create(Upsell upsell) {
        upsell.setActive(true);
        return repository.save(upsell);
    }

    // READ
    public Upsell getById(UUID upsellId) {
        return repository.findById(upsellId)
                .orElseThrow(() -> new RuntimeException("Upsell not found"));
    }

    public List<Upsell> getAllActive() {
        return repository.findByIsActiveTrue();
    }

    public List<Upsell> getByAsset(UUID assetId) {
        return repository.findByAssetIdAndIsActiveTrue(assetId);
    }

    // USED BY DeliverService
    public Upsell findActiveByAsset(String assetId) {
        return repository
                .findFirstByAssetIdAndIsActiveTrue(UUID.fromString(assetId))
                .orElseThrow(() -> new RuntimeException("No active upsell found"));
    }

    // UPDATE
    public Upsell update(UUID upsellId, Upsell updated) {
        Upsell existing = getById(upsellId);

        if (!existing.isActive()) {
            throw new RuntimeException("Cannot update inactive upsell");
        }

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setUrl(updated.getUrl());

        return repository.save(existing);
    }

    // DELETE (soft)
    public void deactivate(UUID upsellId) {
        Upsell upsell = getById(upsellId);
        upsell.setActive(false);
        repository.save(upsell);
    }
}
