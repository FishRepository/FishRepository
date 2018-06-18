function setCookie(name,value){ 
	var exp = new Date(); 
	exp.setTime(exp.getTime() + 5*60*60*1000);
	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
function getCookie(name){
	var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
	if(arr != null)
	return unescape(arr[2]);
	return null;
}
function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if(cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

function isBlankStr(str){
    if(!str){
        return true;
    }
    if(str===''){
        return true;
    }
    if(str===undefined){
        return true;
    }
    if(str==='undefined'){
        return true;
    }
    return false;
}

function checkLogin() {
    var userId = getCookie("userId");
    if(userId) location.href = urlPath+"/admin.do?method=index&user_name="+getCookie("userName");
}

var page_table={
    dom : "<'row'r>t<'row'<'col-sm-6'li><'col-sm-6'p>>",
    domEL : "<'row'r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
    language:{
        "processing": "处理中...",
        "search": "搜索:",
        "loadingRecords": "载入中...",
        "lengthMenu": "每页_MENU_",
        "zeroRecords": "没有找到记录",
        "info": "当前 _START_到 _END_条，共 _TOTAL_条记录",
        //"infoEmpty": "无数据",
        "infoEmpty":"",
        //"infoFiltered": "(从 _MAX_ 条记录过滤)",
        "infoFiltered":"",
        "paginate": {
            "first": "首页",
            "previous": "上一页 ",
            "next": "下一页 ",
            "last": "尾页 "
        }
    },
    selected:function(tableid)	{
        $('#'+tableid+' tbody').on('click','tr',function(){
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            } else {
                $('#'+tableid+' tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
        } );
    },
    //fixed:datatable本地分页，换页时，前一页所选行不会消失
    localSelected:function(tableid,table){
        this.selected(tableid);
        $('#'+tableid).on( 'page.dt', function () {
            $( table.row('.selected').nodes()).removeClass( 'selected' );
        } );
    },
    //fixed：当出现2层弹出框：（完全）关闭弹窗时将父窗口重新聚焦
    closeChildModal:function(childId){
        $('#'+childId).on('hidden.bs.modal', function () {
            //$('#${portletid}activityModal').modal('toggle');
            //$('#${portletid}activityModal').modal('show');
            $("body").toggleClass("modal-open");
        });
    },
    //自适应iframe高度
    autoIframeHeight : function(){
        var iframeId = $(window.parent.document).find("#permissionTab li.active").find("a").attr("href");
        if(iframeId){
            iframeId = iframeId.substr(1);
            var main = $(window.parent.document).find("#iframe"+iframeId)
            var thisheight = $(document).height()+30;
            main.height(thisheight<695?695:thisheight);
        }
    }
};
var urlPath = "http://127.0.0.1:8080/youyou_client";
// var urlPath = "http://111.111.125.238:8080/youyou_client";
// var urlPath = "https://www.yoyotec.com/youyou_client";