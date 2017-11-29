package com.icss.hr.emp.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.util.Version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icss.hr.common.Pager;
import com.icss.hr.emp.dao.EmpMapper;
import com.icss.hr.emp.index.EmpIndexDao;
import com.icss.hr.emp.pojo.Emp;
import com.icss.hr.emp.pojo.EmpDto;

/**
 * Ա��service
 * 
 * @author DLETC
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EmpService {

	@Autowired
	private EmpMapper mapper;

	@Autowired
	private EmpIndexDao indexDao;

	public void addEmp(Emp emp) throws IOException {
		mapper.insert(emp);

		// ��������
		Integer empId = mapper.getPrimaryKey();
		
		Document document = new Document();
		document.add(new TextField("empId", String.valueOf(empId), Store.YES));
		document.add(new TextField("empName", emp.getEmpName(), Store.YES));
		document.add(new TextField("empPhone", emp.getEmpPhone(), Store.YES));
		document.add(new TextField("empInfo", emp.getEmpInfo(), Store.YES));

		indexDao.create(document);
	}

	public void updateEmp(Emp emp) throws IOException {
		mapper.update(emp);

		// ��������
		Document document = new Document();
		document.add(new TextField("empId", String.valueOf(emp.getEmpId()), Store.YES));
		document.add(new TextField("empName", emp.getEmpName(), Store.YES));
		document.add(new TextField("empPhone", emp.getEmpPhone(), Store.YES));
		document.add(new TextField("empInfo", emp.getEmpInfo(), Store.YES));

		Term term = new Term("empId", String.valueOf(emp.getEmpId()));

		indexDao.update(term, document);
	}

	public void deleteEmp(Integer empId) throws IOException {
		mapper.delete(empId);

		// ��������
		Term term = new Term("empId", String.valueOf(empId));
		indexDao.delete(term);
	}

	@Transactional(readOnly = true)
	public Emp queryEmpById(Integer empId) {
		return mapper.queryById(empId);
	}

	@Transactional(readOnly = true)
	public List<Emp> queryEmpByPage(Pager pager) {
		return mapper.queryByPage(pager);
	}

	@Transactional(readOnly = true)
	public int getEmpCount() {
		return mapper.getCount();
	}

	@Transactional(readOnly = true)
	public Emp queryEmpByLoginName(String empLoginName) {
		return mapper.queryByLoginName(empLoginName);
	}

	/**
	 * ��¼ҵ�� ����1�û��������� 2������� 3��¼�ɹ�
	 */
	@Transactional(readOnly = true)
	public int checkLogin(String empLoginName, String empPwd) {

		Emp emp = mapper.queryByLoginName(empLoginName);

		if (emp == null) {
			return 1;
		} else if (!empPwd.equals(emp.getEmpPwd())) {
			return 2;
		} else {
			return 3;
		}

	}

	public void updateEmpPic(String empLoginName, String empPic) {
		mapper.updateEmpPic(empLoginName, empPic);
	}

	public void updateEmpPwd(Emp emp) {
		mapper.updatePwd(emp);
	}

	/**
	 * ������ҳ
	 * 
	 * @param deptId
	 * @param jobId
	 * @param empName
	 * @param pager
	 * @return
	 */
	public List<Emp> queryEmpByCondition(Integer deptId, Integer jobId, String empName, Pager pager) {

		return mapper.queryByCondition(deptId, jobId, empName, pager);
	}

	/**
	 * �����������ܼ�¼��
	 */
	public int getEmpCountByCondition(Integer deptId, Integer jobId, String empName) {

		return mapper.getCountByCondition(deptId, jobId, empName);
	}

	/**
	 * ȫ�ļ���Ա��
	 * @throws ParseException 
	 * @throws InvalidTokenOffsetsException 
	 * @throws IOException 
	 */
	public EmpDto queryEmpByIndex(String queryStr) throws Exception {

		// ���ķִ���
		Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_47);

		//��Ա�����������绰�������ж��ֶμ���
		QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_47, 
				new String[] { "empName", "empPhone", "empInfo" },analyzer);
		
		Query query = queryParser.parse(queryStr);
		
		EmpDto empDto = indexDao.search(query);
		
		return empDto;
	}

}