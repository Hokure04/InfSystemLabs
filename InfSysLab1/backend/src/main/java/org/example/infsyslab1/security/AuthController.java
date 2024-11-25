package org.example.infsyslab1.security;

import org.example.infsyslab1.model.User;
import org.example.infsyslab1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        try {
            User user = userService.authenticate(username, password);
            String token = jwtTokenUtil.generateToken(user.getUsername());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("id", user.getId());
            response.put("username", user.getUsername());
            response.put("role", user.getRole().name());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
        }
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> params){
        String username = params.get("username");
        String password = params.get("password");
        try{
            User user = userService.register(username, password);
            String token = jwtTokenUtil.generateToken(user.getUsername());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("id", user.getId());
            response.put("username", user.getUsername());
            response.put("role", user.getRole().name());
            return ResponseEntity.ok(response);
        }catch (RuntimeException e) {
            e.printStackTrace();
            if(e.getMessage().contains("Username already taken")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","Username already taken"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error while registration"));
        }

    }

}
