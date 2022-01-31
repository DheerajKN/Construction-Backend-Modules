package com.dheeraj.startup.server.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dheeraj.startup.entities.dto.OrganizationRequest;
import com.dheeraj.startup.entities.model.Organization;
import com.dheeraj.startup.entities.repo.OrganizationRepository;
import com.dheeraj.startup.entities.type.Role;
import com.dheeraj.startup.server.service.iface.OrganizationService;
import com.dheeraj.startup.server.service.iface.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationServiceImpl implements OrganizationService {

	private OrganizationRepository orgRepository;
	private UserService userService;

	@Override
	public UUID createOrg(OrganizationRequest orgRequest) {
		Organization org = Organization.builder().orgName(orgRequest.getName()).build();

		final UUID orgId = orgRepository.save(org).getOrgId();
		System.out.println(orgId);
		orgRequest.getUserRequest().setRole(Role.ADMIN.name());
		userService.saveUser(orgId, orgRequest.getUserRequest());
		return orgId;
	}

}
