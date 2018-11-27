package com.xedflix.video.utils;

/**
 * Author: Mohamed Saleem
 */
public class StringUtils {
    public static String replaceLast(String string, String toReplace, String with) {
        int index = string.lastIndexOf(toReplace);
        if(index == -1) {
            return string;
        }
        return new StringBuilder(string).replace(index, index + toReplace.length(), with).toString();
    }
}
