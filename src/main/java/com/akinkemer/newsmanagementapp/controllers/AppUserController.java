package com.akinkemer.newsmanagementapp.controllers;

import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import com.akinkemer.newsmanagementapp.service.AppUserService;
import com.akinkemer.newsmanagementapp.utilities.results.DataResult;
import com.akinkemer.newsmanagementapp.utilities.results.Result;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/user/save")
    public DataResult<AppUser> saveUser(@RequestBody AppUser user){
        return appUserService.saveUser(user);
    }

    @PostMapping("/role/save")
    public DataResult<AppRole> saveRole(@RequestBody AppRole role){
        return appUserService.saveRole(role);
    }
    @PostMapping("/role/addToUser")
    public Result addRoleToUser(@RequestBody RoleToUserForm form){
        return appUserService.addRoleToUser(form.getUserName(), form.getRoleName());
    }
}

@Data
class RoleToUserForm{
    private String userName;
    private String roleName;
}
