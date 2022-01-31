package com.dheeraj.startup.entities.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dheeraj.startup.entities.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

}
