package br.com.sdvs.innova.core.service;

import java.util.List;
import java.util.Optional;

import br.com.sdvs.innova.core.domain.Role;
import br.com.sdvs.innova.core.domain.User;

public interface UserService {

    User getUser(String username);
    Optional<User> getUserById(Long id);
    User saveUser(User user);    
    User updateUser(User user);
    List<User> getUsers();
    void deleteById(Long id);
    Role saveRole(Role role);
    Role getRole(String role);
    void addRoleToUser(String username, String roleName);
}
