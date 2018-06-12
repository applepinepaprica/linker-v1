package com.example.linker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.linker.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Role findByName(String role);
}