package com.iiiset.fm.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.iiiset.fm.model.DbVO;
import com.iiiset.fm.model.UserVO;
import com.iiiset.fm.model.TeamVO;

public interface AdminService {

	int insertDb(DbVO vo);

	List<DbVO> selectDb(DbVO vo);

	List<UserVO> selectUserDb(UserVO vo);
	
	int selectGrade(UserVO vo);
	
	int countDb(DbVO vo);

	int updateDb(DbVO vo);

	int deleteDb(DbVO vo);
	
	UserVO loginCheck(UserVO vo);
	
	List<TeamVO> selectTeamDb(TeamVO vo);
	
	int insertTeamDb(TeamVO vo);
	
	int updateTeamDb(TeamVO vo);

	int deleteTeamDb(TeamVO vo);
	
	int insertUserDb(UserVO vo);
	
	int updateUserDb(UserVO vo);
	
	ByteArrayInputStream excelDownLoad(DbVO vo) throws IOException;

}