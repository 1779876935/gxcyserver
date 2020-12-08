package com.gxcy.config;

import com.gxcy.utils.PropertiesUtil;

import java.util.Properties;

public class PayConfig {
    //微信支付类型
    //NATIVE--原生支付
    //JSAPI--公众号支付-小程序支付
    //MWEB--H5支付
    //APP -- app支付
    public static final String TRADE_TYPE_NATIVE = "NATIVE";
    public static final String TRADE_TYPE_JSAPI = "JSAPI";
    public static final String TRADE_TYPE_MWEB = "MWEB";
    public static final String TRADE_TYPE_APP = "APP";

    //小程序支付参数
    public static String XCX_APP_ID;
    public static String XCX_MCH_ID;
    public static String XCX_KEY;

    //微信支付API
    public static final String WX_PAY_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //服务器域名
    public static final String  PRO_SERVER_DOMAIN = "";
    //参数
    static{
        Properties properties = new Properties();
        try {
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("payment_config.properties"));
            //xcx
            XCX_APP_ID=(String) properties.get("xcx.pay.appid");
            XCX_MCH_ID=(String) properties.get("xcx.pay.mchid");
            XCX_KEY=(String) properties.get("xcx.pay.key");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
