package com.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "SpringSecurity-OAuth2";
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.requestMatchers().regexMatchers("/chief/.*")
		.and()
		.authorizeRequests()
		.regexMatchers("/agent/.*").access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('AGENT'))")
		.regexMatchers("/chief/.*").access("#oauth2.hasScope('write') and (hasRole('CHIEF'))")
		.and()
		.anonymous().disable()
		.csrf().disable()
		.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

}