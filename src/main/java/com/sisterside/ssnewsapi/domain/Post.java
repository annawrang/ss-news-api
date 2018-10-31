package com.sisterside.ssnewsapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
     private LocalDateTime date;
     private String postNumber;
     private User user;
     private String text;
     private boolean edited;
     private List<PostLike> likes;
     private List<Comment> comments;
    
}
