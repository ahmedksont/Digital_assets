package com.example.digitalassets.service;

public record StorageResult(
        String bucket,
        String path,
        String filename,
        long size
) {}
