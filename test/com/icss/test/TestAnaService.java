package com.icss.test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icss.hr.analysis.service.AnaService;
import com.icss.hr.dept.service.DeptService;

/**
 * �������ݷ���service
 * @author DLETC
 *
 */
public class TestAnaService {
	
	private ApplicationContext context;
	private AnaService service;
	
	//@Before�������κ�@Test����֮ǰ�Զ�ִ��
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = (AnaService) context.getBean("anaService");
	}
	
	@Test
	public void testQueryEmpCount() {
		
		List<Map<String, Object>> list = service.queryEmpCount();
		
		for (Map map : list) {
			System.out.println(map);
		}
		
	}

}