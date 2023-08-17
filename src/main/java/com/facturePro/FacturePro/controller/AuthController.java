package com.facturePro.FacturePro.controller;

import com.facturePro.FacturePro.dto.JwtAuthResponse;
import com.facturePro.FacturePro.dto.LoginDto;
import com.facturePro.FacturePro.dto.RegisterDto;
import com.facturePro.FacturePro.entity.User;
import com.facturePro.FacturePro.services.AuthService;
import com.facturePro.FacturePro.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String message = authService.register(registerDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // Build your own login endpoint
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        JwtAuthResponse message = authService.login(loginDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping({"/create"})
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("non");
        User userSave = this.userService.createUser(user);
        System.out.println("ok");
        return ResponseEntity.ok(userSave);
    }

}
