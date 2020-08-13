package com.xiaoshu.entity;

public class ContentVo extends Content{
	private String gname;
	private Integer num;
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "ContentVo "+super.toString()+"[gname=" + gname + ", num=" + num + "]";
	}
	

}
