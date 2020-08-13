package com.xiaoshu.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.dao.tbMajorDayMapper;
import com.xiaoshu.entity.tbMajorDay;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class MyMessageListener implements MessageListener{
	@Autowired
	JedisPool jedisPool;
	@Autowired
	tbMajorDayMapper mm;
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			TextMessage msg=(TextMessage)message;
			String string;
			string = msg.getText();
			tbMajorDay d = JSONObject.parseObject(string, tbMajorDay.class);
			Jedis jedis = jedisPool.getResource();
			jedis.set(d.getMdname(), d.getMdId()+"");
			System.out.println("添加："+string);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
