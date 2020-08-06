package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.expressPerson;
import com.xiaoshu.entity.expressPersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface expressPersonMapper extends BaseMapper<expressPerson> {
	
	List<PersonVo> findAll(PersonVo p);
}