package com.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.demo.to.UserTo;

/**
 * @author ankidaemon
 *
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
     
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView visitHome() {
    	ModelAndView mav = new ModelAndView("home");
        return mav;
    }
		
    @RequestMapping(value="/customlogout", method = RequestMethod.POST)
    public void logOut(){
    }
    
    @RequestMapping(value = "/chief/updateProfile", method = RequestMethod.GET)
	public ModelAndView updateChiefPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chiefUpdate");
		return mav;
	}
    
    @RequestMapping(value = "/agent/updateProfile", method = RequestMethod.GET)
	public ModelAndView updateUserPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("userUpdate");
		return mav;
	}
    
    @RequestMapping(value = "/withUserNameTest", method = RequestMethod.GET)
	public ModelAndView withUserNameTest(UserTo userTo) {
    	ModelAndView mav = new ModelAndView("home");
    	mav.addObject("userTo",userTo);
        return mav;
	}
    
    @RequestMapping(value="/accessDenied", method = RequestMethod.GET)
    public ModelAndView accessDenied() {
    	ModelAndView mav = new ModelAndView("accessDenied");
        return mav;
    }
    
}