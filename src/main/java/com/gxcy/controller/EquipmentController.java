package com.gxcy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/eqment")
public class EquipmentController {
    private static Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @RequestMapping("/queryEquipment")
    public Object queryEquipment(String  openID, String longitude,String dimension){
        logger.info("开始获取当前位置的车衣{},{},{}",openID,longitude,dimension);

        return null;
    }

}
