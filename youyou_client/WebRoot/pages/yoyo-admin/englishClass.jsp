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
    <div class="form-group clearfix">
        <h4>添加英语课程</h4>
        <div class="col-lg-5 col-md-5 col-xs-5 col-sm-5">
            <label>课程名称</label>
            <input type="text" id="className" class="form-control" placeholder="输入课程名称"/>
        </div>
        <div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
            <label>是否付费</label>
            <select id="isPay" class="form-control">
                <option value="0">否</option>
                <option value="1">是</option>
            </select>
        </div>
        <div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
            <label></label>
            <button type="button" id="btnAdd" class="btn btn-block btn-success">添加英语课程</button>
        </div>
    </div>



    <div class="form-group clearfix">
        <h4>英语课程列表查看</h4>
        <div class="form-group clearfix">
        <div class="col-lg-5 col-md-5 col-xs-5 col-sm-5">
            <label>课程名称</label>
            <input type="text" id="classNameQ" class="form-control" placeholder="输入课程名称"/>
        </div>
        <div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
            <label>是否付费</label>
            <select id="isPayQ" class="form-control">
                <option value="0">否</option>
                <option value="1">是</option>
            </select>
        </div>
        <div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
            <label></label>
            <button type="button" id="btnSearch" class="btn btn-block btn-primary">查询</button>
        </div>
        </div>
        <table class="table table-bordered">
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


    <div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">修改课程</h4>
                    <input type="hidden" id="mClassId">
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>课程名称</label>
                            <input type="text" id="mClassName" class="form-control" placeholder=""/>
                        </div>
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>是否付费</label>
                            <select id="mIsPay" class="form-control">
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" id="addImage" class="btn btn-primary" data-id="" data-pay="">提交修改</button>
                </div>
            </div>
        </div>
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
                    for (var i = 0; i < d.LIST.length; i++) {
                        var x = d.LIST[i].isPay == 1 ? '是' : '否';
                        tr += '<tr>' +
                                '<th>' + (i + 1) + '</th>' +
                                '<td>' + d.LIST[i].className + '</td>' +
                                '<th>' + x + '</th>' +
                                '<td>'+
                                    '<a href="javascript:;" class="btn btn-sm btn-info" onclick="open(\''+d.LIST[i].id+'\',\''+d.LIST[i].className+'\',\''+d.LIST[i].isPay+'\')">修改</a>' +
                                    '<a class="btn btn-sm btn-danger" onclick="del(\'' + d.LIST[i].id + '\',this)">删除</a>' +
                                '</td>' +
                            '</tr>'
                    }
                    $(".search-list").html(tr);
                }
            });
        });
        /*添加英语课程*/
        $("#btnAdd").click(function(){
            var className = $("#className").val();
            var isPay = $("#isPay").val();
            if(!className){
                alert("课程名称不能为空！");
                return;
            }else{
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
            }
        });
    });

    function open(id,className,isPay){
        $('#myModal').modal('show');
        $("#mclassId").val(id);
        $("#mclassName").val(className);
        $("#mIsPay").val(isPay);
    }

    function del(id,t){
        if(window.confirm('确定删除该记录吗？')){
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
            return true;
        }else{
            return false;
        }
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
