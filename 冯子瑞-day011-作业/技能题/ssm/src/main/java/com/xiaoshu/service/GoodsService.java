package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.GoodsMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.GoodsVo;

@Service
public class GoodsService {

	@Autowired
	GoodsMapper gm;


	public PageInfo<GoodsVo> findUserPage(GoodsVo g, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<GoodsVo> goodsList = gm.findAll(g);
		PageInfo<GoodsVo> pageInfo = new PageInfo<GoodsVo>(goodsList);
		return pageInfo;
	}


	public Object existUserWithUserName(String code) {
		// TODO Auto-generated method stub
		Goods goods = new Goods();
		goods.setCode(code);
		return gm.selectOne(goods);
	}


	public void update(Goods g) {
		// TODO Auto-generated method stub
		gm.updateByPrimaryKey(g);
		
	}


	public void addUser(Goods g) {
		// TODO Auto-generated method stub
		gm.insert(g);
		
	}


	public List<GoodsVo> findtype() {
		// TODO Auto-generated method stub
		return gm.findtype();
	}


}
