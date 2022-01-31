package com.dheeraj.startup.server.service.impl;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dheeraj.startup.authentication.utils.AuthUtility;
import com.dheeraj.startup.entities.dto.UserRequest;
import com.dheeraj.startup.entities.model.User;
import com.dheeraj.startup.entities.model.UserRole;
import com.dheeraj.startup.entities.repo.OrganizationRepository;
import com.dheeraj.startup.entities.repo.UserRepository;
import com.dheeraj.startup.entities.repo.UserRoleRepository;
import com.dheeraj.startup.entities.type.Role;
import com.dheeraj.startup.server.service.iface.UserService;
//import com.dheeraj.startup.utils.AuthUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private OrganizationRepository orgRepository;
	private UserRoleRepository userRoleRepository;
	private AuthUtility authUtils;

	@Override
	public void saveUser(UUID orgId, UserRequest userRequest) {
		User user = new User();
		BeanUtils.copyProperties(userRequest, user);
		user.setUserPassword(authUtils.convertStringToBCryptHash(userRequest.getUserPassword()));
		user = userRepository.save(user);
		UserRole userRole = UserRole.builder().org(orgRepository.findById(orgId).get()).user(user)
				.userRole(Role.valueOf(userRequest.getRole())).build();
		userRoleRepository.save(userRole);
	}

}
