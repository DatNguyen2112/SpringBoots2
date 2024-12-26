package com.example.demo.Service;

import com.common.demo.PaginationResponse;
import com.example.demo.Data.Request.ProductRequest;
import com.example.demo.Data.Request.UserRequest;
import com.example.demo.Modal.UserCreateModal;

public interface AuthService {
    String login(UserCreateModal userCreateModal);
    public PaginationResponse getUserList(UserRequest userRequest);
}
