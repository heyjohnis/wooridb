package com.iiiset.fm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iiiset.fm.service.AdminService;



@Controller
@EnableAutoConfiguration
public class MainController {
	
	@Autowired
	AdminService service;

	
	@RequestMapping(value = "/db/reg")
	public @ResponseBody ModelAndView dbReg()  throws Exception{
		
		ModelAndView mv = new ModelAndView();

		mv.setViewName("login");
		return mv;
	}

	@RequestMapping(value = "/topsfit")
	public @ResponseBody ModelAndView topsfit()  throws Exception{
		
		ModelAndView mv = new ModelAndView();

		mv.setViewName("topsfit");
		mv.addObject("gdcd", "001");
		return mv;
	}
	
}
