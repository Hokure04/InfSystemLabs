package org.example.infsyslab1.service;

import org.example.infsyslab1.dto.UserDTO;
import org.example.infsyslab1.model.User;
import org.example.infsyslab1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

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

    public Optional<UserDTO> getUserById(Long id){
        return userRepository.findById(id).map(this::mapToDTO);
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

}
