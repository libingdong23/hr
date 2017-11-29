package com.icss.hr.perm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * Ȩ��dao
 * @author Administrator
 *
 */
public interface PermissionMapper {
	
	/**
	 * ��ѯ�û�Ȩ���б�
	 * @param empLoginName
	 * @return
	 */
	List<HashMap<String, Object>> queryPerm(@Param("empLoginName") String empLoginName);
	
	/**
	 * ��ѯ�û���ɫ�б�
	 * @param empLoginName
	 * @return
	 */
	List<HashMap<String, Object>> queryRole(@Param("empLoginName") String empLoginName);
   
}