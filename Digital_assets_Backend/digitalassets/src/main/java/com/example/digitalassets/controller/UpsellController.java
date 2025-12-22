package com.example.digitalassets.controller;

import com.example.digitalassets.entity.Upsell;
import com.example.digitalassets.service.UpsellService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/upsells")
public class UpsellController {

    private final UpsellService service;

    public UpsellController(UpsellService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public Upsell create(@RequestBody Upsell upsell) {
        return service.create(upsell);
    }

    // READ by ID
    @GetMapping("/{id}")
    public Upsell getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    // READ all active
    @GetMapping
    public List<Upsell> getAllActive() {
        return service.getAllActive();
    }

    // READ by asset
    @GetMapping("/asset/{assetId}")
    public List<Upsell> getByAsset(@PathVariable UUID assetId) {
        return service.getByAsset(assetId);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Upsell update(
            @PathVariable UUID id,
            @RequestBody Upsell upsell
    ) {
        return service.update(id, upsell);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable UUID id) {
        service.deactivate(id);
    }
}
