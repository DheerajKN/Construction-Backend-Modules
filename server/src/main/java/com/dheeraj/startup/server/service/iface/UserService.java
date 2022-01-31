package com.dheeraj.startup.server.service.iface;

import java.util.UUID;

import com.dheeraj.startup.entities.dto.UserRequest;

public interface UserService {

	void saveUser(UUID orgId, UserRequest userRequest);
}
