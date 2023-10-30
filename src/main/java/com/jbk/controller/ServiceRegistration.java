package com.jbk.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceRegistration {
	 @Autowired
	    private DaoRegistration daoRegistration;

	    public Map<String,Object> login(Registration reg) {
	    	Registration regs = daoRegistration.login(reg);
	    	Map<String,Object> map=new HashMap<String,Object>();
	       if(regs!=null) {
	    	   map.put("msg", "Valid User");
	    	   map.put("user",regs);
	       }else {
	    	   map.put("msg", "InValid User");
	    	   map.put("user",null);
	       }
	       return map;
	        }
}
