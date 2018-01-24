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

    public static void main(String[] args) {
        String sourcePath = "D:\\gitSpace\\Seework\\youyou_client\\WebRoot\\preASRVoice\\fileoF5Qa0bk5EjjNVFwLAf3ggTtT8mM.mp3";
        String targetPath = "D:\\gitSpace\\Seework\\youyou_client\\WebRoot\\preASRVoice\\fileoF5Qa0bk5EjjNVFwLAf3ggTtT8mM.wav";
        changeToWav(sourcePath,targetPath);
    }
}
