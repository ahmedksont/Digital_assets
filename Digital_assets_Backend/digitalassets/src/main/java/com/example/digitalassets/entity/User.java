package com.example.digitalassets.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String userId;
    private String email;
    private String displayName;
    private boolean isAuthor;
    private Instant createdAt = Instant.now();
}
