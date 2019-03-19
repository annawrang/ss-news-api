package com.sisterside.ssnewsapi.controller;

import com.sisterside.ssnewsapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    // FUNKAR
    @PostMapping(path = "/login")
    public ResponseEntity authenticateAccounts(@RequestParam("email") final String email,
                                               @RequestParam("password") final String password) {
        String jwtToken = accountService.login(email, password);
        return ResponseEntity.ok().header("Authorization", jwtToken).build();
    }

    // FUNKAR
    @PostMapping(path = "/signup")
    public void aignupAccounts(@RequestParam("email") final String email,
                               @RequestParam("password") final String password) {
        accountService.createAccount(email, password);
    }

    // TROR att den funkar
    @DeleteMapping(path = "/{email}")
    public ResponseEntity deleteAccount(@PathVariable("email") final String email) {
        accountService.deleteAccount(email);
        return ResponseEntity.noContent().build();
    }

    // TROR att den funkar
    @PostMapping(path = "/{accountNumber}/disable")
    public ResponseEntity disableAccount(@RequestParam("accountNumber") final UUID accountNumber) {
        accountService.disableAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }
}
