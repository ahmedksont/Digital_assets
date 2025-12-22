package com.example.digitalassets.service;

import com.example.digitalassets.entity.Asset;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StorageService {

    private final WebClient supabaseWebClient;

    public StorageService(
            @Qualifier("supabaseWebClient") WebClient supabaseWebClient
    ) {
        this.supabaseWebClient = supabaseWebClient;
    }

    public byte[] download(Asset asset) {

        String path = String.format(
                "/storage/v1/object/%s/%s",
                asset.getStorageBucket(),
                asset.getStoragePath()
        );

        return supabaseWebClient.get()
                .uri(path)
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + System.getenv("SUPABASE_SERVICE_ROLE_KEY")
                )
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }
}
