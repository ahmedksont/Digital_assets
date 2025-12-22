package com.example.digitalassets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;
@Entity
@Table(
        name = "assets",
        indexes = {
                @Index(name = "idx_assets_active", columnList = "isActive"),
                @Index(name = "idx_assets_author", columnList = "authorUserId")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue
    private UUID assetId;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    private String version;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String storagePath;

    @Column(nullable = false)
    private String storageBucket;

    private Long sizeBytes;
    private String contentType;

    @Column(nullable = false)
    private String authorUserId;

    private boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
