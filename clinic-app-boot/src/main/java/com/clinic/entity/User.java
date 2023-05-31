package com.clinic.entity;

import com.clinic.controller.request.dto.UserRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Specialization specialization;

    @OneToMany(mappedBy = "doctor")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Visit> visitsAsDoctor;

    @OneToMany(mappedBy = "patient")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Visit> visitsAsPatient;

    public static User buildUserFromDTO(UserRequestDTO userDTO) {
        return User.builder()
                .name(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }

    public static void margeUser(User initialUser, User updatedUser) {
        initialUser.setName(updatedUser.getName());
        initialUser.setEmail(updatedUser.getEmail());
    }
}
