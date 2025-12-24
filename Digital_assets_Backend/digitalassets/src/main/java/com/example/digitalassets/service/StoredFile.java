package com.example.digitalassets.service;

public record StoredFile(
        String filename,
        String path,
        long size,
        String contentType
) {}
