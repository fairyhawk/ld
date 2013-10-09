<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<title>左边导航</title>
<style type="text/css">
.text_display{ display:none}
</style>
<script type="text/javascript" src="<%=path %>/system/boxy/jquery-1.2.6.pack.js"></script>
</head>

<body>
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
	<script type="text/javascript">
		$(".btn").click(function() {
			$(".btn").removeClass("btn_on");
			$(".menu_sub").hide();
			$(this).addClass("btn_on");
			$(this).parent("h3").next(".menu_sub").show();
		});
	</script>
</body>
</html>
