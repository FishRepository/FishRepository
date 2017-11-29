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
        <h4>添加英语课程章节</h4>
        <div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
            <label>选择英语课程</label>
            <select id="selClassAdd" class="form-control">
            </select>
        </div>
        <div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
            <label>章节名称</label>
            <input type="text" id="chapNameAdd" class="form-control" placeholder="输入章节名称"/>
        </div>
        <div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
            <label></label>
            <button type="button" id="btnAdd" class="btn btn-block btn-success">添加</button>
        </div>
    </div>



    <div class="form-group clearfix">
        <h4>英语课程章节查看</h4>
        <div class="form-group clearfix">
            <div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
                <label>选择英语课程</label>
                <select id="classIdQ" class="form-control">
                </select>
            </div>
            <div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
                <label>章节名称</label>
                <input type="text" id="chapNameQ" class="form-control" placeholder="输入章节名称"/>
            </div>
            <div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
                <label></label>
                <button type="button" id="btnSearch" class="btn btn-block btn-primary">查询</button>
            </div>
        </div>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th width="10%">序号</th>
                <th width="30%">课程名称</th>
                <th width="30%">章节名称</th>
                <th width="20%">操作</th>
            </tr>
            </thead>
            <tbody class="search-list">

            </tbody>
        </table>
    </div>


    <!-- 模态框（Modal） -->
    <div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
        <div class="modal-dialog" style="width:500px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">修改课程章节</h4>
                    <input type="hidden" id="mChapId"/>
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>课程名称</label>
                            <input type="text" id="mClassName" readonly class="form-control" placeholder=""/>
                        </div>
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>章节名称</label>
                            <input type="text" id="mChapName" class="form-control" placeholder=""/>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="btnUpd">提交修改</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var data=null;
    $(function(){
        $.ajax({
            type:"get",
            url:urlPath+"/admin.do?method=getSelClass",
            async:true,
            success:function(d){
                data=d;
                var op="";
                for (var i=0;i<d.LIST.length;i++) {
                    op+='<option value="'+d.LIST[i].classId+'">'+d.LIST[i].className+'</option>';
                }
                $(" #selClassAdd, #classIdQ").html(op);
            }
        });

        /*查询英语课程*/
        $("#btnSearch").click(function(){
            refleshTable();
        });
        $("#btnUpd").click(function () {
            updEgClass();
        });
        /*添加英语课程*/
        $("#btnAdd").click(function(){
            var selClassId = $("#selClassAdd").val();
            var chapNameAdd = $("#chapNameAdd").val();
            if(!selClassId){
                bootbox.alert({title: "提示", message: "请选择要添加章节的课程！"});
                return false;
            }else if(!chapNameAdd){
                bootbox.alert({title: "提示", message: "请输入章节名称！"});
                return false;
            }else{
                $.ajax({
                    type:"post",
                    url:urlPath+"/admin.do?method=addEnglishClassChap",
                    async:true,
                    data:{
                        classId:selClassId,
                        chapName:chapNameAdd
                    },
                    success:function(d){
                        if(d.RESULT=="SUCCESS"){
                            refleshTable();
                            bootbox.alert({title: "提示", message: "添加成功！"});
                        }
                    }
                });
            }
        });
    });

    function opModal(id,className,chapName){
        $("#mChapId").val(id);
        $("#mClassName").val(className);
        $("#mChapName").val(chapName);
        $("#myModal").modal('show');
    }

    function del(id,t){
        bootbox.confirm({
            title: '提示',
            message: '确定删除该记录吗？',
            buttons: {
                cancel: {
                    label: '取消'
                },
                confirm: {
                    label: '确认'
                }
            },
            callback: function (result) {
                if (result) {
                    $.ajax({
                        type:"post",
                        data:{
                            id:id
                        },
                        url:urlPath+"/admin.do?method=delEnglishClassChap",
                        async:true,
                        success:function(d){
                            if(d.RESULT=="SUCCESS"){
                                $(t).parents("tr").remove();
                                bootbox.alert({title: '提示', message: '删除成功！'});
                            }
                        }
                    });
                }
            }
        });
    }

    function updEgClass(){
        var a=$('#mChapId').val();
        var b=$('#mChapName').val();
        $.ajax({
            type:"post",
            data:{
                id:a,
                chapName:b
            },
            url:urlPath+"/admin.do?method=updEnglishClassChap",
            async:true,
            success:function(d){
                if(d.RESULT=="SUCCESS"){
                    $("#myModal").modal('hide');
                    refleshTable();
                    bootbox.alert({title: '提示', message: '修改成功！'});
                }
            }
        });
    }

    function refleshTable(){
        var classIdQ = $("#classIdQ").val();
        var chapNameQ = $("#chapNameQ").val();
        $.ajax({
            type:"get",
            url:urlPath+"/admin.do?method=getEnglishClassChap",
            async:true,
            data:{
                classId:classIdQ,
                chapName:chapNameQ
            },
            success:function(d){
                var tr="";
                for (var i = 0; i < d.LIST.length; i++) {
                    tr += '<tr>' +
                        '<th>' + (i + 1) + '</th>' +
                        '<td>'+d.LIST[i].className+'</td>' +
                        '<td>' + d.LIST[i].chapName + '</td>' +
                        '<td>'+
                        '<a class="btn btn-primary" onclick="opModal(\''+d.LIST[i].id+'\',\''+d.LIST[i].className+'\',\''+d.LIST[i].chapName+'\')">修改</a>' +
                        '<a class="btn btn-danger" onclick="del(\'' + d.LIST[i].id + '\',this)">删除</a>' +
                        '</td>' +
                        '</tr>'
                }
                $(".search-list").html(tr);
            }
        });
    }
</script>
</html>
