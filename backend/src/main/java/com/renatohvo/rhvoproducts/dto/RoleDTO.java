package com.renatohvo.rhvoproducts.dto;

import java.io.Serializable;

import com.renatohvo.rhvoproducts.entities.Role;

public class RoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String authority;
	
	public RoleDTO() {
	}
	
	public RoleDTO(Long id, String authority) {
		this.id = id;
		this.authority = authority;
	}
	
	public RoleDTO(Role entity) {
		this.id = entity.getId();
		this.authority = entity.getAuthority();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
