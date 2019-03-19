package com.sisterside.ssnewsapi.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentResource {

    private String commentNumber;

    @NotNull
    private UserResource user;

    private LocalDateTime date;

    @NotBlank
    private String text;

    private List<LikeResource> likes;

    private List<CommentResource> commentReplies;

    public void generateCreationTime() {
        this.date = LocalDateTime.now();
    }
}
