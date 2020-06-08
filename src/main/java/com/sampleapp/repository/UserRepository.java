package com.sampleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sampleapp.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);
    
    Boolean existsByPhone(String phone);
    
    
    Optional<User> findByUsernameOrEmail(String username, String email);
    
    List<User> findByIdIn(List<Long> userIds);
    
    Optional<User> findByPhone(String phone);

}
