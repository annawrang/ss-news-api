package com.sisterside.ssnewsapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
     @NotNull
     @NotBlank
     private String userNumber;
     @NotNull
     @NotBlank
     private String firstName;
     @NotNull
     @NotBlank
     private String surName;
}
