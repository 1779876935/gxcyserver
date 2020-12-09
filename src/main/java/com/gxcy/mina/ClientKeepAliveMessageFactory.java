package com.gxcy.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class ClientKeepAliveMessageFactory implements KeepAliveMessageFactory {

	@Override
	public boolean isRequest(IoSession session, Object message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isResponse(IoSession session, Object message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getRequest(IoSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getResponse(IoSession session, Object request) {
		// TODO Auto-generated method stub
		return null;
	}

}
