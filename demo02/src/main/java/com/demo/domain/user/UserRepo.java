package com.demo.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//<Entity, Entity-id>
public interface UserRepo extends JpaRepository<UserVo, Integer> {
	Optional<UserVo> findByUsername(String username);
}
