package com.example.digitalassets.repository;

import com.example.digitalassets.entity.DownloadLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DownloadLogRepository extends JpaRepository<DownloadLog, UUID> {

    List<DownloadLog> findByUserId(String userId);

    List<DownloadLog> findByAssetId(UUID assetId);
}
