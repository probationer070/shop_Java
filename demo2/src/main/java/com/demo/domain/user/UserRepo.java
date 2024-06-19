package com.demo.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserVo, Integer> {
	Optional<UserVo> findByUsername(String username);

	Optional<UserVo> findById(Long id);
}
