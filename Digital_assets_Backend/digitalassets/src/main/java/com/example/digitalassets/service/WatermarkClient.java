package com.example.digitalassets.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WatermarkClient {

    private final WebClient flaskWebClient;

    public WatermarkClient(
            @Qualifier("flaskWebClient") WebClient flaskWebClient
    ) {
        this.flaskWebClient = flaskWebClient;
    }

    public byte[] watermarkPdf(byte[] pdfBytes, String userId) {

        ByteArrayResource pdfResource = new ByteArrayResource(pdfBytes) {
            @Override
            public String getFilename() {
                return "source.pdf";
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", pdfResource);
        body.add("user_id", userId);

        return flaskWebClient.post()
                .uri("/watermark")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }
}
