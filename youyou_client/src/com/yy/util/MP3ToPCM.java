package com.yy.util;


import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
/**
 * MP3转PCM Java方式实现
 * @author 小帅丶
 * @date 2017年12月6日
 */
public class MP3ToPCM {

    /**
     * MP3转换PCM文件方法
     *
     * @param mp3filepath
     *            原始文件路径
     * @param pcmfilepath
     *            转换文件的保存路径
     * @throws Exception
     */
    public static void convertMP32PCM(String mp3filepath, String pcmfilepath) throws Exception {
        AudioInputStream audioInputStream = getPcmAudioInputStream(mp3filepath);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(pcmfilepath));
    }

    private static AudioInputStream getPcmAudioInputStream(String mp3filepath) {
        File mp3 = new File(mp3filepath);
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(mp3);
            AudioFormat baseFormat = in.getFormat();
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }

    public static void main(String[] args) {
        String mp3filepath = "D:\\gitSpace\\Seework\\youyou_client\\WebRoot\\preASRVoice\\fileoF5Qa0bk5EjjNVFwLAf3ggTtT8mM.mp3";
        String pcmfilepath = "D:\\gitSpace\\Seework\\youyou_client\\WebRoot\\preASRVoice\\fileoF5Qa0bk5EjjNVFwLAf3ggTtT8mM.pcm";
        try {
            convertMP32PCM(mp3filepath,pcmfilepath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}