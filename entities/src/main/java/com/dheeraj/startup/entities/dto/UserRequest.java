package com.dheeraj.startup.entities.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
	private String userName;
	private String userEmail;
	private String userPassword;
	private String role;
}
