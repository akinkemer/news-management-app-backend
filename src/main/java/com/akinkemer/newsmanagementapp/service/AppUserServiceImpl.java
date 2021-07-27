package com.akinkemer.newsmanagementapp.service;

import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import com.akinkemer.newsmanagementapp.repository.AppRoleRepository;
import com.akinkemer.newsmanagementapp.repository.AppUserRepository;
import com.akinkemer.newsmanagementapp.utilities.results.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<AppUser> user = appUserRepository.findByUserName(userName);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            user.get().getRoles().forEach(
                    appRole -> authorities.add(new SimpleGrantedAuthority(appRole.getName()))
            );

            return new org.springframework.security.core.userdetails.User(
                    user.get().getUserName(),
                    user.get().getPassword(),
                    authorities
            );
        }
    }

    @Override
    public DataResult<AppUser> saveUser(AppUser user) {
        log.info("Saving new user {} to the database", user.getName());
        return new SuccessDataResult<AppUser>(
                appUserRepository.save(user), "User saved successfully");
    }

    @Override
    public DataResult<AppRole> saveRole(AppRole role) {
        log.info("Saving new role {} to the database", role.getName());
        return new SuccessDataResult<AppRole>(
                appRoleRepository.save(role), "Role saved successfully");
    }

    @Override
    public DataResult<AppUser> getUser(String userName) {
        Optional<AppUser> user = appUserRepository.findByUserName(userName);
        if (user.isPresent()) {
            log.info("Fetching user {}", userName);
            return new SuccessDataResult<AppUser>(user.get());
        } else {
            log.error("User not found");
            return new ErrorDataResult<AppUser>("User not found");
        }
    }

    @Override
    public Result addRoleToUser(String userName, String roleName) {

        Optional<AppUser> user = appUserRepository.findByUserName(userName);
        Optional<AppRole> role = appRoleRepository.findByName(roleName);

        if (user.isPresent() && role.isPresent()) {
            log.info("Adding role {} to user {}", roleName, userName);
            user.get().getRoles().add(role.get());
            return new SuccessResult(
                    String.format("Role %s added to user %s successfully", roleName, userName));
        }
        if (!user.isPresent()) {
            log.error("User not found {}", userName);
            return new ErrorResult(String.format("User not found with name %s", userName));
        } else {
            log.error("Role not found {}", roleName);
            return new ErrorResult(String.format("Role not found with name %s", roleName));
        }
    }

    @Override
    public DataResult<List<AppUser>> getUsers() {
        log.info("Fetching all users");
        List<AppUser> users = appUserRepository.findAll();
        if (!users.isEmpty()) {
            return new SuccessDataResult<>(
                    users, "Users fetched successfully");
        } else {
            return new EmptyDataResult("There are no users");
        }
    }
}
