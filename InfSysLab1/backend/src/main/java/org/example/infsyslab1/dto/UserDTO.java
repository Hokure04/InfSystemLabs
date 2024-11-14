package org.example.infsyslab1.dto;

import lombok.Data;
import org.example.infsyslab1.model.enums.Role;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Role role;
}
