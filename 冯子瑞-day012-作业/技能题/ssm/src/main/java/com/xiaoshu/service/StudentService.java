package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.dao.tbStuDayMapper;
import com.xiaoshu.entity.SchoolVo;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;
import com.xiaoshu.entity.tbStuDay;

@Service
public class StudentService {

	@Autowired
	tbStuDayMapper sm;

	public PageInfo<StudentVo> findUserPage(StudentVo s, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<StudentVo> StudentList = sm.findAll(s);
		PageInfo<StudentVo> pageInfo = new PageInfo<StudentVo>(StudentList);
		return pageInfo;
	}

	public void update(tbStuDay s) {
		// TODO Auto-generated method stub
		sm.updateByPrimaryKey(s);
		
	}

	public void addUser(tbStuDay s) {
		// TODO Auto-generated method stub
		sm.insert(s);
		
	}

	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		sm.deleteByPrimaryKey(parseInt);
		
	}

	public List<StudentVo> finA() {
		// TODO Auto-generated method stub
		return sm.findAll(null);
	}

	public List<StudentVo> gettj() {
		// TODO Auto-generated method stub
		return sm.gettj();
	}


}
