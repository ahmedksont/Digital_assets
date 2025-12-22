package com.example.digitalassets.service;

import com.example.digitalassets.entity.DownloadLog;
import com.example.digitalassets.repository.DownloadLogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class DownloadLogService {

    private final DownloadLogRepository repository;

    public DownloadLogService(DownloadLogRepository repository) {
        this.repository = repository;
    }

    public void log(String userId, String assetId, String ip, String userAgent) {
        DownloadLog log = DownloadLog.builder()
                .userId(userId)
                .assetId(UUID.fromString(assetId))
                .ipAddress(ip)
                .userAgent(userAgent)
                .downloadedAt(Instant.now())
                .build();

        repository.save(log);
    }
}
