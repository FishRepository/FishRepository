<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>后台管理</title>
		<%@ include file="../../include/base.jsp" %>
	</head>
	<body class="posts">
		<div class="list hidden">
			<h4>岗位列表</h4>
			<ul id="myTab" class="nav nav-tabs">
				<li class="active"><a href="#0" data-toggle="tab">水手</a></li>
				<li><a href="#1" data-toggle="tab">船长</a></li>
				<li><a href="#2" data-toggle="tab">三幅</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="0">
					<p>菜鸟教程是一个提供最新的web技术站点，本站免费提供了建站相关的技术文档，帮助广大web技术爱好者快速入门并建立自己的网站。菜鸟先飞早入行——学的不仅是技术，更是梦想。</p>
				</div>
				<div class="tab-pane fade" id="1">
					<p>iOS 是一个由苹果公司开发和发布的手机操作系统。最初是于 2007 年首次发布 iPhone、iPod Touch 和 Apple 
						TV。iOS 派生自 OS X，它们共享 Darwin 基础。OS X 操作系统是用在苹果电脑上，iOS 是苹果的移动版本。</p>
				</div>
				<div class="tab-pane fade" id="2">
					<p>jMeter 是一款开源的测试软件。它是 100% 纯 Java 应用程序，用于负载和性能测试。</p>
				</div>
			</div>
		</div>
		<div class="add-post form-horizontal">
			<h4>添加岗位</h4>
			<div class="form-group clearfix" style="margin-bottom: 20px !important;">
				<div class="col-sm-10 col-lg-10 col-md-10 col-xs-10">
			    	<input class="form-control" id="postInput" type="text" placeholder="该输入岗位名称">
			    </div>
			    <div class="col-sm-2 col-lg-2 col-md-2 col-xs-2">
			    	<button type="submit" id="addPost" class="btn btn-primary form-control">添加</button>
			    </div>
			</div>
			<h4>添加职称</h4>
			<div class="form-group clearfix">
				<div class="form-group ">
					<label for="" class="control-label text-danger">选择岗位</label>
					<select class="form-control input-sm" id="postType">
						<option value="" disabled selected>选择岗位</option>
					</select>
				</div>
				<div class="form-group">
					<label for="" class="control-label text-danger">输入职称名称</label>
					<input class="form-control" id="certInput" type="text" placeholder="请输入职称名称">
				</div>	
				<div class="form-group">
					<label for="" class="control-label">是否通用类型题目</label>
					<select id="shareType" class="form-control">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
				<div class="form-group">
					<label for="" class="control-label text-danger">总分</label>
					<input class="form-control" id="totalScore" type="number" placeholder="总分">
				</div>
				<div class="form-group">
					<label for="" class="control-label text-danger">及格分</label>
					<input class="form-control" id="passScroe" type="number" placeholder="及格分">
				</div>
				<div class="form-group">
					<label for="" class="control-label text-danger">考试时间（分钟）</label>
					<input class="form-control" id="examTime" type="number" placeholder="考试时间">
				</div>
				<div class="form-group">
					<div class="form-group">
						<div class="col-lg-5 col-md-5 col-xs-5 col-sm-5">
							<label for="" class="control-label">单份试卷判断题数量</label>
							<input class="form-control" id="certType1" type="text" placeholder="请输入职称名称">
						</div>
						<div class="col-lg-5 col-md-5 col-xs-5 col-sm-5">
							<label for="" class="control-label">单个判断题分值</label>
							<input class="form-control" id="certTypeScore1" type="text" placeholder="请输入职称名称">
						</div>
						<div class="col-lg-5 col-md-5 col-xs-5 col-sm-5">
							<label for="" class="control-label">单份试卷单选题数量</label>
							<input class="form-control" id="certType2" type="text" placeholder="请输入职称名称">
						</div>
						<div class="col-lg-5 col-md-5 col-xs-5 col-sm-5">
							<label for="" class="control-label">单个单选题分值</label>
							<input class="form-control" id="certTypeScore2" type="text" placeholder="请输入职称名称">
						</div>
						<div class="col-lg-5 col-md-5 col-xs-5 col-sm-5">
							<label for="" class="control-label">单份试卷文章题数量</label>
							<input class="form-control" id="certType3" type="text" placeholder="请输入职称名称">
						</div>
						<div class="col-lg-5 col-md-5 col-xs-5 col-sm-5">
							<label for="" class="control-label">单个文章题分值</label>
							<input class="form-control" id="certTypeScore3" type="text" placeholder="请输入职称名称">
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="" class="control-label"> </label>
					<button type="submit" id="addCert" class="btn btn-primary form-control">添加</button>
				</div>	
			</div>
		</div>
	</body>
	<script>
		$(function(){
			$.ajax({
				type:"get",
				url:urlPath+"/admin.do?method=getPost",
				async:true,
				success:function(d){
					for(var i=0;i<d.LIST.length;i++){
						var op = document.createElement("option");
						op.value = d.LIST[i].postId;
						op.innerText = d.LIST[i].postName;
						$("#postType").append(op);
					}
				}
			});
			$("#addPost").click(function(){
				var postInput = $("#postInput").val();
				if (postInput=="") {
					alert("请输入岗位名称");
					return;
				}
				$.ajax({
					type:"post",
					data:{
						"postInput":postInput
					},
					url:urlPath+"/admin.do?method=addPost",
					async:true,
					success:function(d){
						alert(d.MESSAGE)
					}
				});
			})
			$("#addCert").click(function(){
				var postType = $("#postType").val();
				var certInput = $("#certInput").val();
				var shareType = $("#shareType").val();
				var totalScore = $("#totalScore").val();
				var passScroe = $("#passScroe").val();
				var examTime = $("#examTime").val();
				var certType1 = $("#certType1").val();
				var certTypeScore1 = $("#certTypeScore1").val();
				var certType2 = $("#certType2").val();
				var certTypeScore2 = $("#certTypeScore2").val();
				var certType3 = $("#certType3").val();
				var certTypeScore3 = $("#certTypeScore3").val();
				if (postType=="") {
					alert("请选择岗位名称");
					return;
				}
				if(certInput==""){
					alert("请输入证书名称");
					return;
				}
				if(totalScore==""){
					alert("请输入总分");
					return;
				}
				if(passScroe==""){
					alert("请输入及格分");
					return;
				}
				if(examTime==""){
					alert("请输入考试时间");
					return;
				}
				$.ajax({
					type:"post",
					data:{
						"postType":postType,
						"certInput":certInput,
						"shareType":shareType,
						"totalScore":totalScore,
						"passScroe":passScroe,
						"examTime":examTime,
						"certType1":certType1,
						"certTypeScore1":certTypeScore1,
						"certType2":certType2,
						"certTypeScore2":certTypeScore2,
						"certType3":certType3,
						"certTypeScore3":certTypeScore3
					},
					url:urlPath+"/admin.do?method=addCert",
					async:true,
					success:function(d){
						alert(d.MESSAGE)
					}
				});
			})
		})
	</script>
</html>
