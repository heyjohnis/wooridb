package com.iiiset.fm.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiiset.fm.dao.AdminDao;
import com.iiiset.fm.model.DbVO;
import com.iiiset.fm.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDao dao;

	@Override
	public int insertDb(DbVO vo) {
		return dao.insertDb(vo);
	}

	@Override
	public List<DbVO> selectDb(DbVO vo) {
		return dao.selectDb(vo);
	}

	@Override
	public int countDb(DbVO vo) {
		return dao.countDb(vo);
	}

	@Override
	public int updateDb(DbVO vo) {
		return dao.updateDb(vo);
	}

	
}