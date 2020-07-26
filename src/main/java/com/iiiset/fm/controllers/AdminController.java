package com.iiiset.fm.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iiiset.fm.model.DbVO;
import com.iiiset.fm.model.UserVO;
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

	@RequestMapping(value = "/admin/loginCheck")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int loginCheck(HttpServletRequest request, @ModelAttribute UserVO vo) throws Exception {

		int result = 0;

		int check = service.loginCheck(vo);

		if (check > 0) {
			HttpSession session = request.getSession(true);
			session.setAttribute("USER", vo);
			result = 1;
		}

		return result;
	}

	@RequestMapping(value = "/admin/db-list")
	public @ResponseBody ModelAndView dbList(HttpServletRequest request, @ModelAttribute DbVO vo) throws Exception {

		ModelAndView mv = new ModelAndView();

		HttpSession session = request.getSession();

		if (session.getAttribute("USER") == null) {
			mv.setViewName("admin/login");
		} else {

			int page = vo.getPage();
			int page_size = 10;
			int page_scope = (page - 1) * page_size;

			vo.setPage_size(page_size);
			vo.setPage_scope(page_scope);
			List<DbVO> list = service.selectDb(vo);

			int total_cnt = service.countDb(vo);

			double total_page_cnt = Math.ceil(total_cnt / (double) page_size);

			mv.addObject("list", list);
			mv.addObject("search", vo);
			mv.addObject("total_pages", total_page_cnt);
			mv.addObject("start_page", page);
			mv.setViewName("admin/db-list");
		}
		return mv;
	}

	@RequestMapping(value = "/insertDb")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int regDb(@ModelAttribute DbVO vo) throws Exception {

		int result = service.insertDb(vo);

		return result;
	}

	@RequestMapping(value = "/admin/updateDb")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int updateDb(@ModelAttribute DbVO vo) throws Exception {

		int result = service.updateDb(vo);

		return result;
	}

	@GetMapping("/download/woori-db.xlsx")
	public void downloadCsv(HttpServletResponse response, @RequestParam("srh_sta_date") String srh_sta_date,
			@RequestParam("srh_end_date") String srh_end_date, @RequestParam("srh_key") String srh_key)
			throws IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=woori_db.xlsx");

		DbVO vo = new DbVO();
		vo.setSrh_sta_date(srh_sta_date);
		vo.setSrh_end_date(srh_end_date);
		vo.setSrh_key(srh_key);
		vo.setExcel_down(1);
		ByteArrayInputStream stream = service.excelDownLoad(vo);
		IOUtils.copy(stream, response.getOutputStream());
	}

}
