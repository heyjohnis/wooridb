package com.iiiset.fm.dao;
import java.util.List;

import com.iiiset.fm.model.DbVO;
import com.iiiset.fm.model.UserVO;

public interface AdminDao {

	int insertDb(DbVO vo);

	List<DbVO> selectDb(DbVO vo);

	int countDb(DbVO vo);

	int updateDb(DbVO vo);

	int loginCheck(UserVO vo);


}
