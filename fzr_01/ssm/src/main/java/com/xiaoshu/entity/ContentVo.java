package com.xiaoshu.entity;

public class ContentVo extends Content{
	private Integer gid;
	private String gname;
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	@Override
	public String toString() {
		return "ContentVo"+super.toString()+" [gid=" + gid + ", gname=" + gname + "]";
	}
	
	

}
