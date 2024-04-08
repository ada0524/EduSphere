package com.example.userservice.entity;


import lombok.*;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="user_service")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @Column(name = "date_joined", nullable = false)
    private LocalDateTime dateJoined;

    @Column(name = "user_type", nullable = false)
    private String type; // VISITOR_OR_BANNED, UNVERIFIED_USER, NORMAL_USER, ADMIN_USER, SUPER_ADMIN_USER

    @Column(name = "profile_image_url")
    private String profileImageUrl;
}
