package com.sisterside.ssnewsapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String commentNumber;
    private User user;
    private LocalDateTime date;
    private String text;
}
