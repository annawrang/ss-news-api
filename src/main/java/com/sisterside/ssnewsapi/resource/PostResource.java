package com.sisterside.ssnewsapi.resource;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class PostResource {

    private LocalDateTime date;

    private String postNumber;

    @NotNull
    private UserResource user;

    @NotBlank
    private String text;

    private boolean edited;

    private List<LikeResource> likes;

    private List<CommentResource> comments;

    public void generateCreationTime() {
        this.date = LocalDateTime.now();
    }
}
