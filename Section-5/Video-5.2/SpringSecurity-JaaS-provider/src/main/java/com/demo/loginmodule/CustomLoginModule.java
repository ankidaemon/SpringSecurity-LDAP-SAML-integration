package com.demo.loginmodule;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 * @author ankidaemon
 *
 */
public class CustomLoginModule implements LoginModule {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomLoginModule.class);

	private String userName;
	private String password;
	private Subject subject;
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		// TODO Auto-generated method stub
		this.subject=subject;
		NameCallback nameCallback = new NameCallback("prompt");
		PasswordCallback passwordCallback = new PasswordCallback("prompt", false);
		try {
			callbackHandler.handle(new Callback[] { nameCallback, passwordCallback });
		} catch (IOException | UnsupportedCallbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userName = nameCallback.getName();
		password = passwordCallback.getPassword().toString();
	}

	@Override
	public boolean login() throws LoginException {
		// TODO Auto-generated method stub
		logger.debug("login called");
		if (userName == null || (userName.equalsIgnoreCase("")) || !(userName.equalsIgnoreCase("chief"))
				|| !(userName.equalsIgnoreCase("agent"))) {
			throw new LoginException(userName + " not found.");
		} else if (userName.equalsIgnoreCase("chief")) {
			if (password.equalsIgnoreCase("chiefpassword")){
				subject.getPrincipals().add(new UsernamePrincipal(userName));
				logger.debug("setting chief");
				return true;
			}
		} else if (userName.equalsIgnoreCase("agent")) {
			if (password.equalsIgnoreCase("agentpassword")){
				subject.getPrincipals().add(new UsernamePrincipal(userName));
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		return false;
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
