package com.zhang.v1.encodeco;

import com.zhang.v1.common.RPCRequest;
import com.zhang.v1.common.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MyEncode extends MessageToByteEncoder {
    private Serializer serializer;
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        //写入消息类型
        if(o instanceof RPCRequest){
            byteBuf.writeShort(MessageType.REQUEST.getCode());
        }else if(o instanceof RPCResponse){
            byteBuf.writeShort(MessageType.RESPONSE.getCode());
        }else{
            System.out.println("数据类型错误");
            return;
        }
        //序列化方式
        byteBuf.writeShort(serializer.getType());
        //数据长度
        byte[] buf = serializer.serialize(o);
        byteBuf.writeInt(buf.length);
        //数据
        byteBuf.writeBytes(buf);
    }
}
