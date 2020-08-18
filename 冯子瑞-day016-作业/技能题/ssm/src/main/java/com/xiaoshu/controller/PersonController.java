package com.xiaoshu.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import com.xiaoshu.dao.jiyunBankMapper;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.PersonVo;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.jiyunBank;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.PersonService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
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
	private jiyunBankMapper bm;
	
	
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("personIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<jiyunBank> list = bm.selectAll();
		request.setAttribute("list", list);
		return "person";
	}
	
	
	@RequestMapping(value="personList",method=RequestMethod.POST)
	public void personList(PersonVo p,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<PersonVo> personList= ps.findUserPage(p, pageNum, pageSize);
			
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
	public void reserveUser(HttpServletRequest request,PersonVo p,HttpServletResponse response){
	Integer id = p.getpId();
		JSONObject result=new JSONObject();
		try {
			if (id != null) {   // userId不为空 说明是修改
				p.setCreatetime(new Date());
				ps.update(p);
				result.put("success", true);
			}else {   // 添加
				p.setCreatetime(new Date());
				ps.add(p);
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
	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	Destination	queueTextDestination;
	@RequestMapping("reserveband")
	public void reserveband(jiyunBank b,HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		b.setCreatetime(new Date());
		bm.insert(b);
		jiyunBank bank = new jiyunBank();
		bank.setbName(b.getbName());
		jiyunBank one = bm.selectOne(bank);
		jmsTemplate.convertAndSend(queueTextDestination,JSONObject.toJSONString(one));
		
		
			result.put("success", true);
		WriterUtil.write(response, result.toString());
	}
	@RequestMapping("gettj")
	public void gettj(HttpServletRequest request,HttpServletResponse response){
		List<PersonVo> i=ps.gettj();
		Object json = JSONObject.toJSON(i);
		
		WriterUtil.write(response, json.toString());
	}
	@SuppressWarnings("resource")
	@RequestMapping("exp")
	public void exp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject result=new JSONObject();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow(0);
		String[] arr={"用户编号","用户姓名","用户年龄","用户性别","创建时间","用户愛好","金额","银行"};
		for (int i = 0; i < arr.length; i++) {
			row.createCell(i).setCellValue(arr[i]);
		}
		List<PersonVo>li=ps.findexp();
		for (int j = 0; j <li.size(); j++) {
			PersonVo vo = li.get(j);
			HSSFRow r = sheet.createRow(j+1);
			r.createCell(0).setCellValue(vo.getpId());
			r.createCell(1).setCellValue(vo.getpName());
			r.createCell(2).setCellValue(vo.getpAge());
			r.createCell(3).setCellValue(vo.getpSex());
			r.createCell(4).setCellValue(TimeUtil.formatTime(vo.getCreatetime(), "yyyy-MM-dd"));
			r.createCell(5).setCellValue(vo.getpLike());
			r.createCell(6).setCellValue(vo.getpCount());
			r.createCell(7).setCellValue(vo.getBname());
		}
		File file = new File("D:/存款信息.xls");
		FileOutputStream stream = new FileOutputStream(file);
		wb.write(stream);
		wb.close();
		
			result.put("success", true);
		WriterUtil.write(response, result.toString());
	}
	

}
