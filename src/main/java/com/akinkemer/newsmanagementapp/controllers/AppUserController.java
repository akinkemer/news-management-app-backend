package com.akinkemer.newsmanagementapp.controllers;

import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import com.akinkemer.newsmanagementapp.service.AppUserService;
import com.akinkemer.newsmanagementapp.utilities.requestBodies.GetUserForm;
import com.akinkemer.newsmanagementapp.utilities.requestBodies.RoleToUserForm;
import com.akinkemer.newsmanagementapp.utilities.results.DataResult;
import com.akinkemer.newsmanagementapp.utilities.results.ErrorDataResult;
import com.akinkemer.newsmanagementapp.utilities.results.Result;
import com.akinkemer.newsmanagementapp.utilities.results.SuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
    @GetMapping("/user/currentUser")
    public DataResult<GetUserForm> getCurrentUser(Principal principal){
        return appUserService.getCurrentUser(principal.getName());
    }

}

