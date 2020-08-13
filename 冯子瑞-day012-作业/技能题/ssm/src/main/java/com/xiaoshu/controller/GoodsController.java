package com.xiaoshu.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.dao.GoodstypeMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.Goodstype;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.SchoolVo;
import com.xiaoshu.service.GoodsService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("goods")
public class GoodsController extends LogController {
	static Logger logger = Logger.getLogger(GoodsController.class);

	@Autowired
	private GoodsService gs;
	@Autowired
	private GoodstypeMapper tm;

	@Autowired
	private OperationService operationService;

	@RequestMapping("goodsIndex")
	public String index(HttpServletRequest request, Integer menuid) throws Exception {
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		List<Goodstype> list = tm.selectAll();
		request.setAttribute("list", list);

		return "goods";
	}

	@RequestMapping(value = "goodsList", method = RequestMethod.POST)
	public void goodsList(GoodsVo g, HttpServletRequest request, HttpServletResponse response, String offset,
			String limit) throws Exception {
		try {

			Integer pageSize = StringUtil.isEmpty(limit) ? ConfigUtil.getPageSize() : Integer.parseInt(limit);
			Integer pageNum = (Integer.parseInt(offset) / pageSize) + 1;
			PageInfo<GoodsVo> goodsList = gs.findUserPage(g, pageNum, pageSize);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total", goodsList.getTotal());
			jsonObj.put("rows", goodsList.getList());
			WriterUtil.write(response, jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误", e);
			throw e;
		}
	}
	@RequestMapping("gettj")
	public void gettj(HttpServletRequest request,HttpServletResponse response){
		
	List<GoodsVo>li=gs.findtype();
	Object json = JSONObject.toJSON(li);
	WriterUtil.write(response, json.toString());
	
	}
	

	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request,Goods g, HttpServletResponse response) {
		Integer id = g.getId();
		JSONObject result = new JSONObject();
		try {
			if (id != null) { // userId不为空 说明是修改
				gs.update(g);
			} else { // 添加// 没有重复可以添加
				if (gs.existUserWithUserName(g.getCode()) == null) {
					gs.addUser(g);
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
	// 新增type
	@Autowired
	JedisPool jedisPool;
	
	@RequestMapping("addtype")
	public void addtype(HttpServletRequest request,Goodstype gt, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
					gt.setCreatetime(new Date());
					tm.insert(gt);
					Goodstype g = new Goodstype();
					g.setTypename(gt.getTypename());
					Goodstype one = tm.selectOne(g);
					Jedis jedis = jedisPool.getResource();
					jedis.hset("商品分类列表"+one.getId()+"",one.getId()+"",one.toString());
					result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误", e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}



}
