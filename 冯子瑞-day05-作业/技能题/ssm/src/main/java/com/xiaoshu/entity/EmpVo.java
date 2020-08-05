package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EmpVo extends eEmp{
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date start;
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date end;
	private String tname;
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	@Override
	public String toString() {
		return "EmpVo"+super.toString()+" [start=" + start + ", end=" + end + ", tname=" + tname + "]";
	}
	
	

}
