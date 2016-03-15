package com.common.util;

public class StringUtil {

    /**获取字符串中某个字符串出现的次数。*/
    public static int countMatches(String res, String findString) {
        if (res == null) {
            return 0;
        }

        if (findString == null || findString.length() == 0) {
            throw new IllegalArgumentException("The param findString cannot be null or 0 length.");
        }

        return (res.length() - res.replace(findString, "").length()) / findString.length();
    }
}
