package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.dao.expressCompanyMapper;
import com.xiaoshu.dao.expressPersonMapper;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;
import com.xiaoshu.entity.expressCompany;
import com.xiaoshu.entity.expressPerson;

@Service
public class PersonService {

	@Autowired
	expressPersonMapper pm;
	@Autowired
	expressCompanyMapper cm;
	public PageInfo<PersonVo> findPersonVoPage(PersonVo p, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<PersonVo> personList = pm.findAll(p);
		PageInfo<PersonVo> pageInfo = new PageInfo<PersonVo>(personList);
		return pageInfo;
	}
	public List<expressCompany> selectAll() {
		// TODO Auto-generated method stub
		return cm.selectAll();
	}
	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		pm.deleteByPrimaryKey(parseInt);
		
	}
	public void updateUser(expressPerson ep) {
		// TODO Auto-generated method stub
		ep.setCreateTime(new Date());
		pm.updateByPrimaryKey(ep);
		
	}
	public void add(expressPerson ep) {
		// TODO Auto-generated method stub
		ep.setCreateTime(new Date());
		pm.insert(ep);
		
	}


}
