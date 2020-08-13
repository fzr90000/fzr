package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.GoodsVo;

public interface GoodsMapper extends BaseMapper<Goods> {
	List<GoodsVo> findAll(GoodsVo g);

	List<GoodsVo> findtype();
}