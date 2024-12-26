package com.example.demo.controller;

import com.common.demo.PaginationResponse;
import com.example.demo.Data.Request.UserRequest;
import com.example.demo.Data.Response.ResponseHandler;
import com.example.demo.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/getUsers")
    public ResponseEntity<?> getUsersList(@RequestBody UserRequest request) {
        PaginationResponse obj = authService.getUserList(request);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, obj );
    }
}
