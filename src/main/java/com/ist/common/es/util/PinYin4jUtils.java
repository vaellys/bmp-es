package com.ist.common.es.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * @author qianguobing
 *
 */
public class PinYin4jUtils {
    /**
     * 将汉字转换为全拼
     * 
     * @param src
     * @return String
     */
    public static String chinieseToPinyin(String src) {
        char[] data = null;
        data = src.toCharArray();
        String[] joinTones = new String[data.length];
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < data.length; i++) {
                // 判断是否为汉字字符
                if (Character.toString(data[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    joinTones = PinyinHelper.toHanyuPinyinStringArray(data[i], format);// 将汉字的几种全拼都存到joinTones数组中
                    sb.append(joinTones[0]);// 取出该汉字全拼的第一种读音并连接到字符串sb后
                } else {
                    // 如果不是汉字字符，间接取出字符并连接到字符串sb后
                    sb.append(Character.toString(data[i]));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 提取每个汉字的首字母
     * 
     * @param str
     * @return String
     */
    public static String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母

            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 将字符串转换成ASCII码
     * 
     * @param cnStr
     * @return String
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        // 将字符串转换成字节序列

        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            // 将每个字符转换成ASCII码

            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

    public static void main(String[] args) {
        String cnStr = "我24岁，java重生，长大!";
        System.out.println(chinieseToPinyin(cnStr));
        System.out.println(getPinYinHeadChar(cnStr));
        System.out.println(getCnASCII(cnStr));
    }
}
