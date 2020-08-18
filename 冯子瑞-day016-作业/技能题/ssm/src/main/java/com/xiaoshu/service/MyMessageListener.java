package com.xiaoshu.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.entity.jiyunBank;

public class MyMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		TextMessage msg=(TextMessage)message;
		try {
			String text = msg.getText();
			jiyunBank jiyunBank = JSONObject.parseObject(text, jiyunBank.class);
			System.out.println("银行信息"+jiyunBank);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
