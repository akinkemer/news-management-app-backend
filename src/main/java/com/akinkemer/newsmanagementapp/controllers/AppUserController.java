package com.akinkemer.newsmanagementapp.controllers;

import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import com.akinkemer.newsmanagementapp.service.AppUserService;
import com.akinkemer.newsmanagementapp.utilities.request.GetUserForm;
import com.akinkemer.newsmanagementapp.utilities.request.RoleToUserForm;
import com.akinkemer.newsmanagementapp.utilities.result.DataResult;
import com.akinkemer.newsmanagementapp.utilities.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
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
    @GetMapping("/user/getUser")
    public DataResult<GetUserForm> getUserByUserName(@RequestParam String userName){
        log.info("CURRENT USER:{}",userName);
        return appUserService.getUserByUserName(userName);
    }

}

