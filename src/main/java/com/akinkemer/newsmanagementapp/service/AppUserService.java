package com.akinkemer.newsmanagementapp.service;

import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import com.akinkemer.newsmanagementapp.utilities.request.GetUserForm;
import com.akinkemer.newsmanagementapp.utilities.result.DataResult;
import com.akinkemer.newsmanagementapp.utilities.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface AppUserService {
    DataResult<AppUser> saveUser(AppUser user);
    DataResult<AppRole> saveRole(AppRole role);
    DataResult<AppUser> getUser(String userName);
    Result addRoleToUser(String userName, String roleName);
    DataResult<List<AppUser>> getUsers();
    DataResult<GetUserForm> getUserByUserName(String username);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    }
