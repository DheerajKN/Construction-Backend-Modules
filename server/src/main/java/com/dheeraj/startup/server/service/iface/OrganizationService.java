package com.dheeraj.startup.server.service.iface;

import java.util.UUID;

import com.dheeraj.startup.entities.dto.OrganizationRequest;

public interface OrganizationService {

	UUID createOrg(OrganizationRequest orgRequest);

}
