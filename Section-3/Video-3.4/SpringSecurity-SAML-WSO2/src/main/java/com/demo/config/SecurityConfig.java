package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLEntryPoint;
import org.springframework.security.saml.metadata.MetadataGeneratorFilter;
import org.springframework.security.saml.websso.WebSSOProfileOptions;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


/**
 * @author ankidaemon
 *
 */
@Configuration
@EnableWebSecurity
@ImportResource({"classpath:securityContext.xml"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	MetadataGeneratorFilter metadataGeneratorFilter;
	
	@Autowired
	FilterChainProxy samlFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(samlAuthenticationProvider());
	}

	 @Bean
	 public SAMLAuthenticationProvider samlAuthenticationProvider() {
	    SAMLAuthenticationProvider samlAuthenticationProvider = new SAMLAuthenticationProvider();
	    //custom userDetailService can be implemented here.
	    samlAuthenticationProvider.setForcePrincipalAsString(false);
	    return samlAuthenticationProvider;
	 }
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
        	.antMatchers("/logout.jsp").permitAll()
       		.antMatchers("/saml/**").permitAll()
		.anyRequest()
				.authenticated()
				.and().httpBasic().authenticationEntryPoint(samlEntryPoint())
				.and().requiresChannel().anyRequest().requiresInsecure();
		
		http
        	.addFilterBefore(metadataGeneratorFilter, ChannelProcessingFilter.class)
        	.addFilterAfter(samlFilter, BasicAuthenticationFilter.class);
		
		http.csrf().disable();
				
	}

	@Bean
	public AuthenticationEntryPoint samlEntryPoint() {
		// TODO Auto-generated method stub
		SAMLEntryPoint samlEntryPoint = new SAMLEntryPoint();
		WebSSOProfileOptions webSSOProfileOptions=new WebSSOProfileOptions();
		webSSOProfileOptions.setIncludeScoping(false);
		webSSOProfileOptions.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        samlEntryPoint.setDefaultProfileOptions(webSSOProfileOptions);
        return samlEntryPoint;
	}
	
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler(){
		SavedRequestAwareAuthenticationSuccessHandler successHandler=new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl("/");
		return successHandler;
	}
	
	@Bean
	public SimpleUrlAuthenticationFailureHandler failureRedirectHandler(){
		SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
		failureHandler.setUseForward(true);
		failureHandler.setDefaultFailureUrl("/loginFailed.jsp");
		return failureHandler;
	}
	
}
