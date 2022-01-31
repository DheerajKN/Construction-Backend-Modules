package com.dheeraj.startup.authentication.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.HandlerMapping;

import com.dheeraj.startup.authentication.utils.JWTUtility;
import com.dheeraj.startup.entities.model.User;
import com.dheeraj.startup.entities.model.UserRole;
import com.dheeraj.startup.entities.repo.UserRepository;
import com.dheeraj.startup.entities.type.Role;
import com.dheeraj.startup.entities.type.RoleStrictness;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dheeraj.startup.entities.constants.ConstructionConstants;
import com.dheeraj.startup.entities.exception.BeanNotFoundException;

import lombok.AllArgsConstructor;

@Aspect
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticateAndAuthorizeAspect {
	private UserRepository userRepository;
	private HttpServletRequest httpServletRequest;
	private ObjectMapper oMapper;
	private JWTUtility jwtUtils;

	/**
	 * First check for the presence of all the required data Second check if the
	 * token is valid Third whether the user has any connection to the org id
	 * provided Fourth if there is a record present check if the provided role in
	 * request is greater than the annotation
	 */
	@Around("@annotation(com.dheeraj.startup.authentication.aspect.AuthenticateAndAuthorize)")
	public void myAdvice(ProceedingJoinPoint call) throws Throwable {
		IntBinaryOperator ge = (x, y) -> x >= y ? 1 : -1;
		IntBinaryOperator eq = (x, y) -> x == y ? 1 : -1;

		System.out.println("entered");
		MethodSignature signature = (MethodSignature) call.getSignature();
		Method method = signature.getMethod();
		AuthenticateAndAuthorize annotation = method.getAnnotation(AuthenticateAndAuthorize.class);

		// Presence check done
		Role annotatedRole = annotation.role();
		UUID orgId = getTheProvidedOrgID(signature, call);
		String jwtToken = Optional.ofNullable(httpServletRequest.getHeader("Authorization"))
				.orElseThrow(() -> new BeanNotFoundException("Auth Header is Empty", HttpStatus.NOT_ACCEPTABLE));
		call.proceed();

		switch (annotation.enforce()) {
			case PREMIT_ALL:
				checkForTokenValidity(jwtToken);
				call.proceed();
				break;
			case ORG_LEVEL:
				checkForTokenValidity(jwtToken);
				orgLevelChecks(jwtToken, orgId);
				call.proceed();
				break;
			case ROLE_LEVEL:
				checkForTokenValidity(jwtToken);
				UserRole userRoleInThatOrg = orgLevelChecks(jwtToken, orgId);
				roleLevelChecks(userRoleInThatOrg, annotatedRole,
						annotation.role_strict() == RoleStrictness.DEFAULT ? ge : eq);
				call.proceed();
				break;
			default:
				break;
		}

	}

	private static <X extends Throwable> void ifFalseThrowException(final boolean value,
			Supplier<? extends X> exceptionSupplier) throws X {
		if (value == false) {
			throw exceptionSupplier.get();
		}
	}

	private void checkForTokenValidity(final String jwtToken) throws BeanNotFoundException {
		// Check if provided token has not expired
		jwtUtils.validateToken(jwtToken);
	}

	private UserRole orgLevelChecks(final String jwtToken, final UUID orgId) throws BeanNotFoundException {
		// Check if record present has any connection with provided orgId
		User loggedInUser = userRepository.findById(jwtUtils.getUserIDFromToken(jwtToken)).get();
		Optional<UserRole> userRoleInThatOrg = loggedInUser.getUserRoles().stream()
				.filter(userRole -> userRole.getOrg().getOrgId() == orgId).findFirst();
		return userRoleInThatOrg
				.orElseThrow(() -> new BeanNotFoundException("Access to this Resource denied", HttpStatus.CONFLICT));
	}

	private void roleLevelChecks(final UserRole userRoleInThatOrg, final Role annotatedRole,
			final IntBinaryOperator operator) throws BeanNotFoundException {
		// check if loggedIn user role required for this access is greater or equal to
		// annotated
		ifFalseThrowException(
				operator.applyAsInt(userRoleInThatOrg.getUserRole().getOrder(), annotatedRole.getOrder()) > 0,
				() -> new BeanNotFoundException("Higher Privileges required", HttpStatus.NOT_ACCEPTABLE));
	}

	private UUID getTheProvidedOrgID(MethodSignature signature, ProceedingJoinPoint call)
			throws NoSuchMethodException, SecurityException, BeanNotFoundException {

		String methodName = signature.getMethod().getName();
		Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
		Annotation[][] annotationss = call.getTarget().getClass().getMethod(methodName, parameterTypes)
				.getParameterAnnotations();

		for (Annotation[] annotations : annotationss) {
			for (Annotation annotation : annotations) {
				if (annotation instanceof PathVariable
						&& ((PathVariable) annotation).value().equals(ConstructionConstants.organizationId)) {
					// System.out.println(call);
					// System.out.println(" annotation = " + annotation);
					// System.out.println(" annotation value = " + ((PathVariable)
					// annotation).value());
					Map<?, ?> pathVariables = oMapper.convertValue(
							httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE),
							Map.class);
					return UUID.fromString(String.valueOf(pathVariables.get(ConstructionConstants.organizationId)));
				}
			}
		}
		throw new BeanNotFoundException("orgId not provided", HttpStatus.BAD_REQUEST);
	}
}