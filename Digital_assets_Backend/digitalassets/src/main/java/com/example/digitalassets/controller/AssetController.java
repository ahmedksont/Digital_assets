package com.example.digitalassets.controller;

import com.example.digitalassets.entity.Asset;
import com.example.digitalassets.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AssetController {

    private final AssetService assetService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Asset> create(
            @RequestPart("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "version", required = false) String version,
            @RequestParam("authorUserId") String authorUserId
    ) {
        return ResponseEntity.ok(
                assetService.createWithUpload(
                        file, name, description, version, authorUserId
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(assetService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAllActive() {
        return ResponseEntity.ok(assetService.getAllActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asset> update(
            @PathVariable("id") UUID id,
            @RequestBody Asset updated
    ) {
        return ResponseEntity.ok(assetService.update(id, updated));
    }

    @PutMapping(value = "/{id}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Asset> updateFile(
            @PathVariable("id") UUID id,
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.ok(assetService.updateFile(id, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable("id") UUID id) {
        assetService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    /* ============================
       DOWNLOAD
       ============================ */
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable("id") UUID id) {
        byte[] file = assetService.download(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        "Content-Disposition",
                        "attachment; filename=\"asset.pdf\""
                )
                .body(file);
    }
}
