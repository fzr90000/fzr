package com.xiaoshu.entity;

public class StudentVo extends Student{
	private String tname;

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	@Override
	public String toString() {
		return "StudentVo"+super.toString()+" [tname=" + tname + "]";
	}


	
	
	
}
