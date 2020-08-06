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
import com.xiaoshu.dao.expressCompanyMapper;
import com.xiaoshu.dao.expressPersonMapper;
import com.xiaoshu.entity.EmpVo;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;
import com.xiaoshu.entity.dDept;
import com.xiaoshu.entity.eEmp;
import com.xiaoshu.entity.expressCompany;
import com.xiaoshu.entity.expressPerson;

@Service
public class PersonService {

	@Autowired
	expressPersonMapper ep;
	@Autowired
	expressCompanyMapper ec;

	public PageInfo<PersonVo> findEmpPage(PersonVo p, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<PersonVo> personList = ep.findAll(p);
		PageInfo<PersonVo> pageInfo = new PageInfo<PersonVo>(personList);
		return pageInfo;
	}

	public List<expressCompany> finddepet() {
		// TODO Auto-generated method stub
		return ec.selectAll();
	}

	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		ep.deleteByPrimaryKey(parseInt);
		
	}

	public void updateUser(expressPerson per) {
		// TODO Auto-generated method stub
		ep.updateByPrimaryKey(per);
		
	}

	public void addUser(expressPerson per) {
		// TODO Auto-generated method stub
		ep.insert(per);
		
	}




}
