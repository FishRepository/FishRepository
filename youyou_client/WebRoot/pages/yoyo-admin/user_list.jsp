<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>YOUYOU后台管理系统</title>
		<%@ include file="../../include/base.jsp" %>
	</head>
	<body>
		<div class="form-inline">
			<input type="text" class="form-control" id="userName" placeholder="输入用户名查询">
			<input class="btn btn-info" type="button" onclick="search()" value="搜索"/>
		</div>
		<div class="content">
			<table id="usertable" class="display">
				<thead>
				<tr>
					<th>姓名</th>
					<th>性别</th>
					<th>电话号码</th>
					<th>Email</th>
				</tr>
				</thead>
			</table>
		</div>
	</body>
	<script>
		var outType = ["xls","json","txt","doc"];
		function exportExcel(){
			tableExport('userTab',"用户列表", outType[0]);
		}
		$(document).ready(function(){
            $('#usertable').DataTable({
                scrollX: true,
                searching: false,
                ordering: false,
                processing: true,
                serverSide: true,
                'ajax': {
                    url: urlPath+'/admin.do?method=getUserByPage',
					data:{
                        userName: function(){
                            return $.trim($('#userName').val());
                        }
					}
                },
                dom: page_table.dom,
                language: page_table.language,
                'columns': [
                    {'data': 'user_name'},
                    {'data': function (row) {
						return row.user_sex ==='1'?'男':'女';
                    	}
					},
                    {'data':
						function (row) {
                        	if(row.user_phone){
                                return returnStr(row.user_phone);
							}return '';
                        }
					},
                    {'data':
						function (row) {
                        	if(row.user_email){
                                return returnStr(row.user_email);
							}return '';
                        }
                    }
                ]
            });

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
			return $.trim(v)==='undefined'?"":v
		}
        function search(){
            $('#usertable').DataTable().ajax.reload();
        }
	</script>
</html>
