package com.example.demo.Impl;

import com.common.demo.PaginationResponse;
import com.example.demo.Data.Entity.User;
import com.example.demo.Data.Request.UserRequest;
import com.example.demo.Data.Response.UserResponse;
import com.example.demo.Modal.UserCreateModal;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AuthService;
import com.example.demo.Specification.UserSpecification;
import com.example.demo.Until.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private UserSpecification userSpecification;

    // check neu khong co pageNumber thi set default bang 1
    @Value("${pagination.page.size.default}")
    private Integer defaultPageSize;

    @Autowired
    public AuthServiceImpl(JwtTokenProvider jwtTokenProvider
            , AuthenticationManager authenticationManager
            , UserRepository userRepository
            , UserSpecification userSpecification) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userSpecification = userSpecification;
    }

    @Override
    public String login(UserCreateModal loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public PaginationResponse getUserList(UserRequest userRequest) {
        List<User> list = null;

        Page<User> pages = null;
        if (userRequest.getPageNumber() == 0) {
            pages = new PageImpl<>(userRepository.findAll(userSpecification.getUsers(userRequest)));
        } else {
            if (userRequest.getPageSize() == 0) {
                userRequest.setPageSize(defaultPageSize);
            }
            Pageable paging = PageRequest.of(userRequest.getPageNumber() - 1, userRequest.getPageSize());
            pages = userRepository.findAll(userSpecification.getUsers(userRequest), paging);
        }
        if (pages != null && pages.getContent() != null) {
            list = pages.getContent();
            if (list != null && list.size() > 0) {
                PaginationResponse respList = new PaginationResponse();
                respList.setPages(pages.getTotalPages());
                respList.setCount(pages.getTotalElements());
                respList.setCurrentPage(pages.getNumber() + 1);
                respList.setItems(new ArrayList<Object>());
                for (User users : list) {
                    UserResponse obj = new UserResponse();
                    respList.getItems().add(obj);
                }
                return respList;
            }
        }
        return null;
    }
}
