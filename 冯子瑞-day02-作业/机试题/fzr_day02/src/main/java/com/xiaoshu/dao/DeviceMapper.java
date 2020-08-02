package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.DeviceExample;
import com.xiaoshu.entity.DeviceVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeviceMapper extends BaseMapper<Device> {
	
	List<DeviceVo> findAll(DeviceVo dv);

	Device selectWithname(String devicename);
}