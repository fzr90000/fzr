package com.xiaoshu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.EmpVo;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.dDept;
import com.xiaoshu.entity.eEmp;
import com.xiaoshu.entity.expressCompany;
import com.xiaoshu.entity.expressPerson;
import com.xiaoshu.service.EmpService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.PersonService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("person")
public class PersonController extends LogController{
	static Logger logger = Logger.getLogger(PersonController.class);

	@Autowired
	private PersonService ps;
	
	
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("personIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<expressCompany> li=ps.finddepet();
		request.setAttribute("li", li);
		return "person";
	}
	
	
	@RequestMapping(value="personList",method=RequestMethod.POST)
	public void personList(PersonVo p,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			System.out.println(p);
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<PersonVo> personList= ps.findEmpPage(p, pageNum, pageSize);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",personList.getTotal() );
			jsonObj.put("rows", personList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request,expressPerson per,HttpServletResponse response,MultipartFile file) throws Exception, IOException{
		
		Integer id = per.getId();
		JSONObject result=new JSONObject();
		try {
			if (id != null) {   // userId不为空 说明是修改
					ps.updateUser(per);
					result.put("success", true);
				
			}else {   // 添加
					ps.addUser(per);
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
				ps.deleteUser(Integer.parseInt(id));
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
