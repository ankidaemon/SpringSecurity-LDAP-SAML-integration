package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
		.url("ldap://127.0.0.1:10389/dc=packt,dc=com")
		.managerDn("uid=admin,ou=system")
		.managerPassword("secret")
		.and()
			.userDnPatterns("uid={0},ou=finance")
			.groupSearchBase("ou=groups");
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
