package com.yy.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class AudioUploadUtil {

    public static String storeAudio(String path, String urlPath,MultipartFile audio) throws IOException {
        hasFile(path);
        String y = Calendar.getInstance().get(Calendar.YEAR)+"";
        String m = Calendar.getInstance().get(Calendar.MONTH)+ 1 +"";
        String timeString = y+m;
        path += timeString + "/";
        hasFile(path);
        String pathString = null;
        String fileName=audio.getOriginalFilename();
        String fileTyle=fileName.substring(fileName.lastIndexOf("."),fileName.length());
        String newfileName = new Date().getTime() + fileTyle;
        FileOutputStream os = new FileOutputStream(path + newfileName);
        os.write(audio.getBytes());
        os.flush();
        os.close();
        pathString = urlPath + timeString + "/" +newfileName;
        return pathString;
    }

    private static boolean hasFile(String filename){
        File file =new File(filename);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        return true;
    }
}
