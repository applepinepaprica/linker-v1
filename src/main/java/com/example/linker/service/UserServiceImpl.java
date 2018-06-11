package com.example.linker.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.linker.model.Role;
import com.example.linker.model.User;
import com.example.linker.repository.RoleRepository;
import com.example.linker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		HashSet<Role> h = new HashSet<Role>();
		h.add(roleRepository.findByName("USER"));
		user.setRoles(h);
		
		userRepository.save(user);
	}
}
