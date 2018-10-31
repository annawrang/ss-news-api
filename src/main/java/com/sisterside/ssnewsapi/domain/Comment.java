package com.sisterside.ssnewsapi.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Comment {
    private String commentNumber;
    private User user;
    private LocalDateTime date;
    private String text;
}
