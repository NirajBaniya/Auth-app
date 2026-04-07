package com.substring.auth.auth_app_backend.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;
    @Column(name = "user_email", unique = true)
    private String email;
    private String name;
    private String password;
    private String image;

    // when login check if user is enable or not.
    private boolean enable = true;

    // Instant is like a class that represent date or time in UTC formate
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private String gender;
    private Address address;

    // provider means how it is signup like google, local or github
    private Provider provider = Provider.LOCAL;
    private Set<Role> roles = new HashSet<>();


}
