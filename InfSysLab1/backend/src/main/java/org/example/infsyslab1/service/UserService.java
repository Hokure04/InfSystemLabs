package org.example.infsyslab1.service;

import org.example.infsyslab1.dto.UserDTO;
import org.example.infsyslab1.model.User;
import org.example.infsyslab1.model.enums.Role;
import org.example.infsyslab1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (userRepository.count() == 0) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }

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
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user = userRepository.save(user);
        return mapToDTO(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
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

    public User register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already taken");
        }
        boolean adminExists = userRepository.findAll().stream().anyMatch(user -> user.getRole() == Role.ADMIN);

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(adminExists ? Role.USER : Role.ADMIN);
        return userRepository.save(user);
    }

    public boolean requestAdminApproval(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ADMIN) {
            return false;
        }

        user.setRole(Role.USER);
        userRepository.save(user);
        return true;
    }

    public List<UserDTO> getPendingApprovals() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.USER)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public boolean approveAdminRequest(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ADMIN) {
            return false;
        }

        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return true;
    }

}
