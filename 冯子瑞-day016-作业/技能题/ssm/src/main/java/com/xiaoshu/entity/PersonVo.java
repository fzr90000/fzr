package com.xiaoshu.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class PersonVo extends jiyunPerson{
private Integer num;
private String bname;
@DateTimeFormat(pattern="yyyy-MM-dd")
private Date start;
@DateTimeFormat(pattern="yyyy-MM-dd")
private Date end;
public Integer getNum() {
	return num;
}
public void setNum(Integer num) {
	this.num = num;
}
public String getBname() {
	return bname;
}
public void setBname(String bname) {
	this.bname = bname;
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
	return "PersonVo "+super.toString()+"[num=" + num + ", bname=" + bname + ", start=" + start + ", end=" + end + "]";
}

}
