package com.example.springsse.controller;

import com.example.springsse.dto.TokenResponse;
import com.example.springsse.dto.UserDto;
import com.example.springsse.security.UserDetailsImpl;
import com.example.springsse.security.UserDetailsServiceImpl;
import com.example.springsse.security.jwt.JwtUtils;
import com.example.springsse.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    @PostMapping("/user/signup")
    public ResponseEntity signup(@RequestBody UserDto userDto) {
        userService.signup(userDto);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/user/signin")
    public ResponseEntity login(@RequestBody UserDto userDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("로그인 실패");
        }

        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        String token = jwtUtils.createToken(userDetails.getUser());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/user/logout")
    public ResponseEntity logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("ok");
    }

}
