package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.dao.tbGoodsMapper;
import com.xiaoshu.dao.tbGoodstypeMapper;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;
import com.xiaoshu.entity.tbGoods;
import com.xiaoshu.entity.tbGoodstype;

@Service
public class GoodsService {

	@Autowired
	tbGoodsMapper gm;
	@Autowired
	tbGoodstypeMapper tm;

	
	public PageInfo<GoodsVo> findgooodsPage(GoodsVo gv, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<GoodsVo> goodsList = gm.findAll(gv);
		PageInfo<GoodsVo> pageInfo = new PageInfo<GoodsVo>(goodsList);
		return pageInfo;
	}


	public List<tbGoodstype> findtype() {
		// TODO Auto-generated method stub
		return tm.selectAll();
	}


	public void add(tbGoods tb) {
		// TODO Auto-generated method stub
		gm.insert(tb);
		
	}


	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		gm.deleteByPrimaryKey(parseInt);
		
	}


	public List<GoodsVo> gettj() {
		// TODO Auto-generated method stub
		return gm.gettj();
	}


}
