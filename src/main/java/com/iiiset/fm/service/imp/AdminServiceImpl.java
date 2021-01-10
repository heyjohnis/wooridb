package com.iiiset.fm.service.imp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiiset.fm.dao.AdminDao;
import com.iiiset.fm.model.DbVO;
import com.iiiset.fm.model.GoodsVO;
import com.iiiset.fm.model.UserVO;
import com.iiiset.fm.model.TeamVO;
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
	public int insertDb_list(DbVO vo) {
		return dao.insertDb_list(vo);
	}
	
	@Override
	public List<DbVO> selectDb(DbVO vo) {
		return dao.selectDb(vo);
	}
	
	@Override
	public List<UserVO> selectUserDb(UserVO vo) {
		return dao.selectUserDb(vo);
	}
	
	@Override
	public List<UserVO> selectMember(UserVO vo) {
		return dao.selectMember(vo);
	}
	
	@Override
	public List<GoodsVO> selectGoods(GoodsVO vo){
		return dao.selectGoods(vo);
	}
	
	@Override
	public List<GoodsVO> selectGoodsDt(GoodsVO vo){
		return dao.selectGoodsDt(vo);
	}
	
	@Override
	public List<DbVO> selectOrderCnt(DbVO vo){
		return dao.selectOrderCnt(vo);
	}
	
	@Override
	public List<DbVO> selectCntRank(DbVO vo){
		return dao.selectCntRank(vo);
	}
	
	@Override
	public List<DbVO> selectAmtRank(DbVO vo){
		return dao.selectAmtRank(vo);
	}
	
	@Override
	public List<DbVO> selectCallRank(DbVO vo){
		return dao.selectCallRank(vo);
	}
	
	@Override
	public int selectGrade(UserVO vo) {
		return dao.selectGrade(vo);
	}
	
	@Override
	public String selectTeam_cd(UserVO vo) {
		return dao.selectTeam_cd(vo);
	}
	
	@Override
	public int selectCnt(DbVO vo) {
		return dao.selectCnt(vo);
	}
	
	@Override
	public int countDb(DbVO vo) {
		return dao.countDb(vo);
	}
	
	@Override
	public int updateDb(DbVO vo) {
		return dao.updateDb(vo);
	}
	
	@Override
	public int deleteDb(DbVO vo) {
		return dao.deleteDb(vo);
	}

	@Override
	public UserVO loginCheck(UserVO vo) {
		return dao.loginCheck(vo);
	}

	@Override
	public List<TeamVO> selectTeamDb(TeamVO vo) {
		return dao.selectTeamDb(vo);
	}
	
	@Override
	public int insertTeamDb(TeamVO vo) {
		return dao.insertTeamDb(vo);
	}
	
	@Override
	public int updateTeamDb(TeamVO vo) {
		return dao.updateTeamDb(vo);
	}
	
	@Override
	public int deleteTeamDb(TeamVO vo) {
		return dao.deleteTeamDb(vo);
	}
	
	@Override
	public int insertGoodsDb(GoodsVO vo) {
		return dao.insertGoodsDb(vo);
	}
	
	@Override
	public int updateGoodsDb(GoodsVO vo) {
		return dao.updateGoodsDb(vo);
	}
	
	@Override
	public int deleteGoodsDb(GoodsVO vo) {
		return dao.deleteGoodsDb(vo);
	}
	
	@Override
	public int insertUserDb(UserVO vo) {
		return dao.insertUserDb(vo);
	}
	
	@Override
	public int updateUserDb(UserVO vo) {
		return dao.updateUserDb(vo);
	}
	
	
	@Override
	public ByteArrayInputStream excelDownLoad(DbVO vo) throws IOException {

		List<DbVO> list = dao.selectDb(vo);

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("wooridb");

			Row row = sheet.createRow(0);
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// Creating header
			Cell cell = row.createCell(0);
			cell.setCellValue("번호");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(1);
			cell.setCellValue("접수일자");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(2);
			cell.setCellValue("출처");
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(3);
			cell.setCellValue("고객명");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(4);
			cell.setCellValue("연락처");
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(5);
			cell.setCellValue("담당자");
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(6);
			cell.setCellValue("주문수량");
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(7);
			cell.setCellValue("금액");
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(8);
			cell.setCellValue("제품명");
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(9);
			cell.setCellValue("구매상태");
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(10);
			cell.setCellValue("콜성공률");
			cell.setCellStyle(headerCellStyle);
			
			cell = row.createCell(11);
			cell.setCellValue("콜성공수");
			cell.setCellStyle(headerCellStyle);
			
			// Creating data rows for each customer
			for (int i = 0; i < list.size(); i++) {
				Row dataRow = sheet.createRow(i + 1);
				dataRow.createCell(0).setCellValue(list.get(i).getSeq());
				dataRow.createCell(1).setCellValue(list.get(i).getReg_date());
				dataRow.createCell(2).setCellValue(list.get(i).getSite());
				dataRow.createCell(3).setCellValue(list.get(i).getCust_nm());
				dataRow.createCell(4).setCellValue(list.get(i).getCust_tel());
				dataRow.createCell(5).setCellValue(list.get(i).getManager());
				dataRow.createCell(6).setCellValue(list.get(i).getOrder_cnt());
				dataRow.createCell(7).setCellValue(list.get(i).getOrder_amount());
				dataRow.createCell(8).setCellValue(list.get(i).getGd_cd());
				dataRow.createCell(9).setCellValue(list.get(i).getGd_buyType());
				dataRow.createCell(10).setCellValue(list.get(i).getCall_succRate());
				dataRow.createCell(11).setCellValue(list.get(i).getCall_cnt());
			}

			// Making size of column auto resize to fit with data
			
			for (int i = 0; i < 12; i++) 
				sheet.autoSizeColumn(i);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());

		}
	}

}