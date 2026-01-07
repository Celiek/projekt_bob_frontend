package com.test.bob.Controller;

import com.test.bob.DTO.LoginDto;
import com.test.bob.DTO.RegisterDTO;
import com.test.bob.DTO.UserResponseDTO;
import com.test.bob.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody RegisterDTO dto){
        return ResponseEntity.status(201).body(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(
            @Valid @RequestBody LoginDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }
}
