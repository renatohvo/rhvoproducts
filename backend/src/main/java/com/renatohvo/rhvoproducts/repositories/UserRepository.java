package com.renatohvo.rhvoproducts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renatohvo.rhvoproducts.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
}
