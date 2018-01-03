package com.yy.util;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

public class SaveFileFromWX {

    private Logger logger = Logger.getLogger(SaveFileFromWX.class);

    public boolean saveFile(HttpServletRequest request,CommonsMultipartFile mFile){
        String fileNameFromWX = request.getParameter("fileName");
        //获取文件需要上传到的路径
        String path = request.getSession().getServletContext().getRealPath("")+"\\preASRVoice\\";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        logger.debug("path=" + path);

        try {
            request.setCharacterEncoding("utf-8");  //设置编码
        } catch (UnsupportedEncodingException e) {
            logger.error("服务端异常"+e.getMessage());
        }
        //自定义上传图片的名字为userId.jpg
        String fileName = fileNameFromWX;
        String destPath = path + fileName;
        logger.debug("destPath=" + destPath);

        try {
            //真正写到磁盘上
            File file = new File(destPath);
            OutputStream out = new FileOutputStream(file);
            DiskFileItem fileItem = (DiskFileItem) mFile.getFileItem();
            if(fileItem.getSize()<=0){
                return false;
            }
            InputStream in = fileItem.getInputStream();
            int length = 0;
            byte[] buf = new byte[1024];
            // in.read(buf) 每次读到的数据存放在buf 数组中
            while ((length = in.read(buf)) != -1) {
                //在buf数组中取出数据写到（输出流）磁盘上
                out.write(buf, 0, length);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            logger.error("录音文件上传出错"+e.getMessage());
            return false;
        }
        return true;
    }
}
