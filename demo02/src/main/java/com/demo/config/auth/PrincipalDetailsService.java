package com.demo.config.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.entity.User;
import com.demo.repo.UserRepo;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntityOptional = userRepo.findByUsername(username);
        if (userEntityOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        User userEntity = userEntityOptional.get();
        System.out.println("Password ===>"+userEntity.getPassword());
        
        return new PrincipalDetails(userEntity);
    }
}
