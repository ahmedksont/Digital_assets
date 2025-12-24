package com.example.digitalassets.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliverService {

    private final AssetService assetService;
    private final LicenseService licenseService;
    private final UpsellService upsellService;
    private final WatermarkClient watermarkClient;
    private final DownloadLogService logService;

    public DeliveredFile deliver(
            String userId,
            String assetId,
            String ip,
            String userAgent
    ) {
        // 1️⃣ Asset actif obligatoire
        var asset = assetService.getActiveAsset(assetId);

        // 2️⃣ Validation licence (inclut increment downloads)
        licenseService.validate(userId, UUID.fromString(assetId));

        // 3️⃣ Charger le PDF depuis le disque local
        byte[] originalPdf;
        try {
            originalPdf = Files.readAllBytes(
                    Path.of(asset.getStoragePath())
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to read asset file", e);
        }

        // 4️⃣ Watermark via Flask
        byte[] watermarkedPdf =
                watermarkClient.watermarkPdf(originalPdf, userId);

        // 5️⃣ Log sécurité
        logService.log(userId, assetId, ip, userAgent);

        // 6️⃣ Upsell contextuel (optionnel)
        var upsell = upsellService.findActiveByAsset(assetId);

        return new DeliveredFile(
                watermarkedPdf,
                upsell != null ? upsell.getUpsellId().toString() : null,
                upsell != null ? upsell.getUrl() : null
        );
    }
}
