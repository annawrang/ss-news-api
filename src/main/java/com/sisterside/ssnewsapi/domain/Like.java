package com.sisterside.ssnewsapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @NotNull
    private User user;
    private String likeNumber;
}
