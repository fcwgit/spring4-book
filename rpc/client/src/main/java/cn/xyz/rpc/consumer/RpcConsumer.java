package cn.xyz.rpc.consumer;

import cn.xyz.rpc.api.IRpcHello;
import cn.xyz.rpc.consumer.proxy.RpcProxy;

/**
 * Created by fanchengwei on 2018/1/23.
 */
public class RpcConsumer {
    public static void main(String[] args) {
        IRpcHello hello = RpcProxy.create(IRpcHello.class);
        System.out.println(hello.hello("tom"));
    }
}
