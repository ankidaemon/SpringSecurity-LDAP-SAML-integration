package com.demo.config;

import java.util.HashMap;

import javax.security.auth.login.AppConfigurationEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.demo.authoritygranter.CustomAuthorityGranter;

/**
 * @author ankidaemon
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.demo.config")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomAuthorityGranter customAuthorityGranter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(jaasAuthProvider());
	}

	@Bean
	DefaultJaasAuthenticationProvider jaasAuthProvider() {
		AppConfigurationEntry appConfig = new AppConfigurationEntry("com.demo.loginmodule.CustomLoginModule",
				AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, new HashMap());
		
		InMemoryConfiguration memoryConfig = new InMemoryConfiguration(new AppConfigurationEntry[] { appConfig });

		DefaultJaasAuthenticationProvider def = new DefaultJaasAuthenticationProvider();
		def.setConfiguration(memoryConfig);
		def.setAuthorityGranters(new AuthorityGranter[] { customAuthorityGranter });
		return def;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.regexMatchers("/chief/.*").hasRole("CHIEF")
		.regexMatchers("/agent/.*").access("hasRole('AGENT')")
				.anyRequest().authenticated().and().httpBasic().and().requiresChannel()
				.anyRequest().requiresInsecure();

		http.formLogin().loginPage("/login").permitAll();
		http.logout().logoutSuccessUrl("/");
		http.exceptionHandling().accessDeniedPage("/accessDenied");
		

	}

}
