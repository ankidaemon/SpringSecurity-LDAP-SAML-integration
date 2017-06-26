package com.demo.authoritygranter;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.stereotype.Component;

/**
 * @author ankidaemon
 *
 */
@Component
public class CustomAuthorityGranter implements AuthorityGranter {

	@Override
	public Set<String> grant(Principal principal) {
		// TODO Auto-generated method stub
		if (principal.getName().equalsIgnoreCase("chief")) {
			return Collections.singleton("ROLE_CHIEF");
		} else if (principal.getName().equalsIgnoreCase("agent")) {
			return Collections.singleton("ROLE_AGENT");
		} else
			return Collections.singleton("ROLE_USER");
	}

}
