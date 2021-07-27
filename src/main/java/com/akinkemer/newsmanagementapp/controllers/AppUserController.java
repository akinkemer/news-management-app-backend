package com.akinkemer.newsmanagementapp.controllers;

import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import com.akinkemer.newsmanagementapp.service.AppUserService;
import com.akinkemer.newsmanagementapp.utilities.requestBodies.RoleToUserForm;
import com.akinkemer.newsmanagementapp.utilities.results.DataResult;
import com.akinkemer.newsmanagementapp.utilities.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/users")
    public DataResult<List<AppUser>> getUsers() {
        return appUserService.getUsers();
    }

    @PostMapping("/user/save")
    public DataResult<AppUser> saveUser(@RequestBody AppUser user) {
        return appUserService.saveUser(user);
    }

    @PostMapping("/role/save")
    public DataResult<AppRole> saveRole(@RequestBody AppRole role) {
        return appUserService.saveRole(role);
    }

    @PostMapping("/role/addToUser")
    public Result addRoleToUser(@RequestBody RoleToUserForm form) {
        return appUserService.addRoleToUser(form.getUserName(), form.getRoleName());
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        appUserService.refreshToken(request, response);
    }
}

