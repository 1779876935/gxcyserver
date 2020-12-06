package com.gxcy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gxcy.service.WxpayService;
import com.gxcy.utils.AesCbcUtil;
import com.gxcy.utils.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WxPayServiceImpl implements WxpayService {
    private static Logger logger = LoggerFactory.getLogger(WxPayServiceImpl.class);

    // 小程序唯一标识 (在微信小程序管理后台获取)
    final String wxspAppid = "wx7d7900c4f6e6f09c";
    // 小程序的 app secret (在微信小程序管理后台获取)
    final String wxspSecret = "3bf4648c1554684716abf617243b9e63";
    // 授权（必填）
    final String grant_type = "authorization_code";

    @Override
    public Map<String, Object> wxlogin(String encryptedData, String iv, String code) {
        //  1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        Map map = new HashMap();
        // 请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type="
                + grant_type;
        logger.info("请求参数:"+params);
        // 发送请求
        String sr = HttpRequestUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        // 解析相应内容（转换成json对象）
        JSONObject json = JSON.parseObject(sr);
        logger.info("结果："+json);
        // 获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        // 用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        // 2、对encryptedData加密数据进行AES解密
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");

                JSONObject userInfoJSON = JSON.parseObject(result);
                Map userInfo = new HashMap();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                // 解密unionId & openId;

                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);
            } else {
                map.put("status", 0);
                map.put("msg", "解密失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> saveGxUser() {
        return null;
    }
}