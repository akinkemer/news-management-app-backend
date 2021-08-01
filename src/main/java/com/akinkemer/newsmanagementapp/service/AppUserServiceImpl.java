package com.akinkemer.newsmanagementapp.service;

import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import com.akinkemer.newsmanagementapp.repository.AppRoleRepository;
import com.akinkemer.newsmanagementapp.repository.AppUserRepository;
import com.akinkemer.newsmanagementapp.utilities.request.GetUserForm;
import com.akinkemer.newsmanagementapp.utilities.result.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;

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
        Optional<AppUser> userOptional=appUserRepository.findByUserName(user.getUserName());
        if(userOptional.isPresent()){
            log.error("Failed to save user {}. Username already taken", user.getName());
            return new ErrorDataResult("Username already taken");
        }
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser savedUser=appUserRepository.save(user);
        addRoleToUser(savedUser.getUserName(),"ROLE_USER");
        return new SuccessDataResult<AppUser>(
                savedUser, "User saved successfully");
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

        boolean hasUserSameRole=user.get().getRoles().stream().anyMatch(appRole -> {
           if(appRole.getName().equals(roleName)){return true;}
           else{ return false;}
        });

        if(hasUserSameRole){
            log.error("User already has this role");
            return new ErrorResult("User already has this role");
        }

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

    @Override
    public DataResult<GetUserForm> getUserByUserName(String username) {
        Optional<AppUser> user = appUserRepository.findByUserName(username);
        if (user.isPresent()) {
            return new SuccessDataResult<GetUserForm>(
                    new GetUserForm(
                            user.get().getUserName(),
                            user.get().getName(),
                            user.get().getRoles()),
                    "Current user fetched successfully"
            );
        } else {
            return new ErrorDataResult<>("Failed to fetch current user");
        }
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String userName = decodedJWT.getSubject();
                DataResult<AppUser> userDataResult = getUser(userName);
                AppUser user = userDataResult.getData();
                String access_token = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles()
                                .stream()
                                .map(AppRole::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token is missing");

        }
    }
}
