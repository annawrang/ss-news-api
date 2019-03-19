package com.sisterside.ssnewsapi.service;

import com.sisterside.ssnewsapi.AccountResource;
import com.sisterside.ssnewsapi.config.JwtTokenProvider;
import com.sisterside.ssnewsapi.exception.BadRequestException;
import com.sisterside.ssnewsapi.exception.ForbiddenException;
import com.sisterside.ssnewsapi.exception.NotFoundException;
import com.sisterside.ssnewsapi.model.Account;
import com.sisterside.ssnewsapi.model.Role;
import com.sisterside.ssnewsapi.repository.AccountRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final AccountRepository accountRepository;

    public AccountService(final AuthenticationManager authenticationManager, final JwtTokenProvider jwtTokenProvider,
                          final AccountRepository accountRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountRepository = accountRepository;
    }

    public AccountResource createAccount(final String email, final String password) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.READ_WRITE);
        Account account = new Account()
                .setAccountNumber(UUID.randomUUID())
                .setEmail(email)
                .setPassword(password)
                .setRoles(roles)
                .setDisabled(false);
        return convert(accountRepository.save(account));
    }

    public String login(final String email, final String password) {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            throw new BadRequestException("Username and/or password can not be blank");
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email,
                    new ArrayList<>(accountRepository.findByEmailAndPassword(email, password).get().getRoles()));
        } catch (Exception e) {
            throw new ForbiddenException("Invalid email/password supplied");
        }
    }

    public void deleteAccount(String email) {
        String currentUser = getCurrentUser();
        Account account = accountRepository.findByAccountNumber(UUID.fromString(currentUser)).orElseThrow(NotFoundException::new);

        if (!account.getEmail().equals(email)) {
            throw new ForbiddenException("Authenticated user does not match email provided");
        }
        accountRepository.delete(account);
    }

    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public void disableAccount(UUID accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(NotFoundException::new);
        account.setDisabled(true);
        accountRepository.save(account);
    }

    private AccountResource convert(Account account) {
        return new AccountResource()
                .setAccountNumber(account.getAccountNumber())
                .setEmail(account.getEmail())
                .setPassword(account.getPassword())
                .setDisabled(account.isDisabled())
                .setRoles(account.getRoles());
    }
}
