package com.demo.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .defaultRequest(get("/").with(user("anyUser").roles("anyRole")))
                .build();
    }
    
    @Test
    public void postWithCsrf() throws Exception {
        mvc
            .perform(post("/customlogout").with(csrf()))
            .andExpect(redirectedUrl("/"));
    }
    
    @Test
    public void postWithCsrfInHeader() throws Exception {
        mvc
            .perform(post("/customlogout").with(csrf().asHeader()))
            .andExpect(redirectedUrl("/"));
    }
    
    @Test
    public void postWithInvalidCsrf() throws Exception {
        mvc
            .perform(post("/customlogout").with(csrf().useInvalidToken()))
            .andExpect((status().isForbidden()));
    }
    
    @Test
    public void getUpdateChiefPage() throws Exception {
        mvc.perform(get("/chief/updateProfile").with(user("chief").password("password").roles("CHIEF")))
        .andExpect((status().isOk()));
    }
    
    @Test
    public void getUpdateAgentPage() throws Exception {
        mvc.perform(get("/agent/updateProfile").with(anonymous()))
        .andExpect((status().isUnauthorized()));
    }
      
    @Test
    public void getHome() throws Exception {
    	mvc.perform(get("/").with(httpBasic("agent","userpassword")))
    	.andExpect((status().isOk()));
    }   
}
