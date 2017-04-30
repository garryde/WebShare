package com.share.service;

import com.share.domain.IsUrlResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GarryChung on 2017/4/30.
 */
public class UrlJudge {
    public IsUrlResult isUrl(String url) {
        IsUrlResult isUrlResult = new IsUrlResult();

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
        Matcher matcher = p.matcher(url);
        boolean result1 = matcher.matches();

        if (!result1) {
            url = "http://" + url;
        }

        Pattern p2 = Pattern.compile(regEx2);
        Matcher matcher2 = p2.matcher(url);
        boolean result2 = matcher2.matches();

        isUrlResult.setResult(result2);
        isUrlResult.setUrl(url);
        return isUrlResult;
    }
}
