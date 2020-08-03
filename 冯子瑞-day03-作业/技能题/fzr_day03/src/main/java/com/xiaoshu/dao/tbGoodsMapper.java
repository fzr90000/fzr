package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.tbGoods;
import com.xiaoshu.entity.tbGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface tbGoodsMapper extends BaseMapper<tbGoods> {
	List<GoodsVo> findAll(GoodsVo gv);

	List<GoodsVo> gettj();
}