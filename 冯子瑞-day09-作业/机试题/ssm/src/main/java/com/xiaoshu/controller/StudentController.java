package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.SchoolMapper;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.School;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.expressCompany;
import com.xiaoshu.entity.expressPerson;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.PersonService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("student")
public class StudentController extends LogController{
	static Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private StudentService ss;
	
	
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("studentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<School>li=ss.selectAll();
		request.setAttribute("li", li);
		return "student";
	}
	
	
	@RequestMapping(value="studentList",method=RequestMethod.POST)
	public void studentList(StudentVo stu,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{

		try {
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<StudentVo> studentList= ss.findStudentPage(stu, pageNum, pageSize);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",studentList.getTotal() );
			jsonObj.put("rows", studentList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
//	
	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request,Student stu,HttpServletResponse response) throws Exception, IOException{
		Integer sid = stu.getSid();
		JSONObject result=new JSONObject();
		try {
			if (sid != null) {   // userId不为空 说明是修改
					ss.updateUser(stu);
					result.put("success", true);
				
			}else {   // 添加
					ss.add(stu);
					result.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
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
	//echares
	@RequestMapping("getTj")
	public void getTj(HttpServletRequest request,HttpServletResponse response){
		List<StudentVo>li=ss.gettj();
		System.out.println(li);
		Object json = JSONObject.toJSON(li);
		WriterUtil.write(response, json.toString());
	}
	@Autowired 
	private SchoolMapper sm;
	@Autowired
	JedisPool jedisPool;
	//kecheng
	@RequestMapping("adddept")
	public void adddept(School sx,HttpServletRequest request,HttpServletResponse response){
		System.out.println(sx);
		JSONObject result=new JSONObject();
		sm.insert(sx);
		School one = sm.selectOne(sx);
		Jedis jedis = jedisPool.getResource();
		jedis.hset("课程目录添加", one.getTid()+"", one.toString());
		result.put("success", true);
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("exp")
	public void exp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow(0);
	
		String[] tit={"学生编号","学生名","编号","学生年龄","创建时间","入学时间","年级","学校"};
		for (int i = 0; i < tit.length; i++) {
			row.createCell(i).setCellValue(tit[i]);
		}
		List<StudentVo>li=ss.findexp();
		for (int j = 0; j < li.size(); j++) {
			StudentVo s = li.get(j);
			System.out.println(s);
			HSSFRow r = sheet.createRow(j+1);
			r.createCell(0).setCellValue(s.getSid());
			r.createCell(1).setCellValue(s.getSname());
			r.createCell(2).setCellValue(s.getCode());
			r.createCell(3).setCellValue(s.getAge());
			r.createCell(4).setCellValue(TimeUtil.formatTime(s.getCreatetime(), "yyyy-MM-dd"));
			r.createCell(5).setCellValue(TimeUtil.formatTime(s.getEntrytime(), "yyyy-MM-dd"));
			r.createCell(6).setCellValue(s.getGrade());
			r.createCell(7).setCellValue(s.getTname());
			
		}
		File file = new File("D:/student.xls");
		FileOutputStream stream = new FileOutputStream(file);
		wb.write(stream);
		wb.close();
		
		
		
	}
//	
//	@RequestMapping("imp")
//	public void imp(HttpServletRequest request,HttpServletResponse response,MultipartFile file) throws Exception{
//		JSONObject result=new JSONObject();
//		HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
//		HSSFSheet sheetAt = wb.getSheetAt(0);
//		int rowNum = sheetAt.getLastRowNum();
//		for (int i = 1; i <=rowNum; i++) {
//			HSSFRow r = sheetAt.getRow(i);
//			Student s = new Student();
//			s.setSname(r.getCell(1).toString());
//			s.setSex(r.getCell(2).toString());
//			
//			s.setAge(r.getCell(3).toString());
//			s.setCreatetime(r.getCell(4).getDateCellValue());
//			String name = r.getCell(5).toString();
//			School school=ss.getde(name);
//			s.setTid(school.getTid());
//			ss.add(s);
//		}
//		result.put("success", true);
//		WriterUtil.write(response, result.toString());
//		
//	}
//	

}
