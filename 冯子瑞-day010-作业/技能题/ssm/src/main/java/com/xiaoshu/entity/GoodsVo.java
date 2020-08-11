package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class GoodsVo extends Goods{
	private Integer num;
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	private String tname;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date start;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date end;
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
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
	@Override
	public String toString() {
		return "GoodsVo"+super.toString()+" [num=" + num + ", tname=" + tname + ", start=" + start + ", end=" + end + "]";
	}
    
}
