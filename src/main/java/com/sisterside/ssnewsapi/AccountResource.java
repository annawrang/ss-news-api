package com.sisterside.ssnewsapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sisterside.ssnewsapi.model.Role;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResource {
    @Id
    private UUID id;

    private UUID accountNumber;

    @Email
    private String email;

    @Length(min = 8, max = 30)
    private String password;

    private Set<Role> roles;

    private boolean isDisabled;
}