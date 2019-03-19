package com.sisterside.ssnewsapi.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResource {
     @NotBlank
     private String userNumber;
     @NotBlank
     private String firstName;
     @NotBlank
     private String surName;
}
