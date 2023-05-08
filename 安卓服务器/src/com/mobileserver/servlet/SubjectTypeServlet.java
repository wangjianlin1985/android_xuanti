package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SubjectTypeDAO;
import com.mobileserver.domain.SubjectType;

import org.json.JSONStringer;

public class SubjectTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������Ŀ����ҵ������*/
	private SubjectTypeDAO subjectTypeDAO = new SubjectTypeDAO();

	/*Ĭ�Ϲ��캯��*/
	public SubjectTypeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ��Ŀ���͵Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ����Ŀ���Ͳ�ѯ*/
			List<SubjectType> subjectTypeList = subjectTypeDAO.QuerySubjectType();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SubjectTypes>").append("\r\n");
			for (int i = 0; i < subjectTypeList.size(); i++) {
				sb.append("	<SubjectType>").append("\r\n")
				.append("		<subjectTypeId>")
				.append(subjectTypeList.get(i).getSubjectTypeId())
				.append("</subjectTypeId>").append("\r\n")
				.append("		<subjectTypeName>")
				.append(subjectTypeList.get(i).getSubjectTypeName())
				.append("</subjectTypeName>").append("\r\n")
				.append("	</SubjectType>").append("\r\n");
			}
			sb.append("</SubjectTypes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SubjectType subjectType: subjectTypeList) {
				  stringer.object();
			  stringer.key("subjectTypeId").value(subjectType.getSubjectTypeId());
			  stringer.key("subjectTypeName").value(subjectType.getSubjectTypeName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����Ŀ���ͣ���ȡ��Ŀ���Ͳ������������浽�½�����Ŀ���Ͷ��� */ 
			SubjectType subjectType = new SubjectType();
			int subjectTypeId = Integer.parseInt(request.getParameter("subjectTypeId"));
			subjectType.setSubjectTypeId(subjectTypeId);
			String subjectTypeName = new String(request.getParameter("subjectTypeName").getBytes("iso-8859-1"), "UTF-8");
			subjectType.setSubjectTypeName(subjectTypeName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = subjectTypeDAO.AddSubjectType(subjectType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����Ŀ���ͣ���ȡ��Ŀ���͵����ͱ��*/
			int subjectTypeId = Integer.parseInt(request.getParameter("subjectTypeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = subjectTypeDAO.DeleteSubjectType(subjectTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������Ŀ����֮ǰ�ȸ���subjectTypeId��ѯĳ����Ŀ����*/
			int subjectTypeId = Integer.parseInt(request.getParameter("subjectTypeId"));
			SubjectType subjectType = subjectTypeDAO.GetSubjectType(subjectTypeId);

			// �ͻ��˲�ѯ����Ŀ���Ͷ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("subjectTypeId").value(subjectType.getSubjectTypeId());
			  stringer.key("subjectTypeName").value(subjectType.getSubjectTypeName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������Ŀ���ͣ���ȡ��Ŀ���Ͳ������������浽�½�����Ŀ���Ͷ��� */ 
			SubjectType subjectType = new SubjectType();
			int subjectTypeId = Integer.parseInt(request.getParameter("subjectTypeId"));
			subjectType.setSubjectTypeId(subjectTypeId);
			String subjectTypeName = new String(request.getParameter("subjectTypeName").getBytes("iso-8859-1"), "UTF-8");
			subjectType.setSubjectTypeName(subjectTypeName);

			/* ����ҵ���ִ�и��²��� */
			String result = subjectTypeDAO.UpdateSubjectType(subjectType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
