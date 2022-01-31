package com.dheeraj.startup.authentication.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dheeraj.startup.entities.type.Enforcement;
import com.dheeraj.startup.entities.type.Role;
import com.dheeraj.startup.entities.type.RoleStrictness;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthenticateAndAuthorize {
	Role role() default Role.ADMIN;

	Enforcement enforce() default Enforcement.ROLE_LEVEL;

	RoleStrictness role_strict() default RoleStrictness.DEFAULT;
}