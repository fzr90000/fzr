package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.jiyunBank;
import com.xiaoshu.entity.jiyunBankExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface jiyunBankMapper extends BaseMapper<jiyunBank> {
    long countByExample(jiyunBankExample example);

    int deleteByExample(jiyunBankExample example);

    List<jiyunBank> selectByExample(jiyunBankExample example);

    int updateByExampleSelective(@Param("record") jiyunBank record, @Param("example") jiyunBankExample example);

    int updateByExample(@Param("record") jiyunBank record, @Param("example") jiyunBankExample example);
}