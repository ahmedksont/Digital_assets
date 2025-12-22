package com.example.digitalassets.controller;

import com.example.digitalassets.service.DeliverService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliverService deliverService;

    @GetMapping("/deliver")
    public ResponseEntity<byte[]> deliver(
            @RequestParam String userId,
            @RequestParam String assetId,
            HttpServletRequest request
    ) {

        var delivered = deliverService.deliver(
                userId,
                assetId,
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
        );

        return ResponseEntity.ok()
                .header("X-Upsell-Id", delivered.upsellId())
                .header("X-Upsell-Url", delivered.upsellUrl())
                .contentType(MediaType.APPLICATION_PDF)
                .body(delivered.file());
    }
}
