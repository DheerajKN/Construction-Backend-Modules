package com.dheeraj.startup.server.controller;

import static com.dheeraj.startup.entities.constants.ConstructionConstants.organizationId;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dheeraj.startup.authentication.aspect.AuthenticateAndAuthorize;
import com.dheeraj.startup.entities.dto.UserRequest;
import com.dheeraj.startup.entities.type.Role;
import com.dheeraj.startup.server.service.iface.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@AuthenticateAndAuthorize(role = Role.ADMIN)
	@PostMapping("/organizations/{" + organizationId + "}/users")
	public ResponseEntity<Void> saveUser(@PathVariable(organizationId) UUID orgId,
			@RequestBody UserRequest userRequest) {
		userService.saveUser(orgId, userRequest);
		return ResponseEntity.badRequest().build();
	}
}
