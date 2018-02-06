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
    <h4>导入英语试题</h4>
    <div class="form-group clearfix">
        <div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
            <label>选择文件</label>
            <input type="file" id="xls" accept=".xls" onchange="importf(this)"/>
        </div>
        <div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
            <label>选择课程</label>
            <select id="selClassId" class="form-control" onchange="changeAdd('#selClassId','#selChapId')">
            </select>
        </div>
        <div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
            <label>选择章节</label>
            <select id="selChapId" class="form-control">
            </select>
        </div>
        <div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
            <label></label>
            <button type="button" class="btn btn-default btn-block btn-success" id="addXls">导入</button>
        </div>
    </div>
    <h4>英语试题搜索</h4>
    <div class="form-group clearfix">
        <div class="col-lg-4 col-md-4 col-xs-4 col-sm-4">
            <label>选择课程</label>
            <select id="selClassIdQ" class="form-control" onchange="changeAdd('#selClassIdQ','#selChapIdQ')">
            </select>
        </div>
        <div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
            <label>选择章节</label>
            <select id="selChapIdQ" class="form-control">
            </select>
        </div>
        <div class="col-lg-2 col-md-2 col-xs-2 col-sm-2">
            <label></label>
            <button type="button" id="searchBtn" class="btn btn-block btn-primary">查询</button>
        </div>
    </div>
    <div class="form-group clearfix">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th width="5%">序号</th>
                <th width="5%">题目类型</th>
                <th width="5%">问题类型</th>
                <th width="30%">题目</th>
                <th width="10%">图片</th>
                <th width="20%">音频</th>
                <th width="25%">操作</th>
            </tr>
            </thead>
            <tbody class="search-list">
            </tbody>
        </table>
    </div>

    <!-- 修改模态框（Modal） -->
    <div id="qtUpdModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
        <div class="modal-dialog" style="width:1000px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">修改试题</h4>
                    <input type="hidden" id="questionId"/>
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <div class="col-lg-6 col-md-6 col-xs-6 col-sm-6">
                            <label>题目类型</label>
                            <select id="qtType" class="form-control">
                                <option value="1">听力</option>
                                <option value="2">会话</option>
                            </select>
                        </div>
                        <div class="col-lg-6 col-md-6 col-xs-6 col-sm-6">
                            <label>问题类型</label>
                            <select id="qtDif" class="form-control">
                                <option value="1">判断题</option>
                                <option value="2">单选题</option>
                                <option value="3">阅读题</option>
                            </select>
                        </div>
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>题目内容</label>
                            <textarea id="qtContent" class="form-control" rows="5"></textarea>
                        </div>
                        <div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
                            <label>A</label>
                            <input type="text" id="optionA" class="form-control" placeholder=""/>
                        </div>
                        <div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
                            <label>B</label>
                            <input type="text" id="optionB" class="form-control" placeholder=""/>
                        </div>
                        <div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
                            <label>C</label>
                            <input type="text" id="optionC" class="form-control" placeholder=""/>
                        </div>
                        <div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
                            <label>D</label>
                            <input type="text" id="optionD" class="form-control" placeholder=""/>
                        </div>
                        <div class="col-lg-3 col-md-3 col-xs-3 col-sm-3">
                            <label>正确选项</label>
                            <select id="rightOption" class="form-control">
                                <option value="1">A</option>
                                <option value="2">B</option>
                                <option value="3">C</option>
                                <option value="4">D</option>
                            </select>
                        </div>
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>题目解释说明</label>
                            <textarea id="explanTxt" class="form-control" rows="5"></textarea>
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

    <!-- 图片上传模态框（Modal） -->
    <div class="modal fade" id="qtPicModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">添加图片</h4>
                </div>
                <audio></audio>
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
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" id="addQtImage" class="btn btn-primary" data-id="" data-pay="0">开始上传</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 音频上传模态框（Modal） -->
    <div class="modal fade" id="qtVolModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">添加音频</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>选择音频文件</label><%--这里只允许指定上传音频文件--%>
                            <input class="btn btn-default" type="file" multiple id="volFile" accept="audio/*"/>
                        </div>
                        <div class="col-lg-12 col-md-12 col-xs-12 col-sm-12">
                            <label>音频预览</label>
                            <div class="form-control-sty2 " id="preVolView">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" id="addQtVol" class="btn btn-primary" data-id="" data-pay="0">开始上传</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var data=null;
    $(function(){
        /*初始化课程下拉框*/
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
                $("#selClassId, #selClassIdQ").html(op);
                changeAdd('#selClassId','#selChapId');
                changeAdd('#selClassIdQ','#selChapIdQ');
            }
        });

        $("#addXls").click(function(){
            var classId = $("#selClassId").val();
            var chapId = $("#selChapId").val();
            if(!classId){
                bootbox.alert({title: "提示", message: "请选择课程！"});
                return false;
            }else if(!chapId){
                bootbox.alert({title: "提示", message: "请选择章节！"});
                return false;
            }
            if(!$("#xls").val()){
                bootbox.alert({title: "提示", message: "请选择类型为.xls的Excel文件！"});
                return false;
            }
            // for(var i in XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])){
                // var parm = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])[i];
                var parm = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);
                var xlsData = JSON.stringify(parm);
                // parm.classId = classId;
                // parm.chapId = chapId;
                $.ajax({
                    type:"post",
                    data:{
                        'xls':xlsData,
                        'classId':classId,
                        'chapId':chapId
                    },
                    url:urlPath+"/admin.do?method=addEnglishQuestion",
                    async:true,
                    success:function(d){
                        if(d.RESULT!="SUCCESS"){
                            return false;
                        }
                    }
                });
            // }
            refleshTable();
            bootbox.alert({title: "提示", message: "导入成功！"});
        });

        $("#searchBtn").click(function(){
            refleshTable();
        });

        $("#btnUpd").click(function () {
            updEnglishQuestion();
        });

        /**点击上传音频**/
        $('#addQtVol').on('click',function(){
            var formData = new FormData();
            formData.append("targetId",$(this).attr("data-id"));
            formData.append("audio",document.getElementById("volFile").files[0]);
            $.ajax({
                processData: false,
                contentType: false,
                url:urlPath+"/admin.do?method=updateEnglishQuestionVol",
                type:'POST',
                dataType:'json',
                data:formData,
                async:true,
                success:function(data){
                    if(data.RESULT=="SUCCESS"){
                        $("#qtVolModal").modal('hide');
                        refleshTable();
                        bootbox.alert({title: "提示", message: "上传成功！"});
                    }
                }
            });
        });

        // 给modal绑定事件
        $('#qtVolModal').on('hidden.bs.modal', function () {
            stopOtherAudio();
        });
    });

    /**
     * 音频控件初始化
     **/
    window.onload=function(){
        var file=document.getElementById("volFile");
        file.onchange=function(){
            var divNode2 = document.getElementById("preVolView");
            var textNode = divNode2.childNodes[0];
            divNode2.removeChild(textNode);
            $("#preVolView").append("<audio id=\"volAudio\" controls onclick='stopOtherAudio()'></audio>");
            $("#volAudio").attr("src",window.URL.createObjectURL(file.files[0]));
        }
    };

    function stopOtherAudio(){
        $('audio').not(this).each(function(){
            this.pause();
        });
    }

    function refleshTable(){
        var chapId = $("#selChapIdQ").val();
        $.ajax({
            type:"get",
            url:urlPath+"/admin.do?method=getEnglishQuestion",
            data:{
                chapId:chapId
            },
            async:true,
            success:function(d){
                var tr="";
                for (var i = 0; i < d.LIST.length; i++) {
                    var x = d.LIST[i].qtType == 1 ? '听力' : '会话';
                    var y = d.LIST[i].qtDif == 1 ? '判断题' : (d.LIST[i].qtDif == 2 ?'单选题' : '阅读题');
                    var pic='';
                    if(d.LIST[i].picUrl){
                        pic='<img width="100" height="100" class="img-thumbnail" src="'+urlPath+d.LIST[i].picUrl+'" /img>';
                    }
                    var vol='';
                    if(d.LIST[i].voUrl){
                        var ul=d.LIST[i].voUrl;
                        var ulName=ul.substring(ul.lastIndexOf('/')+1,ul.length);
                        vol='<span><h4>'+ulName+'</h4></span><audio controls src="'+urlPath+d.LIST[i].voUrl+'" onclick="stopOtherAudio()"></audio>';
                    }
                    tr += '<tr>' +
                        '<td>' + (i + 1) + '</td>' +
                        '<td>'+x+'</td>' +
                        '<td>'+y+'</td>' +
                        '<td>' + d.LIST[i].qtContent + '</td>' +
                        '<td>' + pic + '</td>' +
                        '<td>' + vol + '</td>' +
                        '<td>'+
                        '<a class="btn btn-primary" onclick="opUpdModal(\'' + d.LIST[i].id + '\',\'' + d.LIST[i].qtType + '\',\'' + d.LIST[i].qtDif + '\',\'' + d.LIST[i].qtContent + '\',\'' + d.LIST[i].optionA + '\',\'' + d.LIST[i].optionB + '\',\'' + d.LIST[i].optionC + '\',\'' + d.LIST[i].optionD + '\',\'' + d.LIST[i].rightOption + '\',\'' + d.LIST[i].explanTxt + '\')">修改</a>' +
                        '<a class="btn btn-info" onclick="opVolModal(\'' + d.LIST[i].id + '\')">上传音频</a>' +
                        '<a class="btn btn-info" onclick="opQtPicModal(\'' + d.LIST[i].id + '\')">上传图片</a>' +
                        '<a class="btn btn-danger" onclick="del(\'' + d.LIST[i].id + '\',this)">删除</a>' +
                        '</td>' +
                        '</tr>'
                }
                $(".search-list").html(tr);
            }
        });
    }

    function opUpdModal(id,qtType,qtDif,qtContent,optionA,optionB,optionC,optionD,rightOption,explanTxt){
        $("#questionId").val(id);
        $("#qtType").val(qtType);
        $("#qtDif").val(qtDif);
        $("#qtContent").val(qtContent);
        $("#optionA").val(optionA);
        $("#optionB").val(optionB);
        $("#optionC").val(optionC);
        $("#optionD").val(optionD);
        $("#rightOption").val(rightOption);
        $("#explanTxt").val(explanTxt);
        $("#qtUpdModal").modal('show');
    }

    function opVolModal(qtId){
        $("#addQtVol").attr("data-id",qtId);
        $("#qtVolModal").modal('show');
    }

    function opQtPicModal(qtId){
        $("#addQtImage").attr("data-id",qtId);
        $("#qtPicModal").modal('show');
    }

    function updEnglishQuestion(){
        $.ajax({
            type:"post",
            data:{
                id:$("#questionId").val(),
                qtType:$("#qtType").val(),
                qtDif:$("#qtDif").val(),
                qtContent:$("#qtContent").val(),
                optionA:$("#optionA").val(),
                optionB:$("#optionB").val(),
                optionC:$("#optionC").val(),
                optionD:$("#optionD").val(),
                rightOption:$("#rightOption").val(),
                explanTxt:$("#explanTxt").val()
            },
            url:urlPath+"/admin.do?method=updEnglishQuestion",
            async:true,
            success:function(d){
                if(d.RESULT=="SUCCESS"){
                    $("#qtUpdModal").modal('hide');
                    refleshTable();
                    bootbox.alert({title: '提示', message: '修改成功！'});
                }
            }
        });
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
                        url:urlPath+"/admin.do?method=delEnglishQuestion",
                        async:true,
                        success:function(d){
                            if(d.RESULT=="SUCCESS"){
                                $(t).parents("tr").remove();
                                bootbox.alert({title: "提示", message: "删除成功！"});
                            }
                        }
                    });
                }
            }
        });
    }

    function changeAdd(p,c){
        var selClassId = $(p).val();
        getSection(selClassId,c);
    }

    function getSection(p,c){
        $.ajax({
            type:"get",
            data:{
                classId:p
            },
            url:urlPath+"/admin.do?method=getChapByClassId",
            async:true,
            success:function(data){
                var op = '';
                for(var i in data.LIST){
                    op+='<option value="'+data.LIST[i].chapId+'">'+data.LIST[i].chapName+'</option>';
                }
                $(c).html(op)
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
