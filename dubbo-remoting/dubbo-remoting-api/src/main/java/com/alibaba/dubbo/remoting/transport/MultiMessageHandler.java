package com.alibaba.dubbo.remoting.transport;

import com.alibaba.dubbo.remoting.exchange.support.MultiMessage;
import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.remoting.ChannelHandler;
import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.exchange.support.MultiMessage;

import java.util.concurrent.ExecutorService;

/**
 * @author <a href="mailto:gang.lvg@alibaba-inc.com">kimi</a>
 * @see MultiMessage
 */
public class MultiMessageHandler extends AbstractChannelHandlerDelegate {

    private ExecutorService executor;

    public MultiMessageHandler(ChannelHandler handler, ExecutorService executor) {
        super(handler);
        this.executor = executor;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void received(Channel channel, Object message) throws RemotingException {
        if (message instanceof MultiMessage) {
            MultiMessage list = (MultiMessage)message;
            for(Object obj : list) {
                handler.received(channel, obj);
            }
        } else {
            handler.received(channel, message);
        }
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
