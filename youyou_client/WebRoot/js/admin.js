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
var urlPath = "http://127.0.0.1:8080/youyou_client";
// var urlPath = "http://111.111.125.238:8080/youyou_client";
//var urlPath = "https://www.yoyotec.com/youyou_client";