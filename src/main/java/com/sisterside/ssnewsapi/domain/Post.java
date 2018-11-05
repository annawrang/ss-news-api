package com.sisterside.ssnewsapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private LocalDateTime date;
    private String postNumber;
    @NotNull
    private User user;
    @NotBlank
    private String text;
    private boolean edited;
    private List<PostLike> likes;
    private List<Comment> comments;

}
