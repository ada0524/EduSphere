package com.example.authservice.entity;


import lombok.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Boolean active;

    private LocalDateTime dateJoined;

    private String type; // VISITOR_OR_BANNED, UNVERIFIED_USER, NORMAL_USER, ADMIN_USER, SUPER_ADMIN_USER

    private String profileImageUrl;
}
