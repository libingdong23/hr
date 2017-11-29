package com.icss.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icss.hr.common.BirthdayService;
import com.icss.hr.emp.service.EmpService;

/**
 * �������ռ��service
 * @author DLETC
 *
 */
public class TestBirthdayService {
	
	private ApplicationContext context;
	private BirthdayService service;
	
	//@Before�������κ�@Test����֮ǰ�Զ�ִ��
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = (BirthdayService) context.getBean("birthdayService");
	}
	
	@Test
	public void testCheckBirthday() {
		
		service.checkBirthday();
		
	}

}