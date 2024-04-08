package com.example.history.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryPost {
    private String postId;

    private String content;

    private LocalDateTime viewTime;

    private String title;

}
