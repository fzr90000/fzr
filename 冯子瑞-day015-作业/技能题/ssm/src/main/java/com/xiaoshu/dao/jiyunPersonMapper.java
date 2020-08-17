package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.jiyunPerson;
import com.xiaoshu.entity.jiyunPersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface jiyunPersonMapper extends BaseMapper<jiyunPerson> {
	List<PersonVo> findAll(PersonVo p);

	List<PersonVo> gettj();
}