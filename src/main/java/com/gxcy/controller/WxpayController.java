package com.gxcy.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gxcy.service.WxpayService;
import com.gxcy.utils.AesCbcUtil;
import com.gxcy.utils.HttpRequestUtil;
import com.gxcy.utils.StringUtils;
import com.gxcy.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wxpay")
public class WxpayController {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(WxpayController.class);

    @Autowired
    private WxpayService wxpayService;

    /**
     * 登录
     * @param
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ResponseBody
    @GetMapping(value="/login")
    public Object login(String  encryptedData, String iv,String code) {
        logger.info("开始验证");
        Map map = new HashMap();
        // 登录凭证不能为空
        if (StringUtils.isBlank(code) || StringUtils.isBlank(encryptedData) || StringUtils.isBlank(iv)) {
            map.put("status", 0);
            map.put("msg", "参数不能为空");
            return map;
        }
        map = wxpayService.wxlogin(encryptedData,iv,code);
        return map;
    }


}
