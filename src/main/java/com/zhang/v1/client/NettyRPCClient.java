package com.zhang.v1.client;

import com.zhang.v1.common.RPCRequest;
import com.zhang.v1.common.RPCResponse;
import com.zhang.v1.register.ServiceRegister;
import com.zhang.v1.register.ZkServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import javax.print.attribute.Attribute;
import java.net.InetSocketAddress;

public class NettyRPCClient implements RPCClient{
    private String host;
    private int port;
    private static final Bootstrap bootStrap;
    private static final EventLoopGroup group;
    private ServiceRegister serviceRegister;
    public NettyRPCClient(){
        this.serviceRegister=new ZkServiceRegister();
    }

    static {
        group=new NioEventLoopGroup();
        bootStrap=new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
        host=address.getHostName();
        port=address.getPort();
        try {
            ChannelFuture channelFuture = bootStrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            // 发送数据
            channel.writeAndFlush(request);
            channel.closeFuture().sync();
            AttributeKey<Object> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = (RPCResponse)channel.attr(key).get();
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }


    }
}
