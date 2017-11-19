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
		table tbody tr td:nth-child(1){
			text-align: left !important;
		}
		.add-cont{
			position: fixed;
			width: 100%;
			height: 100%;
			left: 0;
			top: 0;
			display: none;
			background: rgba(0,0,0,.5);
			z-index: 10;
		}
		.add-cont .add{
			background: #FFFFFF;
			border-radius: 10px;
			width: 50%;
			height: 80%;
			position: absolute;
			top: 10%;
			left: 25%;
			overflow: auto;
		}
		.add-cont .add .close{
			position: fixed;
			display: block;
			width: 40px;
			height: 40px;
			border-radius: 50%;
			line-height: 40px;
			text-align: center;
			color: #FFFFFF;
			background: rgba(0,0,0,.8);
			border: 1px solid #FFFFFF;
			right: 0;
			top: 0;
			cursor: pointer;
		}
		.add-cont .add form{
			padding: 20px;
		}
		.add-cont .add form div{
			padding: 10px 0;
		}
		.add-cont .add form div.right label{
			margin-right: 10px;
			font-size: 14px;
		}
		.add-cont .add form div input[type="text"]{
			width: 80%;
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
		#box_bottom{
			height: auto;
		}
		.table td{
			font-size:12px !important;
		}
		.ui_tb_h30{
			height:auto
		}
	</style>
	<body>
		<div class="add-cont">
			<div class="add">
				<span class="close">X</span>
				<form>
					<div>
						<label>
							题目：
							<input type="text" id="title" class="ui_input_txt02" placeholder="题目"/>
						</label>
					</div>
					<div>类型：
						<select id="type">
							<option value="1">选择题</option>
							<option value="2">判断题</option>
							<option value="3">多选题</option>
						</select>
					</div>
					<div>
						<label>
							答案A：
							<input type="text" id="optionA" class="ui_input_txt02" placeholder="答案A"/>
						</label>
					</div>
					<div>
						<label>
							答案B：
							<input type="text" id="optionB" class="ui_input_txt02" placeholder="答案B"/>
						</label>
					</div>
					<div>
						<label>
							答案C：
							<input type="text" id="optionC" class="ui_input_txt02" placeholder="答案C"/>
						</label>
					</div>
					<div>
						<label>
							答案D：
							<input type="text" id="optionD" class="ui_input_txt02" placeholder="答案D"/>
						</label>
					</div>
					<div>
						<label>
							答案E：
							<input type="text" id="optionE" class="ui_input_txt02" placeholder="答案E"/>
						</label>
					</div>
					<div>
						<label>
							答案F：
							<input type="text" id="optionF" class="ui_input_txt02" placeholder="答案F"/>
						</label>
					</div>
					<div>
						<label>
							答案G：
							<input type="text" id="optionG" class="ui_input_txt02" placeholder="答案G"/>
						</label>
					</div>
					<div>
						<label>
							答案H：
							<input type="text" id="optionH" class="ui_input_txt02" placeholder="答案H"/>
						</label>
					</div>
					<div class="right">正确答案:
						<label>A:<input type="checkbox" value="1"/></label>
						<label>B:<input type="checkbox" value="2"/></label>
						<label>C:<input type="checkbox" value="3"/></label>
						<label>D:<input type="checkbox" value="4"/></label>
						<label>E:<input type="checkbox" value="5"/></label>
						<label>F:<input type="checkbox" value="6"/></label>
						<label>G:<input type="checkbox" value="7"/></label>
						<label>H:<input type="checkbox" value="8"/></label>
					</div>
					<div>
						<input type="button" id="submit" class="ui_input_btn01" value="确定" />
						<input type="button" class="ui_input_btn01" value="取消" />
					</div>
				</form>
			</div>
		</div>
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_bottom">
						<span style="display:none">请输入题目名称&nbsp;&nbsp;</span><input type="text" id="fyZldz" name="fyZldz" class="ui_input_txt02"  style="display:none"/><input type="button" value="搜索" class="ui_input_btn01" onclick="search();"  style="display:none"/> 
							<input type="button" value="新增" class="ui_input_btn01" id="addBtn" style="display:none"/> <br />
							<input type="file" id="xls" accept=".xls" onchange="importf(this)"/>
							<input type="button" value="导入" class="ui_input_btn01" id="importBtn" />
						</div>
					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" id="topics" data-name = "试题" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
						<thead>
							<tr>
								<td>题目</td>
								<td>类型</td>
								<td>选项A</td>
								<td>选项B</td>
								<td>选项C</td>
								<td>选项D</td>
								<td>选项E</td>
								<td>选项F</td>
								<td>选项G</td>
								<td>选项H</td>
								<td>正确答案</td>
								<td>操作</td>
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
						<!--<input type="button" value="首页" class="ui_input_btn01" />-->
						<input type="button" value="上一页" class="ui_input_btn01" onclick="beforePage()"/><span><span class="now_page">1</span>/<span class="tt_page">2</span></span>
						<input type="button" value="下一页" class="ui_input_btn01"
							onclick="afterPage();" />
						<!--<input type="button" value="尾页" class="ui_input_btn01"
							onclick="jumpNormalPage(9);" />-->
						<!--     如果是最后一页，则只显示首页、上一页 -->
						<!--转到第<input type="text" id="jumpNumTxt" class="ui_input_txt01" />页-->
						<!--<input type="button" class="ui_input_btn01" value="跳转" onclick="jumpInputPage(9);" />-->
					</div>
				</div>
			</div>
		</div>
	</body>
	<script>
		var topics_length;
		var page = 0;
		$(document).ready(function(){
			$(".now_page").text("1")
			/**获取题目**/
			$.ajax({
				type:"get",
	    		data:{
	    			begin:0,
	    			end:10
	    		},
	    		url:"/youyou_client/admin.do?method=getTopicsByPage",
	    		async:true,
	    		success:function(d){
	    			if(d.RESULT=="SUCCESS"){
	    				var tr = "";
	    				topics_length = d.TOPICS_LENGTH;
	    				$(".tt_page").text(Math.ceil(d.TOPICS_LENGTH/10))
	    				$(".topic_num").text(d.TOPICS_LENGTH);
	    				for(var i=0;i<d.LIST.length;i++){
	    					tr+="<tr><td>"+d.LIST[i].topic_title+"</td><td>"+topicType[d.LIST[i].topic_type]+"</td><td>"+d.LIST[i].topic_option_a+"</td><td>"+d.LIST[i].topic_option_b+"</td><td>"+d.LIST[i].topic_option_c+"</td><td>"+d.LIST[i].topic_option_d+"</td><td>"+d.LIST[i].topic_option_e+"</td><td>"+d.LIST[i].topic_option_f+"</td><td>"+d.LIST[i].topic_option_g+"</td><td>"+d.LIST[i].topic_option_h+"</td><td>"+changeNum(d.LIST[i].topic_right_option,"0")+"</td><td><span onclick='delTopic("+d.LIST[i].id+")'>删除</span></td></tr>"
	    				}
	    				$(".topic_tab").html(tr);
	    			}
	    		}
			})
			/** 显示新增   **/
		    /*$("#addBtn").click(function(){
		    	$(".add-cont").show()
		    });
			$(".close").click(function(){
				$(".add-cont").hide()
			})*/
			/**新增**/
			
		    /** 导入  **/
		    $("#importBtn").click(function(){
			    if($("#xls").val()==""){
		    		alert("请选择xls文件");
		    		return;
		    	}
		    	for(var i in XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])){
		    		$.ajax({
			    		type:"post",
			    		data:XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])[i],
			    		url:"/youyou_client/admin.do?method=addXls",
			    		async:true,
			    		success:function(d){
			    			if(d.RESULT!="SUCCESS"){
			    				alert("第"+i+"题上传失败");
			    			}
			    		}
			    	});
		    	}
		    	alert("导入成功！");
		    });
		    
		    /*上一页*/
		    
		});
		
		function delTopic(id){
			if(confirm("确认删除？")){
   				$.ajax({
					type:"post",
		    		data:{
		    			topicId:id
		    		},
		    		url:"/youyou_client/admin.do?method=delTopic",
		    		async:true,
		    		success:function(d){
		    			if(d.RESULT=="SUCCESS"){
		    				alert("已删除");
		    				window.location.reload()
		    			}
		    		}
				})
   			}
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
	    		url:"/youyou_client/admin.do?method=getTopicsByPage",
	    		async:true,
	    		success:function(d){
	    			if(d.RESULT=="SUCCESS"){
	    				var tr = "";
	    				$(".topic_num").text(d.TOPICS_LENGTH);
	    				for(var i=0;i<d.LIST.length;i++){
	    					tr+="<tr><td>"+d.LIST[i].topic_title+"</td><td>"+topicType[d.LIST[i].topic_type]+"</td><td>"+d.LIST[i].topic_option_a+"</td><td>"+d.LIST[i].topic_option_b+"</td><td>"+d.LIST[i].topic_option_c+"</td><td>"+d.LIST[i].topic_option_d+"</td><td>"+d.LIST[i].topic_option_e+"</td><td>"+d.LIST[i].topic_option_f+"</td><td>"+d.LIST[i].topic_option_g+"</td><td>"+d.LIST[i].topic_option_h+"</td><td>"+changeNum(d.LIST[i].topic_right_option,"0")+"</td><td><span onclick='delTopic("+d.LIST[i].id+")'>删除</span></td></tr>"
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
	    		url:"/youyou_client/admin.do?method=getTopicsByPage",
	    		async:true,
	    		success:function(d){
	    			if(d.RESULT=="SUCCESS"){
	    				var tr = "";
	    				$(".topic_num").text(d.TOPICS_LENGTH);
	    				for(var i=0;i<d.LIST.length;i++){
	    					tr+="<tr><td>"+d.LIST[i].topic_title+"</td><td>"+topicType[d.LIST[i].topic_type]+"</td><td>"+d.LIST[i].topic_option_a+"</td><td>"+d.LIST[i].topic_option_b+"</td><td>"+d.LIST[i].topic_option_c+"</td><td>"+d.LIST[i].topic_option_d+"</td><td>"+d.LIST[i].topic_option_e+"</td><td>"+d.LIST[i].topic_option_f+"</td><td>"+d.LIST[i].topic_option_g+"</td><td>"+d.LIST[i].topic_option_h+"</td><td>"+changeNum(d.LIST[i].topic_right_option,"0")+"</td><td><span onclick='delTopic("+d.LIST[i].id+")'>删除</span></td></tr>"
	    				}
	    				$(".topic_tab").html(tr)
	    			}
	    		}
			})
		}
		
		
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
		/** 模糊查询来电用户  **/
		function search(){
			$("#submitForm").attr("action", "house_list.html?page=" + 1).submit();
		}
		
		/** 删除 **/
		function del(fyID){
			
		}
		
		
		/** 普通跳转 **/
		function jumpNormalPage(page){
			$("#submitForm").attr("action", "house_list.html?page=" + page).submit();
		}
		
		/** 输入页跳转 **/
		function jumpInputPage(totalPage){
			
		}
	</script>
</html>
