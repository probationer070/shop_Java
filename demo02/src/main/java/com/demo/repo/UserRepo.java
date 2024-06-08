package com.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.User;

//<Entity, Entity-id>
public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
