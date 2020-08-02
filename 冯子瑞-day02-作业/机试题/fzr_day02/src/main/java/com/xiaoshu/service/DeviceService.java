package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.DeviceMapper;
import com.xiaoshu.dao.DevicetypeMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.DeviceVo;
import com.xiaoshu.entity.Devicetype;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class DeviceService {

	@Autowired
	DeviceMapper dm;
	@Autowired
	DevicetypeMapper tm;


	public PageInfo<DeviceVo> findDervicePage(DeviceVo dv, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<DeviceVo> deviceList = dm.findAll(dv);
		PageInfo<DeviceVo> pageInfo = new PageInfo<DeviceVo>(deviceList);
		return pageInfo;
	}


	public List<Devicetype> selectAll() {
		// TODO Auto-generated method stub
		return tm.selectAll();
	}


	public Device existUserWithUserName(String devicename) {
		// TODO Auto-generated method stub
		return dm.selectWithname(devicename);
	}


	public void addDe(Device device) {
		// TODO Auto-generated method stub
		device.setCreatetime(new Date());
		dm.insert(device);
		
	}


	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		dm.deleteByPrimaryKey(parseInt);
		
	}


	public List<DeviceVo> findAll() {
		// TODO Auto-generated method stub
		return dm.findAll(null);
	}


}
