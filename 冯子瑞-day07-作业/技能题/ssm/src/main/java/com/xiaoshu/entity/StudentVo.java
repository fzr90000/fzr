package com.xiaoshu.entity;

public class StudentVo extends Student{
	private String teaname;
	private Integer num;
	public String getTeaname() {
		return teaname;
	}
	public void setTeaname(String teaname) {
		this.teaname = teaname;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "StudentVo"+super.toString()+" [teaname=" + teaname + ", num=" + num + "]";
	}
	
	
}
