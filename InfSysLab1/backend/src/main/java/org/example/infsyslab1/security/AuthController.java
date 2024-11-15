package org.example.infsyslab1.security;

import org.example.infsyslab1.model.User;
import org.example.infsyslab1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password){
        try{
            User user = userService.authenticate(username, password);
            String token = jwtTokenUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password){
        try{
            User user = userService.register(username, password);
            String token = jwtTokenUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        }catch (RuntimeException e) {
            e.printStackTrace();
            if(e.getMessage().contains("Username already taken")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while registration");
        }

    }

}
