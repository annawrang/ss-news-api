package com.sisterside.ssnewsapi.model;

import com.sisterside.ssnewsapi.resource.CommentResource;
import com.sisterside.ssnewsapi.resource.LikeResource;
import com.sisterside.ssnewsapi.resource.UserResource;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class Post {
    @Id
    private UUID id;

    private LocalDateTime date;

    private String postNumber;

    private UserResource user;

    private String text;

    private boolean edited;

    private List<LikeResource> likes;

    private List<CommentResource> comments;
}
