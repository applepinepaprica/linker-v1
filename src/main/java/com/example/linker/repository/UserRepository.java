package com.example.linker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.linker.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUsername(String username);
}
