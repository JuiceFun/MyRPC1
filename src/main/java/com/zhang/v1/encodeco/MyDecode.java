package com.zhang.v1.encodeco;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor

public class MyDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //获取消息类型
        short msgType = byteBuf.readShort();
        if(msgType!=MessageType.REQUEST.getCode()&&msgType!=MessageType.RESPONSE.getCode()){
            System.out.println("不支持此数据类型");
            return;
        }
        //获取对应的序列化器
        short serializeType = byteBuf.readShort();
        Serializer serializer = Serializer.getSerializerByCode(serializeType);
        //获取长度
        int len = byteBuf.readInt();
        //序列化数据
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        Object o = serializer.deSerialize(bytes, msgType);
        list.add(o);

    }
}
