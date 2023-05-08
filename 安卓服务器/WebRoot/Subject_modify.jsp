<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Subject" %>
<%@ page import="com.chengxusheji.domain.SubjectType" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�SubjectType��Ϣ
    List<SubjectType> subjectTypeList = (List<SubjectType>)request.getAttribute("subjectTypeList");
    //��ȡ���е�Teacher��Ϣ
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    Subject subject = (Subject)request.getAttribute("subject");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸���Ŀ��Ϣ</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    var subjectName = document.getElementById("subject.subjectName").value;
    if(subjectName=="") {
        alert('��������Ŀ����!');
        return false;
    }
    var content = document.getElementById("subject.content").value;
    if(content=="") {
        alert('��������Ŀ����!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="Subject/Subject_ModifySubject.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��Ŀ���:</td>
    <td width=70%><input id="subject.subjectId" name="subject.subjectId" type="text" value="<%=subject.getSubjectId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��Ŀ����:</td>
    <td width=70%><input id="subject.subjectName" name="subject.subjectName" type="text" size="40" value='<%=subject.getSubjectName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��Ŀ����:</td>
    <td width=70%>
      <select name="subject.subjectTypeObj.subjectTypeId">
      <%
        for(SubjectType subjectType:subjectTypeList) {
          String selected = "";
          if(subjectType.getSubjectTypeId() == subject.getSubjectTypeObj().getSubjectTypeId())
            selected = "selected";
      %>
          <option value='<%=subjectType.getSubjectTypeId() %>' <%=selected %>><%=subjectType.getSubjectTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ŀ����:</td>
    <td width=70%><textarea id="subject.content" name="subject.content" rows=5 cols=50><%=subject.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>��ѡ����:</td>
    <td width=70%><input id="subject.studentNumber" name="subject.studentNumber" type="text" size="8" value='<%=subject.getStudentNumber() %>'/></td>
  </tr>

  <tr>
    <td width=30%>ָ����ʦ:</td>
    <td width=70%>
      <select name="subject.teacherObj.teacherNumber">
      <%
        for(Teacher teacher:teacherList) {
          String selected = "";
          if(teacher.getTeacherNumber().equals(subject.getTeacherObj().getTeacherNumber()))
            selected = "selected";
      %>
          <option value='<%=teacher.getTeacherNumber() %>' <%=selected %>><%=teacher.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="subject.addTime" name="subject.addTime" type="text" size="20" value='<%=subject.getAddTime() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
