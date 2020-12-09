package com.gxcy.mina;

import java.net.InetSocketAddress;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MinaServer implements ServletContextListener, HttpSessionListener {
	private static NioSocketAcceptor acceptor;
	private static final int port = 10026;
	private static final Logger LOGGER = LoggerFactory.getLogger(MinaServer.class);

	// 停止MINA服务
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			MinaServer.acceptor.unbind();
			MinaServer.acceptor.dispose();
			LOGGER.info("Mina服务停止...");
		} catch (Exception e) {
			//logUtil.customLog(e);
		}
	}

	// 启动MINA服务
	public void contextInitialized(ServletContextEvent sce) {
		try {
			// 创建一个非堵塞的server端的Socket
			acceptor = new NioSocketAcceptor();
			// 设置过滤器
			acceptor.getFilterChain().addLast(
					"serverCodec",
					new ProtocolCodecFilter(
							new ObjectSerializationCodecFactory()));
			acceptor.getFilterChain().addLast("ServerFilter",
					new ExecutorFilter());
			// 设置读取数据的缓冲区大小
			acceptor.getSessionConfig().setReadBufferSize(1024*102400);
			// 读写通道10秒内无操作进入空暇状态
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			// 加入逻辑处理器
			acceptor.setHandler(new MinaServerHandler());
			// 绑定端口
			try {
				acceptor.bind(new InetSocketAddress(port));
			} catch (Exception e) {
			}
			LOGGER.info("服务端启动成功...     端口号为：" + port);
			//logUtil.customLog();
		} catch (Exception e) {
			LOGGER.info("服务端启动异常....");
			//logUtil.customLog();
		}

	}

	public void sessionCreated(HttpSessionEvent arg0) {
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
	}

}
