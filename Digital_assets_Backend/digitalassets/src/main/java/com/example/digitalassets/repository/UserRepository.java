package com.example.digitalassets.repository;

import com.example.digitalassets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
