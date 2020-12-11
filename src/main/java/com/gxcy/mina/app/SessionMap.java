package com.gxcy.mina.app;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionMap {
	private final static Logger log = LoggerFactory.getLogger(SessionMap.class);
	
	private static SessionMap sessionMap = null;
			
	private Map<String, IoSession>map = new HashMap<String, IoSession>();
	
	
	//构造私有化 单例
	private SessionMap(){}
	
	
	/**
	 * @Description: 获取唯一实例
	 * @author whl
	 * @date 2014-9-29 下午1:29:33
	 */
	public static SessionMap newInstance(){
		log.debug("SessionMap单例获取---");
		if(sessionMap == null){
			sessionMap = new SessionMap();
		}
		return sessionMap;
	}
	
	
	/**
	 * @Description: 保存session会话
	 * @author whl
	 * @date 2014-9-29 下午1:31:05
	 */
	public void addSession(String key, IoSession session){
		log.debug("保存会话到SessionMap单例---key=" + key);
		this.map.put(key, session);
	}
	
	/**
	 * @Description: 根据key查找缓存的session
	 * @author whl
	 * @date 2014-9-29 下午1:31:55
	 */
	public IoSession getSession(String key){
		log.debug("获取会话从SessionMap单例---key=" + key);
		return this.map.get(key);
	}
	
	/**
	 * @Description: 发送消息到客户端
	 * @author whl
	 * @date 2014-9-29 下午1:57:51
	 */
	public void sendMessage(String[] keys, Object message){
		for(String key : keys){
			IoSession session = getSession(key);
			
			log.debug("反向发送消息到客户端Session---key=" + key + "----------消息=" + message);
			if(session == null){
				return;
			}
			session.write(message);
			
		}
	}
	
	/**
	 * @Description: 发送消息到客户端
	 * @author whl
	 * @date 2014-9-29 下午1:57:51
	 */
	public void sendMessage(String key, Object message){
		IoSession session = getSession(key);
		log.debug("反向发送消息到客户端Session---key=" + key + "----------消息=" + message);
		if(session == null)
			return;
		session.write(IoBuffer.wrap(message.toString().getBytes()));
			
	}
	
	
}
