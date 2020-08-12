package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.SchoolMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.SchoolVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class SchoolService {

	@Autowired
	SchoolMapper sm;


	public PageInfo<SchoolVo> findUserPage(SchoolVo s, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SchoolVo> SchoolList = sm.findAll(s);
		PageInfo<SchoolVo> pageInfo = new PageInfo<SchoolVo>(SchoolList);
		return pageInfo;
	}


	public School existUserWithUserName(String schoolname) {
		// TODO Auto-generated method stub
		School school = new School();
		school.setSchoolname(schoolname);
		return sm.selectOne(school);
	}


	public void addUser(School s) {
		// TODO Auto-generated method stub
		sm.insert(s);
		
	}


	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		sm.deleteByPrimaryKey(parseInt);
	}


	public List<SchoolVo> finA() {
		// TODO Auto-generated method stub
		return sm.findAll(null);
	}


}
