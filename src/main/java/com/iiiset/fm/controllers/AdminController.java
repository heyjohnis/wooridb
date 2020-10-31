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
import com.iiiset.fm.model.TeamVO;
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
	
	
	@RequestMapping(value = "/admin/dbAdmin")
	public @ResponseBody ModelAndView dbAdmin(HttpServletRequest request, @ModelAttribute DbVO vo) throws Exception {

		ModelAndView mv = new ModelAndView();

		HttpSession session = request.getSession();

		if (session.getAttribute("USER") == null) {
			mv.setViewName("admin/login");
		} else {
			UserVO userVO = (UserVO)session.getAttribute("USER");
			String grade = userVO.getGrade();
			String manager = userVO.getUser_id();
			vo.setAdminYn(1);
			vo.setManager(manager);
			List<DbVO> list = service.selectDb(vo);
			
			mv.addObject("user_id", manager);
			mv.addObject("list", list);
			mv.addObject("grade", grade);
			mv.setViewName("admin/dbAdmin");
		}
		return mv;
	}
	
	@RequestMapping(value = "/admin/user")
	public @ResponseBody ModelAndView user(HttpServletRequest request, @ModelAttribute UserVO vo) throws Exception {

		ModelAndView mv = new ModelAndView();
		
		HttpSession session = request.getSession();

		if (session.getAttribute("USER") == null) {
			mv.setViewName("admin/login");
		} else {
			UserVO userVO = (UserVO)session.getAttribute("USER");
			String this_manager = userVO.getUser_id();
			List<UserVO> list = service.selectUserDb(vo);
			List<TeamVO> team_list = service.selectTeamDb(new TeamVO());

			mv.addObject("this_manager", this_manager);
			mv.addObject("list", list);
			mv.addObject("team_list", team_list);
			mv.addObject("search", vo);
			mv.setViewName("admin/user");
		}
		return mv;
	}

	
	@RequestMapping(value = "/admin/team")
	public @ResponseBody ModelAndView team(HttpServletRequest request, @ModelAttribute TeamVO vo) throws Exception {

		ModelAndView mv = new ModelAndView();

		HttpSession session = request.getSession();

		if (session.getAttribute("USER") == null) {
			mv.setViewName("admin/login");
		} else {
			UserVO userVO = (UserVO)session.getAttribute("USER");
			String manager = userVO.getUser_id();
			List<TeamVO> list = service.selectTeamDb(vo);
			mv.addObject("list", list);
			mv.addObject("search", vo);
			mv.addObject("user_id", manager);
			mv.setViewName("admin/team");
		}
		return mv;
	}
	
	@RequestMapping(value = "/admin/loginCheck")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int loginCheck(HttpServletRequest request, @ModelAttribute UserVO vo) throws Exception {

		int check = 0;
		UserVO userInfo= service.loginCheck(vo);
		if(userInfo != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("USER", userInfo);
			check = 1;
		}
		
		return check;
	}

	@RequestMapping(value = "/admin/db-list")
	public @ResponseBody ModelAndView dbList(HttpServletRequest request, @ModelAttribute DbVO vo) throws Exception {

		ModelAndView mv = new ModelAndView();

		HttpSession session = request.getSession();

		if (session.getAttribute("USER") == null) {
			mv.setViewName("admin/login");
		} else {
			
			UserVO userVO = (UserVO)session.getAttribute("USER");
			String user_id = userVO.getUser_id();
			String grade = userVO.getGrade();
			String team_cd = userVO.getTeam_cd();
			int page = vo.getPage();
			int page_size = 10;
			int page_scope = (page - 1) * page_size;
			vo.setAdminYn(0);
			vo.setManager(user_id);
			vo.setGrade(grade);
			vo.setTeam_cd(team_cd);
			vo.setPage_size(page_size);
			vo.setPage_scope(page_scope);
			List<UserVO> user_list = service.selectUserDb(new UserVO());
			List<DbVO> list = service.selectDb(vo);

			int total_cnt = service.countDb(vo);

			double total_page_cnt = Math.ceil(total_cnt / (double) page_size);
			
			mv.addObject("grade", grade);
			mv.addObject("team_cd", team_cd);
			mv.addObject("user_list", user_list);
			mv.addObject("list", list);
			mv.addObject("search", vo);
			mv.addObject("total_pages", total_page_cnt);
			mv.addObject("start_page", page);
			mv.setViewName("admin/db-list");
		}
		return mv;
	}

	@RequestMapping(value = "/selectGrade")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int selectGrade(@ModelAttribute UserVO vo) throws Exception {

		int result = service.selectGrade(vo);

		return result;
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

	@RequestMapping(value = "/admin/deleteDb")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int deleteDb(@ModelAttribute DbVO vo) throws Exception {

		int result = service.deleteDb(vo);

		return result;
	}
	
	@RequestMapping(value = "/admin/insertTeamDb")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int regTeamDb(@ModelAttribute TeamVO vo) throws Exception {

		int result = service.insertTeamDb(vo);

		return result;
	}
	
	@RequestMapping(value = "/admin/updateTeamDb")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int updateTeamDb(@ModelAttribute TeamVO vo) throws Exception {

		int result = service.updateTeamDb(vo);

		return result;
	}
	
	@RequestMapping(value = "/admin/deleteTeamDb")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int deleteTeamDb(@ModelAttribute TeamVO vo) throws Exception {

		int result = service.deleteTeamDb(vo);

		return result;
	}
	
	@RequestMapping(value = "/admin/insertUserDb")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int regUserDb(@ModelAttribute UserVO vo) throws Exception {

		int result = service.insertUserDb(vo);

		return result;
	}
	
	@RequestMapping(value = "/admin/updateUserDb")
	@CrossOrigin(origins = "*", maxAge = 4800, allowCredentials = "false")
	public @ResponseBody int updateUserDb(@ModelAttribute UserVO vo) throws Exception {

		int result = service.updateUserDb(vo);

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
	
	@RequestMapping(value = "/logout")
	public @ResponseBody ModelAndView logout(HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		session.invalidate();		
		mv.setViewName("admin/login");
		return mv;
	}
	

}
