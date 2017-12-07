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
    <div class="form-group clearfix">
        <h4>添加英语课程</h4>
        <div class="col-lg-5 col-md-5 col-xs-5 col-sm-5">
            <label>课程名称</label>
            <input type="text" id="className" class="form-control" placeholder="输入课程名称"/>
        </div>
        <div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
            <label>是否付费</label>
            <select id="isPay" class="form-control" onchange="selectIsPay('#isPay')">
                <option value="0">否</option>
                <option value="1">是</option>
            </select>
        </div>
        <div class="col-lg-2 col-md-2 col-xs-2 col-sm-2" id="showMoneyDiv">
            <label>价格(单位/元)</label>
            <input type="text" id="payMoney" class="form-control" placeholder="填写价格"/>
        </div>
        <div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
            <label></label>
            <button type="button" id="btnAdd" class="btn btn-block btn-success">添加</button>
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
                <th width="20%">课程图片</th>
                <th width="30%">课程名称</th>
                <th width="10%">是否付费</th>
                <th width="10%">价格(单位/元)</th>
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
                    <h4 class="modal-title">修改课程</h4>
                    <input type="hidden" id="mClassId"/>
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>课程名称</label>
                            <input type="text" id="mClassName" class="form-control" placeholder=""/>
                        </div>
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>是否付费</label>
                            <select id="mIsPay" class="form-control" onchange="upSelPay('#mIsPay')">
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </div>
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>价格(单位/元)</label>
                            <input type="text" id="mPayMoney" class="form-control" placeholder=""/>
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


    <!-- 模态框添加图片 -->
    <div id="myModalPic" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">添加图片</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>添加图片<small class="text-danger">有图片的课程可选择上传图片</small></label>
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
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" id="addImageEgPic" class="btn btn-primary" data-id="">开始上传</button>
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
            refleshTable();
        });
        $("#btnUpd").click(function () {
            updEgClass();
        });
        /*添加英语课程*/
        $("#btnAdd").click(function(){
            var className = $("#className").val();
            var isPay = $("#isPay").val();
            var payMoney = $("#payMoney").val();
            if(!className){
                bootbox.alert({title: "提示", message: "请输入课程名称！"});
                return false;
            }else if("1"==isPay&&!payMoney){
                bootbox.alert({title: "提示", message: "收费课程请填写价格！"});
                return false;
            }else if(parseInt(payMoney)<1){
                bootbox.alert({title: "提示", message: "价格必须大于等于1！"});
            }{
                $.ajax({
                    type:"post",
                    url:urlPath+"/admin.do?method=addEnglishClass",
                    async:true,
                    data:{
                        className:className,
                        isPay:isPay,
                        payMoney:payMoney
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
        selectIsPay('#isPay');
    });

    function selectIsPay(p){
        var selIsPay = $(p).val();
        if("1"==selIsPay){
            $("#showMoneyDiv").show();
            $("#payMoney").val("");
        }else{
            $("#showMoneyDiv").hide();
            $("#payMoney").val("");
        }
    }

    function upSelPay(c){
        var selIsPay = $(c).val();
        if("1"==selIsPay){
            $("#mPayMoney").removeAttr("readonly");
            $("#mPayMoney").val("");
        }else{
            $("#mPayMoney").attr({ readonly: 'true' });
            $("#mPayMoney").val("");
        }
    }

    function opModal(id,className,isPay,payMoney){
        $("#mClassId").val(id);
        $("#mClassName").val(className);
        $("#mIsPay").val(isPay);
        $("#myModal").modal('show');
        upSelPay("#mIsPay");
        if(payMoney){
            $("#mPayMoney").val(payMoney);
        }else{
            $("#mPayMoney").val("");
        }
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
                        url:urlPath+"/admin.do?method=delEnglishClass",
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
        var a=$('#mClassId').val();
        var b=$('#mClassName').val();
        var c=$('#mIsPay').val();
        var d = $("#mPayMoney").val();
        if(!b){
            bootbox.alert({title: "提示", message: "请输入课程名称！"});
            return false;
        }else if("1"==c&&!d){
            bootbox.alert({title: "提示", message: "收费课程请填写价格！"});
            return false;
        }else if(parseInt(d)<1){
            bootbox.alert({title: "提示", message: "价格必须大于等于1！"});
        }else{
            $.ajax({
                type:"post",
                data:{
                    id:a,
                    className:b,
                    isPay:c,
                    payMoney:d
                },
                url:urlPath+"/admin.do?method=updEnglishClass",
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
    }

    function opPicModal(classId){
        $("#addImageEgPic").attr("data-id",classId);
        $("#myModalPic").modal('show');
    }

    function refleshTable(){
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
                    var pic='';
                    if(d.LIST[i].classPic){
                        pic='<img width="120" height="120" class="img-thumbnail" src="'+urlPath+d.LIST[i].classPic+'" /img>';
                    }
                    tr += '<tr>' +
                        '<th>' + (i + 1) + '</th>' +
                        '<td>'+pic+'</td>' +
                        '<td>' + d.LIST[i].className + '</td>' +
                        '<td>' + x + '</td>' +
                        '<td>' + d.LIST[i].payMoney + '</td>' +
                        '<td>'+
                        '<a class="btn btn-primary" onclick="opModal(\''+d.LIST[i].id+'\',\''+d.LIST[i].className+'\',\''+d.LIST[i].isPay+'\',\''+d.LIST[i].payMoney+'\')">修改</a>' +
                        '<a class="btn btn-info" onclick="opPicModal(\'' + d.LIST[i].id + '\')">上传图片</a>' +
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
