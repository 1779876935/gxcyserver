package com.gxcy.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaServerHandler extends IoHandlerAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MinaServerHandler.class);

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		LOGGER.info("服务端与client创建连接...");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		LOGGER.info("服务端与client连接打开...");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println(message.toString());
		session.write(message);

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		LOGGER.info("服务端发送信息成功...");
		//logUtil.customLog("服务端发送信息成功...");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		//logUtil.customLog("服务端与client连接关闭...");
		session.close(true);
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
		session.close(true);
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
