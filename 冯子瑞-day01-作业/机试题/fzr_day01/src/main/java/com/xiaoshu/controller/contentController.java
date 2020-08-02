package com.xiaoshu.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.Util;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentVo;
import com.xiaoshu.entity.Contentcategory;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.ContentService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("content")
public class contentController extends LogController{
	static Logger logger = Logger.getLogger(contentController.class);

	@Autowired
	private ContentService cs;
	
	
	
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("contentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<Contentcategory>li=cs.findgory();
		request.setAttribute("li", li);
		return "content";
	}
	
	
	@RequestMapping(value="contentList",method=RequestMethod.POST)
	public void contentList(ContentVo vo,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<ContentVo> contentList= cs.findUserPage(vo,pageNum,pageSize);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",contentList.getTotal() );
			jsonObj.put("rows", contentList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request,Content content,HttpServletResponse response){
		Integer contentid = content.getContentid();
		JSONObject result=new JSONObject();
		try {
			if (contentid != null) {   // userId不为空 说明是修改
				
				
			}else {   // 添加
				System.out.println(cs.findByName(content.getContenttitle()));
				if(cs.findByName(content.getContenttitle()).size()==0){  // 没有重复可以添加
					cs.addcontent(content);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
//	
	@RequestMapping("deleteUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				cs.deleteUser(Integer.parseInt(id));
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
	
	@RequestMapping("imp")
	public void imp(HttpServletRequest request,HttpServletResponse response,MultipartFile file) throws Exception{
		JSONObject result=new JSONObject();
		HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
		HSSFSheet sheet = wb.getSheetAt(0);
		int num = sheet.getLastRowNum();
		for (int i = 0; i <=num; i++) {
			Content c = new Content();
			HSSFRow row = sheet.getRow(i);
			c.setContentcategoryid((int)row.getCell(1).getNumericCellValue());
			c.setContenttitle(row.getCell(2).getStringCellValue());
			c.setContenturl(row.getCell(3).getStringCellValue());
			c.setPicpath(row.getCell(4).getStringCellValue());
			c.setPrice((int)row.getCell(5).getNumericCellValue());
			c.setStatus(row.getCell(6).getStringCellValue());
			Date date = row.getCell(7).getDateCellValue();
			c.setCreatetime(date);
		cs.addcontent(c);
		}
		wb.close();
		result.put("success", true);
		
		
		WriterUtil.write(response, result.toString());
	}

}
