package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.dao.jiyunPersonMapper;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class PersonService {

	@Autowired
	jiyunPersonMapper pm;


	public PageInfo<PersonVo> findUserPage(PersonVo p, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<PersonVo> personList = pm.findAll(p);
		PageInfo<PersonVo> pageInfo = new PageInfo<PersonVo>(personList);
		return pageInfo;
	}


	public void update(PersonVo p) {
		// TODO Auto-generated method stub
		pm.updateByPrimaryKey(p);
	}


	public void add(PersonVo p) {
		// TODO Auto-generated method stub
		pm.insert(p);
		
	}


	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		pm.deleteByPrimaryKey(parseInt);
		
	}


	public List<PersonVo> findexp() {
		// TODO Auto-generated method stub
		return pm.findAll(null);
	}


	public List<PersonVo> gettj() {
		// TODO Auto-generated method stub
		return pm.gettj();
	}


}
