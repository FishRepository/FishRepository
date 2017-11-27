<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>后台管理</title>
		<%@ include file="../../include/base.jsp" %>
	</head>
	<body class="index">
		<div class="row">
			<div class="col-lg-2 col-md-2 col-xs-2">
				<ul class="list-group nav-list">
					<li class="list-group-item active" data-src = "pages/yoyo-admin/posts.jsp">科目管理</li>
					<li class="list-group-item" data-src = "pages/yoyo-admin/topics.jsp">试题管理</li>
					<li class="list-group-item" data-src = "pages/yoyo-admin/user_list.jsp">用户管理</li>
					<li class="list-group-item" data-src = "pages/yoyo-admin/pay_posts.jsp">套题管理</li>
					<li class="list-group-item" data-src = "pages/yoyo-admin/pay_topics.jsp">套题试题管理</li>
					<li class="list-group-item" data-src = "pages/yoyo-admin/adv.jsp">广告位管理</li>
					<li class="list-group-item" data-src = "pages/yoyo-admin/advInfo.jsp">广告海报</li>
					<li class="list-group-item" data-src = "pages/yoyo-admin/sectionList.jsp">章节管理</li>
					<li class="list-group-item" data-src = "pages/yoyo-admin/sectionTopics.jsp">章节题目管理</li>
                    <li class="list-group-item" data-src = "pages/yoyo-admin/englishClass.jsp"><span class="badge">新</span>英语课程管理</li>
                    <li class="list-group-item" data-src = "pages/yoyo-admin/englishChap.jsp"><span class="badge">新</span>英语课程章节管理</li>
                    <li class="list-group-item" data-src = "pages/yoyo-admin/englishQuestion.jsp"><span class="badge">新</span>英语课程章节试题管理</li>
				</ul>
			</div>
			<div class="col-lg-10 col-md-10 col-xs-10">
				<iframe id="iframe" src="pages/yoyo-admin/posts.jsp"></iframe>
			</div>
		</div>
	</body>
	<script>
		$(function(){
			$(".nav-list>li").click(function(){
				$(this).addClass("active").siblings("li").removeClass("active");
				var url = $(this).attr("data-src");
				$("#iframe").attr("src",url);
			})
		})
	</script>
</html>
