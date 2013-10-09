<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="zh-CN" xmlns:wb="“http://open.weibo.com/wb”">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="robots" content="index,follow">
<meta name="copyright" content="Copyright 2003-2013. www.shanbay.com . All Rights Reserved.">
<meta name="keywords" content="17bdc 背单词 单词量 单词量测试 单词 英语单词 背单词软件 英语学习网站 学英语">
<meta name="description" content="智能安排词汇复习，在真实阅读环境里激活词汇，强化阅读能力，并记录你成长的每一步， 彻底告别死记硬背和边背边忘。和百万用户组队学习，共同提高">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="">
<meta name="ujianVerification" content="142a9b3e2632832be6781c7e6a9c436f">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/jquery.noty.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/noty_theme_default.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/cover.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/fontdiao.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/font-awesome.css">
<style>
body {
	margin-top: 0;
	padding-top: 0px;
}

.navbar.navbar-fixed-top {
	display: none;
}
</style>
</head>

<body data-target=".subnav" data-offset="50">

	<div id="page-head">
		<div id="logo">
			<a href="/letter"><img src="<%=path%>/images/landing_page_logo.png" style="width: 200px; padding-top: 10px;"></a>
		</div>

		<div id="page-head-right">
			<div id="main-login" class="pull-right">
				<!-- 
				<a id="" class="twi-btn-left" name="login" href="/member"><span>登录</span></a><a id="" class="twi-btn-right" name="register" href="/member/register"><span>注册</span></a>
-->
			</div>

			<div id="main-menu">
				<a href="http://www.luode.org" target="_blank">关注我们</a>
			</div>
		</div>
	</div>
</body>

</html>
