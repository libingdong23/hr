package com.icss.test;

import java.util.List;

import org.apache.catalina.mapper.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icss.hr.dept.pojo.Dept;
import com.icss.hr.dept.service.DeptService;

/**
 * ���Բ���service
 * @author DLETC
 *
 */
public class TestDeptService {
	
	private ApplicationContext context;
	private DeptService service;
	
	//@Before�������κ�@Test����֮ǰ�Զ�ִ��
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = (DeptService) context.getBean("deptService");
	}
	
	@Test
	public void testInsert() {
		Dept dept = new Dept("������","IC����һ¥");
		service.addDept(dept);
	}
	
	@Test
	public void testUpdate() {
		Dept dept = new Dept(4,"������","����5¥");
		service.updateDept(dept);
	}
	
	@Test
	public void testDelete() {
		service.deleteDept(9);
	}
	
	@Test
	public void testQueryById() {
		Dept dept = service.queryDeptById(11111);
		System.out.println(dept);
	}
	
	@Test
	public void testQuery() {
		List<Dept> list = service.queryDept();
		
		for (Dept dept : list) {
			System.out.println(dept);
		}
	}

}