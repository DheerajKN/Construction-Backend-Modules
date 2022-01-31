package com.dheeraj.startup.entities.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserOrgRole {
	private String role;
	private UUID orgId;
}
