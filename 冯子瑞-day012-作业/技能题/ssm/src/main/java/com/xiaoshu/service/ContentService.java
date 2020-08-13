package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.dao.SchoolMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentVo;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.SchoolVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class ContentService {

	@Autowired
	ContentMapper cm;


	public PageInfo<ContentVo> findUserPage(ContentVo cv, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ContentVo> contentList = cm.findAll(cv);
		PageInfo<ContentVo> pageInfo = new PageInfo<ContentVo>(contentList);
		return pageInfo;
	}


	public void update(Content c) {
		// TODO Auto-generated method stub
		c.setCreatetime(new Date());
		cm.updateByPrimaryKey(c);
		
	}


	public void add(Content c) {
		// TODO Auto-generated method stub
		c.setCreatetime(new Date());
		
		cm.insert(c);
		
	}


	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		cm.deleteByPrimaryKey(parseInt);
		
	}


	public List<ContentVo> gettj() {
		// TODO Auto-generated method stub
		return cm.gettj();
	}


//	public School existUserWithUserName(String schoolname) {
//		// TODO Auto-generated method stub
//		School school = new School();
//		school.setSchoolname(schoolname);
//		return cm.selectOne(school);
//	}
//
//
//	public void addUser(School s) {
//		// TODO Auto-generated method stub
//		sm.insert(s);
//		
//	}
//
//
//	public void deleteUser(int parseInt) {
//		// TODO Auto-generated method stub
//		sm.deleteByPrimaryKey(parseInt);
//	}
//
//
//	public List<SchoolVo> finA() {
//		// TODO Auto-generated method stub
//		return sm.findAll(null);
//	}


}
