package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SelectItemDAO;
import com.mobileserver.domain.SelectItem;

import org.json.JSONStringer;

public class SelectItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ѧ��ѡ��ҵ������*/
	private SelectItemDAO selectItemDAO = new SelectItemDAO();

	/*Ĭ�Ϲ��캯��*/
	public SelectItemServlet() {
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
			/*��ȡ��ѯѧ��ѡ��Ĳ�����Ϣ*/
			int subjectObj = 0;
			if (request.getParameter("subjectObj") != null)
				subjectObj = Integer.parseInt(request.getParameter("subjectObj"));
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");

			/*����ҵ���߼���ִ��ѧ��ѡ���ѯ*/
			List<SelectItem> selectItemList = selectItemDAO.QuerySelectItem(subjectObj,studentObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SelectItems>").append("\r\n");
			for (int i = 0; i < selectItemList.size(); i++) {
				sb.append("	<SelectItem>").append("\r\n")
				.append("		<selectItemId>")
				.append(selectItemList.get(i).getSelectItemId())
				.append("</selectItemId>").append("\r\n")
				.append("		<subjectObj>")
				.append(selectItemList.get(i).getSubjectObj())
				.append("</subjectObj>").append("\r\n")
				.append("		<studentObj>")
				.append(selectItemList.get(i).getStudentObj())
				.append("</studentObj>").append("\r\n")
				.append("		<selectTime>")
				.append(selectItemList.get(i).getSelectTime())
				.append("</selectTime>").append("\r\n")
				.append("	</SelectItem>").append("\r\n");
			}
			sb.append("</SelectItems>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SelectItem selectItem: selectItemList) {
				  stringer.object();
			  stringer.key("selectItemId").value(selectItem.getSelectItemId());
			  stringer.key("subjectObj").value(selectItem.getSubjectObj());
			  stringer.key("studentObj").value(selectItem.getStudentObj());
			  stringer.key("selectTime").value(selectItem.getSelectTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ѧ��ѡ�⣺��ȡѧ��ѡ��������������浽�½���ѧ��ѡ����� */ 
			SelectItem selectItem = new SelectItem();
			int selectItemId = Integer.parseInt(request.getParameter("selectItemId"));
			selectItem.setSelectItemId(selectItemId);
			int subjectObj = Integer.parseInt(request.getParameter("subjectObj"));
			selectItem.setSubjectObj(subjectObj);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			selectItem.setStudentObj(studentObj);
			String selectTime = new String(request.getParameter("selectTime").getBytes("iso-8859-1"), "UTF-8");
			selectItem.setSelectTime(selectTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = selectItemDAO.AddSelectItem(selectItem);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ѧ��ѡ�⣺��ȡѧ��ѡ���ѡ��id*/
			int selectItemId = Integer.parseInt(request.getParameter("selectItemId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = selectItemDAO.DeleteSelectItem(selectItemId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ѧ��ѡ��֮ǰ�ȸ���selectItemId��ѯĳ��ѧ��ѡ��*/
			int selectItemId = Integer.parseInt(request.getParameter("selectItemId"));
			SelectItem selectItem = selectItemDAO.GetSelectItem(selectItemId);

			// �ͻ��˲�ѯ��ѧ��ѡ����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("selectItemId").value(selectItem.getSelectItemId());
			  stringer.key("subjectObj").value(selectItem.getSubjectObj());
			  stringer.key("studentObj").value(selectItem.getStudentObj());
			  stringer.key("selectTime").value(selectItem.getSelectTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ѧ��ѡ�⣺��ȡѧ��ѡ��������������浽�½���ѧ��ѡ����� */ 
			SelectItem selectItem = new SelectItem();
			int selectItemId = Integer.parseInt(request.getParameter("selectItemId"));
			selectItem.setSelectItemId(selectItemId);
			int subjectObj = Integer.parseInt(request.getParameter("subjectObj"));
			selectItem.setSubjectObj(subjectObj);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			selectItem.setStudentObj(studentObj);
			String selectTime = new String(request.getParameter("selectTime").getBytes("iso-8859-1"), "UTF-8");
			selectItem.setSelectTime(selectTime);

			/* ����ҵ���ִ�и��²��� */
			String result = selectItemDAO.UpdateSelectItem(selectItem);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
