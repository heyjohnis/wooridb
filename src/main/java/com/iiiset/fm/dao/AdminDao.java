package com.iiiset.fm.dao;
import java.util.List;

import com.iiiset.fm.model.DbVO;

public interface AdminDao {

	int insertDb(DbVO vo);

	List<DbVO> selectDb(DbVO vo);

	int countDb(DbVO vo);

	int updateDb(DbVO vo);

}
