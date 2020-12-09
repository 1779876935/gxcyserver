package com.gxcy.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxcy.mina.filter.LineCodecDecoder;
import com.gxcy.mina.filter.ServiceFilter;

public class MinaServerTest {
	private static Logger logger = LoggerFactory.getLogger(MinaServerTest.class); //日志  
    private static final int port = 10026;   //端口  
  
    public void MinaServerTest(String[] args) {  
        // 创建一个非阻塞的server端的socket  
        IoAcceptor acceptor = new NioSocketAcceptor();  
        ClientKeepAliveMessageFactory heartBeatFactory = new ClientKeepAliveMessageFactory();
	    // 当读操作空闲时发送心跳
	    KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory, IdleStatus.READER_IDLE);
	    // 设置是否将事件继续往下传递
	    heartBeat.setForwardEvent(true);
	    // 设置心跳包请求后超时无反馈情况下的处理机制，默认为关闭连接,在此处设置为输出日志提醒
	    heartBeat.setRequestTimeoutHandler(KeepAliveRequestTimeoutHandler.LOG);
	    //设置心跳频率
	    heartBeat.setRequestInterval(180);
	    acceptor.getFilterChain().addFirst("filter", new ServiceFilter()); 
	    acceptor.getFilterChain().addAfter("filter","keepAlive", heartBeat);
        // 设置过滤器,选用Mina自带的过滤器一行一行读取代码  
        //acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));  
	    //acceptor.getFilterChain().addLast("objectFilter",new ProtocolCodecFilter(new LineCodecDecoder()));
	    // 设置过滤器
	    // 设置读取数据的缓冲区大小   
        acceptor.getSessionConfig().setReadBufferSize(2048);  
        // 读写通道10秒内无操作进入空闲状态   
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,10);  
        // 绑定逻辑处理器  
        acceptor.setHandler(new MinaServerHandler());  
        // 绑定端口,启动服务器  
        try {  
            acceptor.bind(new InetSocketAddress(port));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        logger.info("服务已启动，端口是:"+port);  
    }
}
