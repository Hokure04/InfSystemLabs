package org.example.infsyslab1.service;

import org.example.infsyslab1.dto.UserDTO;
import org.example.infsyslab1.model.User;
import org.example.infsyslab1.model.enums.Role;
import org.example.infsyslab1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user = userRepository.save(user);
        return mapToDTO(user);
    }

    public List<UserDTO> getALLUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public UserDTO getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user = userRepository.save(user);
        return mapToDTO(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userDTO.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public User authenticate(String username, String password){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User nit found"));
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        return user;
    }

    public User register(String username, String password){
        if(userRepository.existsByUsername(username)){
            throw new RuntimeException("Username already taken");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

}
