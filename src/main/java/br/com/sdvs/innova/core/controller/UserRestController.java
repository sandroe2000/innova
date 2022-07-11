package br.com.sdvs.innova.core.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.sdvs.innova.core.domain.Role;
import br.com.sdvs.innova.core.domain.RoleDto;
import br.com.sdvs.innova.core.domain.User;
import br.com.sdvs.innova.core.domain.UserDto;
import br.com.sdvs.innova.core.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id){
        return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/users/save")
    public ResponseEntity<User> saveUser(@RequestBody UserDto user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/update/{id}")
    public ResponseEntity<User> saveUser(@PathVariable long id, @RequestBody UserDto dto){
        return userService.getUserById(id)
           .map(data -> {
                data.setName(dto.getName());
                data.setUsername(dto.getUsername());
                data.setPassword(dto.getPassword());
                User updated = userService.updateUser(data);
                return ResponseEntity.ok().body(updated);
           }).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path ={"/users/delete/{id}"})
    public ResponseEntity<Object> delete(@PathVariable long id) {
        return userService.getUserById(id)
            .map(data -> {
                userService.deleteById(id);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/roles/save")
    public ResponseEntity<Role> saveRole(@RequestBody RoleDto role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/roles/addroletouser")
    public ResponseEntity<Role> saveRole(@RequestBody RoleToUser roleToUser){
        userService.addRoleToUser(roleToUser.getUserName(), roleToUser.getRoleName());
        return ResponseEntity.ok().build();
    }

}

@Data
class RoleToUser {
    private String userName;
    private String roleName;
}