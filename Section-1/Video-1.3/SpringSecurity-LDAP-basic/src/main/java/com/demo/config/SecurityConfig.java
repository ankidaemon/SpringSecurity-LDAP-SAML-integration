package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

/**
 * @author ankidaemon
 *
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.demo.config")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.ldapAuthentication()
		.contextSource()
		//.url("ldap://packt.com:389/dc=packt,dc=com")
		//.url("ldaps://packt.com:636/dc=packt,dc=com")
		.root("dc=packt,dc=com")
		.ldif("classpath:packt.ldif")
		.and()
			.userDnPatterns("uid={0},ou=finance")
			.groupSearchBase("ou=groups");
	
	//	auth.ldapAuthentication()
	//		.userSearchFilter("{uid={0}}")
	//		.userSearchBase("ou=finance").groupSearchBase("ou=groups");
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.regexMatchers("/chief/.*").hasRole("ADMIN")
		.regexMatchers("/agent/.*").access("hasRole('USER')")
		.anyRequest()
				.authenticated()
				.and().httpBasic()	
				.and().requiresChannel().anyRequest().requiresSecure();

		http.formLogin().loginPage("/login").permitAll();
		http.logout().logoutSuccessUrl("/");
		http.exceptionHandling().accessDeniedPage("/accessDenied");
	}

}
