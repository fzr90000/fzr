package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.tbStuDay;
import com.xiaoshu.entity.tbStuDayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface tbStuDayMapper extends BaseMapper<tbStuDay> {

	List<StudentVo> findAll(StudentVo s);

	List<StudentVo> gettj();
}