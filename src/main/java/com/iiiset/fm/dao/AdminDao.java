package com.iiiset.fm.dao;
import java.util.List;

import com.iiiset.fm.model.DbVO;
import com.iiiset.fm.model.GoodsVO;
import com.iiiset.fm.model.UserVO;
import com.iiiset.fm.model.TeamVO;

public interface AdminDao {

	int insertDb(DbVO vo);
	
	int insertDb_list(DbVO vo);

	List<DbVO> selectDb(DbVO vo);

	List<UserVO> selectUserDb(UserVO vo);
	
	List<TeamVO> selectTeamDb(TeamVO vo);
	
	List<UserVO>selectTeamCd(UserVO vo);
	
	List<UserVO>selectMember(UserVO vo);
	
	List<GoodsVO>selectGoods(GoodsVO vo);
	
	List<GoodsVO>selectGoodsDt(GoodsVO vo);
	
	List<DbVO>selectOrderCnt(DbVO vo);
	
	List<DbVO>selectCntRank(DbVO vo);
	
	List<DbVO>selectAmtRank(DbVO vo);
	
	List<DbVO>selectCallRank(DbVO vo);
	
	int selectGrade(UserVO vo);
	
	String selectTeam_cd(UserVO vo);
	
	int selectCnt(DbVO vo);
	
	int countDb(DbVO vo);

	int updateDb(DbVO vo);

	int deleteDb(DbVO vo);
	
	int insertTeamDb(TeamVO vo);
	
	int updateTeamDb(TeamVO vo);
	
	int deleteTeamDb(TeamVO vo);
	
	int insertGoodsDb(GoodsVO vo);
	
	int updateGoodsDb(GoodsVO vo);
	
	int deleteGoodsDb(GoodsVO vo);
	
	int insertUserDb(UserVO vo);
	
	int updateUserDb(UserVO vo);
	
	UserVO loginCheck(UserVO vo);

	int deleteUser(UserVO vo);

	String getManager(DbVO vo);


}
