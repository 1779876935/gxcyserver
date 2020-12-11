package com.gxcy.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
 
/**
 * Created by chenlong on 2016/8/29.
 */
public class MinaClient {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 创建Socket
        NioSocketConnector connector = new NioSocketConnector();
        //设置传输方式
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.WINDOWS.getValue(), 
                        LineDelimiter.WINDOWS.getValue()))
                );
 
        //设置消息处理
        connector.setHandler(new MinaClientHandler());
        //超时设置
        connector.setConnectTimeoutCheckInterval(30);
        //连接
        ConnectFuture cf = connector.connect(new InetSocketAddress("localhost", 10026));
        cf.awaitUninterruptibly();
        cf.getSession().getCloseFuture().awaitUninterruptibly();
 
        connector.dispose();
 
    }
}