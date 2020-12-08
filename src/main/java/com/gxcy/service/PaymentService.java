package com.gxcy.service;

import java.util.Map;

public interface PaymentService {
    Map<String,String> xcxPayment(String orderNo, double money,String openId) throws Exception;

    public int addOrUpdatePaymentRecord(String orderNo, double payAmount) throws Exception;

    int xcxNotify(Map<String,Object> map) throws Exception;
}
