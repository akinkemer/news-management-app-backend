package com.akinkemer.newsmanagementapp.controllers;

import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import com.akinkemer.newsmanagementapp.service.AppUserService;
import com.akinkemer.newsmanagementapp.utilities.results.DataResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/users")
    public DataResult<List<AppUser>> getUsers(){
        return appUserService.getUsers();
    }
}
