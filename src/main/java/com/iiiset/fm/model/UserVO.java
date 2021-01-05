package com.iiiset.fm.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
    
    private int seq;			//고객순번
    private String user_id;		//아이디
    private String user_nm;		//사원이름
    private String password;	//비밀번호
    private String grade;		//직원등급 01:팀원, 05:팀장, 10:관리자, 11:DB업체, 99:슈퍼바이저
    private String team_cd;		//조직코드
    private String team_nm;		//조직명
    private String user_tel;	//연락처
}