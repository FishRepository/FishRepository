/*
 **************************
 author:Keel (keel.sike@gmail.com)
 **************************

 页码显示jquery插件,只需要存在#pageNav,则会在其中显示页码.
 调用时可根据需要先重写go方法.(已去除jquery依赖)

 **************************
 示例(注意：页面中放置id为pageNav的html对象):

 //转到页码时触发的自定义方法,p为当前页码,pn为总页数
 pageNav.fn = function(p,pn){
 alert(p+","+pn);
 };
 //初始跳到第3页,共33页
 pageNav.go(3,33);

 */
var pageNav = pageNav || {};
pageNav.fn = null;
//p为当前页码,pn为总页数,uri为跳转页(包含rows=?)
pageNav.nav = function(p, pn,uri) {
    //只有一页,直接显示1
    if (pn <= 1) {
        this.p = 1;
        this.pn = 1;
        return this.pHtml2(1);
    }
    if (pn < p) { p = pn; }
    var re = "";
    //第一页
    if (p <= 1) {
        p = 1;
    } else {
        //非第一页
        re += this.pHtml(p - 1, pn,uri, "shangyiye");
        //总是显示第一页页码
        re += this.pHtml(1, pn,uri, "1");
    }
    //校正页码
    this.p = p;
    this.pn = pn;

    //开始页码
    var start = 2;
    var end = (pn < 9) ? pn: 9;
    //是否显示前置省略号,即大于10的开始页码
    if (p >= 7) {
        re += "<span>...</span>";
        start = p - 3;
        var e = p + 3;
        end = (pn < e) ? pn: e;
    }
    for (var i = start; i < p; i++) {
        re += this.pHtml(i, pn,uri);
    }
    re += this.pHtml2(p);
    for (var i = p + 1; i <= end; i++) {
        re += this.pHtml(i, pn,uri);
    }
    if (end < pn) {
        re += "<span>...</span>";
        //显示最后一页页码,如不需要则去掉下面这一句
        re += this.pHtml(pn, pn,uri);
    }
    if (p < pn) {
        re += this.pHtml(p + 1, pn,uri, "xiayiye");
    }
    return re;
};
//显示非当前页
pageNav.pHtml = function(pageNo, pn,uri, showPageNo) {
    showPageNo = showPageNo || pageNo;
    if(showPageNo=="shangyiye"){
    	return "<a href='"+uri+ "&page="+pageNo+"'>上一页</a>";
    }
    if(showPageNo=="xiayiye"){
    	return "<a href='"+uri+ "&page="+pageNo+"'>下一页</a>";
    }
    return "<a href='"+uri+ "&page="+pageNo+"'>" + showPageNo + "</a>";
};
//显示当前页
pageNav.pHtml2 = function(pageNo) {
    //return "<span style='font-size: 18px;color: red;'>" + pageNo + "</span>";
    return "<a class='current'>" + pageNo + "</a>";
};
//输出页码,可根据需要重写此方法
pageNav.go = function(p, pn,uri) {
    document.getElementById("pageNav").innerHTML = this.nav(p, pn,uri);
    if (this.fn != null) {this.fn(this.p, this.pn,this.uri);}
};