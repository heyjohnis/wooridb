package com.iiiset.fm.utils;

public class UserInfo {

	private String id;
	private String name;
	private int grade;
	private int team_cd;

	public UserInfo(String id, String name, int grade) {
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.team_cd = team_cd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getTeamCd() {
		return team_cd;
	}

}
