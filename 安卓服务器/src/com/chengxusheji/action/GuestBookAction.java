package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.GuestBookDAO;
import com.chengxusheji.domain.GuestBook;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.dao.TeacherDAO;
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class GuestBookAction extends BaseAction {

    /*�������Ҫ��ѯ������: ����*/
    private String qustion;
    public void setQustion(String qustion) {
        this.qustion = qustion;
    }
    public String getQustion() {
        return this.qustion;
    }

    /*�������Ҫ��ѯ������: ����ѧ��*/
    private Student student;
    public void setStudent(Student student) {
        this.student = student;
    }
    public Student getStudent() {
        return this.student;
    }

    /*�������Ҫ��ѯ������: �����ʦ*/
    private Teacher teacherObj;
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }
    public Teacher getTeacherObj() {
        return this.teacherObj;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int guestBookId;
    public void setGuestBookId(int guestBookId) {
        this.guestBookId = guestBookId;
    }
    public int getGuestBookId() {
        return guestBookId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource StudentDAO studentDAO;
    @Resource TeacherDAO teacherDAO;
    @Resource GuestBookDAO guestBookDAO;

    /*��������GuestBook����*/
    private GuestBook guestBook;
    public void setGuestBook(GuestBook guestBook) {
        this.guestBook = guestBook;
    }
    public GuestBook getGuestBook() {
        return this.guestBook;
    }

    /*��ת�����GuestBook��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Student��Ϣ*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*��ѯ���е�Teacher��Ϣ*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*���GuestBook��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student student = studentDAO.GetStudentByStudentNumber(guestBook.getStudent().getStudentNumber());
            guestBook.setStudent(student);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(guestBook.getTeacherObj().getTeacherNumber());
            guestBook.setTeacherObj(teacherObj);
            guestBookDAO.AddGuestBook(guestBook);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBook��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBook���ʧ��!"));
            return "error";
        }
    }

    /*��ѯGuestBook��Ϣ*/
    public String QueryGuestBook() {
        if(currentPage == 0) currentPage = 1;
        if(qustion == null) qustion = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(qustion, student, teacherObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        guestBookDAO.CalculateTotalPageAndRecordNumber(qustion, student, teacherObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = guestBookDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = guestBookDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("guestBookList",  guestBookList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("qustion", qustion);
        ctx.put("student", student);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryGuestBookOutputToExcel() { 
        if(qustion == null) qustion = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(qustion,student,teacherObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "GuestBook��Ϣ��¼"; 
        String[] headers = { "��¼id","����","����ѧ��","����ʱ��","��ʦ�ظ�","�����ʦ","�ظ�ʱ��","�ظ���־"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<guestBookList.size();i++) {
        	GuestBook guestBook = guestBookList.get(i); 
        	dataset.add(new String[]{guestBook.getGuestBookId() + "",guestBook.getQustion(),guestBook.getStudent().getName(),
guestBook.getQuestionTime(),guestBook.getReply(),guestBook.getTeacherObj().getName(),
guestBook.getReplyTime(),guestBook.getReplyFlag() + ""});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"GuestBook.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯGuestBook��Ϣ*/
    public String FrontQueryGuestBook() {
        if(currentPage == 0) currentPage = 1;
        if(qustion == null) qustion = "";
        List<GuestBook> guestBookList = guestBookDAO.QueryGuestBookInfo(qustion, student, teacherObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        guestBookDAO.CalculateTotalPageAndRecordNumber(qustion, student, teacherObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = guestBookDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = guestBookDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("guestBookList",  guestBookList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("qustion", qustion);
        ctx.put("student", student);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�GuestBook��Ϣ*/
    public String ModifyGuestBookQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������guestBookId��ȡGuestBook����*/
        GuestBook guestBook = guestBookDAO.GetGuestBookByGuestBookId(guestBookId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("guestBook",  guestBook);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�GuestBook��Ϣ*/
    public String FrontShowGuestBookQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������guestBookId��ȡGuestBook����*/
        GuestBook guestBook = guestBookDAO.GetGuestBookByGuestBookId(guestBookId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("guestBook",  guestBook);
        return "front_show_view";
    }

    /*�����޸�GuestBook��Ϣ*/
    public String ModifyGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student student = studentDAO.GetStudentByStudentNumber(guestBook.getStudent().getStudentNumber());
            guestBook.setStudent(student);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(guestBook.getTeacherObj().getTeacherNumber());
            guestBook.setTeacherObj(teacherObj);
            guestBookDAO.UpdateGuestBook(guestBook);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBook��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBook��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��GuestBook��Ϣ*/
    public String DeleteGuestBook() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            guestBookDAO.DeleteGuestBook(guestBookId);
            ctx.put("message",  java.net.URLEncoder.encode("GuestBookɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GuestBookɾ��ʧ��!"));
            return "error";
        }
    }

}
