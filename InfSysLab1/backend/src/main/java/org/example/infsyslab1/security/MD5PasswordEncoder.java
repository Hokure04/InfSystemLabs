package org.example.infsyslab1.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for(byte bytes : hashBytes){
                hexString.append(String.format("%02x", bytes));
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Error encoding password using MD5", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodePassword){
        return encodePassword.equals(encode(rawPassword));
    }
}
