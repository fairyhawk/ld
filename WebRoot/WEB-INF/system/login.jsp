<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>背单词—罗德国际教育</title>
<link href="<%=path %>/system/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.9.0.js"></script>
<script type="text/javascript">
/**
	打开登录界面时焦点到loginName输入框
*/
window.onload=function(){  
	document.getElementById("loginName").focus();  
}

if(document.addEventListener){//如果是Firefox
	document.addEventListener("keypress",fireFoxHandler, true);
}else{
	document.attachEvent("onkeypress",ieHandler);
}
function fireFoxHandler(evt){
  //alert("firefox");
  if(evt.keyCode==13){
     sumitForm();
  }
}
function ieHandler(evt){
  //alert("IE");
  if(evt.keyCode==13){
    sumitForm();
  }
}
function refresh(obj){
	obj.src = "validate_code.action?" + Math.random();            
}
function sumitForm(){
	var username=document.getElementById("loginName");  
	var v_username=username.value;  
   	var password=document.getElementById("password");  
   	var v_password=password.value;   
   	if(v_username==null||v_username.length<1){  
    	$("#mes_name").show();
    	document.getElementById("loginName").focus();  
    	return false;  
   	}else{
   		$("#mes_name").hide();
   	}
   	if(v_password==null||v_password.length<1){  
   		$("#mes_psword").show(); 
    	document.getElementById("password").focus();  
    	return false;  
   	}else{
   		$("#mes_psword").hide();
   	}
	    document.myForm.action="<%=path%>/system/login";
        document.myForm.submit();
}
</script>
</head>

<body>
	<div>
		<div id="login_box">
			<form id="myForm" name="myForm" method="post" validate="true">
				<fieldset style="background: none">
					<div>
						<label>用户名：</label> <input name="loginName" type="text" class="text_box_style" id="loginName" value="" size="25" maxlength="30" /> <span id="mes_name" style="display: none"> <font
							color='#FF00FF'>请输入用户名</font></span>
					</div>
					<div>
						<label>密 码：</label> <input name="password" type="password" class="text_box_style" id="password" value="" size="25" maxlength="30" /> <span id="mes_psword" style="display: none"><font
							color='#FF00FF'>请输入密码</font></span>
					</div>
					<div>
						<label></label> <img src="<%=path %>/system/images/btn_login.gif" width="57" height="24" onclick="sumitForm()" />
					</div>
		</div>
		</fieldset>
		</form>
	</div>
	<br class="clear" />
	<div id="login_copyright"">Copyright©2010-2013 版权所有罗德教育</div>
	</div>

</body>
</html>
