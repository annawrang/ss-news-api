package com.sisterside.ssnewsapi.resource;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LikeResource {
    @NotNull
    private UserResource user;

    private String likeNumber;
}
