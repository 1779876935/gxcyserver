package com.gxcy.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
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
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        ProtocolCodecFilter filter = new ProtocolCodecFilter(new ObjectSerializationCodecFactory());
        chain.addLast("objectFilter", filter);
 
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