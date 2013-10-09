<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>背单词—罗德国际教育</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.text_display{ display:none}
</style>
<script type="text/javascript" src="<%=path %>/system/boxy/jquery-1.2.6.pack.js"></script>
</head>
<body>
<div id="top_bg"><img src="images/main_top_bg.png"/></div>
<div id="left">
	<div class="top_bg">
    	<!--BTN1-->

    	<!--BTN2-->
   	 	<div class="menu">
       	  	<h3><a class="btn" href="#"><img src="images/icon_menu2.gif" />用户管理</a></h3>
            <ul class="menu_sub text_display" >
                <li><a href="/system/queryMember" target="mainFrame"><img src="images/icon_menusub.gif" />学员管理</a></li>
        	</ul>
      	</div>
    	<!--BTN3-->
    	<div class="menu">
        	<h3><a class="btn" href="#"><img src="images/icon_menu3.gif" />单词管理</a></h3>
           	<ul class="menu_sub text_display" >
               	<li><a href="<%=path %>/system/welcome.jsp" target="mainFrame"><img src="images/icon_menusub.gif" />单词书管理</a></li>
       		</ul>
        </div>
    	<!--BTN5-->
    	<div class="menu">
        	<h3><a class="btn" href="#"><img src="images/icon_menu6.gif" />报表统计</a></h3>
           	<ul class="menu_sub text_display" >
   				<li><a href="<%=path %>/system/testStat" target="mainFrame"><img src="images/icon_menusub.gif" />统计详情</a></li>
       		</ul>
        </div>
    	<!--BTN6-->
    	<div class="menu">
        	<h3><a class="btn" href="#"><img src="images/icon_menu7.gif" />系统管理</a></h3>
            	<ul class="menu_sub text_display" >
        			<li><a href="<%=path %>/system/welcome.jsp" target="mainFrame"><img src="images/icon_menusub.gif" />员工管理</a></li>
        		</ul>
        </div>
    </div>
</div>

<div id="right">
  <!--顶部导航-->
  <div id="top_nav">
   		<ul>
       		<li><a href="<%=path %>/system/queryMember" target="mainFrame" class="btn">用户管理</a>
    			<div class="sub_nav text_display">
    				<a href="<%=path %>/system/queryMember" target="mainFrame">学员管理</a>　|　
    			</div>
            </li>
            <li><a href="<%=path %>/system/welcome.jsp" target="mainFrame" class="btn">单词管理</a>
    			<div class="sub_nav text_display">
    				<a href="<%=path %>/system/welcome.jsp" target="mainFrame">单词书管理</a>　|　
    			</div>
            </li>
            <li><a href="<%=path %>/system/welcome.jsp" target="mainFrame" class="btn">报表统计</a>
    			<div class="sub_nav text_display">
					<a href="<%=path %>/system/welcome.jsp" target="mainFrame">统计详情</a>
    			</div>
            </li>
            <li><a href="<%=path %>/system/welcome.jsp" target="mainFrame" class="btn">系统管理</a>
    			<div class="sub_nav text_display" >
    				<a href="<%=path %>/system/welcome.jsp" target="mainFrame">员工管理</a>　|　
    			</div>
            </li>
    	</ul>
    <div class="logout">您好：<strong><font class="font_red">${session.systemUser.id }</font></strong> ,欢迎使用!　<img src="images/btn_logout.gif" align="absmiddle"/>　<img src="images/btn_modifypassword.gif" align="absmiddle"/></div>
  </div>
  <!--主体-->
  <frame src="welcome.jsp" name="mainFrame" id="mainFrame" title="mainFrame" /> 
  <br class="clear" />
</div>
	<script type="text/javascript">
		$(".btn").click(function() {
			$(".btn").removeClass("btn_on");
			$(".menu_sub").hide();
			$(this).addClass("btn_on");
			$(this).parent("h3").next(".menu_sub").show();
		});
	</script>
	<script type="text/javascript">
		$(".btn").click(function() {
			$(".btn").removeClass("btn_on");
			$(".sub_nav").hide();
			$(this).addClass("btn_on");
			$(this).next(".sub_nav").show();
		});
	</script>	
</body>
</html>
