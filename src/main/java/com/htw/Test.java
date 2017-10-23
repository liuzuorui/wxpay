package com.htw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.htw.service.IPayService;

public class Test {

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
        "applicationContext.xml");
		final IPayService pay = (IPayService) ac.getBean("PayServiceImpl");
		try {
			pay.save();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
