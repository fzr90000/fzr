package com.xiaoshu.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.entity.tbMajorDay;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;





public class MyMessageListener implements MessageListener {
	@Autowired
	RedisTemplate redisTemplate;
//	JedisPool jedisPool;
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			TextMessage msg=(TextMessage)message;
			String string;
			string = msg.getText();
			tbMajorDay majorDay = JSONObject.parseObject(string, tbMajorDay.class);
			redisTemplate.boundHashOps("专业信息").put(majorDay.getmId(), majorDay.getmName());
//			Jedis jedis = jedisPool.getResource();
//			jedis.hset("专业信息",majorDay.getmId()+"", majorDay.getmName());
			System.out.println("专业信息："+string);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
