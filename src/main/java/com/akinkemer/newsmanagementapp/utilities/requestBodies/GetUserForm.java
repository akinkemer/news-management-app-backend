package com.akinkemer.newsmanagementapp.utilities.requestBodies;

import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetUserForm {
    private String username;
    private String fullName;
    private List<AppRole> roleList;
}
