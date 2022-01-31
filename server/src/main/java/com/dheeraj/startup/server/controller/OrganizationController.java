package com.dheeraj.startup.server.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dheeraj.startup.entities.dto.OrganizationRequest;
import com.dheeraj.startup.server.service.iface.OrganizationService;

@RestController
public class OrganizationController {

	@Autowired
	private OrganizationService orgService;

	@PostMapping(value = "/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addOrganization(@RequestBody OrganizationRequest orgRequest) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("org_id", orgService.createOrg(orgRequest));
		return ResponseEntity.status(201).body(json.toString());
	}
}
