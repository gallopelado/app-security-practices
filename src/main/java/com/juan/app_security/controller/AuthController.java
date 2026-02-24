package com.juan.app_security.controller;

import com.juan.app_security.entities.JwtRequest;
import com.juan.app_security.entities.JwtResponse;
import com.juan.app_security.services.JwtService;
import com.juan.app_security.services.JwtUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailService jwtUserDetailService;
    private final JwtService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> postToken(@RequestBody JwtRequest request) {
        this.authenticate(request);
        final var userDetail = jwtUserDetailService.loadUserByUsername(request.getUsername());
        final var token = this.jwtService.generateToken(userDetail);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(JwtRequest request) {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException | DisabledException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
