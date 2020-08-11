package com.xiaoshu.entity;

public class SchoolVo extends School{
	private String aname;

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	@Override
	public String toString() {
		return "SchoolVo [aname=" + aname + "]";
	}
	

}
