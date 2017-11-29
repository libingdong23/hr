package com.icss.hr.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * ����������
 */
public class CommonFilter implements Filter {

	public void destroy() {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {

		// ת������
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;

		// ����������������ʵ�ǰ��Ŀ
		response.setHeader("Access-Control-Allow-Origin", "*");
				
		//�������url
		String uri = request.getRequestURI();
		System.out.println(uri);
		
		//webӦ������
		String app = request.getContextPath();
		
		//�ж��û���¼״̬
		if ( !uri.equals(app + "/") 
				&& !uri.equals(app + "/login.html")
				&& !uri.equals(app + "/logout.jsp")
				&& !uri.equals(app + "/emp/login")
				&& !uri.startsWith(app + "/css")
				&& !uri.startsWith(app + "/js")
				&& !uri.startsWith(app + "/images") ) {
			
			//��session�л���û���
			HttpSession session = request.getSession();
			String empLoginName = (String) session.getAttribute("empLoginName");
			System.out.println("empLoginName=" + empLoginName);
						
			if (empLoginName == null) {
				
				//�жϵ�ǰ����������������ajax����
				String ajaxHeader = request.getHeader("x-requested-with");
				
				//�����ajax��������Ӧ�ı�ͷ������һ������ı�־���Ա���ǰ�����жϣ�
				//������������󣬼�������ִ�н���shiro����
				if (ajaxHeader != null && ajaxHeader.equalsIgnoreCase("XMLHttpRequest")) {
					response.setHeader("sessionStatus", "timeout");
					return;
				} 							
				
			}			
			
		}	

		//��������ִ��
		chain.doFilter(request, response);		
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}