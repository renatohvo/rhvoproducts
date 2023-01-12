package com.renatohvo.rhvoproducts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renatohvo.rhvoproducts.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
}
