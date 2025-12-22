package com.example.digitalassets.repository;

import com.example.digitalassets.entity.Upsell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UpsellRepository extends JpaRepository<Upsell, UUID> {

    Optional<Upsell> findFirstByAssetIdAndIsActiveTrue(UUID assetId);

    List<Upsell> findByAssetIdAndIsActiveTrue(UUID assetId);

    List<Upsell> findByIsActiveTrue();
}
