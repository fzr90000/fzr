package com.xiaoshu.entity;

public class PersonVo extends pPerson{
	private String cname;
	
	private Integer num;
	

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "PersonVo"+super.toString()+" [cname=" + cname + ", num=" + num + "]";
	}
	

}
