package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.jiyunPerson;
import java.util.List;

public interface jiyunPersonMapper extends BaseMapper<jiyunPerson> {
	List<PersonVo> findAll(PersonVo p);

	List<PersonVo> gettj();
}