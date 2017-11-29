package com.icss.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icss.hr.perm.service.PermService;

/**
 * 
 * ����Ȩ��service
 *
 */
public class TestPermService {
	
	private ApplicationContext context;
	private PermService service;
	
	//@Before�������κ�@Test����֮ǰ�Զ�ִ��
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = (PermService) context.getBean("permService");
	}
	
	/**
	 * ���Բ�ѯ��ɫ�б�
	 */
	@Test
	public void testQueryRole() {
		
		List<HashMap<String,Object>> list = service.queryRole("tom");
		
		for (Map map : list) {
			System.out.println(map);
		}
		
	}
	
	/**
	 * ���Բ�ѯȨ���б�
	 */
	@Test
	public void testQueryPerm() {
		
		List<HashMap<String,Object>> list = service.queryPerm("rose");
		
		for (Map map : list) {
			System.out.println(map);
		}
		
	}

}