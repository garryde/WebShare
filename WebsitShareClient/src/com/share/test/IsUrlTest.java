package com.share.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GarryChung on 2017/4/29.
 */

public class IsUrlTest {
    public static void main(String[] args) {
        isUrl("www.baidu.com:8080/adc");

    }

    /**
     * URL检查<br>
     * <br>
     * @param pInput     要检查的字符串<br>
     * @return boolean   返回检查结果<br>
     */
    public static boolean isUrl (String pInput) {
        if(pInput == null){
            return false;
        }
        String regEx1 = "^(http|https|ftp)://.*";

        String regEx2 = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"
                + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
                + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
                + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"
                + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"
                + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
                + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"
                + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";

        Pattern p = Pattern.compile(regEx1);
        Matcher matcher = p.matcher(pInput);
        boolean result1 = matcher.matches();
        System.out.println(pInput);
        System.out.println(result1);

        if (!result1) {
            pInput = "http://" + pInput;
            System.out.println(pInput);
        }

        Pattern p2 = Pattern.compile(regEx2);
        Matcher matcher2 = p2.matcher(pInput);
        boolean result2 = matcher2.matches();

        System.out.println(result2);

        return result2;
    }
}
