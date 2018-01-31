package com.yy.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class RASUtil {

    private static final String PERFECT = "perfect";

    private static final String GREAT = "great";

    private static final String GOOD = "good";

    private static final String NORMAL = "normal";

    private static final String BAD = "bad";

    private static Logger logger = Logger.getLogger(RASUtil.class);

    public static Map<String, Object> englishRSA(Map<String, Object> parm){
        Map<String, Object> resultMap = new HashMap<>();
        String RSAResult = BAD;
        String RSAStr = "";
        double sim = 0;
        try {
            String filePath = parm.get("filePath").toString();
            String preContext = parm.get("preContext").toString();
            String preRSAPath = filePath.substring(0, filePath.indexOf('.') + 1) + "wav";
            System.out.println("##############preRSAPath="+preRSAPath);
            ConvertAudio.changeToWav(filePath,preRSAPath);
            //================================测试S
            preRSAPath = "D:\\test.wav";
            //================================测试E
            SRTool sr = new SRTool();
            RSAStr = sr.voice2words(preRSAPath);
            if(StringUtils.isNotBlank(RSAStr)){
                RSAStr = sr.sr2words(RSAStr);
                sim = sim(preContext, RSAStr);
                DecimalFormat df = new DecimalFormat("#.00");
                sim = Double.parseDouble(df.format(sim));
                if(sim >= 0.9){
                    RSAResult = PERFECT;
                }else if(sim >= 0.8){
                    RSAResult = GREAT;
                }else if(sim >= 0.7){
                    RSAResult = GOOD;
                }else if(sim >= 0.6){
                    RSAResult = NORMAL;
                }else if(sim < 0.6){
                    RSAResult = BAD;
                }
                sim = sim*100;
            }
        } catch (Exception e) {
            logger.error("语音识别错误: "+ e.getMessage());
        } finally {
            resultMap.put("RSAResult",RSAResult);
            resultMap.put("RSAStr",RSAStr);
            resultMap.put("sim",(int)sim);
        }
        System.out.println("############## RSAStr="+RSAStr);
        System.out.println("############## RSAResult="+RSAResult);
        return resultMap;
    }

    private static int min(int one, int two, int three) {
        int min = one;
        if (two < min) {
            min = two;
        }
        if (three < min) {
            min = three;
        }
        return min;
    }

    private static int ld(String str1, String str2) {
        int d[][]; // 矩阵
        int n = str1.length();
        int m = str2.length();
        int i; // 遍历str1的
        int j; // 遍历str2的
        char ch1; // str1的
        char ch2; // str2的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) { // 遍历str1
            ch1 = str1.charAt(i - 1);
            // 去匹配str2
            for (j = 1; j <= m; j++) {
                ch2 = str2.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1]+ temp);
            }
        }
        return d[n][m];
    }
    private static double sim(String str1, String str2) {
        try {
            double ld = (double)ld(str1, str2);
            return (1-ld/(double)Math.max(str1.length(), str2.length()));
        } catch (Exception e) {
            return 0.1;
        }
    }

    public static void main(String[] args) {
        String str1 = "my name is zhangsan";
        String str2 = "my room fuck is zhangsan";
        System.out.println("ld=" + ld(str1, str2));
        System.out.println("sim=" + sim(str2, str1));
    }
}
