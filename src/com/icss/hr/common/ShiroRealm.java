package com.icss.hr.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.icss.hr.emp.pojo.Emp;
import com.icss.hr.emp.service.EmpService;
import com.icss.hr.perm.service.PermService;

/**
 * �Զ���realm
 */
public class ShiroRealm extends AuthorizingRealm {
	
	@Autowired
	private EmpService empService;
	
	@Autowired
	private PermService permService;

	/**
	 * �����Ȩ��Ϣ
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		System.out.println("������Ȩ...doGetAuthorizationInfo");

		// ��õ�ǰ�û���
		String userName = (String) getAvailablePrincipal(principals);

		// ͨ���û���ȥ����û���������Դ��������Դ����info��
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
	
		// ����Ȩ��
		Set<String> s = new HashSet<String>();
		List<HashMap<String,Object>> list = permService.queryPerm(userName);
		for (Map map : list) {
			System.out.println(map.get("perm_name"));
			s.add((String)map.get("perm_name"));
		}
		info.setStringPermissions(s);

		// ���ý�ɫ
		Set<String> r = new HashSet<String>();
		list = permService.queryRole(userName);
		for (Map map : list) {
			System.out.println(map.get("role_name"));
			r.add((String)map.get("role_name"));
		}
		info.setRoles(r);
		
		// ���ش��û�����Ȩ��Ϣ
		return info;
	}

	/**
	 * �����֤��Ϣ
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		System.out.println("���е�½��֤...doGetAuthenticationInfo");

		// token�д�����������û���������
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		String password = String.valueOf(upToken.getPassword());
		
		//���õ�¼ҵ�񣬷��ص�¼���
		int status = empService.checkLogin(username, password);
		
		if (status == 1) {
			//�˺Ų�����
			throw new UnknownAccountException();
		} else if (status == 2) {
			//�������
			throw new IncorrectCredentialsException();
		} else {			
			// ��¼�ɹ��򷵻�info
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,
					password.toCharArray(), getName());
			
			return info;
		}		
	}

	/**
	 * ɾ����ǰ�û��Ļ���
	 * �°汾��shiro�������û����µ�¼��ʱ���Զ�������Ȩ�����»���
	 * 
	 * @param userId
	 */
	public void removeUserCache(String userId) {
		SimplePrincipalCollection pc = new SimplePrincipalCollection();
		pc.add(userId, super.getName());
		super.clearCachedAuthorizationInfo(pc);
	}

}