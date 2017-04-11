package com.github2013.ml.utils;

/**
 * Created by shuangfu on 17-4-1.
 * Author : DRUNK
 * email :len1988.zhang@gmail.com
 */
public class SegmentUtil {
    public static String[] run(String sentence) {
        return sentence.toLowerCase()
                .replaceAll(",|\\.|\\?|!|@|#|%", " ")
                .split("\\s+");
    }
}
