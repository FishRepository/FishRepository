$(function(){
    var _upFile=document.getElementById("upFile");

    _upFile.addEventListener("change",function(){
  
    if (_upFile.files.length === 0) {  
        alert("请选择图片");  
        return; }  
    var oFile = _upFile.files[0]; 
    //if (!rFilter.test(oFile.type)) { alert("You must select a valid image file!"); return; }  
  
    /*  if(oFile.size>5*1024*1024){  
     message(myCache.par.lang,{cn:"照片上传：文件不能超过5MB!请使用容量更小的照片。",en:"证书上传：文件不能超过100K!"})  
     
     return;  
     }*/  
    if(!new RegExp("(jpg|jpeg|png)+","gi").test(oFile.type)){  
        alert("照片上传：文件类型必须是JPG、JPEG、PNG");  
        return;  
    }
  
            var reader = new FileReader();  
            reader.onload = function(e) {  
                var base64Img= e.target.result;
                //压缩前预览
                $("#preview").attr("src",base64Img);  
  
                //--执行resize。  
                var _ir=ImageResizer({  
                        resizeMode:"auto"  
                        ,dataSource:base64Img  
                        ,dataSourceType:"base64"  
                        ,maxWidth:1200 //允许的最大宽度  
                        ,maxHeight:600 //允许的最大高度。  
                        ,onTmpImgGenerate:function(img){  
  
                        }  
                        ,success:function(resizeImgBase64,canvas){
                            //压缩后预览
                            $("#nextview").attr("src",resizeImgBase64); 

                            //赋值到隐藏域传给后台
                            $('#imgOne').val(resizeImgBase64.substr(22));

                            $('#addImage').on('click',function(){
                            	var topic_id = $(this).attr("data-id");
                            	var topic_pay = $(this).attr("data-pay");
                                //alert('传给后台base64文件数据为：'+resizeImgBase64.substr(22));
                              $.ajax({
                                  url:urlPath+"/admin.do?method=updateTopicImage",
                                  type:'POST',
                                  dataType:'json',
                                  data:{
                                  	topicId:topic_id,
                                  	topicPay:topic_pay,
                                    topicImage:resizeImgBase64.substr(23)
                                  },
                                  async:true,
                                  success:function(data){
                                      if(data.RESULT=="SUCCESS"){
                                    	  alert("添加成功！");
                                    	  location.reload();
                                      }
                                  }
                              });
                            });
                            
                            $("#addAdvBtn").on('click', function(){
                            	var name = $("#advName").val();
                            	if(name==""){
                            		alert("请填写广告名称")
                            	}else{
                            		$.ajax({
	                                    url:urlPath+"/admin.do?method=updataAdv",
	                                    type:'POST',
	                                    dataType:'json',
	                                    data:{
	                                    	advId:3,
	                                    	advName:name,
	                                    	advImage:resizeImgBase64.substr(23)
	                                    },
	                                    async:true,
	                                    success:function(data){
	                                        if(data.RESULT=="SUCCESS"){
	                                      	  alert("添加成功！");
	                                      	  //location.reload();
	                                        }
	                                    }
	                                });
                            	}
                            });
                            
                            $('#addImage1').on('click',function(){
                            	var topic_id = $(this).attr("data-id");
                            	var topic_pay = $(this).attr("data-pay");
                                //alert('传给后台base64文件数据为：'+resizeImgBase64.substr(22));
                              $.ajax({
                                  url:urlPath+"/admin.do?method=updateSectionTopicImage",
                                  type:'POST',
                                  dataType:'json',
                                  data:{
                                  	topicId:topic_id,
                                  	topicPay:topic_pay,
                                    topicImage:resizeImgBase64.substr(23)
                                  },
                                  async:true,
                                  success:function(data){
                                      if(data.RESULT=="SUCCESS"){
                                    	  alert("添加成功！");
                                    	  location.reload();
                                      }
                                  }
                              });
                            });
                            
                            $("#addAdvInfoBtn").on('click', function(){
                            	$.ajax({
                                    url:urlPath+"/admin.do?method=updateAdvInfo",
                                    type:'POST',
                                    dataType:'json',
                                    data:{
                                    	advId:1,
                                    	advImage:resizeImgBase64.substr(23)
                                    },
                                    async:true,
                                    success:function(data){
                                        if(data.RESULT=="SUCCESS"){
                                      	  alert("添加成功！");
                                      	  //location.reload();
                                        }
                                    }
                                });
                            });

                            $('#addImageEgPic').on('click',function(){
                                var topic_id = $(this).attr("data-id");
                                //alert('传给后台base64文件数据为：'+resizeImgBase64.substr(22));
                                $.ajax({
                                    url:urlPath+"/admin.do?method=updateEnglishClassPic",
                                    type:'POST',
                                    dataType:'json',
                                    data:{
                                        topicId:topic_id,
                                        topicImage:resizeImgBase64.substr(23)
                                    },
                                    async:true,
                                    success:function(data){
                                        if(data.RESULT=="SUCCESS"){
                                            $("#upFile").val('');
                                            $("#nextview").attr("src",'');
                                            $("#myModalPic").modal('hide');
                                            refleshTable();
                                            alert("添加成功！");
                                        }
                                    }
                                });
                            });
  
                        }  
                        ,debug:true  
                });  
  
            };  
            reader.readAsDataURL(oFile);  
  
    },false);  

});