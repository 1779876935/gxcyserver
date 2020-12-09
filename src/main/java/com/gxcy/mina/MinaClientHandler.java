package com.gxcy.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MinaClientHandler extends IoHandlerAdapter{
	public void sessionOpened(IoSession session) throws Exception {
        System.out.println("客户端登陆");
        session.write("HelloWorld");
 
//        messageReceived(session,"");
        for (int i = 0; i < 10; i++) {
            session.write("你是一個大沙壩");
        }
    }
 
    public void sessionClosed(IoSession session)
    {
        System.out.println("client close");
    }
 
    public void messageReceived(IoSession session , Object message)throws Exception
    {
        System.out.println("客户端接受到了消息"+message) ;
 
//        session.write("Sent by Client1");
    }

}
