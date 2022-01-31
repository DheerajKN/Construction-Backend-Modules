package com.dheeraj.startup.authentication.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dheeraj.startup.entities.dto.UserOrgRole;
import com.dheeraj.startup.entities.model.User;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Hasher;

@Component
public class AuthUtility {

	private static Hasher bcryptPasswordEncoder = BCrypt.withDefaults();

	@Value("${bcypt.hash.cost}")
	private int cost;

	public static List<UserOrgRole> convertToUserOrgRoles(User user) {
		return user.getUserRoles().stream().map(userRole -> {
			return UserOrgRole.builder().role(userRole.getUserRole().name()).orgId(userRole.getOrg().getOrgId())
					.build();
		}).collect(Collectors.toList());
	}

	public String convertStringToBCryptHash(final String string) {
		return bcryptPasswordEncoder.hashToString(cost, string.toCharArray());
	}

	public static boolean verifyStringAndHash(final String toCheck, final String bcryptHashString) {
		return BCrypt.verifyer().verify(toCheck.toCharArray(), bcryptHashString).verified;
	}
}
