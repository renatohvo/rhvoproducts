package com.renatohvo.rhvoproducts.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renatohvo.rhvoproducts.dto.RoleDTO;
import com.renatohvo.rhvoproducts.dto.UserDTO;
import com.renatohvo.rhvoproducts.entities.Role;
import com.renatohvo.rhvoproducts.entities.User;
import com.renatohvo.rhvoproducts.repositories.RoleRepository;
import com.renatohvo.rhvoproducts.repositories.UserRepository;
import com.renatohvo.rhvoproducts.services.exceptions.DatabaseException;
import com.renatohvo.rhvoproducts.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> list = userRepository.findAll(pageable);
		return list.map(entity -> new UserDTO(entity));
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = userRepository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("ENTITY NOT FOUND"));
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO insert(UserDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto , entity);
		entity = userRepository.save(entity);
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		try {
			User entity = userRepository.getOne(id);
			copyDtoToEntity(dto , entity);
			entity = userRepository.save(entity);
			return new UserDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID NOT FOUND: " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID NOT FOUND: " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("INTEGRITY VIOLATION");
		}
	}

	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		
		entity.getRoles().clear();
		for (RoleDTO roleDto : dto.getRoles()) {
			Role role = roleRepository.getOne(roleDto.getId());
			entity.getRoles().add(role);
		}
	}
	
}
