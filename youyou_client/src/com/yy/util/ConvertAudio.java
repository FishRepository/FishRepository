package com.yy.util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;

public class ConvertAudio {

    public static void changeToWav(String sourcePath, String targetPath) {
        File source = new File(sourcePath);
        File target = new File(targetPath);
        Encoder encoder = new Encoder();
        AudioAttributes audio = new AudioAttributes();
        //libmp3lame pcm_s16le
        audio.setCodec("pcm_s16le");
        audio.setBitRate(96000);
        audio.setChannels(1);
        audio.setSamplingRate(16000);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("wav");
        attrs.setAudioAttributes(audio);
        try {
            encoder.encode(source, target, attrs);
        } catch (IllegalArgumentException | EncoderException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将上传的录音转为mp3格式
     * @param ffmpegPath 项目的根目录
     * @param sourcePath 文件的相对地址
     */
    public static void toWav(String ffmpegPath, String sourcePath, String targetPath){
        //File file = new File(sourcePath);
        Runtime run = null;
        try {
            run = Runtime.getRuntime();
            long start=System.currentTimeMillis();
            Process p=run.exec(ffmpegPath+"ffmpeg -i "+sourcePath+" -acodec pcm_s16le -ac 1 -ar 16000 "+targetPath);//执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
            //释放进程
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();
            long end=System.currentTimeMillis();
            System.out.println(sourcePath+" convert success, costs:"+(end-start)+"ms");
            //删除原来的文件
            //if(file.exists()){
            //file.delete();
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //run调用lame解码器最后释放内存
            run.freeMemory();
        }
    }

    public static void main(String[] args) {
        String ffmpegPath = "D:\\gitSpace\\Seework\\youyou_client\\";
        String sourcePath = "C:\\Users\\qizhihang\\Desktop\\tmp\\class_01.mp3";
        String targetPath = "C:\\Users\\qizhihang\\Desktop\\tmp\\class_01.wav";
        changeToWav(sourcePath,targetPath);
    }
}
