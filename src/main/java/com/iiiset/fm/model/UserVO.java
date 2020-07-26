package com.iiiset.fm.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
    
    private int seq;
    private String user_id;
    private String email;
    private String name;
    private String password;
    private int grade;
    private Date reg_date;

}