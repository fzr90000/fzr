package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.AreaMapper;
import com.xiaoshu.entity.Area;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.SchoolVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.SchoolService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("school")
public class SchoolController2 extends LogController {
	static Logger logger = Logger.getLogger(SchoolController2.class);

	@Autowired
	private SchoolService ss;
	@Autowired
	private AreaMapper am;

	@Autowired
	private OperationService operationService;

	@RequestMapping("schoolIndex")
	public String index(HttpServletRequest request, Integer menuid) throws Exception {
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<Area> list = am.selectAll();
		request.setAttribute("list", list);

		return "school";
	}

	@RequestMapping(value = "schoolList", method = RequestMethod.POST)
	public void schoolList(SchoolVo s, HttpServletRequest request, HttpServletResponse response, String offset,
			String limit) throws Exception {
		try {

			Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
			Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
			PageInfo<SchoolVo> schoolList = ss.findUserPage(s, pageNum, pageSize);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total", schoolList.getTotal());
			jsonObj.put("rows", schoolList.getList());
			WriterUtil.write(response, jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误", e);
			throw e;
		}
	}
	@RequestMapping("deleteUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				ss.deleteUser(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	

	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request, School s, HttpServletResponse response) {
		Integer id = s.getId();
		JSONObject result = new JSONObject();
		try {
			if (id != null) { // userId不为空 说明是修改

			} else { // 添加// 没有重复可以添加
				if (ss.existUserWithUserName(s.getSchoolname()) == null) {
					String phone = s.getPhone();
					Integer i = Integer.parseInt(phone);
				

					
					 if (i.SIZE==11) {
						 result.put("errorMsg", "手机号比须deng余11位");
					
					} 
					 
					s.setCreatetime(new Date());
					ss.addUser(s);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误", e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}

	@RequestMapping("exp")
	public void exp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow(0);

		String[] all = {"编号","分校名称","所在城市","联系方式","详细地址","分校状态","创建时间"};
		for (int i = 0; i < all.length; i++) {
			row.createCell(i).setCellValue(all[i]);
			
			
		}
		List<SchoolVo> li = ss.finA();
		for (int j = 0; j < li.size(); j++) {
			SchoolVo vo = li.get(j);
			HSSFRow r = sheet.createRow(j + 1);
			r.createCell(0).setCellValue(vo.getId());
			r.createCell(1).setCellValue(vo.getSchoolname());
			r.createCell(2).setCellValue(vo.getAname());
			r.createCell(3).setCellValue(vo.getPhone());
			r.createCell(4).setCellValue(vo.getAddress());
			r.createCell(5).setCellValue(vo.getStatus());
			r.createCell(6).setCellValue(TimeUtil.formatTime(vo.getCreatetime(), "yyyy-MM-dd"));

		}
		File file = new File("D:/1909eyk.xls");
		FileOutputStream stream = new FileOutputStream(file);

		wb.write(stream);
		wb.close();

		result.put("success", true);


		WriterUtil.write(response, result.toString());
	}

}
