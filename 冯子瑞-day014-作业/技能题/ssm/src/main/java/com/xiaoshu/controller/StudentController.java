package com.xiaoshu.controller;

import java.util.Date;
import java.util.List;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.Teacher;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("stduent")
public class StudentController extends LogController{
	static Logger logger = Logger.getLogger(StudentController.class);

	@Autowired
	private StudentService ss;
	@Autowired
	private TeacherMapper tm;
	
	
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("studentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<Teacher> list = tm.selectAll();
		request.setAttribute("list", list);
		return "student";
	}
	
	
	@RequestMapping(value="studentList",method=RequestMethod.POST)
	public void studentList(StudentVo s,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<StudentVo> studentList= ss.findUserPage(s,pageNum,pageSize);
			
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
	
	
	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request,Student s,HttpServletResponse response){
		Integer id = s.getId();
		JSONObject result=new JSONObject();
		try {
			if (id != null) {   // userId不为空 说明是修改
					ss.updateUser(s);
					result.put("success", true);
				
				
			}else {   // 添加
					ss.addUser(s);
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
	
	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	Destination queueTextDestination;
	@RequestMapping("reserveTea")
	public void delUser(Teacher t,HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		t.setCreatetime(new Date());
		t.setEntrytime(new Date());
		tm.insert(t);
		Teacher teacher = new Teacher();
		teacher.setName(t.getName());
		Teacher one = tm.selectOne(teacher);
		jmsTemplate.convertAndSend(queueTextDestination,JSONObject.toJSONString(one));
		
			result.put("success", true);
		WriterUtil.write(response, result.toString());
	}
	@RequestMapping("getttj")
	public void getttj(HttpServletRequest request,HttpServletResponse response){
		List<StudentVo>li=ss.getttj();
		System.out.println(li);
		Object json = JSONObject.toJSON(li);
		
	
		WriterUtil.write(response, json.toString());
	}
//	
//	@RequestMapping("editPassword")
//	public void editPassword(HttpServletRequest request,HttpServletResponse response){
//		JSONObject result=new JSONObject();
//		String oldpassword = request.getParameter("oldpassword");
//		String newpassword = request.getParameter("newpassword");
//		HttpSession session = request.getSession();
//		User currentUser = (User) session.getAttribute("currentUser");
//		if(currentUser.getPassword().equals(oldpassword)){
//			User user = new User();
//			user.setUserid(currentUser.getUserid());
//			user.setPassword(newpassword);
//			try {
//				userService.updateUser(user);
//				currentUser.setPassword(newpassword);
//				session.removeAttribute("currentUser"); 
//				session.setAttribute("currentUser", currentUser);
//				result.put("success", true);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error("修改密码错误",e);
//				result.put("errorMsg", "对不起，修改密码失败");
//			}
//		}else{
//			logger.error(currentUser.getUsername()+"修改密码时原密码输入错误！");
//			result.put("errorMsg", "对不起，原密码输入错误！");
//		}
//		WriterUtil.write(response, result.toString());
//	}
}
