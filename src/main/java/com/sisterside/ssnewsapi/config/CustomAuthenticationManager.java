package com.sisterside.ssnewsapi.config;

import com.sisterside.ssnewsapi.exception.ForbiddenException;
import com.sisterside.ssnewsapi.model.Account;
import com.sisterside.ssnewsapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private AccountRepository accountRepository;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        Account account = accountRepository.findByEmail(email).orElseThrow(ForbiddenException::new);

        if (account.isDisabled()) {
            throw new ForbiddenException();
        }
        if (!credentialsMatching(account, password)) {
            throw new ForbiddenException();
        }
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        account.getRoles()
                .forEach(role -> grantedAuths.add(new SimpleGrantedAuthority(role.toString())));
        return new UsernamePasswordAuthenticationToken(email, password, grantedAuths);
    }

    private boolean credentialsMatching(Account account, String password) {
        return account.getPassword().equals(password);
    }
}
