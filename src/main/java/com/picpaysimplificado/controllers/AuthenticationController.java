package com.picpaysimplificado.controllers;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.AuthenticationDTO;
import com.picpaysimplificado.dtos.LoginResponseDTO;
import com.picpaysimplificado.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private  TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {
        System.out.print("opaopaopa");
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
