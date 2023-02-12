package com.spring.store.controller;

import com.spring.store.config.jwt.JwtTokenProvider;
import com.spring.store.entity.User;
import com.spring.store.model.JwtRequest;
import com.spring.store.model.JwtResponse;
import com.spring.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api")
public class AuthenticateController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticateController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        String username = authenticationRequest.getUsername();

        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("user with username: " + username + " not found");
        }

        authenticate(username, authenticationRequest.getPassword());

        String token = jwtTokenProvider.createToken(username, user.getRoles());

        log.info("User is authenticate: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            return new JwtResponse(token, user.getUsername(), user.isAdmin(), user.isManager());
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user) {
        return userService.register(user);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
