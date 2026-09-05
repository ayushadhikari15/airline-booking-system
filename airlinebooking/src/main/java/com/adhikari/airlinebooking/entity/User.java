package com.adhikari.airlinebooking.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    @NotNull @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;




}
