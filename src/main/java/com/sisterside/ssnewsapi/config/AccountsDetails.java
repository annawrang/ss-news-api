package com.sisterside.ssnewsapi.config;

import com.sisterside.ssnewsapi.exception.ForbiddenException;
import com.sisterside.ssnewsapi.exception.NotFoundException;
import com.sisterside.ssnewsapi.model.Account;
import com.sisterside.ssnewsapi.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountsDetails implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(NotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(ForbiddenException::new);

        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        account.getRoles()
                .forEach(role -> grantedAuths.add(new SimpleGrantedAuthority(role.toString())));

        return org.springframework.security.core.userdetails.User
                .withUsername(account.getAccountNumber().toString())
                .password(account.getPassword())
                .authorities(grantedAuths)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}