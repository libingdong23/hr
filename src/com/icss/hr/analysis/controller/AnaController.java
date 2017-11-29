package com.icss.hr.analysis.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icss.hr.analysis.service.AnaService;

/**
 * ���ݷ���������
 * @author DLETC
 *
 */
@Controller
public class AnaController {
	
	@Autowired
	private AnaService service;
	
	/**
	 * ���ز���Ա������
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/ana/queryEmpCount")
	@ResponseBody
	public List<Map<String, Object>> queryEmpCount(HttpServletRequest request,HttpServletResponse response) {
		
		List<Map<String, Object>> list = service.queryEmpCount();
		
		return list;
	}

}