<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>后台管理</title>
		<%@ include file="../../include/base.jsp" %>
	</head>
	<body>
		<div class="topics">
			<h4>添加章节</h4>
			<div class="form-group clearfix">
				<div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
					<label>选择搜索岗位</label>
					<select id="posts1" class="form-control" onchange="setCret(this, '#certs1')">
					</select>
				</div>
				<div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
					<label>选择搜索证书</label>
					<select id="certs1" class="form-control">
					</select>
				</div>
				<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
					<label>输入章节名称</label>
					<input type="text" id="text" class="form-control" placeholder="输入章节名称"/>
				</div>
				<div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
					<label></label>
					<button type="button" id="searchBtn" class="btn btn-block btn-primary">添 加</button>
				</div>
			</div>
			
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                			<h4 class="modal-title" id="myModalLabel">添加图片</h4>
						</div>
						<div class="modal-body">
							<div class="form-group row">
								<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
									<label>输入章节名称<small class="text-danger">不能为空</small></label>
									<input type="text" id="nName" class="form-control" placeholder="输入章节名称"/>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                			<button type="button" id="addImage" class="btn btn-primary" data-id="" data-pay="">提交更改</button>
						</div>
					</div>
				</div>
			</div>
			
			
			
			
			<div class="form-group clearfix">
				<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
					<label>选择搜索岗位</label>
					<select id="posts10" class="form-control" onchange="setCret(this, '#certs10')">
					</select>
				</div>
				<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
					<label>选择搜索证书</label>
					<select id="certs10" class="form-control">
					</select>
				</div>
				<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
					<label>搜索</label>
					<button type="button" id="search" class="btn btn-block btn-primary">搜 索</button>
				</div>
			</div>
			
			<div class="form-group clearfix">
				<table class="table">
					<thead>
						<tr>
							<th width="50%">题目</th>
							<th width="50%">操作</th>
						</tr>
					</thead>
					<tbody class="search-list">
						
					</tbody>
				</table>
			</div>
			
		</div>
	</body>
	<script>
		var data=null;
		$(function(){
			/*获取试题*/
			$("#search").click(function(){
				var s = $("#posts10").val();
				var c = $("#certs10").val();
				$.ajax({
					type:"get",
					url:urlPath+"/admin.do?method=getSection",
					async:true,
					data:{
						postType:s,
						certType:c
					},
					success:function(d){
						var tr="";
						for (var i=0;i<d.LIST.length;i++) {
							tr+='<tr><td>'+d.LIST[i].NAME+'</td><td><a onclick="del(\''+d.LIST[i].ID+'\',this)">删除</a><a onclick="update(\''+d.LIST[i].ID+'\',\''+d.LIST[i].POST_TYPE+'\',\''+d.LIST[i].CERT_TYPE+'\',this)" data-toggle="modal" data-target="#myModal">修改</a></td></tr>'
						}
						$(".search-list").html(tr);
					}
				});
			})
			
			$("#searchBtn").click(function(){
				var p = $("#posts1").val();
				var c = $("#certs1").val();
				var t = $("#text").val();
				$.ajax({
					type:"post",
					url:urlPath+"/admin.do?method=addSection",
					async:true,
					data:{
						postType:p,
						certType:c,
						name:t
					},
					success:function(d){
						if(d.RESULT=="SUCCESS"){
							alert("添加成功");
							location.reload();
						}
					}
				});
			})
		
			$.ajax({
				type:"get",
				url:urlPath+"/admin.do?method=getCert",
				async:true,
				success:function(d){
					console.log(d)
					data=d;
					var op="",childOp="";
					for (var i=0;i<d.LIST.length;i++) {
						op+='<option value="'+d.LIST[i].postId+'">'+d.LIST[i].postName+'</option>'
					}
					for (var i=0;i<d.LIST[0].certList.length;i++) {
						childOp+='<option value="'+d.LIST[0].certList[i].certId+'" data-type="'+d.LIST[0].certList[i].certType+'">'+d.LIST[0].certList[i].certName+'</option>';
					}
					$(" #posts1, #posts10").html(op);
					$(" #certs1, #certs10").html(childOp);
				}
			});
		})
		function setCret(t,d){
			var index = $(t).find("option:selected").prop("index");
			var op="";
			for (var i=0;i<data.LIST[index].certList.length;i++) {
				op+='<option value="'+data.LIST[index].certList[i].certId+'" data-type="'+data.LIST[index].certList[i].certType+'">'+data.LIST[index].certList[i].certName+'</option>';
			}
			$(d).html(op)
		}
		function del(id,t){
			$.ajax({
				type:"post",
				data:{
					id:id
				},
				url:urlPath+"/admin.do?method=delSection",
				async:true,
				success:function(d){
					if(d.RESULT=="SUCCESS"){
						alert("删除成功")
						$(t).parents("tr").remove();
					}
				}
			});
		}
		function update(i,n){
			$.ajax({
				type:"post",
				data:{
					id:i,
					name:name
				},
				url:urlPath+"/admin.do?method=updataSection",
				async:true,
				success:function(d){
					if(d.RESULT=="SUCCESS"){
						alert("修改成功")
						location.reload();
					}
				}
			});
		}
	</script>
</html>
