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
        AudioAttributes audio = new AudioAttributes();
        Encoder encoder = new Encoder();

        audio.setCodec("libmp3lame");
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
        String sourcePath = "D:\\gitSpace\\Seework\\youyou_service\\WebRoot\\images\\file.mp3";
        String targetPath = "D:\\gitSpace\\Seework\\youyou_service\\WebRoot\\images\\file.wav";
        changeToWav(sourcePath,targetPath);
    }
}
