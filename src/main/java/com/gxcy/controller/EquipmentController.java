package com.gxcy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/eqment")
public class EquipmentController {
    private static Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @RequestMapping("/queryEquipment")
    public Object queryEquipment( String longitude,String dimension){
        logger.info("开始获取当前位置的车衣经度{},纬度{}",longitude,dimension);
        List<Map<String,String>> eqMents = new ArrayList<>();
        for (int i = 0 ; i<10 ;i++){
            Map<String,String> map = new HashMap<>();
            map.put("longitude",String.valueOf(Integer.parseInt(longitude)+i));
            map.put("dimension",String.valueOf(Integer.parseInt(dimension)+i));
            eqMents.add(map);
        }
         return eqMents;
    }

}
