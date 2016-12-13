package com.qunar.tools.dubbo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * since 2016/11/29.
 */
public class RegexUtil {

    private static Pattern pattern = Pattern.compile("^\\$\\{([\\w\\.]+)\\}");

    public static String parseGroup(String group) {
        final Matcher matcher = pattern.matcher(group);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return group;
    }
}
