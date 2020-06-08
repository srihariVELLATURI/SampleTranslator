package com.sampleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sampleapp.model.token.EmailVerificationToken;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    Optional<EmailVerificationToken> findByToken(String token);
}
