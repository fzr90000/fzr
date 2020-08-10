package com.xiaoshu.service;

import java.util.Date;
import java.util.List;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.SchoolMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.dao.expressCompanyMapper;
import com.xiaoshu.dao.expressPersonMapper;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;
import com.xiaoshu.entity.expressCompany;
import com.xiaoshu.entity.expressPerson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class StudentService {

	@Autowired
	StudentMapper sm;
	@Autowired
	SchoolMapper tm;
	
	@Autowired
	JedisPool jedisPool;
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
	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	Destination queueTextDestination;
	public void add(Student stu) {
		// TODO Auto-generated method stub
		sm.insert(stu);
		jmsTemplate.convertAndSend(queueTextDestination,JSONObject.toJSONString(stu));
		Student one = sm.selectOne(stu);
		Jedis jedis = jedisPool.getResource();
		jedis.hset("学生档案",one.getSid()+"",one.toString());
		
		
	}

	public List<School> selectAll() {
		// TODO Auto-generated method stub
		return tm.selectAll();
	}

	public List<StudentVo> findexp() {
		// TODO Auto-generated method stub
		return sm.findAll(null);
	}

	public School getde(String name) {
		// TODO Auto-generated method stub
		School c = new School();
		c.setTname(name);
		return tm.selectOne(c);
	}

	public List<StudentVo> gettj() {
		// TODO Auto-generated method stub
		return sm.gettj();
	}



}
