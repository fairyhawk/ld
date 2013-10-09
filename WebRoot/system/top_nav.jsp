<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<title>罗德教育-顶部导航</title>
<style type="text/css">
.sub_nav{position: absolute;text-align:left; left:20px;white-space:nowrap;}
.text_display{ display:none}
</style>
<script type="text/javascript" src="<%=path%>/system/boxy/jquery-1.2.6.pack.js"></script>
</head>
<body>
  <div id="top_nav">
   		<ul>
       		<li><a href="<%=path%>/system/queryMember" target="mainFrame" class="btn">用户管理</a>
    			<div class="sub_nav text_display">
    				<a href="<%=path%>/system/queryMember" target="mainFrame">学员管理</a>　|　
    			</div>
            </li>
            <li><a href="<%=path%>/system/welcome.jsp" target="mainFrame" class="btn">单词管理</a>
    			<div class="sub_nav text_display">
    				<a href="<%=path%>/system/welcome.jsp" target="mainFrame">单词书管理</a>　|　
    			</div>
            </li>
            <li><a href="<%=path %>/system/testStat" target="mainFrame" class="btn">报表统计</a>
    			<div class="sub_nav text_display">
					<a href="<%=path %>/system/testStat" target="mainFrame">统计详情</a>
    			</div>
            </li>
            <li><a href="<%=path%>/system/welcome.jsp" target="mainFrame" class="btn">系统管理</a>
    			<div class="sub_nav text_display" >
    				<a href="<%=path%>/system/welcome.jsp" target="mainFrame">员工管理</a>　|　
    			</div>
            </li>
    	</ul>
    <div class="logout">您好：<strong><font class="font_red">${session.systemUser.id }</font></strong> 欢迎使用!　<img src="images/btn_logout.gif" align="absmiddle" onclick="javascript:logout()"/></div>
  </div>
    <script type="text/javascript">
	$(".btn").click(function (){
		$(".btn").removeClass("btn_on");
		$(".sub_nav").hide();
		$(this).addClass("btn_on");
		$(this).next(".sub_nav").show();
	});
  </script>
	<script type="text/javascript">
	function logout(){
		top.location.href = "<%=path%>/system/logout";
	}
	function reloadMain() {
		parent.mainFrame.location.reload();
	}
	</script>
</body>
</html>
