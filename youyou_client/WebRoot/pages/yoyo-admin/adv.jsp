<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>后台管理</title>
		<%@ include file="../../include/base.jsp" %>
		<script type="text/javascript" src="../../js/pictureHandle.js" ></script>
	</head>
	<style>
		#nextview{
			width:750px;
			height:100px;
		}
	</style>
	<body>
		<div class="topics">
			<h4>添加广告</h4>
			<div class="form-group clearfix">
				<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12" style="margin-bottom:40px">
					<label>是否显示广告</label>
					<select id="advShow">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
					<button id="advshow">确定</button>
				</div>
				<div>
					<div class="form-group row">
						<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
							<label>添加图片<small class="text-danger">750px*100px</small></label>
							<input class="btn btn-default" type="file" id="upFile" />
						</div>
						<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
							<label>广告名称</label>
							<input type="text" id="advName" class="form-control" placeholder="输入广告名称"/>
						</div>
						<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
							<label>图片预览</label>
							<div class="form-control-sty2 ">
								<img src="" id="nextview"/>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
					<button type="button" id="addAdvBtn" class="btn btn-block btn-primary">添 加</button>
				</div>
			</div>
		</div>
	</body>
	<script>
		$(function(){
			/*获取试题*/
			$("#advshow").on('click', function(){
	           	var advShow = $("#advShow").val();
	           	$.ajax({
                    url:urlPath+"/admin.do?method=updataAdvShow",
                    type:'POST',
                    dataType:'json',
                    data:{
                    	advShow:advShow,
                    	advId:3
                    },
                    async:true,
                    success:function(data){
                        if(data.RESULT=="SUCCESS"){
                      	  alert("修改成功！");
                      	  //location.reload();
                        }
                    }
                });
	           })
			
			
		})
		
		function delTopic(id,payType){
			$.ajax({
				type:"post",
				data:{
					topicId:id,
					payType:payType
				},
				url:urlPath+"/admin.do?method=delTopic",
				async:true,
				success:function(d){
					if(d.RESULT=="SUCCESS"){
						alert("删除成功")
						location.reload();
					}
				}
			});
		}
	</script>
</html>
