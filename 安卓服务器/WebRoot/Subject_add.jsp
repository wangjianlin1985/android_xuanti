<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.SubjectType" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�SubjectType��Ϣ
    List<SubjectType> subjectTypeList = (List<SubjectType>)request.getAttribute("subjectTypeList");
    //��ȡ���е�Teacher��Ϣ
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�����Ŀ��Ϣ</TITLE> 
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
    <TD align="left" vAlign=top >
    <s:form action="Subject/Subject_AddSubject.action" method="post" id="subjectAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>��Ŀ����:</td>
    <td width=70%><input id="subject.subjectName" name="subject.subjectName" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>��Ŀ����:</td>
    <td width=70%>
      <select name="subject.subjectTypeObj.subjectTypeId">
      <%
        for(SubjectType subjectType:subjectTypeList) {
      %>
          <option value='<%=subjectType.getSubjectTypeId() %>'><%=subjectType.getSubjectTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ŀ����:</td>
    <td width=70%><textarea id="subject.content" name="subject.content" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>��ѡ����:</td>
    <td width=70%><input id="subject.studentNumber" name="subject.studentNumber" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>ָ����ʦ:</td>
    <td width=70%>
      <select name="subject.teacherObj.teacherNumber">
      <%
        for(Teacher teacher:teacherList) {
      %>
          <option value='<%=teacher.getTeacherNumber() %>'><%=teacher.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="subject.addTime" name="subject.addTime" type="text" size="20" /></td>
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
