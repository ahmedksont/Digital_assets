package com.example.digitalassets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "download_logs",
        indexes = {
                @Index(name = "idx_dl_user", columnList = "userId"),
                @Index(name = "idx_dl_asset", columnList = "assetId"),
                @Index(name = "idx_dl_date", columnList = "downloadedAt")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DownloadLog {

    @Id
    @GeneratedValue
    private UUID logId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private UUID assetId;

    private String ipAddress;
    private String userAgent;

    @Column(nullable = false, updatable = false)
    private Instant downloadedAt;

    @PrePersist
    protected void onCreate() {
        downloadedAt = Instant.now();
    }
}
