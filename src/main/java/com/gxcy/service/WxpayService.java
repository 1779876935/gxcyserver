package com.gxcy.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface WxpayService {

    Map<String,Object> wxlogin(String encryptedData, String iv,String code);
    Map<String,Object> saveGxUser();
    Map wxPay(String spbill_create_ip, String openId, String orderNumber);
}
