package com.dheeraj.startup.entities.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationRequest {
	private String name;
	private UserRequest userRequest;
}
