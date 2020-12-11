package com.gxcy.mina;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxcy.mina.app.HDecoder;
import com.gxcy.mina.app.SessionMap;

public class MinaServerHandler extends IoHandlerAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MinaServerHandler.class);
	private final static SessionMap sessionMap = SessionMap.newInstance();
	private int count = 0;
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		count++;
		sessionMap.addSession(count+"", session);
		LOGGER.info("第"+count+"个client与服务端创建连接...");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		LOGGER.info("服务端与client连接打开...");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		/*IoBuffer ioBuffer = (IoBuffer) message;

		byte[] b = new byte [ioBuffer.limit()];
		ioBuffer.get(b); 
		StringBuffer buffer = new StringBuffer();   
		for (int i = 0; i < b.length; i++) {   
		  buffer.append((char) b [i]);   
		}   
		
		String mess = buffer.toString();*/
		System.out.println(message.toString());
		TimeUnit.SECONDS.sleep(15);
		new Thread(() -> {
			for(int i=1;i<4;i++){
	    		sessionMap.sendMessage(i+"", "FFBB");
	    	}
		}).start();
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		LOGGER.info("服务端发送信息成功...");
		//logUtil.customLog("服务端发送信息成功...");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		LOGGER.info("服务端与client连接关闭...");
		session.closeNow();
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
		//logUtil.customLog("服务端进入空暇状态...");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		//logUtil.customLog("服务端发送异常..." + cause);
		 System.out.println("one client disconnect");
	     session.closeNow();
	}
	/*private int count = 0;
	 
    // 由底层决定是否创建一个session
    public void sessionCreated(IoSession session) {
        System.out.println("新客户连接");
    }
 
    // 创建了session 后会回调sessionOpened
    public void sessionOpened(IoSession session) throws Exception {
        count++;
        System.out.println("第 " + count + " 个 client 登陆！address： : "
                + session.getRemoteAddress());
 
        sessionWrite(session);
        messageSent(session,"成功连接");
    }
 
//    获取session连接，用来随时向客户端发送消息
    public void sessionWrite(IoSession session) throws Exception {
        session.write("Sent by Server1"+1);
        session.write("Sent by Server1"+2);
    }
 
    // 当收到了客户端发送的消息后会回调这个函数
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        System.out.println("服务器收到客户端发送指令 ：" );
        System.out.println(message);
 
//        session.write("Sent by Server1");
 
        //messageSent(session,"Sent by Server2");
    }
 
    public void messageSent(IoSession session, Object message) {
        System.out.println("message send to client");
 
        session.write("Sent by Server3");
    }
 
    // session 关闭调用
    public void sessionClosed(IoSession session) {
        System.out.println("one client disconnect");
        session.closeNow();
    }
 
    // session 空闲的时候调用
    public void sessionIdle(IoSession session, IdleStatus status) {
        System.out.println("connect idle");
    }
 
    // 异常捕捉
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("throws exception");
    }*/

}
