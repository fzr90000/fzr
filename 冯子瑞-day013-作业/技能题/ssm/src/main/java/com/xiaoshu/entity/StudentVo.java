package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class StudentVo extends tbStuDay{
	private Integer num;
	private String mname;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date strat;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date end;
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public Date getStrat() {
		return strat;
	}
	public void setStrat(Date strat) {
		this.strat = strat;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	@Override
	public String toString() {
		return "StudentVo"+super.toString()+" [num=" + num + ", mname=" + mname + ", strat=" + strat + ", end=" + end + "]";
	}
	

}
