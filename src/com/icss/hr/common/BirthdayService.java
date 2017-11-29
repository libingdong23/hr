package com.icss.hr.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icss.hr.emp.dao.EmpMapper;
import com.icss.hr.emp.pojo.Emp;

/**
 * 员工生日定时检查自动发送电子邮件业务功能
 * @author DLETC
 *
 */
@Service
public class BirthdayService {
	
	@Autowired
	private EmpMapper mapper;
	
	/**
	 * 定时检查员工生日
	 */
	public void checkBirthday() {
		
		//获得今天过生日的员工数据集合
		List<Emp> list = mapper.queryBirthday();
		
		//循环发送电子邮件
		for (Emp emp : list) {
			
			System.out.println("发送电子邮件：" + emp.getEmpEmail());
			
			String content = emp.getEmpName() + ",你好！<br><br> &nbsp;&nbsp;&nbsp;&nbsp;祝你今天生日快乐！<br><br>中软国际公司";
			
			MailUtil.sendMail("tom@icss.com", "tom@icss.com", "123456", emp.getEmpEmail(), "祝你生日快乐", content);
						
		}
		
	}

}