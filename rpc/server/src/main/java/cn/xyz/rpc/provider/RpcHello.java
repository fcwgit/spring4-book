package cn.xyz.rpc.provider;

import cn.xyz.rpc.api.IRpcHello;

/**
 * Created by fanchengwei on 2018/1/23.
 */
public class RpcHello implements IRpcHello {
    @Override
    public String hello(String name) {
        return "hello , " + name;
    }
}
