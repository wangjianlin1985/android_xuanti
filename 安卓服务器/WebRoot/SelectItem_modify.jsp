<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.SelectItem" %>
<%@ page import="com.chengxusheji.domain.Subject" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Subject信息
    List<Subject> subjectList = (List<Subject>)request.getAttribute("subjectList");
    //获取所有的Student信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    SelectItem selectItem = (SelectItem)request.getAttribute("selectItem");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改学生选题</TITLE>
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
/*验证表单*/
function checkForm() {
    var selectTime = document.getElementById("selectItem.selectTime").value;
    if(selectTime=="") {
        alert('请输入选题时间!');
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
    <TD align="left" vAlign=top ><s:form action="SelectItem/SelectItem_ModifySelectItem.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>选题id:</td>
    <td width=70%><input id="selectItem.selectItemId" name="selectItem.selectItemId" type="text" value="<%=selectItem.getSelectItemId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>题目:</td>
    <td width=70%>
      <select name="selectItem.subjectObj.subjectId">
      <%
        for(Subject subject:subjectList) {
          String selected = "";
          if(subject.getSubjectId() == selectItem.getSubjectObj().getSubjectId())
            selected = "selected";
      %>
          <option value='<%=subject.getSubjectId() %>' <%=selected %>><%=subject.getSubjectName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>学生:</td>
    <td width=70%>
      <select name="selectItem.studentObj.studentNumber">
      <%
        for(Student student:studentList) {
          String selected = "";
          if(student.getStudentNumber().equals(selectItem.getStudentObj().getStudentNumber()))
            selected = "selected";
      %>
          <option value='<%=student.getStudentNumber() %>' <%=selected %>><%=student.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>选题时间:</td>
    <td width=70%><input id="selectItem.selectTime" name="selectItem.selectTime" type="text" size="20" value='<%=selectItem.getSelectTime() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
