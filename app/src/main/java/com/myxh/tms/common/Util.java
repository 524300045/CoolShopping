package com.myxh.tms.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
   // 可以通过修改正则表达式实现校验负数，将正则表达式修改为“^-?[0-9]+”即可，修改为“-?[0-9]+.?[0-9]+”即可匹配所有数字。

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

}
