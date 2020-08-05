package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.dDept;
import com.xiaoshu.entity.dDeptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface dDeptMapper extends BaseMapper<dDept> {
    long countByExample(dDeptExample example);

    int deleteByExample(dDeptExample example);

    List<dDept> selectByExample(dDeptExample example);

    int updateByExampleSelective(@Param("record") dDept record, @Param("example") dDeptExample example);

    int updateByExample(@Param("record") dDept record, @Param("example") dDeptExample example);
}