<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.SelectItem" %>
<%@ page import="com.chengxusheji.domain.Subject" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Subject信息
    List<Subject> subjectList = (List<Subject>)request.getAttribute("subjectList");
    //获取所有的Student信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    SelectItem selectItem = (SelectItem)request.getAttribute("selectItem");

%>
<HTML><HEAD><TITLE>查看学生选题</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>选题id:</td>
    <td width=70%><%=selectItem.getSelectItemId() %></td>
  </tr>

  <tr>
    <td width=30%>题目:</td>
    <td width=70%>
      <%=selectItem.getSubjectObj().getSubjectName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>学生:</td>
    <td width=70%>
      <%=selectItem.getStudentObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>选题时间:</td>
    <td width=70%><%=selectItem.getSelectTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
