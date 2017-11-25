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
    <h4>添加英语课程</h4>
    <div class="form-group clearfix">
        <div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
            <label>课程名称</label>
            <input type="text" id="className" class="form-control" placeholder="输入课程名称"/>
        </div>
        <div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
            <label>是否付费</label>
            <select id="isPay" class="form-control">
                <option value="0">否</option>
                <option value="1">是</option>
            </select>
        </div>
        <div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
            <label></label>
            <button type="button" id="btnAdd" class="btn btn-block btn-primary">添加英语课程</button>
        </div>
    </div>



    <h4>英语课程列表查看</h4>
    <div class="form-group clearfix">
        <div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
            <label>课程名称</label>
            <input type="text" id="classNameQ" class="form-control" placeholder="输入课程名称"/>
        </div>
        <div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
            <label>是否付费</label>
            <select id="isPayQ" class="form-control">
                <option value="0">否</option>
                <option value="1">是</option>
            </select>
        </div>
        <div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
            <label></label>
            <button type="button" id="btnSearch" class="btn btn-block btn-primary">查询</button>
        </div>
    </div>

    <div class="form-group clearfix">
        <table class="table">
            <thead>
            <tr>
                <th width="10%">序号</th>
                <th width="50%">课程名称</th>
                <th width="10%">是否付费</th>
                <th width="30%">操作</th>
            </tr>
            </thead>
            <tbody class="search-list">

            </tbody>
        </table>
    </div>

</div>
</body>
<script>
    $(function(){
        /*查询英语课程*/
        $("#btnSearch").click(function(){
            var classNameQ = $("#classNameQ").val();
            var isPayQ = $("#isPayQ").val();
            $.ajax({
                type:"get",
                url:urlPath+"/admin.do?method=getEnglishClass",
                async:true,
                data:{
                    className:classNameQ,
                    isPay:isPayQ
                },
                success:function(d){
                    var tr="";
                    for (var i=0;i<d.LIST.length;i++) {
                        var x=d.LIST[i].isPay==1?'是':'否';
                        tr += '<tr><td>'+(i+1)+'</td><td>' + d.LIST[i].className + '</td><td>'+x+'</td>' +
                            '<td><a onclick="del(\'' + d.LIST[i].id + '\',this)">删除</a>' +
                                '<a onclick="update(\'' + d.LIST[i].id + '\',this)" data-toggle="modal" data-target="#myModal">修改</a>' +
                            '</td></tr>'
                    }
                    $(".search-list").html(tr);
                }
            });
        });
        /*添加英语课程*/
        $("#btnAdd").click(function(){
            var className = $("#className").val();
            var isPay = $("#isPay").val();
            $.ajax({
                type:"post",
                url:urlPath+"/admin.do?method=addEnglishClass",
                async:true,
                data:{
                    className:className,
                    isPay:isPay
                },
                success:function(d){
                    if(d.RESULT=="SUCCESS"){
                        alert("添加成功");
                        location.reload();
                    }
                }
            });
        });
    });

    function del(id,t){
        $.ajax({
            type:"post",
            data:{
                id:id
            },
            url:urlPath+"/admin.do?method=delEnglishClass",
            async:true,
            success:function(d){
                if(d.RESULT=="SUCCESS"){
                    alert("删除成功")
                    $(t).parents("tr").remove();
                }
            }
        });
    }

    function update(i){
        $.ajax({
            type:"post",
            data:{
                id:i,
                name:name
            },
            url:urlPath+"/admin.do?method=updEnglishClass",
            async:true,
            success:function(d){
                if(d.RESULT=="SUCCESS"){
                    alert("修改成功");
                    location.reload();
                }
            }
        });
    }
</script>
</html>
