package com.example.digitalassets.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliverService {

    private final AssetService assetService;
    private final LicenseService licenseService;
    private final UpsellService upsellService;
    private final StorageService storageService;
    private final WatermarkClient watermarkClient;
    private final DownloadLogService logService;

    public DeliveredFile deliver(String userId, String assetId, String ip, String userAgent) {

        // 1️⃣ Asset actif obligatoire
        var asset = assetService.getActiveAsset(assetId);

        // 2️⃣ Validation licence (inclut increment downloads)
        licenseService.validate(userId, UUID.fromString(assetId));

        // 3️⃣ Télécharger le PDF original
        byte[] originalPdf = storageService.download(asset);

        // 4️⃣ Watermark via Flask
        byte[] watermarkedPdf = watermarkClient.watermarkPdf(originalPdf, userId);

        // 5️⃣ Log sécurité
        logService.log(userId, assetId, ip, userAgent);

        // 6️⃣ Upsell contextuel
        var upsell = upsellService.findActiveByAsset(assetId);

        return new DeliveredFile(
                watermarkedPdf,
                upsell.getUpsellId().toString(),
                upsell.getUrl()
        );
    }
}
