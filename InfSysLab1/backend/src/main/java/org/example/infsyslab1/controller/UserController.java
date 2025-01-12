package org.example.infsyslab1.controller;

import org.example.infsyslab1.dto.UserDTO;
import org.example.infsyslab1.model.User;
import org.example.infsyslab1.security.JWTTokenUtil;
import org.example.infsyslab1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JWTTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(UserService userService, JWTTokenUtil jwtTokenUtil){
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUSer(@RequestBody UserDTO userDTO){
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getALlUsers(){
        List<UserDTO> users = userService.getALLUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id, @RequestHeader("Authorization") String token){
        try {
            String username = jwtTokenUtil.getUsernameFromToken(token.substring(7)); // Убираем "Bearer "
            if (username != null) {
                UserDTO user = userService.getUserById(id);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // При ошибке токена
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        UserDTO updateUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/request-admin")
    public ResponseEntity<Boolean> requestAdmin() {
        String username = getCurrentUsername();
        boolean success = userService.requestAdminApproval(username);
        if (success) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
        }
    }

    @GetMapping("/pending-approvals")
    public ResponseEntity<List<UserDTO>> getPendingApprovals() {
        List<UserDTO> users = userService.getPendingApprovals();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/approve-admin/{username}")
    public ResponseEntity<Boolean> approveAdmin(@PathVariable String username) {
        boolean success = userService.approveAdminRequest(username);
        if (success) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
        }
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
