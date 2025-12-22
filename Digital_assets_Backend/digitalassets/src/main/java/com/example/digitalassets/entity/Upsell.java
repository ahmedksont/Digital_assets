package com.example.digitalassets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;
@Entity
@Table(
        name = "upsells",
        indexes = {
                @Index(name = "idx_upsell_asset", columnList = "assetId"),
                @Index(name = "idx_upsell_active", columnList = "isActive")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Upsell {

    @Id
    @GeneratedValue
    private UUID upsellId;

    @Column(nullable = false)
    private UUID assetId;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String url;

    private boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}
