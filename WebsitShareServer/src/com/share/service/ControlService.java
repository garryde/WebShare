package com.share.service;

import com.share.domain.IsUrlResult;

/**
 * Created by GarryChung on 2017/4/30.
 */
public class ControlService {
    JsonService jsonService = new JsonService();
    UrlJudge urlJudge = new UrlJudge();
    IsUrlResult isUrlResult = new IsUrlResult();

    public String handle (String url, String code) {
        if (url == null || code == null || url.equals("") || code.equals("")) {
            return jsonService.handle("参数非法");
        } else {
            isUrlResult = urlJudge.isUrl(url);
            if (!isUrlResult.getResult()) {
                return jsonService.handle("URL非法");
            }

            SendUrlService sendUrlService = new SendUrlService(isUrlResult.getUrl(), code);
            boolean result = sendUrlService.send();

            if (result) {
                return jsonService.handle("发送成功");
            } else {
                return jsonService.handle("发送失败");
            }
        }
    }
}
