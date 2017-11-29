package com.icss.hr.emp.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.icss.hr.common.Pager;
import com.icss.hr.emp.pojo.Emp;

public interface EmpMapper {
   
	void insert(Emp emp);
	
	void update(Emp emp);
	
	void delete(Integer empId);
	
	Emp queryById(Integer empId);
	
	/**
	 * �����¼��,���ݵ�¼���Ʋ�ѯ����������Ա�����ݷ��أ�Ա����š���¼�������룬ͷ�����ݣ� ��¼�������ڣ�����null
	 * @param empLoginName
	 * @return
	 */
	Emp queryByLoginName(String empLoginName);
	
	int getCount();
	
	/**
	 * ��ҳ��ѯ
	 * @param pager
	 * @return
	 */
	List<Emp> queryByPage(Pager pager);
	
	/**
	 * ���ݵ�¼������ͷ��
	 * @param empLoginName
	 * @param empPic
	 */
	void updateEmpPic(@Param("empLoginName")String empLoginName,@Param("empPic")String empPic);
	
	/**
	 * �޸�����
	 * @param emp
	 */
	void updatePwd(Emp emp);
	
	
	/**
	 * ���ݲ��ű�ţ�ְ���ţ�Ա��������������
	 */
	List<Emp> queryByCondition(@Param("deptId")Integer deptId,
			@Param("jobId")Integer jobId,@Param("empName")String empName,@Param("pager")Pager pager);	
	
	/**
	 * �����������ܼ�¼��
	 */
	int getCountByCondition(@Param("deptId")Integer deptId,
			@Param("jobId")Integer jobId,@Param("empName")String empName);
	
	/**
	 * ����������Զ����ֵ
	 */
	int getPrimaryKey();
	
	/**
	 * ��ѯ��������յ�Ա��
	 */
	List<Emp> queryBirthday();
	
}