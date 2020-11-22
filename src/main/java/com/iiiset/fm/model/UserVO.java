package com.iiiset.fm.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
    
    private int seq;
    private String user_id;
    private String user_nm;
    private String password;
    private String grade;
    private String grade_nm;
    private String team_cd;
    private String team_nm;
    private String user_tel;
}