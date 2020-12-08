package com.gxcy.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义响应结构
 */
public class WxResult implements Serializable{

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    // 响应业务状态
    public static Integer OK = 200;
    private Integer status;
    // 响应消息
    private String msg;
    // 响应中的数据
    private Object data;
    //数据
    private Map<String,Object> mapData;

    public WxResult() {

    }

    public WxResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public WxResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public static WxResult build(Integer status, String msg, Object data) {
        return new WxResult(status, msg, data);
    }

    public static WxResult ok(Object data) {
        return new WxResult(data);
    }

    public static WxResult ok() {
        return new WxResult(null);
    }

    public static WxResult okMap(Map<String,Object> mapData) {
        WxResult res = new WxResult(null);
        res.setMapData(mapData);
        return res;
    }

    public static WxResult fail() {
        return build(500,"失败");
    }

    public static WxResult fail(String msg) {
        return build(500,msg);
    }

    public static WxResult failObj(Object data) {
        return new WxResult(500,"失败",data);
    }

    public static WxResult build(Integer status, String msg) {
        return new WxResult(status, msg, null);
    }

    /**
     * 将json结果集转化为Result对象
     *
     * @param jsonData json数据
     * @param clazz Result中的object类型
     * @return
     */
    public static WxResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, WxResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static WxResult format(String json) {
        try {
            return MAPPER.readValue(json, WxResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static WxResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getMapData() {
        return mapData;
    }

    public WxResult setMapData(Map<String, Object> mapData) {
        this.mapData = mapData;
        return this;
    }

    @Override
    public String toString() {
        return "WxResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", mapData=" + mapData +
                '}';
    }
}
