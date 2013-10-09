<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>背单词—罗德国际教育</title>
<link href="<%=path%>/system/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/system/boxy/boxy.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/system/boxy/jquery.boxy.js"></script>
<script type="text/javascript" src="<%=path%>/system/boxy/jquery-1.2.6.pack.js"></script>
<script>
function addMember(){
	document.form1.action = "<%=path%>/system/addMember";
	document.form1.submit();
}
</script>
</head>
<body>
<form name="form1" method="post">
<fieldset>
	<div>
		<label>用户名:</label>
		<input name="member.username" type="text" class="text_box_style" id="member.username" value="" size="30" maxlength="30" />
		<span class="errorlist">${usernameMsg}</span><br/>
	</div>
	<div>
		<label>姓名:</label>
		<input name="member.member_name" type="text" class="text_box_style" id="member.member_name" value="" size="30" maxlength="30" />
		<span class="errorlist">${memberNameMsg}</span><br/>
	</div>
    <div>
		<label>邮箱:<font class="font_red"></font></label>
    	<input name="member.email" type="text" class="text_box_style" id="member.email" value="" size="30" maxlength="30" />
   		<span class="errorlist">${emailMsg}</span><br/>
	</div>
    <div>
      <label>密码:<font class="font_red"></font></label>
      <input name="member.password" type="text" class="text_box_style" id="member.password" value="" size="30" maxlength="30" />
      <span class="errorlist">${passwordMsg}</span><br/>
    </div>
    <div>
      <label>联系电话:<font class="font_red"></font></label>
      <input name="member.telephone" type="text" class="text_box_style" id="member.telephone" value="" size="30" maxlength="30" />
      <span class="errorlist">${phoneMsg}</span><br/>
    </div>
<div>
   		<label></label>
	    <a href="#" title="添加" onclick="addMember()" ><span class="btn_70">确定</span></a>
       <br/>
	</div>
</fieldset>
</form>

</body>
</html>