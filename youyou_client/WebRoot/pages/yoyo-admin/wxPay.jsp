<%--
  Created by IntelliJ IDEA.
  User: qizhihang
  Date: 2018/6/5
  Time: 下午4:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String msg = request.getAttribute("msg").toString();
    String title = "";
    String pay = "";
    String openid = "";
    if(!msg.contains("网络异常")){
        title = request.getAttribute("title").toString();
        pay = request.getAttribute("pay").toString();
        openid = request.getAttribute("openid").toString();
    }
%>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta http-equiv="expires" content="0">

    <script src="<%=path%>/js/jquery-1.11.2.min.js"></script>
    <script src="<%=path%>/js/bootstrap.min.js"></script>
    <script src="<%=path%>/js/bootbox.min.js"></script>
    <script type="text/javascript" charset="utf8" src="<%=path%>/js/jquery.dataTables.min.js"></script>
    <link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"/>
    <%--<style type="text/css">--%>
        <%--.main{--%>
            <%--height: 100%;--%>
            <%--background: #eee;--%>
        <%--}--%>
        <%--.msg{--%>
            <%--text-align: center;--%>
            <%--margin: 10rem 0;--%>
            <%--position: fixed;--%>
            <%--top: 0;--%>
            <%--z-index: 2000;--%>
        <%--}--%>
        <%--.button{--%>
            <%--text-align: center;--%>
            <%--vertical-align: middle;--%>
            <%--height: 100%;--%>
            <%--margin: 30rem 0;--%>
        <%--}--%>
        <%--.bigbtn{--%>
            <%--height: 4rem;--%>
            <%--font-size: 2.5rem;--%>
            <%--align-items: center;--%>
            <%--text-align: center;--%>
            <%--display: flex;--%>
            <%--justify-items: center;--%>
            <%--margin: auto;--%>
            <%--padding-left: 2em;--%>
            <%--padding-right: 2em;--%>
        <%--}--%>
        <%--.foot{--%>
            <%--color: #9d9d9d;--%>
        <%--}--%>
    <%--</style>--%>
</head>

<body>
    <input type="hidden" id="title" value="<%=title%>"/>
    <input type="hidden" id="pay" value="<%=pay%>"/>
    <input type="hidden" id="openid" value="<%=openid%>"/>
    <%--<div class="container-fluid main">--%>
        <%--<div class="row msg">--%>
            <%--<h4><%=msg%></h4>--%>
        <%--</div>--%>
        <%--<div class="row button">--%>
            <%--<button type="button" class="btn btn-success bigbtn">确认支付</button>--%>
        <%--</div>--%>
        <%--<div class="row navbar-fixed-bottom text-center foot">--%>
            <%--Yoyo-游友科技--%>
        <%--</div>--%>
    <%--</div>--%>

    <div class="container" style="background-color: #eee;height: 100%;">
        <div class="caption">
            <form class="form-horizontal">
                <div class="input-group text-center">
                    <label class="msg" style="width: 100%;margin-top: 5rem;padding: 2rem;"><h4><%=msg%></h4></label>

                    <button class="btn btn-lg btn-success bigbtn" style="margin-top: 15rem;padding: 1rem 5rem;">确认支付</button>
                </div>
            </form>
            <h5 class="text-center" style="position: fixed;bottom: 0;width: 100%;">Yoyo-游友科技</h5>
        </div>
    </div>

    <script type="text/javascript">
        $(function(){
          var title = $('.msg').find('h4').text();
          console.log(title);
          if(title.indexOf('网络异常')>-1){
              $('.button').hide();
          }
        });

        //请求后台生成预支付链接
        $('.bigbtn').click(function (){
            $.ajax({
                type:"POST",
                data:{
                    payClass: $('#title').val(),
                    payMoney: $('#pay').val(),
                    openid: $('#openid').val()
                },
                url:"/youyou_client/admin.do?method=payH5Money",
                async:true,
                success:function(d){
                    console.log(d);
                }
            })
        });
    </script>
</body>
</html>
