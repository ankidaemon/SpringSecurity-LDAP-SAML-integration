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
		.url("ldap://packt.com:389/dc=packt,dc=com")
		//.url("ldaps://packt.com:636/dc=packt,dc=com")  //For TLS 
		.managerDn("managerDN")
		.managerPassword("managerPassword")
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
		.anyRequest()
				.authenticated()
				.and().httpBasic()	
				.and().requiresChannel().anyRequest().requiresSecure();

		http.formLogin().loginPage("/login").permitAll();
		http.logout().logoutSuccessUrl("/");
		
	}

}
