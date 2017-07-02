package com.demo.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankidaemon
 *
 */
@RestController
public class HomeController {
     
    @RequestMapping(value = "/chief/internationCriminals", method = RequestMethod.GET)
   	public ResponseEntity<List<String>> updatePage(HttpServletRequest request) {
   		List<String> internationCriminals=new ArrayList<String>();
   		internationCriminals.add("xyzDrugLord");
   		internationCriminals.add("xyzMafia");
   		internationCriminals.add("xyzDon");
   		return new ResponseEntity<List<String>>(internationCriminals, HttpStatus.OK);
   	}    
}
