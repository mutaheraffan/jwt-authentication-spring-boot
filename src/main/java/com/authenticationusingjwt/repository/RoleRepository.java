package com.authenticationusingjwt.repository;

import com.authenticationusingjwt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
