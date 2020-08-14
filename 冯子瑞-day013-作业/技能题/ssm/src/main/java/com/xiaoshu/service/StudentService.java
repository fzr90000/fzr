package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.dao.tbStuDayMapper;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.xiaoshu.entity.tbStuDay;

@Service
public class StudentService {

	@Autowired
	tbStuDayMapper sm;
	@Autowired
	RedisTemplate redisTemplate;

	public PageInfo<StudentVo> findUserPage(StudentVo s, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<StudentVo> userList=(List<StudentVo>) redisTemplate.boundHashOps("mlist").get("ylist");
		if (userList==null) {
			userList=sm.findAll(s);
			redisTemplate.boundHashOps("mlist").put("ylist", userList);
			
			System.out.println("数据库");
		} else {
				System.out.println("从缓存");
		}
		PageInfo<StudentVo> pageInfo = new PageInfo<StudentVo>(userList);
		return pageInfo;
	}

	public void update(tbStuDay s) {
		// TODO Auto-generated method stub
		sm.updateByPrimaryKey(s);
		redisTemplate.delete("mlist");
		
	}

	public void add(tbStuDay s) {
		// TODO Auto-generated method stub
		sm.insert(s);
		redisTemplate.delete("mlist");
	}

	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		sm.deleteByPrimaryKey(parseInt);
		redisTemplate.delete("mlist");
		
	}

	public List<StudentVo> gettj() {
		// TODO Auto-generated method stub
		return sm.gettj();
	}

	public List<StudentVo> findexp() {
		// TODO Auto-generated method stub
		return sm.findAll(null);
	}


}
