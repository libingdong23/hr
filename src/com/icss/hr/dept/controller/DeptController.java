package com.icss.hr.dept.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icss.hr.dept.pojo.Dept;
import com.icss.hr.dept.service.DeptService;

/**
 * ���ſ�����
 * @author DLETC
 *
 */
@Controller
public class DeptController {
	
	@Autowired
	private DeptService service;
	
	/**
	 * ���Ӳ���
	 * @param dept ��������
	 * @param request ����
	 * @param response ��Ӧ
	 * @return ת����ַ
	 */
	@RequestMapping("/dept/add")
	public String add(Dept dept,HttpServletRequest request,HttpServletResponse response) {
		
		service.addDept(dept);
		
		//�ض��򵽲�ѯ
		return "redirect:/dept/query";
	}
	
	/**
	 * ��ѯ��������
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dept/query")
	public String query(Map map,HttpServletRequest request,HttpServletResponse response) {
		
		//����ҵ�����
		List<Dept> list = service.queryDept();
		
		//��Map�����д洢����
		map.put("list", list);//�ȼ���request.setAttribute("list",list)
		
		return "QueryDept";
	}
	
	/**
	 * ɾ������
	 * @param deptId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dept/delete")
	public String delete(Integer deptId,HttpServletRequest request,HttpServletResponse response) {
		
		service.deleteDept(deptId);
		
		//�ض��򵽲�ѯ
		return "redirect:/dept/query";
	}
	
	/**
	 * ��ѯ��������
	 * @param deptId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dept/get")
	public ModelAndView get(Integer deptId,HttpServletRequest request,HttpServletResponse response) {
		
		Dept dept = service.queryDeptById(deptId);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("dept",dept);
		mv.setViewName("UpdateDept");
		
		//ת����JSP��ͼ
		return mv;
	}
	
	/**
	 * �޸Ĳ���
	 * @param dept
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dept/update")
	public String update(Dept dept,HttpServletRequest request,HttpServletResponse response) {
		
		service.updateDept(dept);
		
		//�ض��򵽲�ѯ
		return "redirect:/dept/query";
	}
	
	/**
	 * ��Ӧ����json���ݼ���
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/dept/queryJson")
	public List<Dept> queryJson(HttpServletRequest request,HttpServletResponse response) {
		List<Dept> list = service.queryDept();
		return list;
	}
	
}