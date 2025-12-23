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
public class AssetController {

    private final AssetService assetService;

    /* ============================
       CREATE (UPLOAD FILE)
       ============================ */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Asset> create(
            @RequestPart("file") MultipartFile file,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String version,
            @RequestParam String authorUserId
    ) {
        return ResponseEntity.ok(
                assetService.createWithUpload(file, name, description, version, authorUserId)
        );
    }

    /* ============================
       READ
       ============================ */
    @GetMapping("/{id}")
    public ResponseEntity<Asset> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(assetService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAllActive() {
        return ResponseEntity.ok(assetService.getAllActive());
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Asset>> getByAuthor(@PathVariable String authorId) {
        return ResponseEntity.ok(assetService.getByAuthor(authorId));
    }

    /* ============================
       UPDATE METADATA
       ============================ */
    @PutMapping("/{id}")
    public ResponseEntity<Asset> update(
            @PathVariable UUID id,
            @RequestBody Asset updated
    ) {
        return ResponseEntity.ok(assetService.update(id, updated));
    }

    /* ============================
       UPDATE FILE (NEW VERSION)
       ============================ */
    @PutMapping(
            value = "/{id}/file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Asset> updateFile(
            @PathVariable UUID id,
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.ok(assetService.updateFile(id, file));
    }

    /* ============================
       SOFT DELETE
       ============================ */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        assetService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
