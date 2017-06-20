package com.demo.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.demo.config.SecurityConfig;
import com.demo.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class,SecurityConfig.class})
@WebAppConfiguration
public class SecurityMockMvc {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }
    
    @Test
    public void performLoginWithUserPassword() throws Exception {
    	mvc.perform(formLogin("/login").user("chief").password("password"))
    	.andExpect(authenticated().withUsername("chief").withRoles("CHIEF"));
    }
    
    @Test
    public void performLoginWithParameterSet() throws Exception {
    	mvc.perform(formLogin("/login").user("user","agent").password("pass","userpassword"))
    	.andExpect(unauthenticated());
    }
    
    @Test
    public void performLogout() throws Exception {
        mvc.perform(logout("/customlogout"))
        .andExpect(redirectedUrl("/"));
    }  
}
