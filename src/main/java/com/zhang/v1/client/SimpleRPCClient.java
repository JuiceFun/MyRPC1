package com.zhang.v1.client;

import com.zhang.v1.common.RPCRequest;
import com.zhang.v1.common.RPCResponse;
import com.zhang.v1.register.ServiceRegister;
import com.zhang.v1.register.ZkServiceRegister;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SimpleRPCClient implements RPCClient{
   private String host;
    private int port;
    private ServiceRegister serviceRegister;
    public SimpleRPCClient(){
        this.serviceRegister=new ZkServiceRegister();
    }
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        String interfaceName = request.getInterfaceName();
        InetSocketAddress address = serviceRegister.serviceDiscovery(interfaceName);
        try {
            host=address.getHostName();
            port=address.getPort();
            System.out.println("开始连接"+host+":"+port);
            Socket socket = new Socket(host,port );
            System.out.println("连接成功");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(request);
            oos.flush();
            RPCResponse response = (RPCResponse)ois.readObject();
            return response;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }


    }
}
