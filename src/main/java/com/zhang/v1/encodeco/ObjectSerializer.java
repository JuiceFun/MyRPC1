package com.zhang.v1.encodeco;

import java.io.*;

public class ObjectSerializer implements Serializer{
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes=null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    @Override
    public Object deSerialize(byte[] buf, int messageType) {
        Object obj=null;
        ByteArrayInputStream bis=new ByteArrayInputStream(buf);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            bis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getType() {
        return 0;
    }
}
