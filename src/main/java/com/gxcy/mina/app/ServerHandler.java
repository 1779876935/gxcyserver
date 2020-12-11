package com.gxcy.mina.app;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends IoHandlerAdapter{
	private final static Logger log = LoggerFactory.getLogger(ServerHandler.class);
	private final static SessionMap sessionMap = SessionMap.newInstance();
	
	 private ServerHandler() {
	  // TODO Auto-generated constructor stub
	 }
	 @Override
	 public void exceptionCaught(IoSession session, Throwable cause)
	   throws Exception {
	 }
	 
	 
	 @Override
	 public void messageReceived(IoSession session, Object message)
	   throws Exception {
		 log.debug("服务端收到信息-------------");
		
		  //获取客户端发过来的key
		  String key = message.toString();
		  System.out.println("message :"+message.toString());
		  String carPark_id = key.substring(key.indexOf("=") + 1);
		  System.out.println("carPark_id :"+carPark_id);
		  

		  //保存客户端的会话session
		  SessionMap sessionMap = SessionMap.newInstance();
		  sessionMap.addSession(carPark_id, session);
	  
	 }
	 @Override
	 public void messageSent(IoSession session, Object message) throws Exception {
		 log.debug("------------服务端发消息到客户端---");
	 }
	 @Override
	 public void sessionClosed(IoSession session) throws Exception {
	  // TODO Auto-generated method stub
		 log.debug("远程session关闭了一个..." + session.getRemoteAddress().toString());
	 }
	 @Override
	 public void sessionCreated(IoSession session) throws Exception {
		 log.debug(session.getRemoteAddress().toString() +"----------------------create");
	 }
	 @Override
	 public void sessionIdle(IoSession session, IdleStatus status)
	   throws Exception {
		 log.debug(session.getServiceAddress() +"IDS");
	 }
	 @Override
	 public void sessionOpened(IoSession session) throws Exception {
		 log.debug("连接打开："+session.getLocalAddress());
	 }
	 
}
