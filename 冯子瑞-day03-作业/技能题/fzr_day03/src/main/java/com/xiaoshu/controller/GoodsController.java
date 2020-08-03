package com.xiaoshu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.tbGoods;
import com.xiaoshu.entity.tbGoodstype;
import com.xiaoshu.service.GoodsService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("goods")
public class GoodsController extends LogController{

	@Autowired
	private GoodsService gs;
	
	
	
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("goodsIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<tbGoodstype>li=gs.findtype();
		request.setAttribute("li", li);
		return "goods";
	}
	
	
	@RequestMapping(value="goodsList",method=RequestMethod.POST)
	public void goodsList(GoodsVo gv,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<GoodsVo> goodsList= gs.findgooodsPage(gv,pageNum,pageSize);
			
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",goodsList.getTotal() );
			jsonObj.put("rows", goodsList.getList());
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
	public void reserveUser(HttpServletRequest request,tbGoods tb,HttpServletResponse response){
	Integer id = tb.getgId();
		JSONObject result=new JSONObject();
		try {
			if (id != null) {   // userId不为空 说明是修改
				
				
			}else {   // 添加
					gs.add(tb);
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
				gs.deleteUser(Integer.parseInt(id));
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
	@RequestMapping("gettj")
	public void gettj(HttpServletRequest request,HttpServletResponse response){
		List<GoodsVo>li=gs.gettj();
		Object json = JSONObject.toJSON(li);
		
		WriterUtil.write(response, json.toString());
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
