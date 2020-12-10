package com.gxcy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.gxcy.entity.ResultBean;
import com.gxcy.utils.AesCbcUtil;
import com.gxcy.utils.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序登录控制层入口
 *
 * 1.前端需先调用官方wx.login接口获取登录凭证code。
 * 2.后端接收code 调用官方接口地址获取用户秘钥 sessionKey。
 * 3.前端通过官方getPhoneNumber获取encryptedData，iv
 * 4.前端通过参数 **【encryptedData】 、【iv】 、【sessionKey】** 发送请求后端接口，解密用户手机号
 */


@RestController
@RequestMapping("/wxLogin")
public class WxLoginController {

    private static final Logger logger = LoggerFactory.getLogger(WxLoginController.class);

    //微信小程序appId
    @Value("${wxspAppId}")
    private String wxspAppId;

    //微信小程序secret
    @Value("${wxspSecret}")
    private String wxspSecret;

    //授权类型，此处只需填写 authorization_code
    private final String grant_type = "authorization_code";

    /**
     * 请求授权登录
     * @param js_code
     * @return
     * @throws JSONException
     */
    @PostMapping("/getCode")
    public ResultBean initWxLogin(@RequestParam(value = "js_code", required = true) String js_code) throws JSONException {

        logger.info("请求授权登录入口");

        //微信获取session_key接口地址
        String wxLoginUrl = "https://api.weixin.qq.com/sns/jscode2session";
        logger.info("请求授权URL：{}", wxLoginUrl);

        //接口参数
        String params = "appid=" + wxspAppId + "&secret=" + wxspSecret + "&js_code=" + js_code + "&grant_type=" + grant_type;
        logger.info("请求授权参数：{}", params);

        //调用获取session_key接口 请求方式get
        String jsonString = HttpRequestUtil.sendGet(wxLoginUrl, params);
        logger.info("请求授权返回报文：{}", jsonString);

        //因为jsonString字符串是大括号包围，所以用JSONObject解析
        JSONObject json = JSONObject.parseObject(jsonString);
        logger.info("解析报文：{}", json);

        logger.info("请求授权登录成功");
        //返回给前端小程序
        return ResultBean.success("SUCCESS", json);
    }


    /**
     * 解密小程序用户敏感数据
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @param sessionKey 用户秘钥
     * @return
     * @throws JSONException
     */
    @PostMapping(value = "/getUserInfo")
    public ResultBean decodeUserInfo(@RequestParam(required = true, value = "encryptedData") String encryptedData,
                                     @RequestParam(required = true, value = "iv") String iv,
                                     @RequestParam(required = true, value = "sessionKey") String sessionKey
    ) throws JSONException {

        logger.info("请求解密小程序用户敏感数据入口");

        try {
            String result = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");
            //判断返回参数是否为空
            if (null != result && result.length() > 0) {
                JSONObject userInfoJSON = JSON.parseObject(result);

                logger.info("请求解密敏感数据成功，解密数据：{}", userInfoJSON);
                return ResultBean.success("解密敏感数据成功", userInfoJSON);
            } else {

                logger.info("请求解密敏感数据失败，数据为空");
                return ResultBean.error("解密敏感数据失败，数据为空");
            }

        } catch (Exception e) {
            logger.info("请求解密敏感数据失败，{}", e.getMessage());
            return ResultBean.error("解密敏感数据失败");
        }

    }
}
