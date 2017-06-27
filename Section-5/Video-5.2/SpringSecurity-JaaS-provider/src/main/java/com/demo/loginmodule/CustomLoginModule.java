package com.demo.loginmodule;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author ankidaemon
 *
 */
public class CustomLoginModule implements LoginModule {

	private String userName;
	private String password;
	private Subject subject;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		// TODO Auto-generated method stub
		this.subject = subject;
		NameCallback nameCallback = new NameCallback("prompt");
		PasswordCallback passwordCallback = new PasswordCallback("prompt", false);
		try {
			callbackHandler.handle(new Callback[] { nameCallback, passwordCallback });
		} catch (IOException | UnsupportedCallbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userName = nameCallback.getName();
		password = new String(passwordCallback.getPassword());
	}

	@Override
	public boolean login() throws LoginException {
		// TODO Auto-generated method stub
		if (userName == null || (userName.equalsIgnoreCase(""))) {
			throw new LoginException(userName + " not found.");
		} else if (userName.equalsIgnoreCase("chief")) {
			if (password.equalsIgnoreCase("chiefpassword")) {
				subject.getPrincipals().add(new UsernamePrincipal(userName));
				return true;
			}
		} else if (userName.equalsIgnoreCase("agent")) {
			if (password.equalsIgnoreCase("agentpassword")) {
				subject.getPrincipals().add(new UsernamePrincipal(userName));
				return true;
			}
		}else return false;
		return false;
	}

	@Override
	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
        return true;
	}

	@Override
	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		// TODO Auto-generated method stub
		return true;
	}

	private static class UsernamePrincipal implements Principal, Serializable {
		private final String username;

		public UsernamePrincipal(String username) {
			this.username = username;
		}

		public String getName() {
			return username;
		}

		public String toString() {
			return "Principal [name=" + getName() + "]";
		}

		private static final long serialVersionUID = 8049681145355488137L;
	}
}
