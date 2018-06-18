<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>后台管理</title>
		<script src="<%=path%>/js/bootstrap.min.js"></script>
		<script src="<%=path%>/js/admin.js"></script>
		<script src="<%=path%>/js/jquery-1.11.2.min.js"></script>
		<link href="<%=path%>/css/admin.css" rel="stylesheet"/>
		<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"/>
	</head>
	<body class="login">
		<form role="form">
			<h3 class="text-center">管理控制台</h3>
			<div class="form-group">
			    <label>帐号</label>
			    <input type="text" class="form-control name" value="" id="name" placeholder="请输入帐号">
			</div>
			<div class="form-group">
			    <label>密码</label>
			    <input type="password" class="form-control password" value="" id="pwd" placeholder="请输入密码">
			</div>
			<div class="form-group">
			    <button type="button" id="submit" class="btn btn-primary btn-block">登录</button>
			</div>
		</form>
	</body>

	<script>
		$(function(){
			var userId = getCookie("userId");
			if(userId) location.href = urlPath+"/admin.do?method=index&user_name="+getCookie("userName");
			$("#submit").click(function(){
				var name = $("#name").val();
				var pwd = $("#pwd").val();
				if(isBlankStr(name) || isBlankStr(pwd)){
				    alert('请输入用户名密码');
				    return;
				}
				$.ajax({
					type:"post",
					url:urlPath+"/admin.do?method=doLogin",
					data:{
						adminName:name,
						adminPassword:pwd
					},
					// async:true,
					success:function(d){
					    if(d.RESULT === 'FAIL'){
                            alert('用户名或密码错误');
                            return;
						}
						setCookie("userId",d.user_id);
						setCookie("userName",d.admin_name);
						location.href = urlPath+"/admin.do?method=index&user_name="+d.admin_name
					}
				});
			});
			$("#login_ret").click(function(){
				window.location.reload();
			})
		})
	</script>
</html>
