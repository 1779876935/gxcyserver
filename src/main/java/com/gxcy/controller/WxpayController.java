package com.gxcy.controller;


import com.alibaba.fastjson.JSONObject;
import com.gxcy.common.ParamException;
import com.gxcy.common.WxResult;
import com.gxcy.service.PaymentService;
import com.gxcy.service.WxpayService;
import com.gxcy.utils.*;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wxpay")
public class WxpayController {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(WxpayController.class);

    @Autowired
    private WxpayService wxpayService;

    @Autowired
    private PaymentService paymentService;


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
/*
    @RequestMapping(value = "pay",method = RequestMethod.POST)
    @ResponseBody
    public Object pay(HttpServletRequest request){
        try {
            String openid = request.getParameter("openid");
            String eqOrderId = request.getParameter("eqOrderId");
            String requestStr = getHttpServletRequestToString(request);
            if (StringUtils.isEmpty(requestStr)) {
                throw new ParamException();
            }
            JSONObject jsonObj = JSONObject.parseObject(requestStr);
            if(StringUtils.isEmpty(jsonObj.getString("orderNo")) || StringUtils.isEmpty(jsonObj.getString("openId"))){
                throw new ParamException();
            }
            Object orderInfo = .....//此处写获取订单信息方法
            if(orderInfo == null){
                return WxResult.fail("订单不存在！");
            }else if(orderInfo.getPayAmount() == null || orderInfo.getPayAmount() <= 0){
                return WxResult.fail("订单有误，请确认！");
            }else if(orderInfo.getOrderStatus() != 1){//1待付款
                String msg = orderInfo.getOrderStatus() >1 ?"此订单已支付！":"订单未提交，请确认！";
                return WxResult.fail(msg);
            }else{
                logger.info("【小程序支付服务】请求订单编号:["+orderInfo.getOrderNo()+"]");
                Map<String, String> resMap = paymentService.xcxPayment(+orderInfo.getOrderNo(),orderInfo.getPayAmount(),jsonObj.getString("openId"));
                if("SUCCESS".equals(resMap.get("returnCode")) && "OK".equals(resMap.get("returnMsg"))){
                    //统一下单成功
                    resMap.remove("returnCode");
                    resMap.remove("returnMsg");
                    logger.info("【小程序支付服务】支付下单成功！");
                    return WxResult.ok(resMap);
                }else{
                    logger.info("【小程序支付服务】支付下单失败！原因:"+resMap.get("returnMsg"));
                    return  WxResult.fail(resMap.get("returnMsg"));
                }
            }
        }catch (Exception e){
            logger.info("【小程序支付服务】支付下单失败!");
            return WxResult.fail("【小程序支付服务】支付下单失败!");
        }
    }*/


    /**
     * <p>回调Api</p>
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="xcxNotify")
    public void xcxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream =  request.getInputStream();
        //获取请求输入流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len=inputStream.read(buffer))!=-1){
            outputStream.write(buffer,0,len);
        }
        outputStream.close();
        inputStream.close();
        Map<String,Object> map = PayUtil.getMapFromXML(new String(outputStream.toByteArray(),"utf-8"));
        logger.info("【小程序支付回调】 回调数据： \n"+map);
        String resXml = "";
        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equalsIgnoreCase(returnCode)) {
            String returnmsg = (String) map.get("result_code");
            if("SUCCESS".equals(returnmsg)){
                //更新数据
                int result = paymentService.xcxNotify(map);
                if(result > 0){
                    //支付成功
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>"+"</xml>";
                }
            }else{
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]></return_msg>" + "</xml>";
                logger.info("支付失败:"+resXml);
            }
        }else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]></return_msg>" + "</xml>";
            logger.info("【订单支付失败】");
        }

        logger.info("【小程序支付回调响应】 响应内容：\n"+resXml);
        response.getWriter().print(resXml);
    }
    public static String getHttpServletRequestToString(HttpServletRequest request) throws Exception{
//StreamUtils为springframework包的工具类
        return StreamUtils.copyToString(request.getInputStream(), Charset.forName(Charsets.UTF_8.name()));
    }

}
