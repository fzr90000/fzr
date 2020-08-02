package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.dao.ContentcategoryMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentVo;
import com.xiaoshu.entity.Contentcategory;
import com.xiaoshu.entity.User;

@Service
public class ContentService {

	@Autowired
	ContentMapper cm;
	@Autowired
	ContentcategoryMapper gm;

/*	// 查询所有
	public List<User> findUser(User t) throws Exception {
		return userMapper.select(t);
	};*/


	public PageInfo<ContentVo> findUserPage(ContentVo vo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ContentVo> conList = cm.findAll(vo);
		PageInfo<ContentVo> pageInfo = new PageInfo<ContentVo>(conList);
		return pageInfo;
	}

	public List<Contentcategory> findgory() {
		// TODO Auto-generated method stub
		return gm.selectAll();
	}

	public List<Content> findByName(String contenttitle) {
		// TODO Auto-generated method stub
		return cm.findByName(contenttitle);
	}

	public void addcontent(Content content) {
		// TODO Auto-generated method stub
		cm.insert(content);
		
	}

	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		cm.deleteByPrimaryKey(parseInt);
		
	}


}
