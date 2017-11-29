package com.icss.hr.emp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icss.hr.common.Pager;
import com.icss.hr.common.ShiroRealm;
import com.icss.hr.emp.pojo.Emp;
import com.icss.hr.emp.pojo.EmpDto;
import com.icss.hr.emp.service.EmpService;

/**
 * Ա��������
 * 
 * @author DLETC
 *
 */
@Controller
public class EmpController {

	@Autowired
	private EmpService service;

	@Autowired
	private ShiroRealm shiroRealm;

	/**
	 * �ж��û����Ƿ����
	 * 
	 * @param empLoginName
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/emp/checkLoginName")
	public String checkLoginName(String empLoginName, HttpServletRequest request, HttpServletResponse response) {

		Emp emp = service.queryEmpByLoginName(empLoginName);

		if (emp == null)
			return "yes";
		else
			return "no";
	}

	/**
	 * ����Ա��
	 * 
	 * @param emp
	 * @throws IOException
	 */
	@RequestMapping("/emp/add")
	public void addEmp(Emp emp, HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(emp);
		service.addEmp(emp);
	}

	/**
	 * ��ҳ��ѯԱ������
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/emp/query")
	@ResponseBody
	public HashMap<String, Object> queryEmp(String pageNum, String pageSize, HttpServletRequest request,
			HttpServletResponse response) {

		int pageNumInt = 1;

		try {
			pageNumInt = Integer.parseInt(pageNum);
		} catch (Exception e) {

		}

		int pageSizeInt = 10;

		try {
			pageSizeInt = Integer.parseInt(pageSize);
		} catch (Exception e) {

		}

		// ��ҳ����
		Pager pager = new Pager(service.getEmpCount(), pageSizeInt, pageNumInt);

		// ��õ�ǰҳ�����ݼ���
		List<Emp> list = service.queryEmpByPage(pager);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pager", pager);
		map.put("list", list);

		return map;
	}

	/**
	 * ɾ��Ա��
	 * 
	 * @param empId
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/emp/delete")
	public void deleteEmp(Integer empId, HttpServletRequest request, HttpServletResponse response) throws IOException {

		service.deleteEmp(empId);

	}

	/**
	 * ��ѯ����Ա������
	 */
	@RequestMapping("/emp/get")
	@ResponseBody
	public Emp getEmp(Integer empId, HttpServletRequest request, HttpServletResponse response) {
		Emp emp = service.queryEmpById(empId);
		return emp;
	}

	/**
	 * �޸�Ա������
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/emp/update")
	@ResponseBody
	public void updateEmp(Emp emp, HttpServletRequest request, HttpServletResponse response) throws IOException {
		service.updateEmp(emp);
	}

	/**
	 * ��õ�ǰ��¼����
	 */
	@RequestMapping("/emp/getLoginName")
	@ResponseBody
	public String getLoginName(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		String empLoginName = (String) session.getAttribute("empLoginName");

		return empLoginName;
	}

	/**
	 * ��õ�ǰ�û�����
	 */
	@RequestMapping("/emp/getPwd")
	@ResponseBody
	public String getPwd(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");
		Emp emp = service.queryEmpByLoginName(empLoginName);

		return emp.getEmpPwd();
	}

	/**
	 * ���ݵ�ǰ��¼������Ա����Ϣ
	 */
	@RequestMapping("/emp/queryByLoginName")
	@ResponseBody
	public Emp queryEmpByLoginName(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");

		Emp emp = service.queryEmpByLoginName(empLoginName);

		return emp;
	}

	/**
	 * ��¼��֤
	 */
	@RequestMapping("/emp/login")
	@ResponseBody
	public String login(String empLoginName, String empPwd, HttpServletRequest request, HttpServletResponse response) {

		// ��װ�û���������
		UsernamePasswordToken token = new UsernamePasswordToken(empLoginName, empPwd);
		// ����RememberMe
		token.setRememberMe(true);

		/* ͨ��shiro��¼ */
		Subject currentUser = SecurityUtils.getSubject();

		try {
			currentUser.login(token);
		} catch (UnknownAccountException e) { // �û���������
			return "1";
		} catch (IncorrectCredentialsException e) { // �������
			return "2";
		}

		/* ��֤�ɹ� */

		// ��õ�ǰ��½Ա����id����
		Emp emp = (Emp) service.queryEmpByLoginName(empLoginName);

		// ��session��Χ�洢����
		HttpSession session = request.getSession();
		session.setAttribute("empLoginName", empLoginName);
		session.setAttribute("empId", emp.getEmpId());

		return "3";
	}

	/**
	 * ���ص�ǰ�û�ͷ��base64����
	 */
	@RequestMapping("/emp/queryPic")
	@ResponseBody
	public String queryEmpPic(HttpServletRequest request, HttpServletResponse response) {

		// ��session����û���¼����
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");

		// ����ͷ������
		Emp emp = service.queryEmpByLoginName(empLoginName);

		return emp.getEmpPic();
	}

	/**
	 * �����û�ͷ��
	 */
	@RequestMapping("/emp/updatePic")
	public void updateEmpPic(String empPic, HttpServletRequest request, HttpServletResponse response) {

		// ��session����û���¼����
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");

		// ����ͷ������
		service.updateEmpPic(empLoginName, empPic);
	}

	/**
	 * ��������
	 */
	@RequestMapping("/emp/updatePwd")
	public void updatePwd(String empPwd, HttpServletRequest request, HttpServletResponse response) {

		// ����û���
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");

		// ��װpojo����
		Emp emp = new Emp();
		emp.setEmpLoginName(empLoginName);
		emp.setEmpPwd(empPwd);

		// �޸�����
		service.updateEmpPwd(emp);
	}

	/**
	 * ��������Ա��
	 */
	@RequestMapping("/emp/queryByCondition")
	@ResponseBody
	public Map<String, Object> queryEmpByCondition(Integer deptId, Integer jobId, String empName, String pageNum,
			String pageSize, HttpServletRequest request, HttpServletResponse response) {

		int pageNumInt = 1;

		try {
			pageNumInt = Integer.parseInt(pageNum);
		} catch (Exception e) {

		}

		int pageSizeInt = 10;

		try {
			pageSizeInt = Integer.parseInt(pageSize);
		} catch (Exception e) {

		}

		// ���������������ܼ�¼��
		int recordCount = service.getEmpCountByCondition(deptId, jobId, empName);

		Pager pager = new Pager(recordCount, pageSizeInt, pageNumInt);

		// ��������������Ա����¼
		List<Emp> list = service.queryEmpByCondition(deptId, jobId, empName, pager);

		HashMap<String, Object> map = new HashMap<>();
		map.put("pager", pager);
		map.put("list", list);

		return map;
	}
	
	/**
	 * ȫ�ļ���Ա��
	 * @throws Exception 
	 */
	@RequestMapping("/emp/queryByIndex")
	@ResponseBody
	public EmpDto queryEmpByIndex(String keywords) throws Exception {
		
		return service.queryEmpByIndex(keywords);
	}	

}