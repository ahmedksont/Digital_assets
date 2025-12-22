package com.example.digitalassets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "licenses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userId", "assetId"})
        },
        indexes = {
                @Index(name = "idx_license_user", columnList = "userId"),
                @Index(name = "idx_license_asset", columnList = "assetId")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class License {

    @Id
    @GeneratedValue
    private UUID licenseId;

    @Column(nullable = false)
    private String userId; // comes from Hub Digital

    @Column(nullable = false)
    private UUID assetId;

    private Integer maxDownloads;      // null = unlimited
    private Integer downloadsCount = 0;

    private Instant expiresAt;          // null = never expires

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
