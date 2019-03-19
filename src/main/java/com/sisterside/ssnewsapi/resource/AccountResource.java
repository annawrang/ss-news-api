package com.sisterside.ssnewsapi.resource;

import com.sisterside.ssnewsapi.model.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import java.util.Set;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class AccountResource {

    private UUID accountNumber;

    @Email
    private String email;

    private Set<Role> roles;
}

