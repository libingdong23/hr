package com.icss.hr.pic.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.icss.hr.pic.pojo.Pic;
import com.icss.hr.pic.service.PicService;

/**
 * ͼ�������
 * @author DLETC
 *
 */
@Controller
public class PicController {

	@Autowired
	private PicService service;
	
	/**
	 * ���ͼƬ
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/pic/add")
	public void add(String picInfo, @RequestParam("picData") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// ��õ�¼�û���
		HttpSession session = request.getSession();
		String empLoginName = (String) session.getAttribute("empLoginName");
				

		// pojo����
		Pic pic = new Pic(file.getOriginalFilename(), picInfo, file.getSize(), empLoginName, new Date(), file.getBytes());

		service.addPic(pic);
	}

	/**
	 * ����ͼƬ�б�
	 */
	@RequestMapping("/pic/query")
	@ResponseBody
	public List<Pic> query(HttpServletRequest request, HttpServletResponse response) {

		return service.queryPic();

	}

	/**
	 * ����ͼƬ����������
	 */
	@RequestMapping("/pic/get")
	@ResponseBody
	public byte[] getPic(Integer picId, HttpServletRequest request, HttpServletResponse response) {

		Pic pic = service.queryPicById(picId);

		return pic.getPicData();
	}

	/**
	 * ɾ��ͼƬ
	 */
	@RequestMapping("/pic/delete")
	public void deletePic(Integer picId, HttpServletRequest request, HttpServletResponse response) {

		service.deletePic(picId);
	}
	
	/**
	 * ����ͼƬ
	 * @throws IOException 
	 */
	@RequestMapping("/pic/download")
	public void downloadPic(Integer picId, HttpServletRequest request, HttpServletResponse response) throws IOException {

		// ������Ӧ��MIME����Ϊ�����������
		response.setContentType("image/*");

		// ��Ӧ�ֽ������
		OutputStream out = response.getOutputStream();

		//����ͼƬ����
		Pic pic = service.queryPicById(picId);
		
		//����ͼƬ�ļ���
		String picName = new String(pic.getPicName().getBytes("utf-8"),"iso-8859-1");
		
		//������Ӧ��ͷ��������Ը�����ʽ��������
		response.setHeader("Content-disposition","attachment;filename=" + picName);
		
		//�����Ӧ���ͻ���
		FileCopyUtils.copy(pic.getPicData(), out);
	}
	
}