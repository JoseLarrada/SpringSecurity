package com.ejercicios.SpringSecurity.Repository;

import com.ejercicios.SpringSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
