package com.iiiset.fm.service;

import java.util.List;

import com.iiiset.fm.model.DbVO;

public interface AdminService {

	int insertDb(DbVO vo);

	List<DbVO> selectDb(DbVO vo);

	int countDb(DbVO vo);

}