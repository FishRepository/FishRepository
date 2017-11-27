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
            <label><small class="text-danger">包含图片的题目请手动上传图片</small></label>
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
        <table class="table">
            <thead>
            <tr>
                <th width="10%">序号</th>
                <th width="10%">题型</th>
                <th width="30%">题目</th>
                <th width="20%">图片</th>
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
        });

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
        });
    });

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
