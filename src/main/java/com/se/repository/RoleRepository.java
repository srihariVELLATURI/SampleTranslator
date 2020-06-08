package com.se.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.se.model.Role;
import com.se.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRole(RoleName roleName);

}
