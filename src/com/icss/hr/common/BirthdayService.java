package com.icss.hr.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icss.hr.emp.dao.EmpMapper;
import com.icss.hr.emp.pojo.Emp;

/**
 * Ա�����ն�ʱ����Զ����͵����ʼ�ҵ����
 * @author DLETC
 *
 */
@Service
public class BirthdayService {
	
	@Autowired
	private EmpMapper mapper;
	
	/**
	 * ��ʱ���Ա������
	 */
	public void checkBirthday() {
		
		//��ý�������յ�Ա�����ݼ���
		List<Emp> list = mapper.queryBirthday();
		
		//ѭ�����͵����ʼ�
		for (Emp emp : list) {
			
			System.out.println("���͵����ʼ���" + emp.getEmpEmail());
			
			String content = emp.getEmpName() + ",��ã�<br><br> &nbsp;&nbsp;&nbsp;&nbsp;ף��������տ��֣�<br><br>������ʹ�˾";
			
			MailUtil.sendMail("tom@icss.com", "tom@icss.com", "123456", emp.getEmpEmail(), "ף�����տ���", content);
						
		}
		
	}

}