package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class StudentService {

	@Autowired
	StudentMapper sm;


	public PageInfo<StudentVo> findUserPage(StudentVo s, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<StudentVo> syudentList = sm.findAll(s);
		PageInfo<StudentVo> pageInfo = new PageInfo<StudentVo>(syudentList);
		return pageInfo;
	}


	public void updateUser(Student s) {
		// TODO Auto-generated method stub
		s.setCreatetime(new Date());
		sm.updateByPrimaryKey(s);
		
	}


	public void addUser(Student s) {
		// TODO Auto-generated method stub
		s.setCreatetime(new Date());
		sm.insert(s);
		
	}


	public List<StudentVo> getttj() {
		// TODO Auto-generated method stub
		return sm.gettj();
	}


}
