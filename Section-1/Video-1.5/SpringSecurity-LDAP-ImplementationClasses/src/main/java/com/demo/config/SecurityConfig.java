package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.PasswordComparisonAuthenticator;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;

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
		.passwordCompare()
			.passwordEncoder(new PlaintextPasswordEncoder())
				.passwordAttribute("userPassword")
		.and()
		.contextSource()
			.root("dc=packt,dc=com")
			.ldif("classpath:packt.ldif")
		.and()
			.userDnPatterns("uid={0},ou=finance")
			.groupSearchBase("ou=groups");
		
		//auth.authenticationProvider(authProvider());
	}

	
	
	LdapAuthenticationProvider authProvider(){
		DefaultSpringSecurityContextSource contextSource = 
				new DefaultSpringSecurityContextSource("ldap://packt.com:389/");
		
		contextSource.setBase("dc=packt,dc=com");
				
		PasswordComparisonAuthenticator authenticator=new PasswordComparisonAuthenticator(contextSource);
		authenticator.setUserDnPatterns(new String[]{"uid={0},ou=finance"});
		
		DefaultLdapAuthoritiesPopulator authorizer=new DefaultLdapAuthoritiesPopulator(contextSource,"ou=groups");
				
		LdapAuthenticationProvider provider=new LdapAuthenticationProvider(authenticator,authorizer);
		return provider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.regexMatchers("/chief/.*").hasRole("ADMIN")
		.regexMatchers("/agent/.*").access("hasRole('USER')")
		.anyRequest()
				.authenticated()
				.and().httpBasic()	
				.and().requiresChannel().anyRequest().requiresInsecure();

		http.formLogin().loginPage("/login").permitAll();
		http.logout().logoutSuccessUrl("/");
		http.exceptionHandling().accessDeniedPage("/accessDenied");
		
	}

}
