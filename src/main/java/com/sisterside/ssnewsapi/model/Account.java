package com.sisterside.ssnewsapi.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;

@Data
@Document // needed?
@Accessors(chain = true)
public class Account {

    @Id
    private UUID id = UUID.randomUUID();

    private UUID accountNumber;

    private String email;

    private String password;

    private Set<Role> roles;

    private boolean isDisabled;
}
