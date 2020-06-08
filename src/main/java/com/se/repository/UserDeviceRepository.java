package com.se.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.se.model.UserDevice;
import com.se.model.token.RefreshToken;

import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    @Override
    Optional<UserDevice> findById(Long id);

    Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken);

    Optional<UserDevice> findByUserId(Long userId);
}
