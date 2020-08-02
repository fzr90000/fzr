package com.xiaoshu.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.DeviceVo;
import com.xiaoshu.entity.Devicetype;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.DeviceService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("device")
public class DerviceController extends LogController{
	static Logger device = Logger.getLogger(DerviceController.class);

	@Autowired
	private DeviceService ds;
	
	
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("deviceIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<Devicetype> li=ds.selectAll();
		request.setAttribute("li", li);
		return "device";
	}
	
	
	@RequestMapping(value="deviceList",method=RequestMethod.POST)
	public void deviceList(DeviceVo dv,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			System.out.println(dv);
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<DeviceVo> deviceList= ds.findDervicePage(dv,pageNum,pageSize);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",deviceList.getTotal() );
			jsonObj.put("rows", deviceList.getList());
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
	public void reserveUser(HttpServletRequest request,Device de,HttpServletResponse response){
		Integer deviceid = de.getDeviceid();
		JSONObject result=new JSONObject();
		try {
			if (deviceid != null) {   // userId不为空 说明是修改
				
				
			}else {   // 添加
				if(ds.existUserWithUserName(de.getDevicename())==null){  // 没有重复可以添加
					ds.addDe(de);
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
				ds.deleteUser(Integer.parseInt(id));
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
//	导出
	@RequestMapping("exp")
	public void exp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject result=new JSONObject();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row = sheet.createRow(0);

			String[] arr={"编号","设备名称","设备类型名称","内存","机身颜色","价格","状态","创建时间"};
			for (int i = 0; i < arr.length; i++) {
				row.createCell(i).setCellValue(arr[i]);
			}
			List<DeviceVo> li=ds.findAll();
			for (int i = 0; i < li.size(); i++) {
				HSSFRow r = sheet.createRow(i+1);
				DeviceVo vo = li.get(i);
				r.createCell(0).setCellValue(vo.getDeviceid());
				r.createCell(1).setCellValue(vo.getDevicename());
				r.createCell(2).setCellValue(vo.getTname());
				r.createCell(3).setCellValue(vo.getDeviceram());
				r.createCell(4).setCellValue(vo.getColor());
				r.createCell(5).setCellValue(vo.getPrice());
				r.createCell(6).setCellValue(vo.getStatus());
				r.createCell(7).setCellValue(TimeUtil.formatTime(vo.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
			}
			File file = new File("D:/1910设备管理.xls");
			FileOutputStream stream = new FileOutputStream(file);
			wb.write(stream);
			wb.close();
		
		
		
		result.put("success", true);
		WriterUtil.write(response, result.toString());
	}

}
