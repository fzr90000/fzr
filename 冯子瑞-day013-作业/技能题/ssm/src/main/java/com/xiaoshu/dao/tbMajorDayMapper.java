package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.tbMajorDay;
import com.xiaoshu.entity.tbMajorDayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface tbMajorDayMapper extends BaseMapper<tbMajorDay> {
    long countByExample(tbMajorDayExample example);

    int deleteByExample(tbMajorDayExample example);

    List<tbMajorDay> selectByExample(tbMajorDayExample example);

    int updateByExampleSelective(@Param("record") tbMajorDay record, @Param("example") tbMajorDayExample example);

    int updateByExample(@Param("record") tbMajorDay record, @Param("example") tbMajorDayExample example);
}