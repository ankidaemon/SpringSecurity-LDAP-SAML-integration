package com.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ankidaemon
 *
 */
@Controller
public class HomeController {
     
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView visitHome() {
    	ModelAndView mav = new ModelAndView("home");
        return mav;
    }
    
    @RequestMapping(value = "/chief/updateProfile", method = RequestMethod.GET)
   	public ModelAndView updatePage(HttpServletRequest request) {
   		ModelAndView mav = new ModelAndView();
   		mav.setViewName("chiefUpdate");
   		return mav;
   	}
     
       @RequestMapping(value="/accessDenied", method = RequestMethod.GET)
       public ModelAndView accessDenied() {
       	ModelAndView mav = new ModelAndView("accessDenied");
           return mav;
       }
    
}
