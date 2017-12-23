package com.yy.util;

import com.iflytek.cloud.speech.*;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qizhihang on 2017/12/22.
 */
public class VoiceUtil {

    private static Logger log = Logger.getLogger(VoiceUtil.class);

    private boolean mIsEndOfSpeech = false;

    private static StringBuffer mResult = new StringBuffer();

    private static SpeechRecognizer speechRecognizer;

    private static SpeechRecognizer getInstance(){
        if(speechRecognizer==null){
            speechRecognizer = SpeechRecognizer.createRecognizer();
            SpeechUtility.createUtility("appid=5a3ca8c7");
//            speechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
//            speechRecognizer.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
            speechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
            speechRecognizer.setParameter(SpeechConstant.DOMAIN, "iat");
            speechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//en_us
            speechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
            speechRecognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        }
        return speechRecognizer;
    }

    public Map<String, Object> voice2word(byte[] voiceBuffer){
        Map<String, Object> resultMap = new HashMap<>();
        speechRecognizer = getInstance();
        speechRecognizer.startListening(recListener);
        speechRecognizer.writeAudio(voiceBuffer, 0, voiceBuffer.length);
        speechRecognizer.stopListening();
        resultMap.put("result",mResult.toString());
        return resultMap;
    }

    public String voice2wordStr(byte[] voiceBuffer){
        speechRecognizer = getInstance();
        speechRecognizer.startListening(recListener);
        speechRecognizer.writeAudio(voiceBuffer, 0, voiceBuffer.length);
        speechRecognizer.stopListening();
        if(null!=mResult){
            return mResult.toString();
        }
        return "";
    }

    public static SpeechRecognizer getSpeechRecognizer() {
        return speechRecognizer;
    }

    public void setSpeechRecognizer(SpeechRecognizer speechRecognizer) {
        this.speechRecognizer = speechRecognizer;
    }

    /**
     * 听写监听器
     */
    private RecognizerListener recListener = new RecognizerListener() {

        public void onBeginOfSpeech() {
            System.out.printf("onBeginOfSpeech enter");
            System.out.printf("*************开始录音*************");
            mResult = new StringBuffer("");
        }

        public void onEndOfSpeech() {
            System.out.printf("onEndOfSpeech enter");
            mIsEndOfSpeech = true;
        }

        public void onVolumeChanged(int volume) {
            System.out.printf("onVolumeChanged enter");
            if (volume > 0)
                System.out.printf("*************音量值:" + volume + "*************");

        }

        public void onResult(RecognizerResult result, boolean islast) {
            System.out.printf("onResult enter");
            mResult.append(JSONUtil.toJsonString(result.getResultString()));

            if( islast ){
                System.out.printf("识别结果为######:" + mResult.toString());
                mIsEndOfSpeech = true;
//                mResult.delete(0, mResult.length());
                waitupLoop();
            }
        }

        public void onError(SpeechError error) {
            mIsEndOfSpeech = true;
            System.out.printf("错误*************" + error.getErrorCode()
                    + "*************");
            waitupLoop();
        }

        public void onEvent(int eventType, int arg1, int agr2, String msg) {
            System.out.printf("onEvent enter");
        }

    };

    private void waitupLoop(){
        synchronized(this){
            VoiceUtil.this.notify();
        }
    }

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
        ArrayList<byte[]> buffers = splitBuffer(buffer);
        StringBuffer sb = new StringBuffer("");
        for(int i=0;i<buffers.size();i++){
            VoiceUtil voiceUtil = new VoiceUtil();
            sb.append(voiceUtil.voice2wordStr(buffers.get(i)));
        }
        System.out.printf("识别结果:"+sb);
    }
}
