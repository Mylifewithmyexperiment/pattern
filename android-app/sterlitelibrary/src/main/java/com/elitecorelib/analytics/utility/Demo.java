package com.elitecorelib.analytics.utility;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {


    public static void main(String arg[]) {
        String msg = "fail:1,success:1,policyfetch:1";
        System.out.println(getKeyValuesFromMsg(msg));
    }

    public static Map getKeyValuesFromMsg(String str) {
        Map map = new HashMap();
        String[] keyValueParts = str.split(",");
        for (String s : keyValueParts) {
            String parts[] = s.split(":");
            map.put(parts[0], parts[1]);
        }
        return map;

    }
}