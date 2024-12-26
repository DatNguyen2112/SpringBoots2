package com.example.demo.controller;

import com.example.demo.Data.Response.JwtAuthResponse;
import com.example.demo.Data.Response.ResponseHandler;
import com.example.demo.Modal.UserCreateModal;
import com.example.demo.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API//
    // @Operation(security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserCreateModal loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setEmail(loginDto.getUsernameOrEmail());

        return ResponseHandler.generateResponse("Đăng nhập thành công", HttpStatus.OK, jwtAuthResponse);
    }

}
