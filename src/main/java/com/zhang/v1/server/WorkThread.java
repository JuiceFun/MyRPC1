package com.zhang.v1.server;

import com.zhang.v1.common.RPCRequest;
import com.zhang.v1.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

@AllArgsConstructor
public class WorkThread implements Runnable{

    private Socket socket;
    private ServiceProvider serviceProvider;

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            RPCRequest request=(RPCRequest)ois.readObject();
            RPCResponse response = getResponse(request);
            oos.writeObject(response);
            oos.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("从IO中读取数据错误");
            e.printStackTrace();
        }
    }

    private RPCResponse getResponse(RPCRequest request){
        String interfaceName = request.getInterfaceName();
        Object service = serviceProvider.getService(interfaceName);
        try {
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("方法执行错误");
            return RPCResponse.fail();
        }


    }
}
