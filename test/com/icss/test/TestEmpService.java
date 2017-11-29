package com.icss.test;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icss.hr.common.Pager;
import com.icss.hr.dept.pojo.Dept;
import com.icss.hr.dept.service.DeptService;
import com.icss.hr.emp.pojo.Emp;
import com.icss.hr.emp.service.EmpService;
import com.icss.hr.job.pojo.Job;

/**
 * ����Ա��service
 * @author DLETC
 *
 */
public class TestEmpService {
	
	private ApplicationContext context;
	private EmpService service;
	
	//@Before�������κ�@Test����֮ǰ�Զ�ִ��
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = (EmpService) context.getBean("empService");
	}
	
	@Test
	public void testInsert() throws IOException {
		
		Dept dept = new Dept();
		dept.setDeptId(1);
		
		Job job = new Job();
		job.setJobId(7);
		
		Emp emp = new Emp("��������6","dongfang6","123456","dongfang@163.com",
				"13012345678",3999.0,Date.valueOf("2017-11-20"),dept,job,"�ó�java oracle mysql webǰ�ˣ��������䣬���¾Ž�");
		
		service.addEmp(emp);		
	}
	
	@Test
	public void testUpdate() throws IOException {
		
		Dept dept = new Dept();
		dept.setDeptId(2);
		
		Job job = new Job();
		job.setJobId(2);
		
		Emp emp = new Emp(2,"jack222","jack222","123456","tom@163.com",
				"13822222222",7999.0,Date.valueOf("2017-11-11"),dept,job,"�ó�css javascript jquery");
		
		service.updateEmp(emp);		
	}
	
	@Test
	public void testDelete() throws IOException {		
		service.deleteEmp(2);		
	}
	
	@Test
	public void testQueryById() {		
		
		Emp emp = service.queryEmpById(3);
		System.out.println(emp);
		
	}
	
	@Test
	public void testQueryByPage() {		
		
		Pager pager = new Pager(service.getEmpCount(),7,4);
		
		List<Emp> list = service.queryEmpByPage(pager);
		
		for (Emp emp : list) {
			System.out.println(emp);
		}
		
	}
	
	@Test
	public void testQueryByLoginName() {		
		Emp emp = service.queryEmpByLoginName("tom");
		System.out.println(emp);		
	}
	
	@Test
	public void testCheckLogin() {		
		int result = service.checkLogin("tom", "123456");
		System.out.println("result=" + result);
	}
	
	@Test
	public void testUpdateEmpPic() {		
		service.updateEmpPic("tom", null);
	}

	@Test
	public void testUpdatePwd() {		
		Emp emp = new Emp();
		emp.setEmpLoginName("tom");
		emp.setEmpPwd("666666");
		
		service.updateEmpPwd(emp);	
	}
	
	@Test
	public void testQueryByCondition() {
		
		Integer deptId = 1;
		Integer jobId = 7;
		String empName = "jack2";
		
		int recordCount = service.getEmpCountByCondition(deptId, jobId, empName);
		System.out.println("���������ļ�¼��" + recordCount);
		
		Pager pager = new Pager(recordCount, 5, 1);	
		
		List<Emp> list = service.queryEmpByCondition(deptId, jobId, empName, pager);
		
		for (Emp emp : list) {
			System.out.println(emp);
		}
		
	}
	
	
	
}