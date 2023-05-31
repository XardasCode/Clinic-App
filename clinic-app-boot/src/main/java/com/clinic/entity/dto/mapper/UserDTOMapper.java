package com.clinic.entity.dto.mapper;

import com.clinic.entity.User;
import com.clinic.entity.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return user.getRole().getName().equals("DOCTOR") ?
                applyWithSpecialization(user) :
                applyWithoutSpecialization(user);
    }

    private UserDTO applyWithSpecialization(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole().getName())
                .email(user.getEmail())
                .specialization(user.getSpecialization().getName())
                .build();
    }

    public UserDTO applyWithoutSpecialization(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole().getName())
                .email(user.getEmail())
                .build();
    }
}
