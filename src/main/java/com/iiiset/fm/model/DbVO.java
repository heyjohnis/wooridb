package com.iiiset.fm.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DbVO {
	
	int seq = 0;					//순번
	int adminYn = 1;				//관리자여부
	String cust_nm = "";			//고객명
	String cust_tel = "";			//고객연락처
	String comment = "";			//상세내역
	String gender = "";				//성별
	String age = "";				//나이
	String memo = "";				//메모
	String addr = "";				//주소
	String order_cnt = "";			//주문수량
	String order_amount = "";		//주문금액
	String order_date = "";			//주문일자
	String bank_account = "";		//은행계좌
	String card_number = "";		//카드번호
	String expire_date = "";		//만료일자
	String manager = "";			//담당직원명
	String following_date = "";		
	String reg_date = "";			//등록일자
	String mod_date = "";			//수정일자
	String site = "";				//인터넷주소
	String grade = "";				//등급		
	String team_cd = "";			//조직코드
	String user_id = "";		    //직원명
	String password = "";			//암호
	String manager_nm = "";			//직원명
	int page_size = 0;
	int page = 1;
	int page_scope = 0;
	
	String srh_sta_date = "";
	String srh_end_date = "";
	String srh_key = "";
	int excel_down = 0;
	String gd_nm = "";
	String gd_cd = "";				//상품코드
	String gd_buyType="";			//판매유형 01.접수 02.부재 03.보류 04.구매완료 05.재구매 
	String cnt_sum = "";					//총주문수량
	int inp_cnt=0;					//재고수량
	String call_succRate ="";		//콜백성공률
	String term = "";
	String termParam = "";
	int amt_rank = 0;				//금액순위
	int call_rank = 0;				//콜순위
	int cnt_rank = 0;				//수량순위
	int call_cnt = 0;				//콜건수
}

