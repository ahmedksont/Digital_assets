package com.example.digitalassets.service;

public record DeliveredFile(
        byte[] file,
        String upsellId,
        String upsellUrl
) {}
