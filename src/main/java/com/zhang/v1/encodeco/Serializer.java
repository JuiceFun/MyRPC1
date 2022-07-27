package com.zhang.v1.encodeco;

public interface Serializer {
    byte[] serialize(Object obj);

    Object deSerialize(byte[] buf,int messageType);

    int getType();

    static Serializer getSerializerByCode(int code){
        switch (code){
            case 0:
                return new ObjectSerializer();
            case 1:
                return  new JsonSerializer();
            default:
                return null;
        }
    }
}
