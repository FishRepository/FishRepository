<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>YOUYOU后台管理系统</title>
		<%@ include file="../../include/base.jsp" %>
	</head>
	<style>
		.alt td{ background:black !important;}
		table tr td{
			padding: 5px;
		}
		table tr td:nth-child(1){
			text-align: left !important;
		}
		table thead tr td{
			text-align: center;
			background: #044599 no-repeat;
		    text-align: center;
		    border-left: 1px solid #02397F;
		    border-right: 1px solid #02397F;
		    border-bottom: 1px solid #02397F;
		    border-top: 1px solid #02397F;
		    letter-spacing: 2px;
		    text-transform: uppercase;
		    font-size: 14px;
		    color: #fff !important;
		    height: 37px;
		}
		.ui_tb_h30{
			height:auto
		}
	</style>
	<body>
		<div id="container">
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0" id="userTab">
						<thead>
							<tr>
								<td>姓名</td>
								<td>性别</td>
								<td>电话号码</td>
								<td>Email</td>
							</tr>
						</thead>
						<tbody class="topic_tab">
						</tbody>
					</table>
				</div>
				<div class="ui_tb_h30">
					<div class="ui_flt" style="height: 30px; line-height: 30px;">
						共有
						<span class="ui_txt_bold04 topic_num"></span>
						条记录
					</div>
					<div class="ui_frt">
						<input type="button" value="上一页" class="ui_input_btn01" onclick="beforePage();"/><span><span class="now_page">1</span>/<span class="tt_page">2</span></span>
						<input type="button" value="下一页" class="ui_input_btn01" onclick="afterPage();" />
						<input type="button" value="导出" class="ui_input_btn01" onclick="exportExcel();" />
					</div>
				</div>
			</div>
		</div>
	</body>
	<script>
		var topics_length;
		var page = 0;
		var outType = ["xls","json","txt","doc"];
		var sex = ["女","男"];
		function exportExcel(){
			tableExport('userTab',"用户列表", outType[0]);
		}
		$(document).ready(function(){
			$(".now_page").text("1")
			/**获取题目**/
			$.ajax({
				type:"get",
	    		data:{
	    			begin:0,
	    			end:10
	    		},
	    		url:urlPath+"/admin.do?method=getUserByPage",
	    		async:true,
	    		success:function(d){
	    			if(d.RESULT=="SUCCESS"){
	    				var tr = "";
	    				topics_length = d.LIST.length;
	    				$(".tt_page").text(Math.ceil(d.LIST.length/10))
	    				$(".topic_num").text(d.LIST.length);
	    				for(var i=0;i<d.LIST.length;i++){
	    					tr+="<tr><td>"+returnStr(d.LIST[i].user_name)+"</td><td>"+returnStr(sex[d.LIST[i].user_sex])+"</td><td>"+returnStr(d.LIST[i].user_phone)+"</td><td>"+returnStr(d.LIST[i].user_email)+"</td></tr>"
	    				}
	    				$(".topic_tab").html(tr);
	    			}
	    		}
			})
		
		
			/** 新增   **/
		    $("#addBtn").click(function(){
		    	$(".add-cont").show()
		    });
			$(".close").click(function(){
				$(".add-cont").hide()
			})
		    /** 导入  **/
		    $("#importBtn").click(function(){
		    	console.log(JSON.stringify( XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])))
		    });
		});
		function returnStr(v){
			return v==undefined?"":v
		}
		function beforePage(){
			if(page<=0){
				return;
			}
			page--;
			$(".now_page").text(page+1)
			$.ajax({
				type:"get",
	    		data:{
	    			begin:page,
	    			end:10
	    		},
	    		url:urlPath+"/admin.do?method=getUserByPage",
	    		async:true,
	    		success:function(d){
	    			if(d.RESULT=="SUCCESS"){
	    				var tr = "";
	    				$(".topic_num").text(d.USER_LENGTH);
	    				for(var i=0;i<d.LIST.length;i++){
	    					tr+="<tr><td>"+returnStr(d.LIST[i].user_name)+"</td><td>"+returnStr(sex[d.LIST[i].user_sex])+"</td><td>"+returnStr(d.LIST[i].user_phone)+"</td><td>"+returnStr(d.LIST[i].user_email)+"</td></tr>"
	    				}
	    				$(".topic_tab").html(tr)
	    			}
	    		}
			})
		}
		function afterPage(){
			if(page>=Math.floor(topics_length/10)){
				return;
			}
			page++;
			$(".now_page").text(page+1)
			$.ajax({
				type:"get",
	    		data:{
	    			begin:10*page,
	    			end:10
	    		},
	    		url:"/youyou_client/admin.do?method=getUserByPage",
	    		async:true,
	    		success:function(d){
	    			if(d.RESULT=="SUCCESS"){
	    				var tr = "";
	    				$(".topic_num").text(d.USER_LENGTH);
	    				for(var i=0;i<d.LIST.length;i++){
	    					tr+="<tr><td>"+returnStr(d.LIST[i].user_name)+"</td><td>"+returnStr(sex[d.LIST[i].user_sex])+"</td><td>"+returnStr(d.LIST[i].user_phone)+"</td><td>"+returnStr(d.LIST[i].user_email)+"</td></tr>"
	    				}
	    				$(".topic_tab").html(tr)
	    			}
	    		}
			})
		}
		
	</script>
</html>
