package com.yy.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class HanyuToPinyin {
	public static String getPinYin(String str) throws Exception {
		StringBuilder sb = new StringBuilder();
		char[] chars = str.toCharArray();
		HanyuPinyinOutputFormat hp = new HanyuPinyinOutputFormat();
		//hp.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		hp.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
		for (int i = 0; i < chars.length; i++) {
			String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(chars[i]);
			if (pinyin != null)
				sb.append(pinyin[0]);
			else
				sb.append(chars[i]);
		}
		return sb.toString();
	}
}
