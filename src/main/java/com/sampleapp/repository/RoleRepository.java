package com.sampleapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sampleapp.model.Role;
import com.sampleapp.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRole(RoleName roleName);

}
