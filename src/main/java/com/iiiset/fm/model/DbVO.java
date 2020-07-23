package com.iiiset.fm.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DbVO {
	
	int seq = 0;
	String cust_nm = "";
	String cust_tel = "";
	String comment = "";
	String gender = "";
	String age = "";
	String memo = "";
	String addr = "";
	String order_cnt = "";
	String order_amount = "";
	String order_date = "";
	String bank_account = "";
	String card_number = "";
	String expire_date = "";
	String manager = "";
	String following_date = "";
	String reg_date = "";
	String mod_date = "";
	int page_size = 0;
	int page = 0;
	int page_scope = 0;
	
}

