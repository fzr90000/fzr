package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.dao.expressCompanyMapper;
import com.xiaoshu.dao.expressPersonMapper;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.Teacher;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;
import com.xiaoshu.entity.expressCompany;
import com.xiaoshu.entity.expressPerson;

@Service
public class StudentService {

	@Autowired
	StudentMapper sm;
	@Autowired
	TeacherMapper tm;
	public PageInfo<StudentVo> findStudentPage(StudentVo stu, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<StudentVo> studentList = sm.findAll(stu);
		PageInfo<StudentVo> pageInfo = new PageInfo<StudentVo>(studentList);
		return pageInfo;
	}
	
	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		sm.deleteByPrimaryKey(parseInt);
		
	}
	public void updateUser(Student stu) {
		// TODO Auto-generated method stub
		sm.updateByPrimaryKey(stu);
		
	}
	public void add(Student stu) {
		// TODO Auto-generated method stub
		sm.insert(stu);
		
	}

	public List<Teacher> selectAll() {
		// TODO Auto-generated method stub
		return tm.selectAll();
	}

	public List<StudentVo> gettj() {
		// TODO Auto-generated method stub
		return sm.gettj();
	}


}
