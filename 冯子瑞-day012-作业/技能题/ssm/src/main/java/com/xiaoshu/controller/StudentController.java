package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.AreaMapper;
import com.xiaoshu.dao.tbMajorDayMapper;
import com.xiaoshu.entity.Area;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.SchoolVo;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.tbMajorDay;
import com.xiaoshu.entity.tbStuDay;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.SchoolService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("student")
public class StudentController extends LogController {
	static Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private StudentService ss;
	@Autowired
	private tbMajorDayMapper mm;

	@Autowired
	private OperationService operationService;

	@RequestMapping("studentIndex")
	public String index(HttpServletRequest request, Integer menuid) throws Exception {
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<tbMajorDay> list = mm.selectAll();
		request.setAttribute("list", list);

		return "student";
	}

	@RequestMapping(value = "studentList", method = RequestMethod.POST)
	public void studentList(StudentVo s, HttpServletRequest request, HttpServletResponse response, String offset,
			String limit) throws Exception {
		try {

			Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
			Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
			PageInfo<StudentVo> studentList = ss.findUserPage(s, pageNum, pageSize);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total", studentList.getTotal());
			jsonObj.put("rows", studentList.getList());
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
	
//
	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request, tbStuDay s, HttpServletResponse response) {
		Integer sdId = s.getSdId();
		JSONObject result = new JSONObject();
		try {
			if (sdId!=null) { // userId不为空 说明是修改
					ss.update(s);
					result.put("success", true);
			} else { // 添加// 没有重复可以添加
				
					 
					ss.addUser(s);
					result.put("success", true);
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
	

		String[] all = {"学生编号","学生名名称","学生性别","学生愛好","学生生日","专业"};
		for (int i = 0; i < all.length; i++) {
			row.createCell(i).setCellValue(all[i]);
			
			
		}
		List<StudentVo> li = ss.finA();
		for (int j = 0; j < li.size(); j++) {
			StudentVo vo = li.get(j);
			HSSFRow r = sheet.createRow(j + 1);
			r.createCell(0).setCellValue(vo.getSdId());
			r.createCell(1).setCellValue(vo.getSdname());
			r.createCell(2).setCellValue(vo.getSdsex());
			r.createCell(3).setCellValue(vo.getSdhobby());
			r.createCell(4).setCellValue(TimeUtil.formatTime(vo.getSdbirth(), "yyyy-MM-dd"));
			r.createCell(5).setCellValue(vo.getMname());

		}
		File file = new File("D:/stu.xls");
		FileOutputStream stream = new FileOutputStream(file);

		wb.write(stream);
		wb.close();

		result.put("success", true);


		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("gettj")
	public void gettj(HttpServletRequest request,HttpServletResponse response){
		List<StudentVo>li=ss.gettj();
		Object json = JSONObject.toJSON(li);
		WriterUtil.write(response, json.toString());
	}
	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	Destination queueTextDestination;
	@RequestMapping("reservetype")
	public void reservetype(tbMajorDay md,HttpServletRequest request,HttpServletResponse response){
		JSONObject result = new JSONObject();
		mm.insert(md);
		tbMajorDay day = new tbMajorDay();
		day.setMdname(md.getMdname());
		tbMajorDay one = mm.selectOne(day);
		jmsTemplate.convertAndSend(queueTextDestination,JSONObject.toJSONString(one));
		result.put("success", true);


		WriterUtil.write(response, result.toString());
		
	}

}
