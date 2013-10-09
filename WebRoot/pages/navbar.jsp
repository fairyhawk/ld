<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="robots" content="index,follow" />
<meta name="copyright" content="Copyright 2003-2013. www.loude.org . All Rights Reserved.">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="">
<meta name="ujianVerification" content="142a9b3e2632832be6781c7e6a9c436f">
<title>背单词—罗德国际教育</title>
<link rel="stylesheet" type="text/css" href="/css/jquery.css">
<link rel="stylesheet" type="text/css" href="/css/noty_theme_default.css">
<link rel="stylesheet" type="text/css" href="/css/orgchart.css">
<link rel="stylesheet" type="text/css" href="/css/shanbay.css">
<link rel="stylesheet" type="text/css" href="/css/fontdiao.css">
<link rel="stylesheet" type="text/css" href="/css/font-awesome.css">
</head>
<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="/member/review/${session.member.id }"><img src="/images/logo2.png" style="width: 200px;padding-top:10px;"></a>
				<div class="nav-collapse">
<%-- 					<ul class="nav">
								<li><a href="/letter/${session.member.id }">单词书</a></li>
								<li><a href="/letter/testRecord/${session.member.id }">测试记录</a></li>
					</ul> --%>
					<ul class="nav nav-right pull-right">
						<li class="dropdown" id="menutest1">
						<a class="dropdown-toggle user-avatar" data-toggle="dropdown" href="#menutest1"> 
							<i class="icon-">
								<img src="/images/avatar.png">
							</i>
							${session.member.username}
							<!-- <b class="caret"></b> -->
						</a>
							<li><a href="/member/logout/">退出</a></li>
						</li>
					</ul>


				</div>
			</div>
		</div>
	</div>
<script src="/js/jquery-1.9.0.js"></script>	
<script src="/js/bootstrap-alert.js"></script>	
<script src="/js/bootstrap-button.js"></script>	
<script src="/js/bootstrap-carousel.js"></script>	
<script src="/js/bootstrap-collapse.js"></script>	
<script src="/js/bootstrap-dropdown.js"></script>	
<script src="/js/bootstrap-modal.js"></script>	
<!-- <script src="/js/bootstrap-popover.js"></script> -->	
<script src="/js/bootstrap-scrollspy.js"></script>	
<script src="/js/bootstrap-tab.js"></script>	
<script src="/js/bootstrap-tooltip.js"></script>	
<!-- <script src="/js/bootstrap-transition.js"></script>	 -->
<script src="/js/bootstrap-typeahead.js"></script>	
	<script type="text/javascript">
		$('.dropdown-toggle').dropdown();
	</script>
</body>
</html>