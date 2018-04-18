package cn.xyz.rpc.consumer.proxy;

import cn.xyz.rpc.core.msg.InvokerMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by fanchengwei on 2018/1/24.
 */
public class RpcProxy {
    public static <T> T create(Class<?> clazz){
        MethodProy methodProy = new MethodProy(clazz);
        T result = (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},methodProy);
        return result;
    }


}

class MethodProy implements InvocationHandler{
    private Class clazz;
    public MethodProy(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //如果传进来的是一个已经实现的具体的类，直接忽略
        if (Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this,args);
        }
        //如果传进来的是一个接口，则远程调用
        else {
            return rpcInvoke(method,args);
        }

    }
    public Object rpcInvoke(Method method, Object[] args){
        InvokerMsg msg = new InvokerMsg();
        msg.setClassName(this.clazz.getName());
        msg.setMethodName(method.getName());
        msg.setParames(method.getParameterTypes());
        msg.setValues(args);

        final RpcProxyHandler handler = new RpcProxyHandler();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //处理拆包、粘包的解码、编码器
                            pipeline.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                            pipeline.addLast("frameEncoder",new LengthFieldPrepender(4));

                            //处理序列化的解码、编码器（JDK默认的序列化）
                            pipeline.addLast("encode",new ObjectEncoder());
                            pipeline.addLast("decode",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            //自己的业务类
                            pipeline.addLast(handler);
                        }
                    });
            ChannelFuture future = bootstrap.connect("localhost",8080).sync();
            future.channel().writeAndFlush(msg).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

        return handler.getResult();
    }
}
