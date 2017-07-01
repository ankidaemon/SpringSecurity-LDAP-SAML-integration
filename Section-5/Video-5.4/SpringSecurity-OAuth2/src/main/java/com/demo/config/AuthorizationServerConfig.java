package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private static String REALM="SpringSecurity-OAuth2";
	
	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UserApprovalHandler handler;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authManager;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory()
	        .withClient("MI6")
	        .authorizedGrantTypes("authorization_code", "refresh_token")
	        .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT") // Generally used in case of Client tokens
            .scopes("read","write")
            .secret("mi6password")
            .redirectUris("http://ankidaemon.com")
            .accessTokenValiditySeconds(180)
            .refreshTokenValiditySeconds(1800)
		.and()
			.withClient("MI5")
			.authorizedGrantTypes("authorization_code")
	        .authorities("ROLE_CLIENT") // Generally used in case of Client tokens
            .scopes("read")
            .secret("mi5password")
            .redirectUris("http://ankidaemon.com")
            .accessTokenValiditySeconds(180)
            .refreshTokenValiditySeconds(1800);
		
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userApprovalHandler(handler)
				.authenticationManager(authManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.realm(REALM+"/client");
	}

}