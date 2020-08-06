package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.dao.dDeptMapper;
import com.xiaoshu.dao.eEmpMapper;
import com.xiaoshu.entity.EmpVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;
import com.xiaoshu.entity.dDept;
import com.xiaoshu.entity.eEmp;

@Service
public class EmpService {

	@Autowired
	eEmpMapper em;
	@Autowired
	dDeptMapper dm;

	public PageInfo<EmpVo> findEmpPage(EmpVo e, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<EmpVo> empList = em.findAll(e);
		PageInfo<EmpVo> pageInfo = new PageInfo<EmpVo>(empList);
		return pageInfo;
	}

	public List<dDept> finddepet() {
		// TODO Auto-generated method stub
		return dm.selectAll();
	}

	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		em.deleteByPrimaryKey(parseInt);
		
	}

	public void updateUser(eEmp emp) {
		// TODO Auto-generated method stub
		em.updateByPrimaryKey(emp);
		
	}

	public void addUser(eEmp emp) {
		// TODO Auto-generated method stub
		em.insert(emp);
		
	}


}
