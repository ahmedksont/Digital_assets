package com.example.digitalassets.controller;

import com.example.digitalassets.entity.Asset;
import com.example.digitalassets.service.AssetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService service;

    public AssetController(AssetService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public Asset create(@RequestBody Asset asset) {
        return service.create(asset);
    }

    // READ by ID
    @GetMapping("/{id}")
    public Asset getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    // READ all active
    @GetMapping
    public List<Asset> getAllActive() {
        return service.getAllActive();
    }

    // READ by author
    @GetMapping("/author/{authorUserId}")
    public List<Asset> getByAuthor(@PathVariable String authorUserId) {
        return service.getByAuthor(authorUserId);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Asset update(
            @PathVariable UUID id,
            @RequestBody Asset asset
    ) {
        return service.update(id, asset);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable UUID id) {
        service.deactivate(id);
    }
}
