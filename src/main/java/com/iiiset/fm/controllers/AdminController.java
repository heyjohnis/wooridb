package com.iiiset.fm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iiiset.fm.model.DbVO;
import com.iiiset.fm.service.AdminService;

@Controller
@EnableAutoConfiguration
public class AdminController {

	@Autowired
	AdminService service;

	@RequestMapping(value = "/admin/login")
	public @ResponseBody ModelAndView login() throws Exception {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("admin/login");
		return mv;
	}

	@RequestMapping(value = "/admin/db-list")
	public @ResponseBody ModelAndView dbList(@ModelAttribute DbVO vo) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		int page = vo.getPage();
		int page_size = 10;
		int page_scope = (page - 1) * page_size;
		
		vo.setPage_size(page_size);
		vo.setPage_scope(page_scope);
		List<DbVO> list = service.selectDb(vo);
		
		int total_cnt = service.countDb(vo);
		
		double total_page_cnt = Math.ceil(total_cnt / (double)page_size );
		
		mv.addObject("list", list);
		mv.addObject("search", vo);
		mv.addObject("total_pages", total_page_cnt);
		mv.addObject("start_page", page);
		mv.setViewName("admin/db-list");
		return mv;
	}

	@RequestMapping(value = "/insertDb")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int regDb(@RequestParam("cust_nm") String cust_nm,
			@RequestParam("cust_tel") String cust_tel, @RequestParam("comment") String comment) throws Exception {

		DbVO vo = new DbVO();
		vo.setCust_nm(cust_nm);
		vo.setCust_tel(cust_tel);
		vo.setComment(comment);
		
		int result = service.insertDb(vo);

		return result;
	}
	
	@RequestMapping(value = "/admin/updateDb")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int updateDb(@ModelAttribute DbVO vo) throws Exception {
		
		System.out.println("cust_nm : " + vo.getCust_nm());
		int result = service.updateDb(vo);

		return result;
	}

}
