package com.sisterside.ssnewsapi.dto;

import com.sisterside.ssnewsapi.domain.Comment;
import com.sisterside.ssnewsapi.domain.PostLike;
import com.sisterside.ssnewsapi.domain.User;
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
public class PostDTO {
    @Id
    private ObjectId id;
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
