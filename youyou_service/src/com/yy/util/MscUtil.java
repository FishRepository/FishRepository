package com.yy.util;

import com.iflytek.cloud.speech.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by qizhihang on 2017/12/23.
 */
public class MscUtil {
    private static final String APPID = "5a3ca8c7";

    private StringBuffer mResult = new StringBuffer();

    /** 最大等待时间， 单位ms */
    private int maxWaitTime = 500;
    /** 每次等待时间 */
    private int perWaitTime = 100;
    /** 出现异常时最多重复次数 */
    private int maxQueueTimes = 3;
    /** 音频文件 */
    private byte[] byteArr;

    static {
        Setting.setShowLog( false );
        SpeechUtility.createUtility("appid=" + APPID);
    }

    public String voice2words(byte[] byteArr) throws InterruptedException {
        return voice2words(byteArr, true);
    }

    /**
     *
     * @desc: 工具类，在应用中有一个实例即可， 但是该实例是有状态的， 因此要消除其他调用对状态的修改，所以提供一个init变量
     * @auth: zona
     * 2017年1月4日 下午4:38:45
     * @param byteArr
     * @param init 是否初始化最大等待时间。
     * @return
     * @throws InterruptedException
     */
    public String voice2words(byte[] byteArr, boolean init) throws InterruptedException {
        if(init) {
            maxWaitTime = 500;
            maxQueueTimes = 3;
        }
        if(maxQueueTimes <= 0) {
            mResult.setLength(0);
            mResult.append("解析异常！");
            return mResult.toString();
        }
        this.byteArr = byteArr;

        return recognize();
    }


    // *************************************音频流听写*************************************

    /**
     * 听写
     * @return
     * @throws InterruptedException
     */
    private String recognize() throws InterruptedException {
        if (SpeechRecognizer.getRecognizer() == null)
            SpeechRecognizer.createRecognizer();
        return RecognizePcmfileByte();
    }

    /**
     * 自动化测试注意要点 如果直接从音频文件识别，需要模拟真实的音速，防止音频队列的堵塞
     * @throws InterruptedException
     */
    private String RecognizePcmfileByte() throws InterruptedException {
        // 1、读取音频文件
//        FileInputStream fis = null;
        byte[] voiceBuffer = byteArr;
//        File file = new File(fileName);
//        try {
//            fis = new FileInputStream(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            byte[] b = new byte[1024];
//            int n;
//            while ((n = fis.read(b)) != -1)
//            {
//                bos.write(b, 0, n);
//            }
//            fis.close();
//            bos.close();
//            voiceBuffer = bos.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != fis) {
//                    fis.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        // 2、音频流听写
        if (0 == voiceBuffer.length) {
            mResult.append("no audio avaible!");
        } else {
            //解析之前将存出结果置为空
            mResult.setLength(0);
            SpeechRecognizer recognizer = SpeechRecognizer.getRecognizer();
            recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
            recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
            //写音频流时，文件是应用层已有的，不必再保存
//          recognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH,
//                  "./iflytek.pcm");
            recognizer.setParameter( SpeechConstant.RESULT_TYPE, "plain" );
            recognizer.startListening(recListener);
//            ArrayList<byte[]> buffers = splitBuffer(voiceBuffer,
//                    voiceBuffer.length, 4800);
//            for (int i = 0; i < buffers.size(); i++) {
                // 每次写入msc数据4.8K,相当150ms录音数据
            recognizer.writeAudio(byteArr, 0, byteArr.length);
//                try {
//                    Thread.sleep(150);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            recognizer.stopListening();

            // 在原有的代码基础上主要添加了这个while代码等待音频解析完成，recognizer.isListening()返回true，说明解析工作还在进行
            while(recognizer.isListening()) {
                if(maxWaitTime < 0) {
                    mResult.setLength(0);
                    mResult.append("解析超时！");
                    break;
                }
                Thread.sleep(perWaitTime);
                maxWaitTime -= perWaitTime;
            }
        }
        return mResult.toString();
    }

    /**
     * 将字节缓冲区按照固定大小进行分割成数组
     *
     * @param buffer
     *            缓冲区
     * @return
     */
    private static ArrayList<byte[]> splitBuffer(byte[] buffer){
        int index = 0;
        ArrayList<byte[]> arr = new ArrayList<>();
        byte[] byteArr = new byte[4800];
        for(int i=0;i<buffer.length;i++){
            byteArr[index] = buffer[i];
            index++;
            if(index==4799){
                index = 0;
                arr.add(byteArr);
                byteArr = new byte[4800];
            }
        }
        return arr;
    }

    /**
     * 听写监听器
     */
    private RecognizerListener recListener = new RecognizerListener() {

        public void onBeginOfSpeech() { }

        public void onEndOfSpeech() { }

        public void onVolumeChanged(int volume) { }

        public void onResult(RecognizerResult result, boolean islast) {
            mResult.append(result.getResultString());
        }

        public void onError(SpeechError error) {
            try {
                voice2words(byteArr);
                maxQueueTimes--;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        public void onEvent(int eventType, int arg1, int agr2, String msg) { }

    };

    public static void main(String[] args) {
        String fileName = "class_01.mp3";
        String realPath = "D:\\gitSpace\\Seework\\youyou_service\\WebRoot\\images\\";
        File file = new File(realPath,fileName);
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MscUtil msc = new MscUtil();
        StringBuffer sb = new StringBuffer("");
        try {
            ArrayList<byte[]> buffers = splitBuffer(buffer);

//            for(int i=0;i<buffers.size();i++){
                sb.append(msc.voice2words(buffers.get(0)));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.printf("1111: "+ sb.toString());
    }
}
