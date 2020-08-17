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
import com.xiaoshu.entity.jiyunPerson;

@Service
public class PersonService {

	@Autowired
	jiyunPersonMapper jm;


	public PageInfo<PersonVo> findUserPage(PersonVo p, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<PersonVo> personList = jm.findAll(p);
		PageInfo<PersonVo> pageInfo = new PageInfo<PersonVo>(personList);
		return pageInfo;
	}


	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		jm.deleteByPrimaryKey(parseInt);
		
	}


	public void update(jiyunPerson p) {
		// TODO Auto-generated method stub
		jm.updateByPrimaryKey(p);
		
	}


	public void add(jiyunPerson p) {
		// TODO Auto-generated method stub
		jm.insert(p);
		
	}


	public List<PersonVo> gettj() {
		// TODO Auto-generated method stub
		return jm.gettj();
	}


}
