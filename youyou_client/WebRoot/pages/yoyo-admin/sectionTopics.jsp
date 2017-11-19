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
	<body>
		<div class="topics">
			<h4>导入试题</h4>
			<div class="form-group clearfix">
				<div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
					<label>选择文件</label>
					<input type="file" id="xls" accept=".xls" onchange="importf(this)"/>
				</div>
				<div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
					<label>选择岗位</label>
					<select id="posts" class="form-control" onchange="setCret('#posts', '#certs' ,this, '#certs', '#sections')">
						
					</select>
				</div>
				<div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
					<label>选择证书</label>
					<select id="certs" class="form-control" onchange="changeAdd('#posts','#certs','#sections')">
						
					</select>
				</div>
				<div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
					<label>选择章节</label>
					<select id="sections" class="form-control">
						
					</select>
				</div>
				<div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
					<label><small class="text-danger">包含图片的题目手动录入</small></label>
					<button type="button" class="btn btn-default btn-block btn-primary" id="addXls">导入</button>
				</div>
			</div>
			<h4>试题搜索</h4>
			<div class="form-group clearfix">
				<div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
					<label>选择搜索岗位</label>
					<select id="posts1" class="form-control" onchange="setCret('#posts1', '#certs1' ,this, '#certs1', '#section1')">
						
					</select>
				</div>
				<div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
					<label>选择搜索证书</label>
					<select id="certs1" class="form-control" onchange="changeAdd('#posts1','#certs1','#sections1')">
						
					</select>
				</div>
				<div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
					<label>选择搜索章节</label>
					<select id="sections1" class="form-control">
						
					</select>
				</div>
				<div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
					<label>输入搜索题目</label>
					<input type="text" id="text" class="form-control" placeholder="输入题目关键字"/>
				</div>
				<div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
					<label>搜索</label>
					<button type="button" id="searchBtn" class="btn btn-block btn-primary">搜 索</button>
				</div>
			</div>
			<div class="form-group clearfix">
				<table class="table">
					<thead>
						<tr>
							<th width="30%">题目</th>
							<th width="30%">图片</th>
							<th width="20%">操作</th>
						</tr>
					</thead>
					<tbody class="search-list"> 
					</tbody>
				</table>
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
									<label>添加图片<small class="text-danger">有图片的试题可选择上传图片</small></label>
									<input class="btn btn-default" type="file" id="upFile" />
								</div>
								<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
									<label>图片预览</label>
									<div class="form-control-sty2 ">
										<img src="" id="nextview"/>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                			<button type="button" id="addImage1" class="btn btn-primary" data-id="" data-pay="0">提交更改</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script>
		var data=null;
		var shareType=["独享","共用"];
		$(function(){
			/*获取试题*/
			$("#search").click(function(){
				var s = $("#posts10").val();
				var c = $("#certs10").val();
				var type = $("#certs10").find("option:selected").attr("data-type");
				$.ajax({
					type:"post",
					url: urlPath+"/admin.do?method=getTopicExplain",
					async:true,
					data:{
						"topicId":"91",
						"payType":"0"
					},
					success:function(d){
						console.log(d)
					}
				});
			})
			
			$.ajax({
				type:"get",
				url: urlPath+"/admin.do?method=getSectionTopic",
				async:true,
				data:{
					post_type:"1",
					cert_type:"122",
					section_type:"14829"
				},
				success:function(d){
					console.log(d)
				}
			});
		
		
		
		
		
			$.ajax({
				type:"get",
				url:urlPath+"/admin.do?method=getCert",
				async:true,
				success:function(d){
					
					data=d;
					var op="",childOp="";
					for (var i=0;i<d.LIST.length;i++) {
						op+='<option value="'+d.LIST[i].postId+'">'+d.LIST[i].postName+'</option>'
					}
					for (var i=0;i<d.LIST[0].certList.length;i++) {
						childOp+='<option value="'+d.LIST[0].certList[i].certId+'" data-type="'+d.LIST[0].certList[i].certType+'">'+d.LIST[0].certList[i].certName+'</option>';
					}
					$("#posts, #posts1, #posts10").html(op);
					$("#certs, #certs1, #certs10").html(childOp);
					getSection(d.LIST[0].postId,d.LIST[0].certList[0].certId,"#sections")
					getSection(d.LIST[0].postId,d.LIST[0].certList[0].certId,"#sections1")
				}
			});
			$("#addXls").click(function(){
				var post = $("#posts").val();
				var cert = $("#certs").val();
				var section = $("#sections").val();
				var pay = 0;
				var share_type = $("#certs").find("option:selected").attr("data-type");
				var cert_text = $("#certs").find("option:selected").text();
				if($("#xls").val()==""){
		    		alert("请选择xls文件");
		    		return;
		    	}
		    	for(var i in XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])){
		    		var parm = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])[i];
		    		parm.post_type = post;
		    		parm.cert_type = cert;
		    		parm.section_type = section;
		    		parm.pays_type = pay;
		    		parm.cert_class = share_type;
		    		parm.cert_text = cert_text;
		    		$.ajax({
			    		type:"post",
			    		data:parm,
			    		url:urlPath+"/admin.do?method=addSectionTopic",
			    		async:true,
			    		success:function(d){
			    			if(d.RESULT!="SUCCESS"){
			    				alert("第"+i+"题上传失败");
			    			}
			    		}
			    	});
		    	}
		    	alert("导入成功！");
			})
			$("#searchBtn").click(function(){
				var post = $("#posts1").val();
				var cert = $("#certs1").val();
				var section = $("#sections1").val();
				var text = $("#text").val();
				var share_type = $("#certs1").find("option:selected").attr("data-type");
				$.ajax({
					type:"post",
					url:urlPath+"/admin.do?method=searchSectionTopic",
					data:{
						post_type:post,
						cert_type:cert,
						section_type:section,
						title:text
					},
					async:true,
					success:function(d){
						var tr = '';
						for(var i=0;i<d.LIST.length;i++){
							tr+='<tr><td>'+d.LIST[i].topicTitle+'</td><td><a href="'+urlPath+d.LIST[i].topicImage+'">'+d.LIST[i].topicImage+'</a></td><td><a class="btn btn-sm btn-primary" data-toggle="modal" data-target="#myModal" onclick="setId(\''+d.LIST[i].topicId+'\')">添加图片</a><a class="btn btn-sm btn-danger" onclick="delTopic(\''+d.LIST[i].topicId+'\',this)">删除</a></td></tr>';
						}
						$(".search-list").html(tr)
					}
				});
			})
		})
		function changeAdd(p,c,s){
			var post = $(p).val();
			var cert = $(c).val();
			getSection(post,cert,s);
		}
		function getSection(p,c,s){
			$.ajax({
				type:"get",
				data:{
					postType:p,
					certType:c
				},
				url:urlPath+"/admin.do?method=getSection",
				async:true,
				success:function(data){
					var op = '';
					for(var i in data.LIST){
						op+='<option value="'+data.LIST[i].ID+'">'+data.LIST[i].NAME+'</option>';
					}
					$(s).html(op)
				}
			});
		}
		function setCret(p,c,t,d,s){
			var index = $(t).find("option:selected").prop("index");
			var op="";
			for (var i=0;i<data.LIST[index].certList.length;i++) {
				op+='<option value="'+data.LIST[index].certList[i].certId+'" data-type="'+data.LIST[index].certList[i].certType+'">'+data.LIST[index].certList[i].certName+'</option>';
			}
			$(d).html(op)
			
			var post = $(p).val();
			var cert = $(c).val();
			getSection(post,cert,s);
		}
		function setId(id){
			$("#addImage1").attr("data-id",id);
		}
		function delTopic(id,t,payType){
			$.ajax({
				type:"post",
				data:{
					id:id
				},
				url:urlPath+"/admin.do?method=delTopic",
				async:true,
				success:function(d){
					if(d.RESULT=="SUCCESS"){
						alert("删除成功")
						$(t).parents("tr").remove();
					}
				}
			});
		}
		/*导入*/
		var wb;
	    var rABS = false;
		function importf(obj) {
	        if(!obj.files) {
	            return;
	        }
	        var f = obj.files[0];
	        var reader = new FileReader();
	        reader.onload = function(e) {
	            var data = e.target.result;
	            if(rABS) {
	                wb = XLSX.read(btoa(fixdata(data)), {
	                    type: 'base64'
	                });
	            } else {
	                wb = XLSX.read(data, {
	                    type: 'binary'
	                });
	            };
	        };
	        if(rABS) {
	            reader.readAsArrayBuffer(f);
	        } else {
	            reader.readAsBinaryString(f);
	        }
	    }
	
	    function fixdata(data) { //文件流转BinaryString
	        var o = "",
	            l = 0,
	            w = 10240;
	        for(; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
	        o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
	        return o;
	    } 
	</script>
</html>
