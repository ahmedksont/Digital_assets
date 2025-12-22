package com.example.digitalassets.service;

import com.example.digitalassets.entity.License;
import com.example.digitalassets.exception.DownloadLimitException;
import com.example.digitalassets.exception.LicenseExpiredException;
import com.example.digitalassets.exception.LicenseNotFoundException;
import com.example.digitalassets.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class LicenseService {

    private final LicenseRepository repository;

    public LicenseService(LicenseRepository repository) {
        this.repository = repository;
    }

    /**
     * Validates access rights for a user and asset.
     * Increments download counter if valid.
     */
    public License validate(String userId, UUID assetId) {

        License license = repository
                .findByUserIdAndAssetId(userId, assetId)
                .orElseThrow(LicenseNotFoundException::new);

        if (license.getExpiresAt() != null &&
                license.getExpiresAt().isBefore(Instant.now())) {
            throw new LicenseExpiredException();
        }

        if (license.getMaxDownloads() != null &&
                license.getDownloadsCount() >= license.getMaxDownloads()) {
            throw new DownloadLimitException();
        }

        license.setDownloadsCount(
                license.getDownloadsCount() + 1
        );

        repository.save(license);
        return license;
    }
}
