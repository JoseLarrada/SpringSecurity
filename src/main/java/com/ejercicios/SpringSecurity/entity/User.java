package com.ejercicios.SpringSecurity.entity;

import com.ejercicios.SpringSecurity.Util.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "\"user\"") //cambiar nombre de la tabla
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String password;
    @Enumerated(EnumType.STRING) //Se guardara el nombre del rol
    private Role role;
}
