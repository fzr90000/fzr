package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.EmpVo;
import com.xiaoshu.entity.eEmp;
import com.xiaoshu.entity.eEmpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface eEmpMapper extends BaseMapper<eEmp> {
	
	List<EmpVo> findAll(EmpVo e);
}