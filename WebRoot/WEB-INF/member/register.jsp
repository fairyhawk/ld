<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN" xmlns:wb="“http://open.weibo.com/wb”">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta charset="utf-8">
<meta name="robots" content="index,follow">
<meta name="copyright" content="Copyright 2003-2013. www.shanbay.com . All Rights Reserved.">

<title>背单词—罗德国际教育</title>
<meta name="keywords" content="17bdc 背单词 单词量 单词量测试 单词 英语单词 背单词软件 英语学习网站 学英语">
<meta name="description" content="智能安排词汇复习，在真实阅读环境里激活词汇，强化阅读能力，并记录你成长的每一步， 彻底告别死记硬背和边背边忘。和百万用户组队学习，共同提高">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="">
<meta name="ujianVerification" content="142a9b3e2632832be6781c7e6a9c436f">

<!-- Le styles -->
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

<body data-spy="scroll" data-target=".subnav" data-offset="50">
	<div class="container main-body ">
		<div id="container">
			<div align="center">
				<c:import url="/pages/head.jsp" />
			</div>
			<hr class="head-separator">
			<div class="cover-content">

				<div id="register-body">
					<div id="blue-background">
						<div class="register-box registration">
							<div class="register-box-inner">
								<h1>注册</h1>
								<hr>
								<form action="/member/addMember" method="POST">
									<div style="display: none">
										<input type="hidden" name="csrfmiddlewaretoken" value="b378f77e2c4482edd3e82e31af240881">
									</div>
									<ul>
										<li><ul class="errorlist">${nameMsg}</ul><label for="id_username">用户名:</label> <input type="text" class="required" name="member.username" maxlength="20" value="${(member.username)}"><span class="helptext">用户名中只能包含英文字母，数字，或者'_'（下划线）</span></li>
										<li><ul class="errorlist">${emailMsg}</ul><label for="id_email">Email 地址:</label> <input type="text" class="required" name="member.email" maxlength="75" value="${(member.email)}"> <span class="helptext">你会收到一封验证邮件</span></li>
										<li><ul class="errorlist">${passwordMsg}</ul><label for="id_password1">密码:</label> <input type="password" class="required" name="member.password" ></li>
										<li><ul class="errorlist">${ps1Msg}</ul><label for="id_password2">密码(重复):</label> <input type="password" class="required" name="password1" ></li>
										<li><ul class="errorlist">${captchaMsg}</ul><label for="id_captcha_1">验证码:</label> 
										<input type="hidden" name="captchaKey" value="<%=session.getId() %>" >
										<input type="text" name="captchaCode" id="id_captcha_1"> <img src="/common/savedCaptcha" alt="captcha" class="captcha"
											onclick="javascript:this.src='/common/savedCaptcha?' + new Date().getTime();"> <span class="helptext">验证码不区分大小写，看不清就请点击图片换一幅</span></li>
										<li><label for="id_agree" class="agree-label">我已经阅读并同意罗德教育的<a href="#">使用协议</a>:
										</label> <input type="checkbox" name="agree" id="id_agree"></li>
									</ul>
									<div class="login-buttons">
										<button type="submit" class="btn btn-large btn-success" id="button-submit">注册</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end of cover content -->
		</div>
	</div>
	<!-- /container -->
	<script src="<%=path%>/js/jquery-1.9.0.js"></script>
</body>
</html>