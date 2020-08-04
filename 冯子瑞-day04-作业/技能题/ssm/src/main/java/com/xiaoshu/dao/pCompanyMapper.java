package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.pCompany;
import com.xiaoshu.entity.pCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface pCompanyMapper extends BaseMapper<pCompany> {
    long countByExample(pCompanyExample example);

    int deleteByExample(pCompanyExample example);

    List<pCompany> selectByExample(pCompanyExample example);

    int updateByExampleSelective(@Param("record") pCompany record, @Param("example") pCompanyExample example);

    int updateByExample(@Param("record") pCompany record, @Param("example") pCompanyExample example);
}