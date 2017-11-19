<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>YOUYOU后台管理系统</title>
		<%@ include file="../../include/base.jsp" %>
	</head>
	<script>
		/**退出系统**/
		function logout(){
			if(confirm("您确定要退出本系统吗？")){
				delCookie("userId");
				delCookie("userName");
				window.location.reload();
			}
		}
		
		/**获得当前日期**/
		function  getDate0(){
			var time = new Date();
			var myYear = time.getFullYear();
			var myMonth = time.getMonth()+1;
			var myDay = time.getDate();
			if(myMonth < 10){
				myMonth = "0" + myMonth;
			}
			document.getElementById("yue_fen").innerHTML =  myYear + "." + myMonth;
			document.getElementById("day_day").innerHTML =  myYear + "." + myMonth + "." + myDay;
		}
		$(function(){
			if(!getCookie("userId")) location.href = "/youyou_client/admin.do?method=loginPage";
			$("a.level0").on("click",function(){
				var href = this.getAttribute("data-to");
				$("#rightMain").attr("src",href)
			})
			$(".adm").text(getCookie("userName"))
		})
		
	</script>
	<body onload="getDate0()">
		<div id="top">
			<div id="top_logo">
				<img alt="logo" src="images/common/logo.png" width="274" height="49" style="vertical-align:middle;">
			</div>
			<div id="top_links">
				<div id="top_op">
					<ul>
						<li>
							<img alt="当前用户" src="images/common/user.jpg">：
							<span class="adm">admin</span>
						</li>
						<li>
							<img alt="事务月份" src="images/common/month.jpg">：
							<span id="yue_fen"></span>
						</li>
						<li>
							<img alt="今天是" src="images/common/date.jpg">：
							<span id="day_day"></span>
						</li>
					</ul> 
				</div>
				<div id="top_close">
					<a href="javascript:void(0);" onclick="logout();" target="_parent">
						<img alt="退出系统" title="退出系统" src="images/common/close.jpg" style="position: relative; top: 10px; left: 25px;">
					</a>
				</div>
			</div>
		</div>
	    <!-- side menu start -->
		<div id="side">
			<div id="left_menu">
			 	<ul id="TabPage2" style="height:200px; margin-top:50px;">
					<li id="left_tab2" class="selected" title="系统管理">
						<img alt="系统管理" title="系统管理" src="images/common/2_hover.jpg" width="33" height="31">
					</li>
				</ul>
			 </div>
			 <div id="left_menu_cnt">
			 	<div id="nav_module">
			 		<img src="images/common/module_2.png" width="210" height="58"/>
			 	</div>
			 	<div id="nav_resource">
					<ul id="dleft_tab1" class="ztree">
						<li id="dleft_tab1_1" class="level0">
							<a class="level0" data-to="pages/admin/topic_list.jsp">
								<span id="dleft_tab1_1_ico" class="button ico_docu"></span>
								<span id="dleft_tab1_1_span">试题管理</span>
							</a>
						</li>
						<li id="dleft_tab1_2" class="level0">
							<a class="level0" data-to="pages/admin/user_list.jsp">
								<span class="button ico_docu"></span>
								<span id="dleft_tab1_2_span">用户管理</span>
							</a>
						</li>
					</ul>
			 	</div>
			 </div>
		</div>
	    <!-- side menu start -->
	    <div id="top_nav">
		 	<span id="here_area">当前位置：系统管理</span>
		</div>
	    <div id="main">
	      	<iframe name="right" id="rightMain" src="pages/admin/topic_list.jsp" frameborder="no" scrolling="auto" width="100%" height="100%" allowtransparency="true"/>
	    </div>
	</body>
</html>
