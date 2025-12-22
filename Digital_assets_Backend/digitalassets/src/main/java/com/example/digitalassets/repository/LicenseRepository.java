package com.example.digitalassets.repository;

import com.example.digitalassets.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LicenseRepository extends JpaRepository<License, UUID> {

    Optional<License> findByUserIdAndAssetId(String userId, UUID assetId);
}
