package com.dheeraj.startup.entities.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
	ADMIN(5), EMPLOYEE(4), CONTRACTOR(3), LABOURER(2), READ_ONLY(1);

	private int order;

	public Role getRole(final String roleName) {
		return Role.valueOf(roleName.toUpperCase());
	}
}
