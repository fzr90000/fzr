package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.dao.pCompanyMapper;
import com.xiaoshu.dao.pPersonMapper;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;
import com.xiaoshu.entity.pCompany;
import com.xiaoshu.entity.pPerson;

@Service
public class PersonService {

	@Autowired
	pPersonMapper pm;
	@Autowired
	pCompanyMapper cm;


	public PageInfo<PersonVo> findPersonPage(PersonVo p, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<PersonVo> personList = pm.findAll(p);
		PageInfo<PersonVo> pageInfo = new PageInfo<PersonVo>(personList);
		return pageInfo;
	}


	public List<pCompany> selectAll() {
		// TODO Auto-generated method stub
		return cm.selectAll();
	}


	public void update(pPerson p) {
		// TODO Auto-generated method stub
		pm.updateByPrimaryKey(p);
	}


	public pPerson existUserWithName(String getpName) {
		// TODO Auto-generated method stub
		return pm.findByName(getpName);
	}


	public void addUser(pPerson p) {
		// TODO Auto-generated method stub
		pm.insert(p);
		
	}


	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		pm.deleteByPrimaryKey(parseInt);
		
	}


	public List<PersonVo> gettj() {
		// TODO Auto-generated method stub
		return pm.gettj();
	}
	


}
