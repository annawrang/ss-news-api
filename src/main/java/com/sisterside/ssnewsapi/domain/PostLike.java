package com.sisterside.ssnewsapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLike {
    private User user;
    private String postLikeNumber;
}
