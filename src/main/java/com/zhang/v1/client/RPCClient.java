package com.zhang.v1.client;

import com.zhang.v1.common.RPCRequest;
import com.zhang.v1.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
