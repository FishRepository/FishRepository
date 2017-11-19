<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>YOUYOU后台管理系统</title>
		<%@ include file="../../include/base.jsp" %>
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<div id="login_center">
			<div id="login_area">
				<div id="login_box">
					<div id="login_form">
						<form>
							<div id="login_tip">
								<span id="login_err" class="sty_txt2"></span>
							</div>
							<div>
								 用户名：<input type="text" name="userCode" class="username" id="name">
							</div>
							<div>
								密&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="password" name="password" class="pwd" id="pwd">
							</div>
							<div id="btn_area">
								<input type="button" class="login_btn" id="login_sub"  value="登  录">
								<input type="reset" class="login_btn" id="login_ret" value="重 置">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
	
	<script>
		$(function(){
			var userId = getCookie("userId");
			if(userId) location.href = urlPath+"/admin.do?method=index&user_name="+getCookie("userName");
			$("#login_sub").click(function(){
				var name = $("#name").val();
				var pwd = $("#pwd").val();
				$.ajax({
					type:"post",
					url:urlPath+"/admin.do?method=doLogin",
					data:{
						adminName:name,
						adminPassword:pwd
					},
					async:true,
					success:function(d){
						SetCookie("userId",d.user_id);
						SetCookie("userName",d.admin_name);
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
