package com.xiaoshu.entity;

public class DeviceVo extends Device{
	private String tname;
	
	private Integer tid;
	

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	@Override
	public String toString() {
		return "DeviceVo "+super.toString()+"[tname=" + tname + ", tid=" + tid + "]";
	}
	

}
