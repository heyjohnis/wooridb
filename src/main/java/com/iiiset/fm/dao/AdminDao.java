package com.iiiset.fm.dao;
import java.util.List;

import com.iiiset.fm.model.DbVO;
import com.iiiset.fm.model.UserVO;
import com.iiiset.fm.model.GroupVO;

public interface AdminDao {

	int insertDb(DbVO vo);

	List<DbVO> selectDb(DbVO vo);

	List<UserVO> selectUserDb(UserVO vo);
	
	List<GroupVO> selectGroupDb(GroupVO vo);
	
	int countDb(DbVO vo);

	int updateDb(DbVO vo);

	int deleteDb(DbVO vo);
	
	int loginCheck(UserVO vo);


}
