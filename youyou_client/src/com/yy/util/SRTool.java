package com.yy.util;

import com.alibaba.fastjson.JSON;
import com.iflytek.cloud.speech.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qizhihang on 2018/1/30.
 */
public class SRTool {
    private int perWaitTime = 100;

    private StringBuffer mResult = new StringBuffer();

    static {
        //qizhihang  5a6eb986
        //海证  5a76b90a
        SpeechUtility.createUtility("appid=5a6eb986");//申请的appid
    }

    public String voice2words(String fileName) throws InterruptedException, IOException {
        return to(fileName);
    }

    public String to(String fileName) throws InterruptedException, IOException {

        File file = new File(fileName);
        if(!file.exists()){
            throw new RuntimeException("要读取的文件不存在");
        }
        FileInputStream fis = new FileInputStream(file);
        int len = 0;
        byte[] buf = new byte[fis.available()];
        fis.read(buf);
        fis.close();

        //1.创建SpeechRecognizer对象
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer();
        //2.设置听写参数，详见《MSC Reference Manual》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        //3.开始听写
        mIat.startListening(mRecoListener);

        //voiceBuffer为音频数据流，splitBuffer为自定义分割接口，将其以4.8k字节分割成数组
        ArrayList<byte[]> buffers = splitBuffer(buf, buf.length, 4800);
        for (int i = 0; i < buffers.size(); i++) {
            // 每次写入msc数据4.8K,相当150ms录音数据
            mIat.writeAudio(buffers.get(i), 0, buffers.get(i).length);
        }
        mIat.stopListening();

        while (mIat.isListening()) {
            Thread.sleep(perWaitTime);
        }
        return mResult+"";
    }

    /**
     * 将字节缓冲区按照固定大小进行分割成数组
     *
     * @param buffer 缓冲区
     * @param length 缓冲区大小
     * @param spsize 切割块大小
     * @return
     */
    private ArrayList<byte[]> splitBuffer(byte[] buffer, int length, int spsize) {
        ArrayList<byte[]> array = new ArrayList<byte[]>();
        if (spsize <= 0 || length <= 0 || buffer == null
                || buffer.length < length)
            return array;
        int size = 0;
        while (size < length) {
            int left = length - size;
            if (spsize < left) {
                byte[] sdata = new byte[spsize];
                System.arraycopy(buffer, size, sdata, 0, spsize);
                array.add(sdata);
                size += spsize;
            } else {
                byte[] sdata = new byte[left];
                System.arraycopy(buffer, size, sdata, 0, left);
                array.add(sdata);
                size += left;
            }
        }
        return array;
    }

    //听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            System.out.println("Result:" + results.getResultString());
            mResult.append(results.getResultString());
        }

        //会话发生错误回调接口
        public void onError(SpeechError error) {
            System.out.println(error.getErrorCode()+"=========="+error.getErrorDesc());
            System.out.println(error);
        }

        //开始录音
        public void onBeginOfSpeech() {
        }

        //音量值0~30
        public void onVolumeChange(int volume) {
        }

        @Override
        public void onVolumeChanged(int i) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onEvent(int i, int i1, int i2, String s) {

        }
    };

    public static String sr2words(String jsonString){
        StringBuffer sb = new StringBuffer();
        String[] split = jsonString.split("}]}]}");
        for (int i = 0; i < split.length; i++) {
            String s = split[i] + "}]}]}";
            System.out.println(s);
            Map parse = (Map) JSON.parse(s);
            List<Map> ws = (List<Map>) parse.get("ws");
            for (int i1 = 0; i1 < ws.size(); i1++) {
                List<Map> cw = (List<Map>)ws.get(i1).get("cw");
                String w = cw.get(0).get("w").toString();
                sb.append(w);
            }

        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String WavFilePath = "D:\\321321.wav";
        HashMap<String, Object> map = new HashMap<>();
        SRTool sr = new SRTool();
        String words = null;
        try {
            words = sr.voice2words(WavFilePath);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("讯飞识别的语音结果："+words);
        if("".equals(words)){
            System.out.println("讯飞识别的语音结果：null");
            map.put("status","error");
            map.put("content","对不起，请您在描述一遍！");
        }
        String result = sr2words(words);
        System.out.println("讯飞识别的语音结果："+result);
        map.put("status","success");
        map.put("content",result);
        System.out.println(map.toString());
    }
}
