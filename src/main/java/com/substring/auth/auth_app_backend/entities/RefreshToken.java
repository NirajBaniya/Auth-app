package com.substring.auth.auth_app_backend.entities;


import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.CloseableThreadContext;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens", indexes = {

        @Index(name = "refresh_tokens_jti_idx", columnList = "jti", unique = true),
        @Index(name = "refresh_token_id_idx", columnList = "user_id")
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RefreshToken {

     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

     @Column(name = "jti", unique = true, nullable = false, updatable = false)
     private String jti;   // it is id of jwt. its like token id.


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
     private User user;


    @Column(updatable = false, nullable = false)
     private Instant createdAt;


    @Column(nullable = false)
     private Instant expiresAt;


    @Column(nullable = false)
     private boolean revoked;

     private String replacedByToken;




}
