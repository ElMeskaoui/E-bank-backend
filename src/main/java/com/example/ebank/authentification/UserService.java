package com.example.ebank.authentification;

import com.example.ebank.entities.Role;
import com.example.ebank.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getAllUsers();
}
