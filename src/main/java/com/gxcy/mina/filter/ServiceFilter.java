package com.gxcy.mina.filter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFilter extends IoFilterAdapter  {
	private static final Logger logger = LoggerFactory.getLogger(ServiceFilter.class);
	 
    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        logger.info("ClientFilter接收到客户端消息：" + message);
        // 传给下一个过滤器
        IoBuffer ioBuffer = (IoBuffer) message;

		byte[] b = new byte [ioBuffer.limit()];
		ioBuffer.get(b); 
		StringBuffer buffer = new StringBuffer();   
		for (int i = 0; i < b.length; i++) {   
		  buffer.append((char) b [i]);   
		}   
		
		String mess = buffer.toString();
        nextFilter.messageReceived(session, mess);
    }
 
    @Override
    public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        logger.info("ClientFilter接收到服务端消息：" + writeRequest.getMessage());
        // 传给下一个过滤器
        nextFilter.messageSent(session, writeRequest);
    }

}
