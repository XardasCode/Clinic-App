package com.clinic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "statuses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "status")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Visit> visits;
}
