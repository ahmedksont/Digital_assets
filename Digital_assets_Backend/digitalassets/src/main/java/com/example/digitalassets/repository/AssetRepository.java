package com.example.digitalassets.repository;

import com.example.digitalassets.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {

    List<Asset> findByIsActiveTrue();

    List<Asset> findByAuthorUserId(String authorUserId);

    List<Asset> findByAuthorUserIdAndIsActiveTrue(String authorUserId);
}
