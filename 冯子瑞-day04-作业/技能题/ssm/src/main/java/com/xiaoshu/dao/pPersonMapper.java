package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.pPerson;
import com.xiaoshu.entity.pPersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface pPersonMapper extends BaseMapper<pPerson> {
	
	List<PersonVo> findAll(PersonVo p);

	pPerson findByName(String getpName);

	List<PersonVo> gettj();
}