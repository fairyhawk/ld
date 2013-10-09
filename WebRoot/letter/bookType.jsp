<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBliC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>背单词—罗德国际教育</title>
<meta content="text/html; charset=UTF-8" http-equiv="content-type" />
<meta name="copyright" content="Copyright 2003-2013. www.luode.org . All Rights Reserved." />
<meta name="keywords" content="托福培训,sat考试培训,美国留学机构,留学培训,出国留学" />
<meta name="description" content="托福培训,sat考试培训,美国留学机构,留学培训,出国留学" />
<link rel="stylesheet" type="text/css" href="/sbay/jquery.noty[1].css" />
<link rel="stylesheet" type="text/css" href="/sbay/noty_theme_default[1].css" />
<link rel="stylesheet" type="text/css" href="/sbay/new_wordbook.bd2862ce19af9826e2017500de704457[1].css" />
<link rel="stylesheet" type="text/css" href="/sbay/fontdiao[1].css" />
<link rel="stylesheet" type="text/css" href="/sbay/font_awesome[1].css" />
<link rel="stylesheet" type="text/css" href="/sbay/luode_ie.cd79192d1f377dd6abbaa9930ff805cd[1].css" />
<link rel="stylesheet" type="text/css" href="/sbay/font_awesome_ie7[1].css" />
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
<!-- 主体 -->
<div class=gradient-bar>&nbsp; </div><!-- 横线 -->

<div class="container main-body " >
			<div align="center">
				<c:import url="/pages/head.jsp" />
			</div>
			<div class=sub-nav>
			<ul class="nav nav-pills">
			<li class=active><a href="/letter">当前单词书</a></li>
			</ul>
		</div>
	<div class=row>
		<div class=span8>
			<div class=tabbable>
				<ul id=wordbook-wordlist-switcher class="nav nav-tabs">
				<li class=active ><a id=wordbook-tab-trigger href="#wordbook-tab" data-toggle="tab">单词书</a></li>
				
				</ul>
				if u bu hao hao xue,zhi neng gen wo yi yang yong pin yin le...
				
			</div>
		</div>
		<div class=span4>
			<div class=page-header>
			<h3>单词书分类</h3>
			</div>
			<span id=current-category-id class=hide></span>
			<ul id=wordbook-category-list class="nav nav-tabs nav-stacked">
				<c:if test="${bookType.list!=null }">
					<c:forEach items="${bookType.list}" var="bookt">
						<li ><a href="/letter/book/${bookt.id}">${bookt.name}</a></li>
					</c:forEach>
				</c:if>		
			</ul>
		</div>
	</div>	<!-- row -->
	
	
</div><!-- container -->

</body>
</html>

