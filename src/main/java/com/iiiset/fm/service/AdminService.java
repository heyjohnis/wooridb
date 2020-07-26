package com.iiiset.fm.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.iiiset.fm.model.DbVO;
import com.iiiset.fm.model.UserVO;

public interface AdminService {

	int insertDb(DbVO vo);

	List<DbVO> selectDb(DbVO vo);

	int countDb(DbVO vo);

	int updateDb(DbVO vo);

	int loginCheck(UserVO vo);

	ByteArrayInputStream excelDownLoad(DbVO vo) throws IOException;

}