package com.sisterside.ssnewsapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String commentNumber;
    @NotNull
    private User user;
    private LocalDateTime date;
    @NotBlank
    private String text;
    private List<Like> likes;
    private List<Comment> commentReplies;

    public void generateCreationTime() {
        this.date = LocalDateTime.now();
    }
}
